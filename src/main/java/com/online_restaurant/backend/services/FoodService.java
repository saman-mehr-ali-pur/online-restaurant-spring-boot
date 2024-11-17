package com.online_restaurant.backend.services;


import com.online_restaurant.backend.model.Enum.FoodType;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;


    public Food save(Food food){
        foodRepo.addFood(food);
        return food;
    }

    public Food update(Food food){
        foodRepo.updateFood(food);
        return food;
    }

    public Food get(Food food){
        return foodRepo.get(food);
    }

    public List<Food> getAll(){
        return foodRepo.getAll();
    }

    public List<Food> getByType(FoodType type){
        List<Food> result = foodRepo.getAll();
        return result.stream().filter(item -> item.getType().equals(type)).toList();
    }


    public boolean remove(Food food){
       return foodRepo.removeFood(food);
    }


}
