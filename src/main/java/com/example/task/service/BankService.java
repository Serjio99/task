package com.example.task.service;

import com.example.task.data.model.dto.PaymentResponse;
import com.example.task.exception.ErrorCode;
import com.example.task.exception.TackBusinessException;
import com.example.task.repository.BankRepository;
import com.example.task.service.state.PaymentState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;
    private final PaymentStateFactory paymentStateFactory;


    @Transactional
    public PaymentResponse payment(String shopType, BigDecimal amount) {
        /**
         * Класс предоставляет сервисные методы для управления банковским счётом,
         * расчёта бонусов и комиссий, а также получения информации о счёте.
         * Этот класс содержит бизнес-логику, определяющую условия начисления бонусов и снятия комиссии
         * в зависимости от типа магазина и суммы платежа.
         *
         * Основные функции:
         * - Выполнение платежа с учётом типа магазина и суммы (`payment`).
         * - Получение информации о бонусном счёте клиента (`getBankAccountOfEMoney`).
         * - Получение информации о наличии денежных средств (наличных и безналичных) (`getMoney`).
         *
         * Комментарии:
         * - В каждом методе используется захардкоженный `UUID` клиента, так как для данного тестового задания
         *   не передаются идентификаторы клиента извне. В реальной среде можно ожидать передачу `UUID` клиента в качестве параметра.
         */
        log.info("Payment of shopType: {}, amount: {}", shopType, amount);
        /**
         Захардкоженный uuid клиента совершающий операцию
         из-за отсутствия каких либо данных
         */
        UUID employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        PaymentState state = paymentStateFactory.getState(shopType, amount);

        var bank = bankRepository.findByClientId(employeeId)
                .orElseThrow(() -> new TackBusinessException("Bank not found for user: " + employeeId, ErrorCode.USER_NOT_FOUND));

        state.processPayment(bank, amount, shopType);

        bank.setTotalBonusAccount(bank.getTotalBonusAccount().add(bank.getLastOperationBonus()));

        bank.setTotalCommissionAccount(bank.getTotalCommissionAccount().add(bank.getLastOperationCommission()));

        bankRepository.save(bank);
        return PaymentResponse.builder()
                .cash(bank.getCash())
                .nonCash(bank.getNonCash())
                .lastOperationBonus(bank.getLastOperationBonus())
                .totalBonusAccount(bank.getTotalBonusAccount())
                .lastOperationCommission(bank.getLastOperationCommission())
                .totalCommissionAccount(bank.getTotalCommissionAccount())
                .build();
    }

    @Transactional
    public PaymentResponse getBankAccountOfEMoney() {
        log.info("Bank account of EMoney");
        /**
         Захардкоженный uuid клиента совершающий операцию
         из-за отсутствия каких либо данных
         */
        UUID employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        var bank = bankRepository.findByClientId(employeeId)
                .orElseThrow(() -> new TackBusinessException("Bank not found for user: " + employeeId, ErrorCode.USER_NOT_FOUND));

        /**
         * А давайте закроем глаза на то, что переиспользую модель ответ
         * все же это тестово задание, в реальном проекте был бы отдельно сервис API
         */
        return PaymentResponse.builder()
                .totalBonusAccount(bank.getTotalBonusAccount())
                .build();

    }

    @Transactional
    public PaymentResponse getMoney() {
        log.info("Bank money");
        /**
         Захардкоженный uuid клиента совершающий операцию
         из-за отсутствия каких либо данных
         */
        UUID employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        var bank = bankRepository.findByClientId(employeeId)
                .orElseThrow(() -> new TackBusinessException("Bank not found for user: " + employeeId, ErrorCode.USER_NOT_FOUND));

        return PaymentResponse.builder()
                .cash(bank.getCash())
                .nonCash(bank.getNonCash())
                .build();
    }
}
