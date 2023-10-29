module com.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.shop to javafx.fxml;
    exports com.shop;
    exports com.shop.controllers;
    opens com.shop.controllers to javafx.fxml;
}