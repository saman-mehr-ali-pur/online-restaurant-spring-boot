package com.online_restaurant.backend.controller;

import com.online_restaurant.backend.model.Payment;
import com.online_restaurant.backend.repository.PaymentRepo;
import com.online_restaurant.backend.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/save")
    public Payment save(@RequestBody Payment payment){
        paymentService.savePayment(payment);
        return payment;
    }

    @DeleteMapping("/delete/{id}")
    public boolean remove(@PathVariable int id){
        Payment payment = new Payment();
        payment.setId(id);
        paymentService.remove(payment);
        return true;
    }
}
