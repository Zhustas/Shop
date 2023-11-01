package com.shop.controllers;

import com.shop.classes.Administrator;
import com.shop.classes.Customer;
import com.shop.classes.Employee;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class MainShopController {
    private User user;
    private Customer customer;
    private Employee employee;
    private Administrator administrator;
    private EntityManagerFactory entityManagerFactory;


    // <----------------------------------------- Account Tab ----------------------------------------->
    @FXML
    private TextField accountNameField;
    @FXML
    private TextField accountLastNameField;
    @FXML
    private TextField accountEmailField;
    @FXML
    private TextField accountUsernameField;
    @FXML
    private TextField accountPasswordField;
    @FXML
    private DatePicker accountBirthDateField;
    @FXML
    private TextField accountPhoneNumberField;
    @FXML
    private TextField accountAddressField;

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

    @FXML
    private DatePicker employmentDateField;
    @FXML
    private TextField employedByIDField;


    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        if (isType("Customer")){
            customer = (Customer) user;
        } else if (isType("Administrator")){
            administrator = (Administrator) user;
        } else {
            employee = (Employee) user;
        }

        limitAccess();
        determineAccountTab();
    }

    private boolean isType(String type){
        return user.getUserType().equals(type);
    }

    private void limitAccess(){
        // For tabs
    }

    private void determineAccountTab(){
        if (isType("Administrator") || isType("Employee")){
            academicDegreeLabel.setVisible(true);
            academicDegreeRed.setVisible(true);
            academicDegreeField.setVisible(true);
            salaryLabel.setVisible(true);
            salaryRed.setVisible(true);
            salaryField.setVisible(true);
        }
    }

    public void printUser(){
        System.out.println(user);
    }

    public void printSMT(){
        System.out.println("SMT");
    }
}
