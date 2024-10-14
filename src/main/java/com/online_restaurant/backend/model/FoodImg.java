package com.online_restaurant.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodImg {
    private int id;
    private String path;
    private Integer foodId;
    public FoodImg(String path, Integer foodId) {
        this.path = path;
        this.foodId = foodId;
    }
}
