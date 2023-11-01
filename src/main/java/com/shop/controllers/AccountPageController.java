package com.shop.controllers;

import com.shop.StartGUI;
import com.shop.Utils.Utils;
import com.shop.classes.Administrator;
import com.shop.classes.Customer;
import com.shop.classes.Employee;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AccountPageController {
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
    private DatePicker birthDateField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;

    // <------ Customer ------>
    @FXML
    private Label registrationDateLabel;
    @FXML
    private DatePicker registrationDateField;

    // <------ Administrator ------>
    @FXML
    private Label academicDegreeLabel;
    @FXML
    private Label academicDegreeRed;
    @FXML
    private TextField academicDegreeField;

    // <------ Employee ------>
    @FXML
    private Label employmentDateLabel;
    @FXML
    private DatePicker employmentDateField;

    @FXML
    private Label salaryLabel;
    @FXML
    private Label salaryRed;
    @FXML
    private TextField salaryField;

    private User user;
    private Customer customer;
    private Employee employee;
    private Administrator administrator;
    private EntityManagerFactory entityManagerFactory;


    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        if (isType("Customer")) {
            customer = (Customer) user;
        } else if (isType("Administrator")) {
            administrator = (Administrator) user;
        } else if (isType("Employee")) {
            employee = (Employee) user;
        }

        showAppropriateFields();
        setFields();
    }

    private boolean isType(String type) {
        return user.getUserType().equals(type);
    }

    private void showAppropriateFields() {
        if (isType("Customer")) {
            registrationDateLabel.setVisible(true);
            registrationDateField.setVisible(true);
            registrationDateField.setDisable(true);
        } else if (isType("Administrator")) {
            academicDegreeLabel.setVisible(true);
            academicDegreeRed.setVisible(true);
            academicDegreeField.setVisible(true);
            salaryRed.setVisible(true);
        } else if (isType("Employee")) {
            employmentDateLabel.setVisible(true);
            employmentDateField.setVisible(true);
            employmentDateField.setDisable(true);
        }

        if (isType("Administrator") || isType("Employee")) {
            salaryLabel.setVisible(true);
            salaryField.setVisible(true);
            if (isType("Employee")) {
                salaryField.setDisable(true);
            }
        }
    }

    public void setFields() {
        nameField.setText(user.getName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        birthDateField.setValue(user.getBirthDate());
        phoneNumberField.setText(user.getPhoneNumber());
        addressField.setText(user.getAddress());

        if (isType("Customer")) {
            registrationDateField.setValue(customer.getRegistrationDate());
        } else if (isType("Administrator")) {
            academicDegreeField.setText(administrator.getAcademicDegree());
            salaryField.setText(String.valueOf(administrator.getSalary()));
        } else if (isType("Employee")) {
            employmentDateField.setValue(employee.getEmploymentDate());
            salaryField.setText(String.valueOf(employee.getSalary()));
        }
    }

    public void updateUser() {
        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        // TO DO: Check if all values are correct
        setNewUserFromFields();

        try {
            if (isType("Customer")) {
                crudHib.update(customer);
            } else if (isType("Administrator")) {
                crudHib.update(administrator);
            } else if (isType("Employee")) {
                crudHib.update(employee);
            }
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "User update", "User has been successfully updated.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "User update", "Error occured while updating user.");
        }

        setFields();
    }

    private void setNewUserFromFields() {
        user.setName(nameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setBirthDate(birthDateField.getValue());
        user.setPhoneNumber(phoneNumberField.getText());
        user.setAddress(addressField.getText());

        if (isType("Customer")) {
            customer = (Customer) user;
            customer.setRegistrationDate(registrationDateField.getValue());
        } else if (isType("Administrator")) {
            administrator = (Administrator) user;
            administrator.setAcademicDegree(academicDegreeField.getText());
            administrator.setSalary(Double.parseDouble(salaryField.getText()));
        } else if (isType("Employee")) {
            employee = (Employee) user;
            employee.setEmploymentDate(employmentDateField.getValue());
            employee.setSalary(Double.parseDouble(salaryField.getText()));
        }
    }

    public void deleteUser(){
        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            if (isType("Customer")) {
                crudHib.delete(Customer.class, customer.getID());
            } else if (isType("Administrator")) {
                crudHib.delete(Administrator.class, administrator.getID());
            } else if (isType("Employee")) {
                crudHib.delete(Employee.class, employee.getID());
            }
            // TO DO: Add (Yes, No) option in dialog box
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "User delete", "User has been successfully deleted.");
            loadLoginPage();
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "User delete", "Error occured while deleting user.");
        }
    }

    private void loadLoginPage() throws IOException {
        FXMLLoader fxmlLoader = Utils.getFXMLLoader("login.fxml");
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
