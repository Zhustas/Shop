package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.Utils.UtilsChecking;
import com.shop.classes.Administrator;
import com.shop.classes.Cart;
import com.shop.classes.Customer;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private RadioButton customerRButton;
    @FXML
    private RadioButton administratorRButton;

    @FXML
    private Label academicDegreeLabel;
    @FXML
    private Label academicDegreeRed;
    @FXML
    private TextField academicDegreeField;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label salaryRed;
    @FXML
    private TextField salaryField;

    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    @FXML
    private void openAdministratorFields(){
        academicDegreeLabel.setVisible(true);
        academicDegreeRed.setVisible(true);
        academicDegreeField.setVisible(true);
        salaryLabel.setVisible(true);
        salaryRed.setVisible(true);
        salaryField.setVisible(true);
    }

    @FXML
    private void closeAdministratorFields(){
        academicDegreeLabel.setVisible(false);
        academicDegreeRed.setVisible(false);
        academicDegreeField.setVisible(false);
        salaryLabel.setVisible(false);
        salaryRed.setVisible(false);
        salaryField.setVisible(false);
    }

    private boolean suitableToCreate(){
        if (!UtilsChecking.isSuitable(nameField, lastNameField, emailField, usernameField, passwordField)){
            return false;
        }
        if (!passwordField.getText().equals(repeatPasswordField.getText())){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password fields", "Passwords do not match.");
            return false;
        }
        if (administratorRButton.isSelected() && academicDegreeField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Academic degree field", "Academic degree should not be empty.");
            return false;
        }
        if (administratorRButton.isSelected() && salaryField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary should not be empty.");
            return false;
        }
        if (administratorRButton.isSelected()){
            if (!UtilsChecking.isSuitableSalary(salaryField)){
                return false;
            }
        }

        return true;
    }

    @FXML
    private void createUser(){
        if (!suitableToCreate()){
            return;
        }

        String address = addressField.getText();
        if (address.isEmpty()){
            address = null;
        }
        String phoneNumber = phoneNumberField.getText();
        if (phoneNumber.isEmpty()){
            phoneNumber = null;
        }

        User user;
        String encryptedPassword = Utils.encrypt(passwordField.getText()), type;
        if (customerRButton.isSelected()){
            type = customerRButton.getText();
            user = new Customer(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), encryptedPassword, type, birthDateField.getValue(), phoneNumber, address, LocalDate.now());
        } else {
            double salary = Double.parseDouble(salaryField.getText());
            type = administratorRButton.getText();
            user = new Administrator(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), encryptedPassword, type, birthDateField.getValue(), phoneNumber, address, academicDegreeField.getText(), salary);
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            Cart cart = new Cart(LocalDate.now());
            crudHib.create(cart);

            user.setCart(cart);
            crudHib.create(user);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Registration", "User registration", "User successfully created.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Registration", "User registration", "Error in creating user.");
        }
    }

    @FXML
    private void clearBirthDateField(){
        birthDateField.setValue(null);
    }
}
