package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainShopController {
    @FXML
    private AnchorPane anchorPane;
    User user;
    private EntityManagerFactory entityManagerFactory;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }
}
