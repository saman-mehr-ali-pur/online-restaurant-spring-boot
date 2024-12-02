package com.online_restaurant.backend.dto;

import com.online_restaurant.backend.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthDto {

    private User user;
    private String jwtToken;
}
