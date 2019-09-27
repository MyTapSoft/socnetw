package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
@EnableTransactionManagement
public class UserRepository extends CrudRepository<User> {

    private static final String GET_DUPLICATE_USER = "SELECT * FROM USERS " +
            "WHERE EMAIL = :userMail OR PHONE_NUMBER = :phoneNumber";
    private static final String GET_USER = "SELECT * FROM USERS " +
            "WHERE USER_NAME = :login AND PASSWORD = :password";
    private static final String GET_ALL_USERS = "SELECT * FROM USERS";
    private static final String GET_USER_FRIENDS = "SELECT DISTINCT U.*\n" +
            " FROM USERS U\n" +
            "         JOIN RELATIONSHIP R on (U.USER_ID = R.USER_ID_FROM OR U.USER_ID = R.USER_ID_TO)\n" +
            " WHERE (R.USER_ID_FROM = :userId OR R.USER_ID_TO = :userId)\n" +
            "    AND R.STATUS = :status\n" +
            "    AND U.USER_ID != :userId";
    private static final String INCOME_USER_REQUESTS = "SELECT U.*\n" +
            " FROM USERS U\n" +
            "         JOIN RELATIONSHIP R on U.USER_ID = R.USER_ID_TO\n" +
            " WHERE R.USER_ID_TO = :userId\n" +
            "  AND R.STATUS = :status";
    private static final String OUTCOME_USER_REQUESTS = "SELECT U.*\n" +
            "FROM USERS U\n" +
            "         JOIN RELATIONSHIP R on U.USER_ID = R.USER_ID_FROM\n" +
            "WHERE R.USER_ID_TO = :userId\n" +
            "  AND R.STATUS = :status;";
    @PersistenceContext
    private EntityManager entityManager;

    public User findByLoginAndPswd(String login, String password) {
        try {
            return (User) entityManager.createNativeQuery(GET_USER, User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException noResult) {
            return null;
        }
    }

    public List<User> findAll() {
        return entityManager.createNamedQuery(GET_ALL_USERS, User.class)
                .getResultList();
    }

    public List<User> findFriendById(Long userId) {
        List<User> list = entityManager.createNativeQuery(GET_USER_FRIENDS, User.class)
                .setParameter("userId", userId)
                .setParameter("status", "accepted")
                .getResultList();
        return list;
    }

    public User checkDuplicate(String userMail, String phoneNumber) {
        try {
            return (User) entityManager.createNativeQuery(
                    GET_DUPLICATE_USER, User.class)
                    .setParameter("userMail", userMail)
                    .setParameter("phoneNumber", phoneNumber)
                    .getSingleResult();
        } catch (NoResultException empty) {
            return null;
        }
    }

    public List<User> findIncomeRequests(String userId) {
        return entityManager.createNativeQuery(INCOME_USER_REQUESTS, User.class)
                .setParameter("userId", userId)
                .setParameter("status", "pending")
                .getResultList();
    }

    public List<User> findOutcomeRequests(String userId) {
        return entityManager.createNativeQuery(OUTCOME_USER_REQUESTS, User.class)
                .setParameter("userId", userId)
                .setParameter("status", "pending")
                .getResultList();

    }
}
