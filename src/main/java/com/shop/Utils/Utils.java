package com.shop.Utils;

import com.shop.StartGUI;
import com.shop.classes.User;
import com.shop.controllers.AccountPageController;
import com.shop.controllers.MainShopController;
import com.shop.controllers.RegisterController;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Utils {
    public static void generateAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean generateDialogBox(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void loadLoginPage(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadRegistrationPage(EntityManagerFactory entityManagerFactory, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegisterController registerCustomerController = fxmlLoader.getController();
        registerCustomerController.setData(entityManagerFactory);
        Stage stage = (Stage) anchorPane.getScene().getWindow(); // Getting current stage, so that scene would be drawn on top of it
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadMainShopPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("main-shop.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainShopController mainShopController = fxmlLoader.getController();
        mainShopController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadAccountPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("account-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AccountPageController accountPageController = fxmlLoader.getController();
        accountPageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (account)");
        stage.setScene(scene);
        stage.show();
    }
}
