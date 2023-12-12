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
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String title;
    private double price;
    private String manufacturer;
    private String description;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Warehouse> warehouseList;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments;

    public void addComment(Comment comment){
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", description='" + description + '\'' +
                ", warehouseList=" + warehouseList +
                '}';
    }
}
