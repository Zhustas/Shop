package com.shop.controllers;

import com.shop.StartGUI;
import com.shop.Utils.Utils;
import com.shop.classes.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainShopController {
    @FXML
    private Button accountButton;
    User user;
    private EntityManagerFactory entityManagerFactory;
    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
    }

    public void loadAccountPage() throws IOException {
        FXMLLoader fxmlLoader = Utils.getFXMLLoader("account-page.fxml");
        Scene scene = new Scene(fxmlLoader.load());
        AccountPageController accountPageController = fxmlLoader.getController();
        accountPageController.setData(entityManagerFactory, user);
        Stage stage = (Stage) accountButton.getScene().getWindow();
        stage.setTitle("Shop (account)");
        stage.setScene(scene);
        stage.show();
    }
}
