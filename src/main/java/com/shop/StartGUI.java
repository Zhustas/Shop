package com.shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Login");
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("warehouse-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Warehouse");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // TODO:
    //  employee page - NOT DONE
    //  warehouse page - NOT DONE (Doing now)
    //  product page - NOT DONE
}
