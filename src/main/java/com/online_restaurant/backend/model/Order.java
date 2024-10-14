package com.online_restaurant.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deliver_id",referencedColumnName = "id")
    private User deliverer;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Address address;
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Food> foodList;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id",referencedColumnName = "id")
    private Payment payment;



    public Double tatalPrice(){
        return  foodList.stream().map(Food::getPrice).
                reduce( 0.0,Double::sum);
    }

}
