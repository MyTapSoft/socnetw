package com.socnetw.socnetw.service;

import com.socnetw.socnetw.exceptions.BadRequestException;
import com.socnetw.socnetw.exceptions.DuplicateException;
import com.socnetw.socnetw.exceptions.NotFoundException;
import com.socnetw.socnetw.exceptions.UnauthorizedException;
import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {

    private final UserRepository dao;

    @Autowired
    public UserService(UserRepository dao) {
        this.dao = dao;
    }

    public User saveUser(User user) {
        if (dao.checkDuplicate(user.getEmail(), user.getPhoneNumber()) != null) {
            throw new DuplicateException("Email or Phone Number Already Exist");
        }
        return dao.save(user);
    }

    public User updateUser(User user, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        User result = dao.update(user);
        if (result == null)
            throw new NotFoundException("User With ID: " + user.getId() + " Doesn't Exist");
        return result;
    }

    public void deleteUser(Long userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        dao.delete(userId, User.class);
    }

    public User getUser(Long id) throws BadRequestException {
        User user = dao.findById(id, User.class);
        if (user == null) throw new BadRequestException("User with ID: " + id + " doesn't exist");
        return user;
    }

    public List<User> getAllUsers() {
        return dao.findAll();
    }

    public List<User> getUserFriends(Long userId) {
        return dao.findFriendById(userId);
    }

    public User login(String login, String password) {

        return dao.findByLoginAndPswd(login, password);
    }

    public List<User> getIncomeRequests(String userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        return dao.findIncomeRequests(userId);

    }

    public List<User> getOutcomeRequests(String userId, HttpSession session) throws UnauthorizedException {
        isUserLogin(session);
        return dao.findOutcomeRequests(userId);

    }

    private void isUserLogin(HttpSession session) throws UnauthorizedException {
        if (session.getAttribute("loginStatus") == null) throw new UnauthorizedException("You have to login first");
    }
}
