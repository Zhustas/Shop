package com.shop.controllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController { // Initializable is for initialize()
    @FXML
    private Label welcomeText;

    private EntityManagerFactory entityManagerFactory;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources){
//        entityManagerFactory = Persistence.createEntityManagerFactory("shop");
//    }
}
