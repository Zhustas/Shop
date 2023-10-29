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
    private String name;           // Mandatory
    private String lastName;       // Mandatory
    private LocalDate birthDate;   // Mandatory
    private String email;          // Mandatory
    private String phoneNumber;
    private String username;       // Mandatory
    private String password;       // Mandatory

    public User(String name, String lastName, LocalDate birthDate, String email, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
