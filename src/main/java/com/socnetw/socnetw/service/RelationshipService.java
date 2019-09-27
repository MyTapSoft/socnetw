package com.socnetw.socnetw.service;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.DuplicateException;
import com.socnetw.socnetw.exceptions.InternalServerException;
import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.model.Relationship;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.repository.RelationshipRepository;
import com.socnetw.socnetw.service.Validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RelationshipService {
    private final RelationshipRepository dao;

    @Autowired
    public RelationshipService(RelationshipRepository dao) {
        this.dao = dao;
    }

    public Relationship save(String userIdFrom, String userIdTo) throws BadRequestException {
        Long from = Long.parseLong(userIdFrom);
        Long to = Long.parseLong(userIdTo);
        Relationship relationship = dao.findByIds(from, to);

        if (relationship != null && (relationship.getStatus() == RelationshipStatus.DELETED || relationship.getStatus() == RelationshipStatus.CANCELED))
            return update(userIdFrom, userIdTo, RelationshipStatus.PENDING);
        else if (relationship != null) throw new DuplicateException("Relationship Already Exist");
        if (dao.findRequestAmount(from) > 10) throw new BadRequestException("Request amount limit");

        relationship = new Relationship();
        relationship.setUserIdFrom(from);
        relationship.setUserIdTo(to);
        relationship.setStatus(RelationshipStatus.PENDING);
        relationship.setFriendsRequestDate(LocalDate.now());
        return dao.save(relationship);
    }

    public Relationship update(String userIdFrom, String userIdTo, RelationshipStatus status) throws BadRequestException {
        Relationship relationship = validate(status, userIdFrom, userIdTo);
        relationship.setStatus(status);
        relationship.setFriendsRequestDate(LocalDate.now());
        return dao.update(relationship);

    }

    private Relationship findByIds(String userIdFrom, String userIdTo) {
        return dao.findByIds(Long.parseLong(userIdFrom), Long.parseLong(userIdTo));
    }

    private Long getFriendsAmount(Long userId) {
        return dao.findFriendsAmount(userId);
    }

    private Long getRequestAmount(Long userId) {
        return dao.findRequestAmount(userId);
    }

    private LocalDate getFriendRequestDate(Long userIdFrom, Long userIdTo) {
        LocalDate friendsRequestDate = dao.findFriendRequestDate(userIdFrom, userIdTo);
        if (friendsRequestDate == null)
            throw new InternalServerException("Date not found. ID from: " + userIdFrom + " ID to: " + userIdTo);
        return friendsRequestDate;
    }

    private Relationship validate(RelationshipStatus desiredStatus, String userIdFrom, String userIdTo) throws BadRequestException {
        Long from = Long.valueOf(userIdFrom);
        Long to = Long.valueOf(userIdTo);
        if (from.equals(to)) throw new BadRequestException("IDs Are Same");

        Optional<Relationship> relationship = Optional.ofNullable(findByIds(userIdFrom, userIdTo));
        if (relationship.isEmpty())
            throw new NotFoundException("Exception between user: " + userIdFrom + " and user: " + userIdTo + " not found");


        if (relationship.get().getStatus() == desiredStatus)
            throw new DuplicateException("Status Already Established between users with ID: " + from + " ID: " + to);

        Chain chain, chain1, chain2, chain3, chain4, chain5, chain6, chain7;
        chain = new FriendsAmountChain(RelationshipStatus.ACCEPTED, getFriendsAmount(from));
        chain1 = chain.setNextChain(new RequestDateChain(RelationshipStatus.DELETED, getFriendRequestDate(from, to)));
        chain2 = chain1.setNextChain(new RequestAmountChain(RelationshipStatus.PENDING, getRequestAmount(from)));
        chain3 = chain2.setNextChain(new RelationshipStatusAcceptedChain(RelationshipStatus.ACCEPTED, relationship.get().getStatus()));
        chain4 = chain3.setNextChain(new RelationshipStatusCancelChain(RelationshipStatus.CANCELED, relationship.get().getStatus()));
        chain5 = chain4.setNextChain(new RelationshipStatusDeclineChain(RelationshipStatus.DECLINE, relationship.get().getStatus()));
        chain6 = chain5.setNextChain(new RelationshipStatusDeleteChain(RelationshipStatus.DELETED, relationship.get().getStatus()));
        chain7 = chain6.setNextChain(new RelationshipStatusPendingChain(RelationshipStatus.PENDING, relationship.get().getStatus()));
        chain.validate(desiredStatus);
        return relationship.get();
    }
}
