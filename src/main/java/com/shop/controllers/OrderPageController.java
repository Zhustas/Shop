package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Cart;
import com.shop.classes.Order;
import com.shop.classes.User;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class OrderPageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> idColumn, productsColumn, dateColumn, totalPriceColumn;

    private EntityManagerFactory entityManagerFactory;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadOrdersTable();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void loadOrdersTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Order> orders = utilsHib.getAllRecordsByID(Order.class, user.getID());

        ordersTable.setItems(FXCollections.observableList(orders));
    }
}
