package com.shop.Utils;

import com.shop.StartGUI;
import com.shop.classes.Order;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.controllers.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public static void loadLoginPage(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadRegistrationPage(EntityManagerFactory entityManagerFactory) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RegisterController registerCustomerController = fxmlLoader.getController();
        registerCustomerController.setData(entityManagerFactory);
        Stage stage = new Stage();
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

    public static void loadEmployeePage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("employee-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EmployeePageController employeePageController = fxmlLoader.getController();
        employeePageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (employee)");
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

    public static void loadWarehousePage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("warehouse-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        WarehousePageController warehousePageController = fxmlLoader.getController();
        warehousePageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (warehouse)");
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

    public static void loadCartPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("cart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CartPageController cartPageController = fxmlLoader.getController();
        cartPageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (cart)");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadCartPageAfterPurchasing(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("cart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CartPageController cartPageController = fxmlLoader.getController();
        cartPageController.setDataAfterPurchase(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (cart)");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadOrderPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane, List<Product> products) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("order.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        OrderController orderController = fxmlLoader.getController();
        orderController.setData(entityManagerFactory, user, products);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Order");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadOrdersPage(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("order-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        OrderPageController orderPageController = fxmlLoader.getController();
        orderPageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setTitle("Shop (orders)");
        stage.setScene(scene);
        stage.show();
    }

    public static void determineMenu(EntityManagerFactory entityManagerFactory, User user, AnchorPane anchorPane){
        Pane pane = new Pane();
        pane.layoutXProperty().set(0);
        pane.layoutYProperty().set(0);
        pane.setPrefWidth(anchorPane.getPrefWidth());
        pane.setPrefHeight(40);
        pane.setStyle("-fx-background-color: ffffff;");
        anchorPane.getChildren().add(pane);

        double buttonX = 14;
        Button[] buttons = {new Button("Shop"), new Button("Employees"), new Button("Products"), new Button("Warehouses"), new Button("Cart"), new Button("Account"), new Button("Orders")};
        String buttonStyle = "-fx-font: 16 Calibri; -fx-background-color: #5089e6; -fx-cursor: hand;";

        ArrayList<Integer> availableButtons = new ArrayList<>();

        availableButtons.add(0);
        if (user.getUserType().equals("Administrator")){
            availableButtons.add(1);
            availableButtons.add(2);
            availableButtons.add(3);
        } else if (user.getUserType().equals("Employee")){
            availableButtons.add(2);
            availableButtons.add(3);
        }
        availableButtons.add(4);
        availableButtons.add(5);
        availableButtons.add(6);

        Button currentButton;
        for (Integer availableButton : availableButtons) {
            currentButton = createMenuButton(buttons[availableButton], buttonX, buttonStyle);
            Button finalButton = currentButton;
            currentButton.setOnAction(event -> {
                try {
                    if (finalButton.getText().equals("Shop")){
                        loadMainShopPage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Employees")){
                        loadEmployeePage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Products")){
                        loadProductPage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Warehouses")){
                        loadWarehousePage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Account")){
                        loadAccountPage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Cart")){
                        loadCartPage(entityManagerFactory, user, anchorPane);
                    } else if (finalButton.getText().equals("Orders")){
                        loadOrdersPage(entityManagerFactory, user, anchorPane);
                    }
                } catch (IOException e){
                    System.out.println("Error in opening page from menu list.");
                    e.printStackTrace();
                }
            });
            anchorPane.getChildren().add(currentButton);
            anchorPane.getScene().snapshot(null);

            buttonX = buttonX + currentButton.getLayoutBounds().getWidth() + 10;
        }
    }

    private static Button createMenuButton(Button button, double buttonX, String buttonStyle){
        button.setStyle(buttonStyle);
        button.setPadding(new Insets(5, 10, 5, 10));
        button.setLayoutX(buttonX);
        button.setLayoutY(6);

        return button;
    }
}
