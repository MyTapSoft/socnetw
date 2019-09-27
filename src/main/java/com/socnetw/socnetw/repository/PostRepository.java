package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
@EnableTransactionManagement
public class PostRepository extends CrudRepository<Post> {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_USER_POSTS = "SELECT * FROM POST WHERE USER_POSTED = :userId";
    private static final String GET_ALL_POSTS = "SELECT * FROM POST";
    private static final String GET_USER_AND_FRIENDS_POSTS_BY_USER_PAGE = "SELECT P.*\n" +
            " FROM POST P\n" +
            "         JOIN USERS U ON P.USER_POSTED = U.USER_ID\n" +
            " WHERE P.USER_PAGE_POSTED = :userId\n" +
            " ORDER BY P.DATE_POSTED";
    private static final String GET_FRIENDS_POSTS_BY_USER_PAGE = "SELECT P.*\n" +
            " FROM POST P\n" +
            "    JOIN USERS U ON P.USER_POSTED = U.USER_ID\n" +
            " WHERE P.USER_PAGE_POSTED = :userId\n" +
            "    AND P.USER_POSTED != :userId";
    private static final String GET_FEED = "SELECT p.*\n" +
            " FROM POST p\n" +
            " WHERE p.USER_POSTED IN (SELECT U.USER_ID\n" +
            "                        FROM USERS U\n" +
            "                                 JOIN RELATIONSHIP R\n" +
            "                                      ON (U.USER_ID = R.USER_ID_FROM OR U.USER_ID = R.USER_ID_TO)\n" +
            "                                          AND U.USER_ID != :userId\n" +
            "                        WHERE R.STATUS = :status\n" +
            "                          AND (R.USER_ID_TO = :userId OR R.USER_ID_FROM = :userId))\n" +
            " ORDER BY P.DATE_POSTED DESC \n" +
            "    OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY;\n";

    private static final int LIMIT = 10;
    private static final Short OFFSET = 0;

    public List<Post> findUserAndFriendsPosts(Long userId) {
        List<Post> list = entityManager.createNativeQuery(GET_USER_AND_FRIENDS_POSTS_BY_USER_PAGE, Post.class)
                .setParameter("userId", userId)
                .getResultList();
        return list;
    }

    public List<Post> findFriendsPosts(Long userId) {
        List<Post> list = entityManager.createNativeQuery(GET_FRIENDS_POSTS_BY_USER_PAGE, Post.class)
                .setParameter("userId", userId)
                .getResultList();
        return list;
    }

    public List<Post> findAll() {
        List<Post> list = entityManager.createNativeQuery(GET_ALL_POSTS, Post.class)
                .getResultList();
        return list;
    }

    public List<Post> findUserPosts(Long userId) {
        List<Post> list = entityManager.createNativeQuery(GET_USER_POSTS, Post.class)
                .setParameter("userId", userId)
                .getResultList();
        return list;
    }

    public List<Post> findFeed(Long userId) {
        List<Post> list = entityManager.createNativeQuery(GET_FEED, Post.class)
                .setParameter("userId", userId)
                .setParameter("offset", OFFSET)
                .setParameter("limit", LIMIT)
                .setParameter("status", "accepted")
                .getResultList();
        return list;
    }
}
