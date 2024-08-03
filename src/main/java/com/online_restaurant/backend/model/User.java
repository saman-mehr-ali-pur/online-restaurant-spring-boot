package com.online_restaurant.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private  Integer id;
    private String username;
    private String password;
    private String email;
    private Date birthdate;
    private Date signupDate;

}
