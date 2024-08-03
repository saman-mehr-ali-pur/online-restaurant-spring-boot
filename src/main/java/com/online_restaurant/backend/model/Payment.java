package com.online_restaurant.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Integer id;
    private Double amount;
    private Double status;
    private Order order;


}
