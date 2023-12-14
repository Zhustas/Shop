package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PurchaseController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField cardholderNameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private DatePicker expirationDateField;
    @FXML
    private TextField cvcField;
    @FXML
    private ListView<String> productsList;

    EntityManagerFactory entityManagerFactory;
    User user;
    String products;
    long orderID;
    public void setData(EntityManagerFactory entityManagerFactory, User user, String products, long ID){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
        this.products = products;
        this.orderID = ID;

        loadProducts();
    }

    private void loadProducts(){
        productsList.getItems().add(products);
    }

    @FXML
    private void clearExpirationDate(){
        expirationDateField.setValue(null);
    }

    @FXML
    private void pay() throws IOException {
        if (canPay()){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Information", "Congratulations", "Your purchase was successful. We will start preparing delivery shortly.");
            Utils.loadOrdersPageAfterPurchasing(entityManagerFactory, user, anchorPane, orderID);
        }

        /*String productsString = "";

        double totalPrice = 0.0;
        for (Product product : products){
            totalPrice += product.getPrice();
            productsString += (product.getID() + " " + product.getTitle() + " " + product.getPrice() + "\n");
        }
        Order order = new Order(user.getID(), user.getUsername(), productsString, LocalDateTime.now(), totalPrice, );

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.create(order);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Information", "Congratulations", "Your purchase was successful. We will start preparing delivery shortly.");
            Utils.loadCartPageAfterPurchasing(entityManagerFactory, user, anchorPane);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Attempt in purchasing", "There was an error while attempting to purchase.");
        }*/
    }

    @FXML
    private void cancel() throws IOException {
        Utils.loadOrdersPage(entityManagerFactory, user, anchorPane);
    }

    private boolean canPay(){
        if (cardholderNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Cardholder name", "Cardholder name can't be empty.");
            return false;
        }
        if (cardNumberField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Card number", "Card number can't be empty.");
            return false;
        }
        if (cvcField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "CVC", "CVC can't be empty.");
            return false;
        }
        if (expirationDateField.getValue() == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Expiration date", "Expiration date can't be empty.");
            return false;
        }
        if (expirationDateField.getValue().isBefore(LocalDate.now())){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Expired card", "Your card is expired.");
            return false;
        }

        return true;
    }
}
