package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Relationship;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@Repository
@Transactional
@EnableTransactionManagement
public class RelationshipRepository {

    private static final String GET_RELATIONSHIP_BY_IDs = "SELECT * \n" +
            " FROM RELATIONSHIP\n" +
            " WHERE (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo)\n" +
            "  OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)";
    private static final String DELETE_RELATIONSHIP_BY_IDs = "DELETE\n" +
            " FROM RELATIONSHIP\n" +
            " WHERE (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo)\n" +
            "   OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)";
    private static final String GET_RELATIONSHIP_BY_STATUS = "SELECT R.*\n" +
            " FROM RELATIONSHIP R\n" +
            " WHERE (R.USER_ID_TO = :userId OR R.USER_ID_FROM = :userId)\n" +
            "  AND STATUS = :status";
    private static final String GET_RELATIONSHIP_BY_IDs_AND_STATUS = "SELECT *\n" +
            " FROM RELATIONSHIP\n" +
            " WHERE STATUS = :status\n" +
            "    AND (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo)\n" +
            "   OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)";
    @PersistenceContext
    private EntityManager entityManager;

    public Relationship save(Relationship relationship) {
        entityManager.persist(relationship);
        return relationship;
    }

    public Relationship update(Relationship relationship) {
        entityManager.merge(relationship);
        return relationship;
    }

    public void delete(Long userOne, Long userTwo) {
        entityManager.createNativeQuery(DELETE_RELATIONSHIP_BY_IDs, Relationship.class)
                .setParameter("idOne", userOne)
                .setParameter("idTwo", userTwo)
                .executeUpdate();
    }

    public Relationship findByIds(Long userOne, Long userTwo) {
        try {
            return (Relationship) entityManager.createNativeQuery(GET_RELATIONSHIP_BY_IDs, Relationship.class)
                    .setParameter("idOne", userOne)
                    .setParameter("idTwo", userTwo)
                    .getSingleResult();
        } catch (NoResultException noResultExc) {
            return null;
        }

    }

    public Long findFriendsAmount(Long userId) {
        return (long) entityManager.createNativeQuery(GET_RELATIONSHIP_BY_STATUS, Relationship.class)
                .setParameter("userId", userId)
                .setParameter("status", "accepted")
                .getResultList().size();
    }

    public Long findRequestAmount(Long userId) {
        return (long) entityManager.createNativeQuery(GET_RELATIONSHIP_BY_STATUS, Relationship.class)
                .setParameter("userId", userId)
                .setParameter("status", "pending")
                .getResultList().size();
    }

    public LocalDate findFriendRequestDate(Long userIdFrom, Long userIdTo) {
        try {
            Relationship result = (Relationship) entityManager.createNativeQuery(GET_RELATIONSHIP_BY_IDs_AND_STATUS, Relationship.class)
                    .setParameter("idOne", userIdFrom)
                    .setParameter("idTwo", userIdTo)
                    .setParameter("status", "accepted")
                    .getSingleResult();
            return result.getFriendsRequestDate();
        } catch (NoResultException noResult) {
            return null;
        }

    }
}
