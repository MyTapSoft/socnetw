package com.socnetw.socnetw.converter;

import com.socnetw.socnetw.model.User;
import com.socnetw.socnetw.model.UserAuthority;
import com.socnetw.socnetw.model.UserAuthorityType;
import com.socnetw.socnetw.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class UserConverter {

    public UserConverter(PasswordEncoder encoder) {
    }

    public static User map(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRealName(userDTO.getRealName());
        user.setPassword(userDTO.getPassword());
        UserAuthority authority = new UserAuthority();
        authority.setName(UserAuthorityType.ROLE_USER);
        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        return user;
    }

    public static User map(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setRealName(user.getRealName());
        userDTO.setPassword(user.getPassword());
        userDTO.setMatchingPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return user;
    }
}
