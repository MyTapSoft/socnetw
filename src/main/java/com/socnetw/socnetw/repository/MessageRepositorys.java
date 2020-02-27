package com.socnetw.socnetw.repository;

import com.socnetw.socnetw.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MessageRepositorys extends CrudRepository<Message, Long> {

    @Query(value = "SELECT M.DATE_READ FROM MESSAGE M" +
            " WHERE M.MESSAGE_ID = :messageId", nativeQuery = true)
     Date findDateRead(@Param("messageId") Long messageId);
}
