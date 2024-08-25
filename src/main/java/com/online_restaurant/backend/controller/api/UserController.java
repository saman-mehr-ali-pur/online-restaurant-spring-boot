package com.online_restaurant.backend.controller.api;

import com.online_restaurant.backend.exception.NotFoundException;
import com.online_restaurant.backend.model.Address;
import com.online_restaurant.backend.model.Enum.Role;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
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

        System.out.println(user);
//        user.setRole(Role.USER);
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


    @GetMapping("/address")
    public Address getAddress(@RequestBody User user){
        user = userService.get(user);
        return userService.getAddress(user);
    }

    @PostMapping("/address")
    public Address addAddress(@RequestBody Address address){
        return userService.addAddress(address);
    }


    @PatchMapping("/address")
    public boolean updateAddress(@RequestBody Address address){
        System.out.println("udate address");
        return  userService.updateAddress(address);
    }



}
