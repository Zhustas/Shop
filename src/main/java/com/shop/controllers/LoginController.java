package com.shop.controllers;

import com.shop.StartGUI;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public void validateAndConnect() throws IOException {
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        User user = utilsHib.getUserByCredentials(usernameField.getText(), passwordField.getText());

        if (user != null){
            FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("main-shop.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainShopController mainShopController = fxmlLoader.getController();
            mainShopController.setData(entityManagerFactory, user);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setTitle("Shop");
            stage.setScene(scene);
            stage.show();
        } else {
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Attempt in connecting", "Check credentials, no such user exists.");
        }
    }

    public void loadRegistrationPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegisterController registerCustomerController = fxmlLoader.getController();
        registerCustomerController.setData(entityManagerFactory);
        Stage stage = (Stage) usernameField.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");
    }
}
