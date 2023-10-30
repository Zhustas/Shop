package com.shop.classes;

import jakarta.persistence.Column;
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
public class Customer extends User {
    private LocalDate registrationDate;    // Mandatory

    public Customer(String name, String lastName, String email, String username, String password, LocalDate birthDate, String phoneNumber, String address, LocalDate registrationDate) {
        super(name, lastName, email, username, password, birthDate, phoneNumber, address);
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                "registrationDate=" + registrationDate +
                '}';
    }
}
