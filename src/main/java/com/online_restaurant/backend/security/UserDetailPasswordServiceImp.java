package com.online_restaurant.backend.security;

import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.UserRepo;
import com.online_restaurant.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailPasswordServiceImp implements UserDetailsPasswordService {

    @Autowired
    private UserService userService;
//    @Autowired
//    private PasswordEncoder encoder;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {

        UserDetailImp userImp = (UserDetailImp) user;
        User userToUpdate = new User();
        userToUpdate.setId(userImp.getId());
//        userToUpdate.setPassword(encoder.encode(newPassword));
        userService.updatePassword(userToUpdate,newPassword);
        return userImp;

    }
}
