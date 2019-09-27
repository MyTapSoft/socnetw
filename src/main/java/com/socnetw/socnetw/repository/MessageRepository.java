package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Message;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
@EnableTransactionManagement
public class MessageRepository extends CrudRepository<Message> {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_DATE_READ = "SELECT M.DATE_READ FROM  MESSAGE M WHERE M.MESSAGE_ID = :messageId";

    public Date findDateRead(Long messageId) {
        return (Date) entityManager.createNativeQuery(GET_DATE_READ)
                .setParameter("messageId", messageId)
                .getSingleResult();
    }

}
