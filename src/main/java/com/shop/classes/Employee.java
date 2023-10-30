package com.shop.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "Employees")
public class Employee extends User {
    @Column(nullable = false)
    private long employedByUserID;              // Mandatory
    @Column(nullable = false)
    private double salary;                      // Mandatory
    @Column(nullable = false)
    private LocalDate employmentDate;           // Mandatory
}
