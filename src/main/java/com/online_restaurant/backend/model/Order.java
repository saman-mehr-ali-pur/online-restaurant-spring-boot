package com.online_restaurant.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    private Integer id;
    private User customer;
    private User deliverer;
    private Address address;
    private List<Food> foodList;
    private Payment payment;

}
