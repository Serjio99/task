package com.example.task.service.state;

import com.example.task.data.model.Bank;

import java.math.BigDecimal;

public interface PaymentState {
    void processPayment(Bank bank, BigDecimal amount, String shopType);
}
