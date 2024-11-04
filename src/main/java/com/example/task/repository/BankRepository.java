package com.example.task.repository;

import com.example.task.data.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
    @Query("SELECT bankClient FROM bank bankClient JOIN bankClient.clients client WHERE client.id = :clientId")
    Optional<Bank> findByClientId(@Param("clientId") UUID clientId);
}
