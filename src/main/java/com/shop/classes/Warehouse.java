package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String city;
    private String address;

    @ManyToMany(mappedBy = "worksAtWarehouse")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Employee> employees;

    @ManyToMany(mappedBy = "warehouseList")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> inStockProducts;

    public Warehouse(String title, String city, String address) {
        this.title = title;
        this.city = city;
        this.address = address;
    }

    @Override
    public String toString() {
        return title + ", " + city;
    }
}
