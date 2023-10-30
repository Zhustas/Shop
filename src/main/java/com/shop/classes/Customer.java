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
public class Customer extends User {
    private long ID;                       // Primary key

    private long userID;                   // Foreign key
    private LocalDate registrationDate;    // Mandatory
}
