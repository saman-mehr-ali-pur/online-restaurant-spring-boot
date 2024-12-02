package com.online_restaurant.backend.services;


import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;


    public User addUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
       return user;
    }



    public User update(User user){

        userRepo.update(user);
        return user;

    }


    public void delete(User user){
        userRepo.remove(user);
    }


    public List<User> getAll(){
        List<User> users = userRepo.getAll();
        return users;
    }


    public User get(int id){
        User user = new User();
        user.setId(id);
        return userRepo.findById(user);

    }

    public User getUsername(String username){
        User user = new User();
        user.setUsername(username);
        return userRepo.findByUsername(user);
    }


    public void updatePassword(User user,String password){
        userRepo.updatePassword(user , encoder.encode(password));
    }
}
