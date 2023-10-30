package com.shop.controllers;

import com.shop.StartGUI;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private EntityManagerFactory entityManagerFactory;

    public void printInformation(){
        System.out.println(usernameField.getText() + " " + passwordField.getText());
    }

    public void loadCustomerRegistrationPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("registerCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegisterCustomerController registerCustomerController = fxmlLoader.getController();
        registerCustomerController.setData(entityManagerFactory);
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Register as Customer");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");
    }
}
