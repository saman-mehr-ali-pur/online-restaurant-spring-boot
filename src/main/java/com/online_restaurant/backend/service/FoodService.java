package com.online_restaurant.backend.service;

import com.online_restaurant.backend.ioUtil.ImageIo;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FoodService implements BaseService<Food>{

    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private ImageIo imageIo;

    @Override
    public Food get(int id) {
        return foodRepo.get(id);
    }

    @Override
    public List<Food> getAll(int limit) {
        return foodRepo.getAll(limit);
    }

    @Override
    public Food save(Food ob) {
        return null;
    }

    @Override
    public boolean update(Food ob) {
        return foodRepo.update(ob);
    }

    @Override
    public boolean delete(int id) {
        Food food = new Food();
        food.setId(id);
        return foodRepo.delete(food);
    }



    public boolean saveImg(byte[] img,Food food,String suffix) throws IOException {

        return foodRepo.saveImg(food,img,suffix);
    }


    public byte[][] getImages(Food food){
        return foodRepo.getImages(food);
    }

}
