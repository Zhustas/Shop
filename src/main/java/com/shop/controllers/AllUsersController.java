package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AllUsersController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> idColumn, userTypeColumn, usernameColumn, nameColumn, lastNameColumn, emailColumn;

    private EntityManagerFactory entityManagerFactory;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        loadUsersTable();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void loadUsersTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        usersTable.setItems(FXCollections.observableList(utilsHib.getAllRecords(User.class)));
    }
}
