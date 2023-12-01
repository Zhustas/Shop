package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

import com.shop.classes.Cart;

public class MainShopController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnDescription, productsColumnPrice;
    private EntityManagerFactory entityManagerFactory;
    User user;
    ArrayList<Long> productsIDS = new ArrayList<>();
    Product selectedProduct;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadProductsIDSInCart();
        loadProductsTable();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    @FXML
    private void addToCart(){
        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "No product selected to add to a cart.");
            return;
        }

        if (productsIDS.contains(selectedProduct.getID())){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Product", "Product select", "Product is already in a cart.");
        } else {
            CRUDHib crudHib = new CRUDHib(entityManagerFactory);
            try {
                crudHib.create(new Cart(user.getID(), selectedProduct.getID()));
                Utils.generateAlert(Alert.AlertType.INFORMATION, "Product", "Product select", "Product added to a cart.");
                loadProductsIDSInCart();
            } catch (Exception e){
                Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "Error in adding product to a cart.");
            }
        }
    }

    private void loadProductsIDSInCart(){
        if (!productsIDS.isEmpty()){
            productsIDS.clear();
        }

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);

        List<Cart> carts = utilsHib.getAllRecords(Cart.class);
        for (Cart cart : carts){
            if (cart.getUserID() == user.getID()){
                productsIDS.add(cart.getProductID());
            }
        }
    }

    @FXML
    private void handleRowSelected(){
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
    }

    private void loadProductsTable(){
        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        productsTable.setItems(FXCollections.observableList(utilsHib.getAllRecords(Product.class)));
    }
}
