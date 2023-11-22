package com.shop.classes;

import jakarta.persistence.Entity;
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
public class Administrator extends User {
    private String academicDegree;
    private double salary;

    public Administrator(String name, String lastName, String email, String username, String password, String userType, LocalDate birthDate, String phoneNumber, String address, String academicDegree, double salary) {
        super(name, lastName, email, username, password, userType, birthDate, phoneNumber, address);
        this.academicDegree = academicDegree;
        this.salary = salary;
    }
}
