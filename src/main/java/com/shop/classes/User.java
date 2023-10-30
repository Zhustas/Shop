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

    protected String name;           // Mandatory
    protected String lastName;       // Mandatory
    protected String email;          // Mandatory
    protected String username;       // Mandatory
    protected String password;       // Mandatory

    protected LocalDate birthDate;
    protected String phoneNumber;
    protected String address;

    public User(String name, String lastName, String email, String username, String password, LocalDate birthDate, String phoneNumber, String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
