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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WarehousePageController implements Initializable {
    @FXML
    private TextField leftTitleField, rightTitleField;
    @FXML
    private TextField leftCityField, rightCityField;
    @FXML
    private TextField leftAddressField, rightAddressField;
    @FXML
    private TableView<Warehouse> warehousesTable;
    @FXML
    private TableColumn<Warehouse, String> warehousesColumnTitle, warehousesColumnCity, warehousesColumnAddress;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnPrice, productsColumnDescription;
    private Warehouse selectedWarehouse;
    EntityManagerFactory entityManagerFactory;
    User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        updateWarehousesTable();
    }

    @FXML
    private void createWarehouse(){
        if (!isSuitableToCreate()){
            return;
        }

        Warehouse newWarehouse = setNewWarehouseFromFields();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.create(newWarehouse);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "Create warehouse", "Successfully created new warehouse.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Create warehouse", "Error in creating warehouse.");
        }

        updateWarehousesTable();
    }

    @FXML
    private void updateWarehouse(){
        if (!isSuitableToUpdate()){
            return;
        }
        if (selectedWarehouse == null){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Error", "Update warehouse", "No warehouse selected to update.");
            return;
        }

        setToUpdateWarehouseFromFields();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.update(selectedWarehouse);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "Update warehouse", "Successfully updated warehouse.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Update warehouse", "Error in updating warehouse.");
        }

        clearRightFields();
        updateWarehousesTable();
    }

    @FXML
    private void deleteWarehouse(){
        if (selectedWarehouse == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete warehouse", "No warehouse is selected to delete.");
            return;
        }

        if (!Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "Warning", "Delete warehouse", "Do you really want to delete warehouse?")){
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        try {
            crudHib.delete(Warehouse.class, selectedWarehouse.getID());
            Utils.generateAlert(Alert.AlertType.WARNING, "Success", "Delete warehouse", "Successfully deleted warehouse.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete warehouse", "Error in deleting warehouse.");
        }

        updateWarehousesTable();
    }

    private void updateWarehousesTable(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        warehousesColumnTitle.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("title"));
        warehousesColumnCity.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("city"));
        warehousesColumnAddress.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("address"));

        warehousesTable.setItems(FXCollections.observableList(warehouses));
    }

    private Warehouse setNewWarehouseFromFields(){
        return new Warehouse(leftTitleField.getText(), leftCityField.getText(), leftAddressField.getText());
    }

    private void setToUpdateWarehouseFromFields(){
        selectedWarehouse.setTitle(rightTitleField.getText());
        selectedWarehouse.setCity(rightCityField.getText());
        selectedWarehouse.setAddress(rightAddressField.getText());
    }

    private boolean isSuitableToCreate(){
        if (leftTitleField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Title field", "Title should not be empty.");
            return false;
        }
        if (leftCityField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "City field", "City should not be empty.");
            return false;
        }
        if (leftAddressField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Address field", "Address should not be empty.");
            return false;
        }

        return true;
    }

    private boolean isSuitableToUpdate(){
        if (rightTitleField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Selected title field", "Selected warehouse title should not be empty.");
            return false;
        }
        if (rightCityField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Selected city field", "Selected warehouse city should not be empty.");
            return false;
        }
        if (rightAddressField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Selected address field", "Selected warehouse address should not be empty.");
            return false;
        }

        return true;
    }

    @FXML
    private void clearLeftFields(){
        leftTitleField.setText("");
        leftCityField.setText("");
        leftAddressField.setText("");
    }

    private void setRightFields(){
        rightTitleField.setText(selectedWarehouse.getTitle());
        rightCityField.setText(selectedWarehouse.getCity());
        rightAddressField.setText(selectedWarehouse.getAddress());
    }

    private void setProductsTable(){ // Select products, where warehouse_list_id = selected_warehouse_id
        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));

        productsTable.setItems(FXCollections.observableList(selectedWarehouse.getInStockProducts()));
    }

    private void clearRightFields(){
        rightTitleField.setText("");
        rightCityField.setText("");
        rightAddressField.setText("");
    }

    @FXML
    private void handleRowSelected(){
        selectedWarehouse = warehousesTable.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null){
            setRightFields();
            setProductsTable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");

        updateWarehousesTable();
    }
}
