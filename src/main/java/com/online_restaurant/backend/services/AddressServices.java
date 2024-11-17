package com.online_restaurant.backend.services;


import com.online_restaurant.backend.exception.IligalEntityException;
import com.online_restaurant.backend.model.Address;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServices {

    @Autowired
    private AddressRepo addressRepo;

    public Address save(Address address){
        if (address.getUser() == null){
            throw  new IligalEntityException("user is null");

        }
        addressRepo.saveAddress(address);
        return address;
    }



    public List<Address> getAll(){
        return addressRepo.getAllAddress();
    }

    public List<Address> getAll(User user){
        return addressRepo.getAllAddress(user);
    }

    public boolean remove(Address address){
//        Address address = new Address();
        return addressRepo.remove(address);
    }


    public Address Update(Address address){
         addressRepo.update(address);
         return address;
    }
}
