package com.shop.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Administrator extends User {
    private long ID;                                  // Primary key

    private long userID;                              // Foreign key
    private double salary;                            // Mandatory
}
