package com.online_restaurant.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer id;
    private String comment;
    private Food food;
    private User user;

}
