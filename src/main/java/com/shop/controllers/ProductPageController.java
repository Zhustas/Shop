package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Product;
import com.shop.classes.User;
import com.shop.classes.Warehouse;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField titleField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private ListView<Warehouse> warehouseList;

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

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        Product product = setNewProductFromFields();

        try {
            crudHib.create(product);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Creating product", "Error in creating product.");
        }
    }

    private Product setNewProductFromFields(){
        Product product = new Product();

        product.setTitle(titleField.getText());
        product.setManufacturer(manufacturerField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setDescription(descriptionField.getText());

        ObservableList<Warehouse> warehouses = warehouseList.getSelectionModel().getSelectedItems();
        product.setWarehouseList(warehouses);

        return product;
    }

    private boolean isSuitableToCreate(){
        if (titleField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Title field", "Title should not be empty.");
            return false;
        }
        if (manufacturerField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Manufacturer field", "Manufacturer should not be empty.");
            return false;
        }
        if (priceField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Price should not be empty.");
            return false;
        }
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
            if (price <= 0.0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Price should be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price field", "Price is not a number.");
            return false;
        }
        if (descriptionField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Description field", "Description should not be empty.");
            return false;
        }
        ObservableList<Warehouse> warehouses = warehouseList.getSelectionModel().getSelectedItems();
        if (warehouses.isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Warehouse field", "At least one warehouse has to be selected.");
            return false;
        }

        return true;
    }

    @FXML
    private void updateProduct(){
        if (productsTable.getSelectionModel().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Product table", "No product has been selected for update.");
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        Product product = getSelectedProductFromTable();
        if (!isSuitableToCreate()){
            return;
        }

        product = setProductFromFields(product);

        try {
            crudHib.update(product);
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Updating product", "Error in updating product.");
        }
    }

    private Product setProductFromFields(Product product){
        product.setTitle(titleField.getText());
        product.setManufacturer(manufacturerField.getText());
        product.setPrice(Double.parseDouble(priceField.getText()));
        product.setDescription(descriptionField.getText());

        ObservableList<Warehouse> warehouses = warehouseList.getSelectionModel().getSelectedItems();
        product.setWarehouseList(warehouses);

        return product;
    }

    private Product getSelectedProductFromTable(){
        return productsTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void deleteProduct(){
        Product product = getSelectedProductFromTable();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.delete(Product.class, product.getID());
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Deleting product", "Error in deleting product.");
        }
    }

    @FXML
    private void clearSelectedWarehouse(){
        warehouseList.getSelectionModel().clearSelection();
    }

    @FXML
    private void clearFields(){
        titleField.setText(null);
        manufacturerField.setText(null);
        priceField.setText(null);
        descriptionField.setText(null);
        warehouseList.getSelectionModel().clearSelection();
    }

    private void loadProductsTable(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Product> products = utilsHib.getAllRecords(Product.class);

        productsTable.setItems((ObservableList<Product>) products);
    }

    private void loadWarehouseList(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        warehouseList.setItems((ObservableList<Warehouse>) warehouses);
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
        warehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadProductsTable();
        loadWarehouseList();
    }
}
