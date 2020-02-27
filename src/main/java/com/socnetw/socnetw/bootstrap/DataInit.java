package com.socnetw.socnetw.bootstrap;

import com.socnetw.socnetw.model.*;
import com.socnetw.socnetw.repository.MessageRepositorys;
import com.socnetw.socnetw.repository.PostRepository;
import com.socnetw.socnetw.repository.RelationshipRepository;
import com.socnetw.socnetw.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Profile("default")
@Component
public class DataInit implements ApplicationListener<ContextRefreshedEvent> {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private RelationshipRepository relationshipRepository;
    private MessageRepositorys messageRepository;
    private PasswordEncoder encoder;

    public DataInit(UserRepository userRepository, PostRepository postRepository, RelationshipRepository relationshipRepository, MessageRepositorys messageRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.relationshipRepository = relationshipRepository;
        this.messageRepository = messageRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User john = new User();
        john.setBirthDate(LocalDate.now());
        john.setEmail("jo4hn@gmail.com");
        john.setPassword(encoder.encode("123"));
        john.setPhoneNumber("82145558741");
        john.setRealName("John");
        john.setUsername("Johny");
        Set<Post> johnPosts = new HashSet<>();
        Set<UserAuthority> authorityTypes = new HashSet<>();
        UserAuthority authority = new UserAuthority();
        authority.setName(UserAuthorityType.ROLE_ADMIN);
        authorityTypes.add(authority);
        john.setAuthorities(authorityTypes);

        User kay = new User();
        kay.setBirthDate(LocalDate.now());
        kay.setEmail("c7at@gmail.com");
        kay.setPassword(encoder.encode("123"));
        kay.setPhoneNumber("89974554774");
        kay.setRealName("Katy");
        kay.setUsername("Cat");
        Set<Post> kayPosts = new HashSet<>();

        User linda = new User();
        linda.setBirthDate(LocalDate.now());
        linda.setEmail("lin2da@gmail.com");
        linda.setPassword(encoder.encode("123"));
        linda.setPhoneNumber("+76322222222");
        linda.setRealName("Linda");
        linda.setUsername("Lindy");
        Set<Post> lindaPosts = new HashSet<>();

        Set<User> taggedUsers = new HashSet<>();
        taggedUsers.add(linda);
        taggedUsers.add(kay);


        Post johnPost = new Post();
        johnPost.setDatePosted(LocalDate.now());
        johnPost.setLocation("NY");
        johnPost.setMessage("Messsdfsdfsdfsdgfdgfdage");
        johnPost.setUserPosted(john);
        johnPost.setUserPagePosted(linda);
        johnPost.setUsersTagged(taggedUsers);

        Post johnPost1 = new Post();
        johnPost1.setDatePosted(LocalDate.now());
        johnPost1.setLocation("NY");
        johnPost1.setMessage("Mesgfdgdfgdfgdfgdfsassage");
        johnPost1.setUserPosted(john);
        johnPost1.setUserPagePosted(kay);
        johnPost1.setUsersTagged(taggedUsers);

        Post johnPost2 = new Post();
        johnPost2.setDatePosted(LocalDate.now());
        johnPost2.setLocation("NY");
        johnPost2.setMessage("dsfsdfgsdfsdsdf");
        johnPost2.setUserPosted(john);
        johnPost2.setUserPagePosted(linda);
        johnPost2.setUsersTagged(taggedUsers);

        Post johnPost3 = new Post();
        johnPost3.setDatePosted(LocalDate.now());
        johnPost3.setLocation("NY");
        johnPost3.setMessage("Messcdbvfdgfdgdfage");
        johnPost3.setUserPosted(john);
        johnPost3.setUserPagePosted(linda);
        johnPost3.setUsersTagged(taggedUsers);

        Post johnPost4 = new Post();
        johnPost4.setDatePosted(LocalDate.now());
        johnPost4.setLocation("NY");
        johnPost4.setMessage("The red-throated loon or red-throated diver (Gavia stellata) is a migratory aquatic bird found in the northern hemisphere.");
        johnPost4.setUserPosted(john);
        johnPost4.setUserPagePosted(linda);
        johnPost4.setUsersTagged(taggedUsers);

        Post johnPost5 = new Post();
        johnPost5.setDatePosted(LocalDate.now());
        johnPost5.setLocation("NY");
        johnPost5.setMessage("Mesxcvxcvsage");
        johnPost5.setUserPosted(john);
        johnPost5.setUserPagePosted(linda);
        johnPost5.setUsersTagged(taggedUsers);

        Post johnPost6 = new Post();
        johnPost6.setDatePosted(LocalDate.now());
        johnPost6.setLocation("NYccc");
        johnPost6.setMessage("The red-throxcvxcvxcated loon or red-throated diver (Gavia stellata) is a migratory aquatic bird found in the northern hemisphere.");
        johnPost6.setUserPosted(john);
        johnPost6.setUserPagePosted(linda);
        johnPost6.setUsersTagged(taggedUsers);

        Post johnPost7 = new Post();
        johnPost7.setDatePosted(LocalDate.now());
        johnPost7.setLocation("NY");
        johnPost7.setMessage("Messsdfsdfdewreage");
        johnPost7.setUserPosted(john);
        johnPost7.setUserPagePosted(kay);
        johnPost7.setUsersTagged(taggedUsers);

        Post johnPost8 = new Post();
        johnPost8.setDatePosted(LocalDate.now());
        johnPost8.setLocation("NY");
        johnPost8.setMessage("Medsfsdgssage");
        johnPost8.setUserPosted(john);
        johnPost8.setUserPagePosted(kay);
        johnPost8.setUsersTagged(taggedUsers);

        Post johnPost9 = new Post();
        johnPost9.setDatePosted(LocalDate.now());
        johnPost9.setLocation("NY");
        johnPost9.setMessage("Mesasdasfgfgfhhhhsage");
        johnPost9.setUserPosted(john);
        johnPost9.setUserPagePosted(kay);
        johnPost9.setUsersTagged(taggedUsers);

        Post johnPost10 = new Post();
        johnPost10.setDatePosted(LocalDate.now());
        johnPost10.setLocation("NY");
        johnPost10.setMessage("Mesdsfsdsage");
        johnPost10.setUserPosted(john);
        johnPost10.setUserPagePosted(kay);
        johnPost10.setUsersTagged(taggedUsers);

        Post kayPost = new Post();
        kayPost.setDatePosted(LocalDate.now());
        kayPost.setLocation("LA");
        kayPost.setMessage("Messdfsdfage to someonedfds");
        kayPost.setUserPosted(kay);
        kayPost.setUserPagePosted(john);

        Post kayPost2 = new Post();
        kayPost2.setDatePosted(LocalDate.now());
        kayPost2.setLocation("LA");
        kayPost2.setMessage("Messsdfsdfsdfage to someone 2");
        kayPost2.setUserPosted(kay);
        kayPost2.setUserPagePosted(john);

        Post kayPost3 = new Post();
        kayPost3.setDatePosted(LocalDate.now());
        kayPost3.setLocation("LA");
        kayPost3.setMessage("Message to sosdfsdfmeone 3");
        kayPost3.setUserPosted(kay);
        kayPost3.setUserPagePosted(linda);
        kayPost3.setUsersTagged(taggedUsers);

        Post lindaPost = new Post();
        lindaPost.setDatePosted(LocalDate.now());
        lindaPost.setLocation("Dallas");
        lindaPost.setMessage("Messdfsdfsage to sodfgdfmeone");
        lindaPost.setUserPosted(linda);
        lindaPost.setUserPagePosted(kay);
        lindaPost.setUsersTagged(taggedUsers);

        johnPosts.add(johnPost);
        johnPosts.add(johnPost1);
        johnPosts.add(johnPost3);
        johnPosts.add(johnPost4);
        johnPosts.add(johnPost5);
        johnPosts.add(johnPost6);
        johnPosts.add(johnPost7);
        johnPosts.add(johnPost8);
        johnPosts.add(johnPost9);
        johnPosts.add(johnPost10);
        kayPosts.add(kayPost);
        kayPosts.add(kayPost2);
        kayPosts.add(kayPost3);
        lindaPosts.add(lindaPost);

        john.setPosts(johnPosts);
        kay.setPosts(kayPosts);
        linda.setPosts(lindaPosts);

        Message message = new Message();
        message.setDateSent(LocalDate.now());
        message.setText("Some message text");
        message.setUserFrom(john);
        message.setUserTo(kay);

        userRepository.save(john);
        userRepository.save(kay);
        userRepository.save(linda);

        Relationship relationship = new Relationship();
        relationship.setUserIdFrom(1L);
        relationship.setUserIdTo(2L);
        relationship.setFriendsRequestDate(LocalDate.now());
        relationship.setStatus(RelationshipStatus.ACCEPTED);

        relationshipRepository.save(relationship);
        messageRepository.save(message);

    }
}
