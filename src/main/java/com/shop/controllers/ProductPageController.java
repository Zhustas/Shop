package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.classes.Warehouse;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    @FXML
    private TextField leftTitleField;
    @FXML
    private TextField leftManufacturerField;
    @FXML
    private TextField leftPriceField;
    @FXML
    private TextArea leftDescriptionField;
    @FXML
    private ListView<Warehouse> leftWarehouseList;

    private EntityManagerFactory entityManagerFactory;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;
    }

    @FXML
    private void createProduct(){
        if (!isSuitableToCreate()){
            return;
        }

        Product newProduct = setNewProductFromFields();
        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.create(newProduct);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "Create product", "Successfully created product.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Create product", "Error in creating product.");
        }
    }

    @FXML
    private void updateProduct(){

    }

    @FXML
    private void deleteProduct(){

    }

    private boolean isSuitableToCreate(){
        if (leftTitleField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Title field", "Title field should not be empty.");
            return false;
        }
        if (leftManufacturerField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Manufacturer field", "Manufacturer field should not be empty.");
            return false;
        }
        if (leftPriceField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Price field should not be empty.");
            return false;
        }
        try {
            double price = Double.parseDouble(leftPriceField.getText());
            if (price <= 0.0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Price should be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Wrong number format.");
            return false;
        }
        if (leftDescriptionField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Description field", "Description field should not be empty.");
            return false;
        }
        if (leftWarehouseList.getSelectionModel().getSelectedItems().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Warehouse field", "At least one warehouse has to be selected.");
            return false;
        }

        return true;
    }

    private Product setNewProductFromFields(){
        Product newProduct = new Product();

        newProduct.setTitle(leftTitleField.getText());
        newProduct.setManufacturer(leftManufacturerField.getText());
        newProduct.setPrice(Double.parseDouble(leftPriceField.getText()));
        newProduct.setDescription(leftDescriptionField.getText());

        List<Warehouse> selectedWarehouses = leftWarehouseList.getSelectionModel().getSelectedItems();
        newProduct.setWarehouseList(selectedWarehouses);

        return newProduct;
    }

    private void loadLeftWarehouseList(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        leftWarehouseList.setItems(FXCollections.observableList(warehouses));
    }

    @FXML
    private void clearLeftFields(){
        leftTitleField.setText("");
        leftManufacturerField.setText("");
        leftPriceField.setText("");
        leftDescriptionField.setText("");
        deselectLeftWarehouseList();
    }

    @FXML
    private void deselectLeftWarehouseList(){
        leftWarehouseList.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearRightWarehouseList(){

    }

    @FXML
    private void loadMainShopPage(){

    }

    @FXML
    private void loadAccountPage(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");

        leftWarehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadLeftWarehouseList();
    }
}
