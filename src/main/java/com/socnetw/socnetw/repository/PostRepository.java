package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query(value = "SELECT P.* FROM POST P " +
            "JOIN USERS U ON P.USER_POSTED = U.USER_ID " +
            "WHERE P.USER_PAGE_POSTED = :userId ORDER BY P.DATE_POSTED", nativeQuery = true)
    List<Post> findUserAndFriendsPosts(@Param("userId")Long userId);

    @Query(value = "SELECT P.* FROM POST P " +
            "JOIN USERS U ON P.USER_POSTED = U.USER_ID " +
            "WHERE P.USER_PAGE_POSTED = :userId " +
            "AND P.USER_POSTED != :userId", nativeQuery = true)
    List<Post> findFriendsPosts(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM POST WHERE USER_POSTED = :userId", nativeQuery = true)
    List<Post> findUserPosts(@Param("userId") Long userId);

    @Query(value = "SELECT p.* FROM POST p " +
            "WHERE p.USER_POSTED " +
            "IN (SELECT U.USER_ID FROM USERS U JOIN RELATIONSHIP R ON (U.USER_ID = R.USER_ID_FROM OR U.USER_ID = R.USER_ID_TO)" +
            " AND U.USER_ID != :userId WHERE R.STATUS = :status AND (R.USER_ID_TO = :userId OR R.USER_ID_FROM = :userId)) " +
            "ORDER BY P.DATE_POSTED DESC OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
    List<Post> findFeed(@Param("userId")Long userId);
}
