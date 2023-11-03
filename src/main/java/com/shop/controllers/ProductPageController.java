package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.classes.Warehouse;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField titleField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private ListView<Warehouse> warehouseList;

    private EntityManagerFactory entityManagerFactory;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
    }

    @FXML
    private void createProduct(){

    }

    @FXML
    private void updateProduct(){

    }

    @FXML
    private void deleteProduct(){

    }

    @FXML
    private void clearFields(){
        titleField.setText(null);
        manufacturerField.setText(null);
        priceField.setText(null);
        descriptionField.setText(null);
        warehouseList.getSelectionModel().clearSelection();
    }

    private void loadProductsTable(){
        // Load all products from database
    }

    @FXML
    private void loadMainShopPage() throws IOException {
        Utils.loadMainShopPage(entityManagerFactory, user, anchorPane);
    }
    @FXML
    private void loadAccountPage() throws IOException {
        Utils.loadAccountPage(entityManagerFactory, user, anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadProductsTable();
    }
}
