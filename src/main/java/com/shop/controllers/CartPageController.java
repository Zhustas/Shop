package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Cart;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartPageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label totalPrice;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnDescription, productsColumnPrice;
    EntityManagerFactory entityManagerFactory;
    User user;
    Product selectedProduct;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadProductsInCart();
        setTotalPrice();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    public void setDataAfterPurchase(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        deleteAllItemsInCart();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void deleteAllItemsInCart(){
        Cart cart = UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID());
        List<Product> products = cart.getProducts();
        products.clear();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.update(cart);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Removing products", "Error in removing products from cart.");
        }
    }

    @FXML
    private void removeFromCart(){
        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "No product selected to remove from a cart.");
            return;
        }

        Cart cart = UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID());
        List<Product> products = cart.getProducts();
        for (Product product : products){
            if (product.getID() == selectedProduct.getID()){
                products.remove(product);
                break;
            }
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.update(cart);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Removing product", "Error in removing product from cart.");
        }

        loadProductsInCart();
        setTotalPrice();
    }

    @FXML
    private void handleRowSelected(){
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
    }

    private void loadProductsInCart(){
        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        Cart cart = UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID());

        productsTable.setItems(FXCollections.observableList(cart.getProducts()));
    }

    private void setTotalPrice(){
        List<Product> products = UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID()).getProducts();
        double total = 0.0;
        for (Product product : products){
            total += product.getPrice();
        }
        totalPrice.setText("Total price: " + String.valueOf(total));
    }

    @FXML
    private void buy() throws IOException {
        Utils.loadOrderPage(entityManagerFactory, user, anchorPane, UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID()).getProducts());
    }
}
