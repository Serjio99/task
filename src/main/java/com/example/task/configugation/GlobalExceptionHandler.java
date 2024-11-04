package com.example.task.configugation;

import com.example.task.exception.TackBusinessException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Класс `GlobalExceptionHandler` является глобальным обработчиком исключений для всего приложения.
     * Он позволяет перехватывать пользовательские исключения и возвращать клиенту структурированный и понятный ответ,
     * обеспечивая централизованную обработку ошибок.
     * <p>
     * Основной функционал:
     * - Перехват кастомного исключения `TackBusinessException` и возврат пользователю сообщения об ошибке
     * с указанием кода и пояснением причины ошибки.
     * - Создание и возврат объекта `ErrorResponse`, который структурирует информацию о произошедшей ошибке
     * и включает код ошибки (`errorCode`) и сообщение (`message`).
     * <p>
     * Использование:
     * - При возникновении `TackBusinessException` метод `handleTackBusinessException` автоматически вызывается
     * и возвращает `ResponseEntity<ErrorResponse>` с HTTP-статусом `NOT_FOUND` (404), указывая,
     * что запрашиваемый ресурс не найден или операция невозможна.
     */

    @ExceptionHandler(TackBusinessException.class)
    public ResponseEntity<?> handleTackBusinessException(TackBusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().name(), ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Getter
    public static class ErrorResponse {
        private final String errorCode;
        private final String message;

        public ErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

    }
}
