package com.example.task.controller;

import com.example.task.BaseTest;
import com.example.task.data.model.dto.PaymentResponse;
import com.example.task.exception.ErrorCode;
import com.example.task.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BankControllerTest extends BaseTest {

    @Autowired
    private BankRepository bankRepository;

    private final String BASE_URL = "/api";


    @ParameterizedTest
    @MethodSource("paymentDataProvider")
    @Sql(scripts = {"/sql/clear.sql", "/sql/init-table-client-bank.sql"})
    void paymentSuccess(String shop, BigDecimal amount) throws Exception {
        var employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        var bankCheck = bankRepository.findByClientId(employeeId).orElseThrow();

        var result = mockMvc.perform(
                        get(BASE_URL + "/payment/{shop}/{amount}", shop, amount)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var responseResult = objectMapper.readValue(result.getContentAsString(StandardCharsets.UTF_8), PaymentResponse.class);
        assertNotNull(responseResult);

        switch (shop) {
            case "online" -> assertEquals(bankCheck.getNonCash().subtract(amount), responseResult.getNonCash());
            case "shop" -> assertEquals(bankCheck.getCash().subtract(amount), responseResult.getCash());
        }
        assertNotNull(responseResult.getLastOperationBonus());
        assertNotNull(responseResult.getTotalBonusAccount());
        assertNotNull(responseResult.getLastOperationCommission());
        assertNotNull(responseResult.getTotalCommissionAccount());


    }

    static Stream<Arguments> paymentDataProvider() {
        return Stream.of(
                arguments("online", BigDecimal.valueOf(100)),
                arguments("shop", BigDecimal.valueOf(120)),
                arguments("online", BigDecimal.valueOf(301)),
                arguments("online", BigDecimal.valueOf(17)),
                arguments("shop", BigDecimal.valueOf(1000)),
                arguments("online", BigDecimal.valueOf(21)),
                arguments("shop", BigDecimal.valueOf(570)),
                arguments("online", BigDecimal.valueOf(700))

        );
    }

    @Test
    @Sql(scripts = {"/sql/clear.sql"})
    void paymentClientNotFound() throws Exception {

        mockMvc.perform(
                        get(BASE_URL + "/payment/{shop}/{amount}", "shop", BigDecimal.valueOf(100))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.USER_NOT_FOUND.name()))
                .andReturn()
                .getResponse();

    }

    @Test
    @Sql(scripts = {"/sql/clear.sql", "/sql/init-table-client-bank.sql"})
    void getBankAccountOfEMoneySuccess() throws Exception {
        var employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        var bankCheck = bankRepository.findByClientId(employeeId).orElseThrow();

        var result = mockMvc.perform(
                        get(BASE_URL + "/bankAccountOfEMoney")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var responseResult = objectMapper.readValue(result.getContentAsString(StandardCharsets.UTF_8), PaymentResponse.class);

        assertNotNull(responseResult);
        assertEquals(bankCheck.getTotalBonusAccount(), responseResult.getTotalBonusAccount());

    }

    @Test
    @Sql(scripts = {"/sql/clear.sql"})
    void getBankAccountOfEMoneyClientNotFound() throws Exception {

        mockMvc.perform(
                        get(BASE_URL + "/bankAccountOfEMoney")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.USER_NOT_FOUND.name()))
                .andReturn()
                .getResponse();

    }


    @Test
    @Sql(scripts = {"/sql/clear.sql", "/sql/init-table-client-bank.sql"})
    void getMoneySuccess() throws Exception {
        var employeeId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        var bankCheck = bankRepository.findByClientId(employeeId).orElseThrow();

        var result = mockMvc.perform(
                        get(BASE_URL + "/money")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var responseResult = objectMapper.readValue(result.getContentAsString(StandardCharsets.UTF_8), PaymentResponse.class);

        assertNotNull(responseResult);
        assertEquals(bankCheck.getCash(), responseResult.getCash());
        assertEquals(bankCheck.getNonCash(), responseResult.getNonCash());

    }

    @Test
    @Sql(scripts = {"/sql/clear.sql"})
    void getMoneyClientNotFound() throws Exception {

        mockMvc.perform(
                        get(BASE_URL + "/money")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.USER_NOT_FOUND.name()))
                .andReturn()
                .getResponse();


    }


}
