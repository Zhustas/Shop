package com.shop.Utils;

import javafx.scene.control.Alert;

public class Utils {
    public static boolean passedRegistration;
    public static void generateAlert(Alert.AlertType alertType, String title, String content){
        passedRegistration = false;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
