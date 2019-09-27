package com.socnetw.socnetw.service.Validator;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.RelationshipStatus;

public class FriendsAmountChain extends Chain {
    private Long friendsAmount;

    public FriendsAmountChain(RelationshipStatus status, Long friendsAmount) {
        this.certainChainStatus = status;
        this.friendsAmount = friendsAmount;

    }

    @Override
    public void check() throws BadRequestException {
        if (friendsAmount >= 100) throw new BadRequestException("Friends Query Limit");
    }
}
