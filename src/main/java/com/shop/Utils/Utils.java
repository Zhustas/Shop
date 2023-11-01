package com.shop.Utils;

import com.shop.StartGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

public class Utils {
    public static void generateAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static FXMLLoader getFXMLLoader(String name){
        return new FXMLLoader(StartGUI.class.getResource(name));
    }
}
