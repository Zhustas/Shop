package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.classes.Warehouse;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

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

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productsColumnTitle, productsColumnManufacturer, productsColumnPrice, productsColumnDescription, productsColumnWarehouse;
    Product selectedProduct;

    @FXML
    private TextField rightTitleField;
    @FXML
    private TextField rightManufacturerField;
    @FXML
    private TextField rightPriceField;
    @FXML
    private TextArea rightDescriptionField;
    @FXML
    private ListView<Warehouse> rightWarehouseList;

    private EntityManagerFactory entityManagerFactory;
    User user;

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

        updateProductsTable();
    }

    @FXML
    private void updateProduct(){
        if (!isSuitableToUpdate()){
            return;
        }

        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Error", "Update product", "No product selected to update.");
            return;
        }

        setToUpdateProductFromFields();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.update(selectedProduct);
            Utils.generateAlert(Alert.AlertType.WARNING, "Success", "Update product", "Successfully updated product.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Update product", "Error in updating product.");
        }

        updateProductsTable();
    }

    @FXML
    private void deleteProduct(){
        if (selectedProduct == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete product", "No product is selected to delete.");
            return;
        }

        if (!Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "Warning", "Delete product", "Do you really want to delete product?")){
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.delete(Product.class, selectedProduct.getID());
            Utils.generateAlert(Alert.AlertType.WARNING, "Success", "Delete product", "Successfully deleted product.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete product", "Error in deleting product.");
        }

        updateProductsTable();
    }

    private void setToUpdateProductFromFields(){
        selectedProduct.setTitle(rightTitleField.getText());
        selectedProduct.setManufacturer(rightManufacturerField.getText());
        selectedProduct.setPrice(Double.parseDouble(rightPriceField.getText()));
        selectedProduct.setDescription(rightDescriptionField.getText());
        selectedProduct.setWarehouseList(rightWarehouseList.getSelectionModel().getSelectedItems());
    }

    private void updateProductsTable(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Product> products = utilsHib.getAllRecords(Product.class);

        productsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        productsColumnManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        productsColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        productsColumnWarehouse.setCellValueFactory(
                (TableColumn.CellDataFeatures<Product, String> p) ->
                {
                    List<Warehouse> warehouses = p.getValue().getWarehouseList();
                    String value = "";
                    if (warehouses.size() == 1){
                        value = value + warehouses.get(0).getTitle() + ", " + warehouses.get(0).getCity();
                    } else {
                        int number = 1;
                        for (int i = 0; i < warehouses.size(); i++){
                            value = value + number + ". " + warehouses.get(i).getTitle() + ", " + warehouses.get(i).getCity();
                            number++;
                            if (i != warehouses.size() - 1){
                                value = value + "\n";
                            }
                        }
                    }
                    return new ReadOnlyStringWrapper(value);
                }
        );

        productsTable.setItems(FXCollections.observableList(products));
    }

    private void setRightFields(){
        rightTitleField.setText(selectedProduct.getTitle());
        rightManufacturerField.setText(selectedProduct.getManufacturer());
        rightPriceField.setText(String.valueOf(selectedProduct.getPrice()));
        rightDescriptionField.setText(selectedProduct.getDescription());

        deselectRightWarehouseList();
        for (Warehouse w : selectedProduct.getWarehouseList()){
            for (int i = 0; i < rightWarehouseList.getItems().size(); i++){
                if (w.getID() == rightWarehouseList.getItems().get(i).getID()){
                    rightWarehouseList.getSelectionModel().select(i);
                    break;
                }
            }
        }
    }

    @FXML
    private void handleRowSelected(){
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null){
            setRightFields();
        }
    }

    private boolean isSuitableToUpdate(){
        if (rightTitleField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Title (selected) field", "Title (selected) field should not be empty.");
            return false;
        }
        if (rightManufacturerField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Manufacturer (selected) field", "Manufacturer (selected) field should not be empty.");
            return false;
        }
        if (rightPriceField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price (selected) field", "Price (selected) field should not be empty.");
            return false;
        }
        try {
            double price = Double.parseDouble(rightPriceField.getText());
            if (price <= 0.0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price (selected) field", "Price should be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price (selected) field", "Wrong number format.");
            return false;
        }
        if (rightDescriptionField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Description (selected) field", "Description (selected) field should not be empty.");
            return false;
        }
        if (rightWarehouseList.getSelectionModel().getSelectedItems().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Warehouse (selected) field", "At least one warehouse has to be selected.");
            return false;
        }

        return true;
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

    private void loadWarehouseList(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        leftWarehouseList.setItems(FXCollections.observableList(warehouses));
        rightWarehouseList.setItems(FXCollections.observableList(warehouses));
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
    private void deselectRightWarehouseList(){
        rightWarehouseList.getSelectionModel().clearSelection();
    }

    @FXML
    private void loadMainShopPage() throws IOException {
        Utils.loadMainShopPage(entityManagerFactory, user, anchorPane);
    }

    @FXML
    private void loadAccountPage() throws IOException {
        Utils.loadAccountPage(entityManagerFactory, user, anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("shop");

        leftWarehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightWarehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadWarehouseList();
        updateProductsTable();
    }
}
