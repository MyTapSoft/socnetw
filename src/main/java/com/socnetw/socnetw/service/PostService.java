package com.socnetw.socnetw.service;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.model.Post;
import com.socnetw.socnetw.model.Relationship;
import com.socnetw.socnetw.model.RelationshipStatus;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.repository.PostRepository;
import com.socnetw.socnetw.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService {

    private final PostRepository dao;
    private final RelationshipRepository relationshipRepository;

    @Autowired
    public PostService(PostRepository dao, RelationshipRepository relationshipRepository) {
        this.dao = dao;
        this.relationshipRepository = relationshipRepository;
    }

    public Post save(Post post) throws BadRequestException {
        validation(post);
        return dao.save(post);
    }

    public void delete(Long id) throws BadRequestException {
        Optional<Post> optionalPost = dao.findById(id);
        if (optionalPost.isEmpty()) throw new NotFoundException("Post doesn't exist");
        if (!optionalPost.get().getId().equals(id)) throw new BadRequestException("You can't modify this post");
        dao.deleteById(id);
    }

    public Post update(Post post) throws BadRequestException {
//        validation(post);
//        return dao.update(post);
        return new Post();
    }

    public Post findById(Long id) {
        Optional<Post> optionalPost = dao.findById(id);
        return optionalPost.orElseThrow(() -> new NotFoundException(String.format("Post with ID: %d not found", id)));

    }

    public List<Post> findByUserId(Long id) {
        return dao.findUserPosts(id);
    }

    public List<Post> findUserAndFriendsPosts(Long id) {
        return dao.findUserAndFriendsPosts(id);
    }

    public List<Post> findFriendsPosts(Long id) {
        return dao.findFriendsPosts(id);
    }

    public List<Post> findAll() {
        return StreamSupport.stream(dao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Post> feed(Long userId) {
        return dao.findFeed(userId);
    }


    private void validation(Post post) throws BadRequestException {
        User userPosted = post.getUserPosted();
        User userPagePosted = post.getUserPagePosted();
        Relationship isFriends = relationshipRepository.findByIds(userPosted.getId(), userPagePosted.getId());
        if (isFriends == null || isFriends.getStatus() != RelationshipStatus.ACCEPTED)
            throw new BadRequestException("The User's Not Your Friend");
        if (post.getMessage().replaceAll("\\s+", "").isEmpty()) throw new BadRequestException("Message is empty");
    }
}
