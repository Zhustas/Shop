module com.shop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.shop to javafx.fxml;
    exports com.shop;
}