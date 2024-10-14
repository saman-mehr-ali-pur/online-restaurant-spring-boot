package com.online_restaurant.backend.service;


import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    public User findById(User user){

        return userRepo.findById(user);
    }

    public User findByUsername(User user){
        return userRepo.findByUsername(user);
    }


    public boolean save(User user){
        return userRepo.save(user);
    }

    public User update(User user){
        return userRepo.update(user);
    }

    public boolean remove(User user){
        return userRepo.remove(user);
    }



}
