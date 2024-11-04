package com.example.task.data.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bank")
@EqualsAndHashCode(exclude = {"id", "cash", "nonCash", "lastOperationBonus", "totalBonusAccount", "lastOperationCommission"})
public class Bank {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "cash")
    private BigDecimal cash;

    @Column(name = "non_cash")
    private BigDecimal nonCash;

    @Column(name = "last_operation_bonus")
    private BigDecimal lastOperationBonus;

    @Column(name = "total_bonus_account")
    private BigDecimal totalBonusAccount;

    @Column(name = "last_operation_commission")
    private BigDecimal lastOperationCommission;

    @Column(name = "total_commission_account")
    private BigDecimal totalCommissionAccount;

    @ManyToMany
    @JoinTable(
            name = "bank_client",
            joinColumns = @JoinColumn(name = "bank_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> clients = new HashSet<>();

}
