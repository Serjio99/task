package com.example.task.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "client")
@Builder
@EqualsAndHashCode(exclude = {"id", "firstName", "lastName", "middleName", "passportId"})
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "passport_id", nullable = false)
    private String passportId;

    @ManyToMany(mappedBy = "clients")
    private Set<Bank> banks = new HashSet<>();
}
