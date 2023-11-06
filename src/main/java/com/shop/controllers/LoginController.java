package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private EntityManagerFactory entityManagerFactory;

    public void validateAndConnect() throws IOException {
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        User user = utilsHib.getUserByCredentials(usernameField.getText(), Utils.encrypt(passwordField.getText()));

        if (user != null){
            Utils.loadMainShopPage(entityManagerFactory, user, anchorPane);
        } else {
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Attempt in connecting", "Check credentials, no such user exists.");
        }
    }

    public void loadRegistrationPage() throws IOException {
        Utils.loadRegistrationPage(entityManagerFactory, anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");
    }
}
