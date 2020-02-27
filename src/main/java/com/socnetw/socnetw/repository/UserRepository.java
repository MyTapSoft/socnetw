package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users u " +
            "WHERE u.username = :login " +
            "AND u.password = :password", nativeQuery = true)
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT DISTINCT u.* FROM users U " +
            "JOIN relationship r on (u.user_id = r.user_id_from OR u.user_id = r.user_id_to) " +
            "WHERE (r.user_id_from = :userId OR r.user_id_to = :userId) " +
            "AND r.status = :statusAND u.user_id != :userId", nativeQuery = true)
    List<User> findFriendById(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM users WHERE email = :userMail OR phone_number221 = :phoneNumber", nativeQuery = true)
    User isEmailOrPhoneExist(@Param("userMail") String userMail, @Param("phoneNumber") String phoneNumber);

    @Query(value = "SELECT u.* FROM users u " +
            "JOIN relationship r on u.user_id = r.user_id_to" +
            " WHERE r.user_id_to = :userId " +
            "AND r.status = 'pending'", nativeQuery = true)
    List<User> findIncomeRequests(@Param("userId") String userId);

    @Query(value = "SELECT u.* FROM users u " +
            "JOIN relationship r on u.user_id = r.user_id_from " +
            "WHERE r.user_id_to = :userId " +
            "AND r.status = 'pending'", nativeQuery = true)
    List<User> findOutcomeRequests(@Param("userId") String userId);

    @Query(value = "SELECT * FROM users u " +
            "WHERE u.username = :username GROUP BY u.username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);
}
