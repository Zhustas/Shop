package com.shop.Utils;

import com.shop.StartGUI;
import com.shop.classes.User;
import com.shop.controllers.AccountPageController;
import com.shop.controllers.MainShopController;
import com.shop.controllers.ProductPageController;
import com.shop.controllers.RegisterController;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String encrypt(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger bi = new BigInteger(1, messageDigest);

            return bi.toString(16);
        } catch (NoSuchAlgorithmException e){
            System.out.println("No such algorithm");
        }
        return null;
    }

    public static void enableMenuList(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane){
        Pane pane = new Pane();

        pane.layoutXProperty().set(0);
        pane.layoutYProperty().set(0);
        pane.setPrefWidth(500);
        pane.setPrefHeight(40);
        pane.setStyle("-fx-background-color: ffffff;");

        anchorPane.getChildren().add(pane);

        double buttonWidth = 76, buttonHeight = 28, buttonX = 14, buttonY = 6;
        String buttonStyle = "-fx-font: 16 Calibri; -fx-background-color: #5089e6; -fx-cursor: hand;";

        Button mainShopButton = new Button("Shop");
        mainShopButton.setPrefWidth(buttonWidth);
        mainShopButton.setPrefHeight(buttonHeight);
        mainShopButton.setLayoutX(buttonX);
        mainShopButton.setLayoutY(buttonY);
        mainShopButton.setStyle(buttonStyle);

        mainShopButton.setOnMouseClicked(mouseEvent -> {
            try {
                loadMainShopPage(entityManagerFactory, user, anchorPane);
            } catch (IOException e){
                System.out.println("Caught IOException in Utils.java while loading main shop page.");
            }
        });

        anchorPane.getChildren().add(mainShopButton);
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

    public static void loadProductPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("product-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProductPageController productPageController = fxmlLoader.getController();
        productPageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (product)");
        stage.setScene(scene);
        stage.show();
    }
}
