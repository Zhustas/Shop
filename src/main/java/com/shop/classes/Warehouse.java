package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private String city;
    private String address;

    @ManyToMany(mappedBy = "worksAtWarehouse")
    private List<Employee> employees;

    @ManyToMany(mappedBy = "warehouseList")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> inStockProducts;
}
