package com.online_restaurant.backend.model;

import com.online_restaurant.backend.model.Enum.FoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    private  Integer id;
    private String name;
    private Double price;
    private  String description;
    private Boolean status;
    private FoodType type;
    private List<String> imagePath;
    private List<Comment> comments;
    private List<User> likes;

}
