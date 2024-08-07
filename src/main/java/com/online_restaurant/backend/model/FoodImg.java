package com.online_restaurant.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodImg {

    private int id;
    private String path;
    private Food food;

}
