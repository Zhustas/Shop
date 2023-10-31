package com.shop.controllers;

import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;

public class MainShopController {
    private User user;
    private EntityManagerFactory entityManagerFactory;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
    }
}
