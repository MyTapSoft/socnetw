package com.socnetw.socnetw.service;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.model.Message;
import com.socnetw.socnetw.model.Relationship;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.repository.MessageRepository;
import com.socnetw.socnetw.repository.RelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, RelationshipRepository relationshipRepository) {
        this.messageRepository = messageRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public Message send(Message message) throws BadRequestException {
        if (message.getText().length() > 140)
            throw new BadRequestException("Message text is to long. Max length 140 characters");
        Relationship returnedRelationship = relationshipRepository.findByIds(message.getUserFrom().getId(), message.getUserTo().getId());
        if (returnedRelationship == null || returnedRelationship.getStatus() != RelationshipStatus.ACCEPTED)
            throw new BadRequestException("You can send message only to your friends");
        return messageRepository.save(message);
    }

    public Message update(Message message) throws BadRequestException {
        isRead(message);
        return messageRepository.update(message);
    }

    public void delete(Long id) throws BadRequestException {
        isRead(id);
        messageRepository.delete(id, Message.class);

    }

    public Message findById(Long id) throws BadRequestException {
        Message result = messageRepository.findById(id, Message.class);
        if (result == null) throw new BadRequestException("There is no message with ID: " + id);
        return result;
    }

    public Date findDateRead(Long messageId) {
        return messageRepository.findDateRead(messageId);
    }

    private void isRead(Long id) throws BadRequestException {
        if (findDateRead(id) != null) {
            throw new BadRequestException("Message has been read. You can't modify it.");
        }
    }
    private void isRead(Message message) throws BadRequestException {
        if (message.getDateRead() != null) {
            throw new BadRequestException("Message has been read. You can't modify it.");
        }
    }
}
