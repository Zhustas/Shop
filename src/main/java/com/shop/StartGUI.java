package com.shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("employee-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Employee");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // TODO:
    //  employee page - NOT DONE
    //  warehouse page - NOT DONE
    //  product page - NOT DONE (Doing now)
    //   add product area - DONE
    //   all products area - DONE (create, update, delete works)
    //   Just do optimizations - NOT DONE
}
