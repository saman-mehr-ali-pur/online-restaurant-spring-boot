package com.online_restaurant.backend.controller.api;

import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @GetMapping("/all")
    public List<Food> getAllOrders(@RequestParam("limit") int limit){
        return foodService.getAll(limit);
    }

    @GetMapping("/get/{id}")
    public Food get(@PathVariable("id") int id){
        return foodService.get(id);
    }


    @PostMapping
    public Food save(@RequestBody Food food){
       return foodService.save(food);
    }


    @DeleteMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable("id") int id){
        return foodService.delete(id);
    }



    @PostMapping("update")
    public boolean updateOrder(@RequestBody Food food){
        return foodService.update(food);
    }

}
