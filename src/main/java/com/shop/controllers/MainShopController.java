package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainShopController {
    @FXML
    private AnchorPane anchorPane;
    User user;
    private EntityManagerFactory entityManagerFactory;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        //Utils.enableMenuList(entityManagerFactory, user, anchorPane);
    }

    public void loadAccountPage() throws IOException {
        Utils.loadAccountPage(entityManagerFactory, user, anchorPane);
    }

    @FXML
    private void loadProductPage() throws  IOException {
        Utils.loadProductPage(entityManagerFactory, user, anchorPane);
    }

    public void check(){
        if (Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "a", "b", "c")){
            System.out.println("LOL");
        } else {
            System.out.println("NOT LOL");
        }
        System.out.println(user);
    }
}
