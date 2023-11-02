package com.shop.classes;

import java.util.Map;

public class Product {
    String title;
    double price;
    String description;

    Map<String, String> properties = null;

    // Warehouses -> where are those products (List).


    public Product(String title, double price, String description, Map<String, String> properties) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.properties = properties;
    }
}
