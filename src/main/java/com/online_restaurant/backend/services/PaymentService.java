package com.online_restaurant.backend.services;

import com.online_restaurant.backend.model.Payment;
import com.online_restaurant.backend.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    public void savePayment(Payment payment){
        paymentRepo.add(payment);
    }

    public void remove(Payment payment){
        paymentRepo.remove(payment);
    }


}
