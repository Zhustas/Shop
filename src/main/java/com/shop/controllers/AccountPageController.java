package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.Utils.UtilsChecking;
import com.shop.classes.*;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountPageController {
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

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
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
        passwordField.setText("");
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
        if (!suitableToCreate()){
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

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
        user.setPassword(Utils.encrypt(passwordField.getText()));
        user.setBirthDate(birthDateField.getValue());

        String address = addressField.getText();
        if (address != null){
            if (address.isEmpty()){
                user.setAddress(null);
            } else {
                user.setAddress(address);
            }
        } else {
            user.setAddress(null);
        }

        String phoneNumber = phoneNumberField.getText();
        if (phoneNumber != null){
            if (phoneNumber.isEmpty()){
                user.setPhoneNumber(null);
            } else {
                user.setPhoneNumber(phoneNumber);
            }
        } else {
            user.setPhoneNumber(null);
        }

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

    private boolean suitableToCreate(){
        if (!UtilsChecking.isSuitable(nameField, lastNameField, emailField, usernameField, passwordField)){
            return false;
        }
        if (isType("Administrator") && academicDegreeField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Academic degree field", "Academic degree should not be empty.");
            return false;
        }
        if (isType("Administrator") && salaryField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary should not be empty.");
            return false;
        }

        if (isType("Administrator")){
            if (!UtilsChecking.isSuitableSalary(salaryField)){
                return false;
            }
        }

        return true;
    }

    public void deleteUser(){
        if (!Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "Confirm", "Confirm deletion", "Are you sure you want to delete user?")){
            return;
        }

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Product> products = utilsHib.getAllRecords(Product.class);

        List<Long> IDsToRemove = new ArrayList<>();
        for (Product product : products){
            for (Comment comment : product.getComments()){
                if (comment.getPostedBy().getID() == user.getID()){
                    //product.removeComment(comment.getID());
                    IDsToRemove.add(comment.getID());
                }
            }
            for (Long ID : IDsToRemove){
                product.removeComment(ID);
            }
            IDsToRemove.clear();
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            for (Product product : products){
                crudHib.update(product);
            }
            List<Comment> comments = utilsHib.getAllRecords(Comment.class);
            for (Comment comment : comments){
                if (comment.getPostedBy().getID() == user.getID()){
                    crudHib.delete(Comment.class, comment.getID());
                }
            }

            if (isType("Customer")) {
                crudHib.delete(Customer.class, customer.getID());
            } else if (isType("Administrator")) {
                crudHib.delete(Administrator.class, administrator.getID());
            } else if (isType("Employee")) {
                crudHib.delete(Employee.class, employee.getID());
            }
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "User delete", "User has been successfully deleted.");
            loadLoginPage();
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "User delete", "Error occured while deleting user.");
        }
    }

    @FXML
    private void clearBirthDateField(){
        birthDateField.setValue(null);
    }

    private void loadLoginPage() throws IOException {
        Utils.loadLoginPage(anchorPane);
    }

    @FXML
    private void logout(){
        try {
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "Log out", "User has been logged out.");
            loadLoginPage();
        } catch (IOException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Log out", "There was an error in logging out.");
        }
    }
}
