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
public class Employee extends User {
    private LocalDate employmentDate;
    private long employedByID;
    private double salary;

    public Employee(String name, String lastName, String email, String username, String password, String userType, LocalDate birthDate, String phoneNumber, String address, LocalDate employmentDate, long employedByID, double salary) {
        super(name, lastName, email, username, password, userType, birthDate, phoneNumber, address);
        this.employmentDate = employmentDate;
        this.employedByID = employedByID;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", employmentDate=" + employmentDate +
                ", employedByID=" + employedByID +
                ", salary=" + salary +
                '}';
    }
}
