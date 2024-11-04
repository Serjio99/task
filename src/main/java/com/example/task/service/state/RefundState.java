package com.example.task.service.state;

import com.example.task.configugation.StateType;
import com.example.task.data.model.Bank;
import com.example.task.exception.ErrorCode;
import com.example.task.exception.TackBusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@StateType("refund")
public class RefundState implements PaymentState {

    @Override
    public void processPayment(Bank bank, BigDecimal amount, String shopType) {

        if (shopType.equalsIgnoreCase("online")) {
            if (bank.getNonCash().compareTo(amount) < 0) {
                throw new TackBusinessException(String.format("Not enough non-cash funds: %s", bank.getNonCash())
                        , ErrorCode.NOT_ENOUGH_MONEY_IN_THE_ACCOUNT);
            }
            bank.setNonCash(bank.getNonCash().subtract(amount));
        } else if (shopType.equalsIgnoreCase("shop")) {
            if (bank.getCash().compareTo(amount) < 0) {
                throw new TackBusinessException(String.format("Not enough non-cash funds: %s", bank.getNonCash())
                        , ErrorCode.NOT_ENOUGH_MONEY_IN_THE_ACCOUNT);
            }
            bank.setCash(bank.getCash().subtract(amount));
        } else {
            throw new TackBusinessException(String.format("Unknown store type: %S", shopType), ErrorCode.STORE_NOT_FOUND);
        }

        bank.setLastOperationCommission(amount.multiply(BigDecimal.valueOf(0.1)));

        bank.setLastOperationBonus(BigDecimal.ZERO);
    }
}
