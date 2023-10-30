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
public class Employee extends User {
    private long ID;                            // Primary key

    private long userID;                        // Foreign key
    private long employedByAdministratorID;     // Foreign key
    private double salary;                      // Mandatory
    private LocalDate employmentDate;           // Mandatory
}
