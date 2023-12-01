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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class CartPageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnDescription, productsColumnPrice;
    EntityManagerFactory entityManagerFactory;
    User user;
    List<Product> productsInCart = new ArrayList<>();
    Product selectedProduct;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadProductsInCart();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    @FXML
    private void removeFromCart(){
        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "No product selected to remove from a cart.");
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.delete(Cart.class, getCartID(user.getID(), selectedProduct.getID()));
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Removing product", "Error in removing product from cart.");
        }

        loadProductsInCart();
    }

    private long getCartID(long userID, long productID){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);

        List<Cart> carts = utilsHib.getAllRecords(Cart.class);
        for (Cart cart : carts){
            if (cart.getUserID() == userID && cart.getProductID() == productID){
                return cart.getID();
            }
        }
        return 0;
    }

    @FXML
    private void handleRowSelected(){
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
    }

    private void loadProductsInCart(){
        if (!productsInCart.isEmpty()){
            productsInCart.clear();
        }

        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Cart> carts = utilsHib.getAllRecords(Cart.class);
        for (Cart cart : carts){
            if (cart.getUserID() == user.getID()){
                productsInCart.add(utilsHib.getEntityById(Product.class, cart.getProductID()));
            }
        }
        productsTable.setItems(FXCollections.observableList(productsInCart));
    }
}
