package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private LocalDate dateCreated;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    public Cart(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
}
