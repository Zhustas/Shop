package com.shop.controllers;

import com.shop.StartGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void printInformation(){
        System.out.println(usernameField.getText() + " " + passwordField.getText());
    }

    public void loadCustomerRegistrationPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("registerCustomer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Register as Customer");
        stage.setScene(scene);
        stage.show();
    }
}
