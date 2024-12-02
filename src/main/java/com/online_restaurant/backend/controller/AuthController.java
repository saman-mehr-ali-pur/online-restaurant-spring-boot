package com.online_restaurant.backend.controller;

import com.online_restaurant.backend.dto.AuthDto;
import com.online_restaurant.backend.exception.NotFoundException;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.security.jwt.JwtService;
import com.online_restaurant.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/signin")
    public AuthDto userSignin(@RequestBody  User user){
        String plainPass = user.getPassword();
//        String password = encoder.encode(user.getPassword());
//        System.out.println(encoder.matches(user.getPassword(),password));
        user = userService.getUsername(user.getUsername());
//        System.out.println(encoder.matches(plainPass,user.getPassword()));
        if(encoder.matches(plainPass,user.getPassword())) {
//            System.out.println("hello");
            AuthDto authDto = new AuthDto();
            authDto.setUser(user);
            String jwt = jwtService.generateToken(user);
            authDto.setJwtToken(jwt);
            return authDto;
        }
//        System.out.println("bye");
        throw new NotFoundException("user not found");

    }


    @PostMapping("/signup")
    public AuthDto userSignup(@RequestBody User user){

        user = userService.addUser(user);
        System.out.println(user);
        AuthDto authDto = new AuthDto();
        String jwt = jwtService.generateToken(user);
        authDto.setJwtToken(jwt);
        user.setPassword(null);
        authDto.setUser(user);
        return authDto;
    }
}
