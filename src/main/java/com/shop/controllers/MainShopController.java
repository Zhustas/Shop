package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Comment;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.List;

import com.shop.classes.Cart;

public class MainShopController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnDescription, productsColumnPrice;
    @FXML
    private TableView<Comment> commentsTable;
    @FXML
    private TableColumn<Comment, String> commentsColumnComments, commentsColumnPostedAt, commentsColumnUser;
    @FXML
    private TextArea commentField;
    private EntityManagerFactory entityManagerFactory;
    User user;
    Product selectedProduct;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadProductsTable();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    @FXML
    private void addToCart(){
        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "No product selected to add to a cart.");
            return;
        }

        Cart cart = UtilsHib.getEntityById(entityManagerFactory, Cart.class, user.getCart().getID());
        List<Product> productsInCart = cart.getProducts();

        for (Product product : productsInCart){
            if (product.getID() == selectedProduct.getID()){
                Utils.generateAlert(Alert.AlertType.INFORMATION, "Product", "Product select", "Product is already in a cart.");
                return;
            }
        }

        cart.getProducts().add(selectedProduct);
        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.update(cart);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Product", "Product select", "Product added to a cart.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Product", "Product select", "Error in adding product to a cart.");
        }
    }

    @FXML
    private void handleRowSelected(){
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        loadComments();
    }

    private void loadProductsTable(){
        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        productsTable.setItems(FXCollections.observableList(utilsHib.getAllRecords(Product.class)));
    }

    private void loadComments(){
        List<Comment> comments = selectedProduct.getComments();

        commentsColumnComments.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentsColumnPostedAt.setCellValueFactory(new PropertyValueFactory<>("postedAt"));
        commentsColumnUser.setCellValueFactory(new PropertyValueFactory<>("postedBy"));

        commentsTable.setItems(FXCollections.observableList(comments));
    }

    @FXML
    private void leaveAComment(){
        if (commentField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.WARNING, "Info", "Leaving a comment", "Comment is empty");
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            Comment comment = new Comment(commentField.getText(), LocalDate.now(), user);
            crudHib.create(comment);

            selectedProduct.addComment(comment);
            crudHib.update(selectedProduct);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Leaving a comment", "Error in leaving a comment");
        }

        commentField.setText("");
        loadComments();
    }
}
