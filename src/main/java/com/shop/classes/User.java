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
    private LocalDate birthDate;
    private String email;          // Mandatory
    private String phoneNumber;
    private String username;       // Mandatory
    private String password;       // Mandatory
    private String userType;       // Mandatory

    public User(String name, String lastName, String email, String username, String password, String userType) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
}
