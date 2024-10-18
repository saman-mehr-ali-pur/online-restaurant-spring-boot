package com.online_restaurant.backend.controller;


import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable(name = "id") int id){
        User user = new User();
        user.setId(id);
       return userService.get(id);
    }


    @GetMapping("/{username}")
    public User getUser(@PathVariable(name = "username") String username){
        User user = new User();
        user.setUsername(username);
        return userService.getUsername(username);
    }



    @PostMapping
    public User saveUser(@RequestBody User user ){

        return  userService.addUser(user);
    }

    @GetMapping("/getall")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/update")
    public User update(@RequestBody User user){
        return userService.update(user);
    }


}
