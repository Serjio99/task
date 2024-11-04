package com.example.task.controller;


import com.example.task.data.model.dto.PaymentResponse;
import com.example.task.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BankController {
    private final BankService bankService;

    @GetMapping("/payment/{shop}/{amount}")
    public ResponseEntity<PaymentResponse> payment(@PathVariable("shop") String shop, @PathVariable("amount") BigDecimal amount) {
        return ResponseEntity.ok(bankService.payment(shop, amount));
    }

    @GetMapping("/bankAccountOfEMoney")
    public ResponseEntity<PaymentResponse> getBankAccountOfEMoney() {
        return ResponseEntity.ok(bankService.getBankAccountOfEMoney());
    }

    @GetMapping("/money")
    public ResponseEntity<PaymentResponse> getMoney() {
        return ResponseEntity.ok(bankService.getMoney());
    }
}
