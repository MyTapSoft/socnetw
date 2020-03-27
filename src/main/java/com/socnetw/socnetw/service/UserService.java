package com.socnetw.socnetw.service;

import com.socnetw.socnetw.authorization.UserAuthDetails;
import com.socnetw.socnetw.converter.UserConverter;
import com.socnetw.socnetw.exceptions.UserAlreadyExist;
import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.model.UserDTO;
import com.socnetw.socnetw.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User saveUser(UserDTO userDTO) {
        if (userRepository.isEmailOrPhoneExist(userDTO.getEmail(), userDTO.getPhoneNumber()) != null) {
            throw new UserAlreadyExist("Email or Phone Number Already Exist");
        }
        User user = UserConverter.map(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, HttpSession session) throws UnauthorizedException {
//        isUserLogin(session);
//        User result = userRepos.update(user);
//        if (result == null)
//            throw new NotFoundException("User With ID: " + user.getId() + " Doesn't Exist");
//        return result;
        return new User();
    }

    public void deleteUser(Long userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        userRepository.deleteById(userId);
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserFriends(Long userId) {
        return userRepository.findFriendById(userId);
    }

    public User login(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public List<User> getIncomeRequests(String userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        return userRepository.findIncomeRequests(userId);

    }

    public List<User> getOutcomeRequests(String userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        return userRepository.findOutcomeRequests(userId);

    }

    private static void isUserLogin(HttpSession session) throws UnauthorizedException {
        if (session.getAttribute("loginStatus") == null) throw new UnauthorizedException("You have to login first");
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        log.info("loadUserByUsername() : {}", username);
        return new UserAuthDetails(user);
    }

}
