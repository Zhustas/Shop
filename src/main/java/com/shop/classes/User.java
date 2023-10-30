package com.shop.classes;

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
    private long ID;               // Primary key

    private String name;           // Mandatory
    private String lastName;       // Mandatory
    private String email;          // Mandatory
    private String username;       // Mandatory
    private String password;       // Mandatory

    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
}
