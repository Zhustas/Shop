package com.shop.classes;

import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class User {
    private String name;           // Mandatory
    private String lastName;       // Mandatory
    private LocalDate birthDate;
    private String email;          // Mandatory
    private String phoneNumber;
    @Column(unique = true)
    private String username;       // Mandatory
    private String password;       // Mandatory
    private String userType;       // Mandatory
}
