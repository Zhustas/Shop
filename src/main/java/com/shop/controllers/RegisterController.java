package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Administrator;
import com.shop.classes.Customer;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

public class RegisterController {
    @FXML
    private AnchorPane anchorPane;
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
        if (this.entityManagerFactory == null){
            this.entityManagerFactory = entityManagerFactory;
        }
    }

    public void openAdministratorFields(){
        academicDegreeLabel.setVisible(true);
        academicDegreeRed.setVisible(true);
        academicDegreeField.setVisible(true);
        salaryLabel.setVisible(true);
        salaryRed.setVisible(true);
        salaryField.setVisible(true);
    }

    public void closeAdministratorFields(){
        academicDegreeLabel.setVisible(false);
        academicDegreeRed.setVisible(false);
        academicDegreeField.setVisible(false);
        salaryLabel.setVisible(false);
        salaryRed.setVisible(false);
        salaryField.setVisible(false);
    }

    private boolean suitableToCreate(){
        if (nameField.getText().isEmpty()){ // Name is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Name field", "Name should not be empty.");
            return false;
        }
        if (lastNameField.getText().isEmpty()){ // Last name is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Last name field", "Last name should not be empty.");
            return false;
        }
        if (emailField.getText().isEmpty()){ // Email is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Email field", "Email should not be empty.");
            return false;
        }
        if (usernameField.getText().isEmpty()){ // Username is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Username field", "Username should not be empty.");
            return false;
        }
        if (passwordField.getText().isEmpty()){ // Password is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password field", "Password should not be empty.");
            return false;
        }
        if (!passwordField.getText().equals(repeatPasswordField.getText())){ // Passwords do not match
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password fields", "Passwords do not match.");
            return false;
        }
        if (administratorRButton.isSelected() && academicDegreeField.getText().isEmpty()){ // Academic degree is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Academic degree field", "Academic degree should not be empty.");
            return false;
        }
        if (administratorRButton.isSelected() && salaryField.getText().isEmpty()){ // Salary is empty
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary should not be empty.");
            return false;
        }
        double salary;
        if (administratorRButton.isSelected()){ // Salary is not numeric and lower than 0
            try {
                salary = Double.parseDouble(salaryField.getText());
            } catch (NumberFormatException e){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Wrong number.");
                return false;
            }
            if (salary <= 0.0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary should be higher than 0.");
                return false;
            }
        }

        return true;
    }

    public void createUser(){
        if (!suitableToCreate()){
            return;
        }

        User user;
        String type;

        String address = addressField.getText();
        if (address.isEmpty()){
            address = null;
        }
        String phoneNumber = phoneNumberField.getText();
        if (phoneNumber.isEmpty()){
            phoneNumber = null;
        }

        if (customerRButton.isSelected()){
            type = customerRButton.getText();
            user = new Customer(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), type, birthDateField.getValue(), phoneNumber, address, LocalDate.now());
        } else {
            double salary = Double.parseDouble(salaryField.getText());
            type = administratorRButton.getText();
            user = new Administrator(nameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText(), type, birthDateField.getValue(), phoneNumber, address, academicDegreeField.getText(), salary);
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        crudHib.create(user);
    }

    @FXML
    private void clearBirthDateField(){
        birthDateField.setValue(null);
    }

    public void loadLoginPage() throws IOException {
        Utils.loadLoginPage(anchorPane);
    }
}
