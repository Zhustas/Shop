package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;                 // Primary key

    @Column(nullable = false)
    protected String name;           // Mandatory
    @Column(nullable = false)
    protected String lastName;       // Mandatory
    @Column(nullable = false)
    protected String email;          // Mandatory
    @Column(unique = true, nullable = false)
    protected String username;       // Mandatory
    @Column(nullable = false)
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
