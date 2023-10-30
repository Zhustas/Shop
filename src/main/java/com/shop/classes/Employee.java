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
public class Employee extends User {
    private int id;

    private LocalDate employmentDate;   // Mandatory
    private double salary;              // Mandatory
    private int administratorID;        // Mandatory
}
