package com.example.task.configugation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateType {
    /**
     * Кастомная аннотация, которая используется для обозначения типа состояния,
     * применяемого в зависимости от типа магазина. Она предназначена для логики внедрения бина в соответствии с
     * указанным типом, что позволяет динамически выбирать нужное состояние (стратегию) в зависимости от значения аннотации.
     * <p>
     * Использование:
     * - Аннотация `StateType` применяется к классам, реализующим интерфейс `PaymentState`, таким как `OnlineState`
     * и `ShopState`.
     * - Класс `PaymentStateFactory` использует эту аннотацию для выбора конкретной реализации `PaymentState`
     * на основании типа магазина, указанного в значении аннотации.
     */
    String value();
}
