package com.online_restaurant.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Double amount;
    @Column
    private Boolean status;
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date datPay;
}
