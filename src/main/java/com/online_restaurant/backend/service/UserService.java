package com.online_restaurant.backend.service;


import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements BaseService<User>{

    @Autowired
    private UserRepo userRepo;


    @Override
    public User get(int id) {
        return userRepo.get(id);
    }

    public User get(User user){
        return userRepo.get(user);
    }

    @Override
    public List<User> getAll(int limit) {
        return userRepo.getAll(limit);
    }

    @Override
    public User save(User ob) {
        return userRepo.save(ob);
    }

    @Override
    public boolean  update(User ob) {
        return userRepo.update(ob);
    }

    @Override
    public boolean delete(int id) {
        User user =new User();
        user.setId(id);
        return userRepo.delete(user);
    }
}
