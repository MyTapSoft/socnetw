package com.socnetw.socnetw.service;

import com.socnetw.socnetw.exceptions.DuplicateException;
import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (userRepository.isEmailOrPhoneExist(user.getEmail(), user.getPhoneNumber()) != null) {
            throw new DuplicateException("Email or Phone Number Already Exist");
        }
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

}
