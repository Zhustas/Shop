package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private long userID;
    private String username;
    private String products;
    private LocalDateTime orderDate;
    private double totalPrice;
    private String status;

    public Order(long userID, String username, String products, LocalDateTime orderDate, double totalPrice, String status) {
        this.userID = userID;
        this.username = username;
        this.products = products;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
