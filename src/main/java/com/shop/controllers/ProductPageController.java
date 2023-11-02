package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProductPageController {
    @FXML
    private AnchorPane anchorPane;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
    }

    @FXML
    private void loadMainShopPage() throws IOException {
        Utils.loadMainShopPage(entityManagerFactory, user, anchorPane);
    }
    @FXML
    private void loadAccountPage() throws IOException {
        Utils.loadAccountPage(entityManagerFactory, user, anchorPane);
    }
}
