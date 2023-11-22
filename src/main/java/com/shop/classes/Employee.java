package com.shop.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends User {
    private LocalDate employmentDate;
    private long employedByID;
    private double salary;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Warehouse> worksAtWarehouse;

    public Employee(String name, String lastName, String email, String username, String password, String userType, LocalDate birthDate, String phoneNumber, String address, LocalDate employmentDate, long employedByID, double salary) {
        super(name, lastName, email, username, password, userType, birthDate, phoneNumber, address);
        this.employmentDate = employmentDate;
        this.employedByID = employedByID;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return name + " " + lastName + ", " + email;
    }
}
