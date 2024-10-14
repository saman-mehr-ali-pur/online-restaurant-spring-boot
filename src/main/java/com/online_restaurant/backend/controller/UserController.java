package com.online_restaurant.backend.controller;


import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable(name = "id") int id){
        User user = new User();
        user.setId(id);
       return userService.findById(user);
    }


    @GetMapping("/{username}")
    public User getUser(@PathVariable(name = "username") String username){
        User user = new User();
        user.setUsername(username);
        return userService.findByUsername(user);
    }



    @PostMapping
    public boolean saveUser(@RequestBody User user ){

        return  userService.save(user);
    }


}
