package com.online_restaurant.backend.model;

import com.online_restaurant.backend.model.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private Role role;
    private List<Address> addresses;

}
