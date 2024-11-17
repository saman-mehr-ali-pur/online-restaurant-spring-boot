package com.online_restaurant.backend.controller;


import com.online_restaurant.backend.model.Enum.FoodType;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {


    @Autowired
    private FoodService foodService;

    @PostMapping("/save")
    public Food save(@RequestBody Food food){
        foodService.save(food);
        return food;
    }

    @GetMapping("/get")
    public List<Food> getAll(){
        return foodService.getAll();
    }


    @GetMapping("/get/{type}")
    public List<Food> getByType(@PathVariable("type")FoodType foodType){
        return  foodService.getByType(foodType);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id){
        Food food = new Food();
        food.setId(id);
        return foodService.remove(food);

    }


    @PatchMapping("/update")
    public Food update(@RequestBody Food food){
        return  foodService.update(food);
    }
}
