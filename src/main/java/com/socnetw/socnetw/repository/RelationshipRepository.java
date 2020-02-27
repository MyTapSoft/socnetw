package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Relationship;
import com.socnetw.socnetw.model.RelationshipPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface RelationshipRepository extends CrudRepository<Relationship, RelationshipPK> {

    @Query(value = "SELECT R.* FROM RELATIONSHIP R " +
            "WHERE (R.USER_ID_TO = :userId OR R.USER_ID_FROM = :userId) " +
            "AND STATUS = 'accepted'", nativeQuery = true)
    Long findAmountOfFriends(@Param("userId") Long userId);

    @Query(value = "SELECT R.* FROM RELATIONSHIP R " +
            "WHERE (R.USER_ID_TO = :userId OR R.USER_ID_FROM = :userId) " +
            "AND STATUS = 'pending'", nativeQuery = true)
    Long findFriendsRequestAmount(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM RELATIONSHIP " +
            "WHERE STATUS = :status " +
            "AND (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo) " +
            "OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)", nativeQuery = true)
    LocalDate findFriendRequestDate(@Param("idOne") Long userIdFrom, @Param("idTwo") Long userIdTo);

    @Query(value = "SELECT * FROM RELATIONSHIP " +
            "WHERE (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo) " +
            "OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)", nativeQuery = true)
    Relationship findByIds(@Param("idOne") Long userOne, @Param("idTwo") Long userTwo);

    @Query(value = "DELETE FROM RELATIONSHIP " +
            "WHERE (USER_ID_FROM = :idOne AND USER_ID_TO = :idTwo) " +
            "OR (USER_ID_FROM = :idTwo AND USER_ID_TO = :idOne)", nativeQuery = true)
    void deleteByIds(@Param("idOne") Long userOne, @Param("idTwo") Long userTwo);
}
