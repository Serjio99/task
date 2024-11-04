package com.example.task.service.state;

import com.example.task.configugation.StateType;
import com.example.task.data.model.Bank;
import com.example.task.exception.ErrorCode;
import com.example.task.exception.TackBusinessException;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
@StateType("shop")
public class ShopState implements PaymentState {
    @Override
    public void processPayment(Bank bank, BigDecimal amount, String shopType) {

        if (bank.getCash().compareTo(amount) < 0) {
            throw new TackBusinessException(String.format("Not enough non-cash funds: %s", bank.getNonCash())
                    , ErrorCode.NOT_ENOUGH_MONEY_IN_THE_ACCOUNT);
        }
        bank.setCash(bank.getCash().subtract(amount));

        if (amount.compareTo(BigDecimal.valueOf(300)) >= 0) {
            bank.setLastOperationBonus(amount.multiply(BigDecimal.valueOf(0.3)));
        } else {
            bank.setLastOperationBonus(amount.multiply(BigDecimal.valueOf(0.1)));
        }

        bank.setLastOperationCommission(BigDecimal.ZERO);
    }
}
