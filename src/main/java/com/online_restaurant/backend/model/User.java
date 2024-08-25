package com.online_restaurant.backend.model;

import com.online_restaurant.backend.model.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    private  Integer id;
    private String username;
    private String password;
    private String email;
    private Date birthdate;
    private Date signupDate;
    private Role role;
    private Address addresses;

}
