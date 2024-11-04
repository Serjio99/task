package com.example.task.service;

import com.example.task.configugation.StateType;
import com.example.task.exception.ErrorCode;
import com.example.task.exception.TackBusinessException;
import com.example.task.service.state.PaymentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStateFactory {
    private final Map<String, PaymentState> stateMap;

    /**
     * Класс PaymentStateFactory позволяет динамически выбор
     * и предоставление подходящей реализации интерфейса PaymentState
     * в зависимости от магазина.
     */

    @Autowired
    public PaymentStateFactory(List<PaymentState> states) {
        this.stateMap = states.stream()
                .collect(Collectors.toMap(state -> state.getClass().getAnnotation(StateType.class).value(), Function.identity()));
        /**
         * Заполняем Map, где ключь значение кастомной анотации @StateType
         * значение сам экземпляр PaymentState
         * Получаем, где каждому магазину соответствуе конкретная реализация.
         */
    }

    public PaymentState getState(String shopType, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(20)) < 0) {
            return stateMap.get("refund");
        } else {
            PaymentState state = stateMap.get(shopType.toLowerCase());
            if (state == null) {
                throw new TackBusinessException("Invalid shop type: " + shopType, ErrorCode.STORE_VALIDATION_ERROR);
            }
            return state;
            /**
             * Если сумма меньше 20-ти, то возвращаем состояние для возврата
             * иначе пытаемся получить состояние из map и возвращаем соответствующие состояние
             * если не найдено, то выбрасываем кастомное исмключение
             */
        }
    }
}

