package com.online_restaurant.backend.controller;


import com.online_restaurant.backend.model.Address;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.services.AddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServices addressServices;

    @GetMapping("/get/{userid}")
    public List<Address> getByUserId(@PathVariable(name="userid") int id){
        User user = new User();
        user.setId(id);
        List<Address> result = addressServices.getAll(user);
//        System.out.println(result);
        return result;
    }


    @GetMapping("/get/all")
    public List<Address> getAll(){
        return addressServices.getAll();
    }

    @PostMapping("/save")
    public Address addAddress(@RequestBody Address address){
        return addressServices.save(address);
    }
    @PatchMapping("/update")
    public Address update(@RequestBody Address address){
        return addressServices.Update(address);
    }

    @DeleteMapping("delete/{id}")
    public boolean remove(@PathVariable("id") int id){
        Address address = new Address();
        address.setId(id);
        return  addressServices.remove(address);
    }


}
