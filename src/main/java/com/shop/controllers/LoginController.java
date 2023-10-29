package com.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    public void printInformation(){
        System.out.println(username.getText() + " " + password.getText());
    }
}
