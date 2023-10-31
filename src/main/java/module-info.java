module com.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;


    opens com.shop to javafx.fxml;
    exports com.shop;
    exports com.shop.controllers;
    opens com.shop.controllers to javafx.fxml;
    opens com.shop.classes to org.hibernate.orm.core;
    exports com.shop.classes;
}
