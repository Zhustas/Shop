package com.shop.controllers;

import com.shop.StartGUI;
import com.shop.classes.Customer;
import com.shop.classes.User;
import hibernateControllers.UserHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createUser(){
        Customer customer = new Customer(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), birthDateField.getValue(), phoneNumberField.getText(), addressField.getText(), LocalDate.now());
        userHib = new UserHib(entityManagerFactory);
        userHib.createUser(customer);
    }

    public void printNewCustomer(){
        Customer customer = new Customer(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), birthDateField.getValue(), phoneNumberField.getText(), addressField.getText(), LocalDate.now());

        System.out.println(customer);
    }

    public void loadLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) nameField.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
