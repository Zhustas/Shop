package com.shop.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UtilsChecking {
    public static boolean isSuitable(TextField nameField, TextField lastNameField, TextField emailField, TextField usernameField, TextField passwordField){
        if (nameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Name field", "Name should not be empty.");
            return false;
        }
        if (lastNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Last name field", "Last name should not be empty.");
            return false;
        }
        if (emailField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Email field", "Email should not be empty.");
            return false;
        }
        if (usernameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Username field", "Username should not be empty.");
            return false;
        }
        if (passwordField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password field", "Password should not be empty.");
            return false;
        }

        return true;
    }

    public static boolean isSuitableSalary(TextField salaryField){
        double salary;
        try {
            salary = Double.parseDouble(salaryField.getText());
            if (salary <= 0.0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary should be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Wrong number.");
            return false;
        }

        return true;
    }
}
