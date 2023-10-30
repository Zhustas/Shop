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
public class Employee extends User {
    private long employedByAdministratorID;     // Foreign key
    private double salary;                      // Mandatory
    private LocalDate employmentDate;           // Mandatory
}
