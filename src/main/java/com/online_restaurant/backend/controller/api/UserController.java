package com.online_restaurant.backend.controller.api;

import com.online_restaurant.backend.exception.NotFoundException;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<User> getAll(@RequestParam("limit") int limit){
        return userService.getAll(limit);
    }


    @PostMapping("/get")
    public User getUser(@RequestBody User user){
        System.out.println(user);
        String username = user.getUsername();
        user = userService.get(user);
        if(user == null){
            throw new NotFoundException("not found user with username: " + username);
        }
        return user;
    }


    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") int id){
        return userService.get(id);
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user){

        System.out.println(user.toString());
        return userService.save(user);
    }


    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable("id") int id){
        return userService.delete(id);
    }


    @PatchMapping("/update")
    public boolean updateUser(@RequestBody User user){
        return userService.update(user);
    }


}
