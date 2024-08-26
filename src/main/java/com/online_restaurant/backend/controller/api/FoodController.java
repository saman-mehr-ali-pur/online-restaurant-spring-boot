package com.online_restaurant.backend.controller.api;

import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodService foodService;


    @GetMapping("/all")
    public List<Food> getAllOrders(@RequestParam("limit") int limit){

        System.out.println("limit"+limit);
        return foodService.getAll(limit);
    }

    @GetMapping("/get/{id}")
    public Food get(@PathVariable("id") int id){
        return foodService.get(id);
    }


    @PostMapping("/save")
    public Food save(@RequestBody Food food){

        System.out.println(food );
        return foodService.save(food);
    }


    @DeleteMapping("/delete/{id}")
    public boolean deleteOrder(@PathVariable("id") int id){
        return foodService.delete(id);
    }



    @PostMapping("update")
    public boolean updateOrder(@RequestBody Food food){

        System.out.println(food);
        return foodService.update(food);
    }


    @GetMapping("/img/")
    public ResponseEntity<?> getImage(@RequestParam("filename") String filename) throws IOException {
        byte[] file = foodService.getImage(filename);
        return ResponseEntity.ok().
                header("content-type","image/svg+xml").
                    body(file);
    }



    @PostMapping("/img/{foodId}")
    public boolean saveImage(@RequestPart("image")MultipartFile file,
                             @PathVariable("foodId") int id) throws IOException {
        byte[] bytes = file.getBytes();
        int lastIndexOfDot = file.getOriginalFilename().lastIndexOf(".");
        System.out.println(file.getOriginalFilename().substring(lastIndexOfDot+1));
        return  foodService.saveImg(bytes,
                Food.builder().id(id).build(),
                file.getOriginalFilename().substring(lastIndexOfDot+1));

    }

}
