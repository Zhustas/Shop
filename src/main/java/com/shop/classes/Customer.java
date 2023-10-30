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

    public Customer(String name, String lastName, String email, String username, String password, LocalDate birthDate, String phoneNumber, String address, LocalDate registrationDate) {
        super(name, lastName, email, username, password, birthDate, phoneNumber, address);
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "registrationDate=" + registrationDate +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
