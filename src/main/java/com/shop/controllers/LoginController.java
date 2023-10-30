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
    private TextField username;
    @FXML
    private PasswordField password;

    public void printInformation(){
        System.out.println(username.getText() + " " + password.getText());
    }

    public void loadRegistrationPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) username.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }
}
