package com.shop.Utils;

import com.shop.StartGUI;
import com.shop.classes.User;
import com.shop.controllers.AccountPageController;
import com.shop.controllers.MainShopController;
import com.shop.controllers.ProductPageController;
import com.shop.controllers.RegisterController;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
        TabPane tabPane = new TabPane();

        ArrayList<String> availablePages = new ArrayList<>();

        String buttonStyle = "-fx-font: 16 Calibri; -fx-background-color: #5089e6; -fx-cursor: hand;";

        availablePages.add("Shop");
        if (user.getUserType().equals("Administrator")){
            availablePages.add("Employees");
            availablePages.add("Products");
            availablePages.add("Warehouses");
        } else if (user.getUserType().equals("Employee")){
            availablePages.add("Products");
            availablePages.add("Warehouses");
        }
        availablePages.add("Account");

        for (String page : availablePages){
            Tab tab = new Tab(page);

            tab.setOnSelectionChanged(event -> {
                try {
                    if (tab.getText().equals("Shop")){
                        loadMainShopPage(entityManagerFactory, user, anchorPane);
                    }
                    if (tab.getText().equals("Products")){
                        loadProductPage(entityManagerFactory, user, anchorPane);
                    }
//                    if (tab.getText().)
                } catch (IOException e){
                    System.out.println("Error in loading page.");
                }
            });

            tabPane.getTabs().add(new Tab(page));

        }

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        anchorPane.getChildren().add(tabPane);
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
