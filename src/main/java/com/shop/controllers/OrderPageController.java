package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Order;
import com.shop.classes.User;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderPageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> idColumn, productsColumn, dateColumn, totalPriceColumn, statusColumn;

    @FXML
    private DatePicker fromDateField, toDateField;
    @FXML
    private TextField minPriceField, maxPriceField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    private LocalDate fromDate, toDate;
    private double minPrice, maxPrice;
    private String selectedStatus;

    private EntityManagerFactory entityManagerFactory;
    private User user;
    private Order selectedOrder;
    List<Order> orders;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        getOrders();
        loadOrdersTable();
        setStatusChoiceBox();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    public void setDataAfterPurchase(EntityManagerFactory entityManagerFactory, User user, long ID){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        getOrders();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        for (Order order : orders){
            if (order.getID() == ID){
                order.setStatus("In progress");
                crudHib.update(order);
                break;
            }
        }

        getOrders();
        loadOrdersTable();
        setStatusChoiceBox();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void getOrders(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        orders = utilsHib.getAllRecordsByID(Order.class, user.getID());
    }

    private void loadOrdersTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ordersTable.setItems(FXCollections.observableList(orders));
    }

    private void loadOrdersTableWithFilters(List<Order> newOrders){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("products"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        ordersTable.setItems(FXCollections.observableList(newOrders));
    }

    @FXML
    private void cancelOrder(){
        if (selectedOrder == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Order select", "No order selected to cancel.");
            return;
        }

        String status = selectedOrder.getStatus();
        if (status.equals("Delivered") || status.equals("Canceled")){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Selected order", "Can't cancel delivered or already canceled order.");
            return;
        }

        if (Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "Cancel", "Cancel order", "Are you sure you want to cancel an order?")){
            selectedOrder.setStatus("Canceled");
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        crudHib.update(selectedOrder);

        getOrders();
        loadOrdersTable();
    }

    @FXML
    private void payOrder() throws IOException {
        if (selectedOrder == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Order select", "No order selected to pay for.");
            return;
        }

        String status = selectedOrder.getStatus();
        if (status.equals("Waiting for payment")){
            Utils.loadPurchasePage(entityManagerFactory, user, anchorPane, selectedOrder.getProducts(), selectedOrder.getID());
            //Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Selected order", "Can't cancel delivered or already canceled order.");
        } else {
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Paying", "Order is either canceled, delivered or paid.");
        }
    }

    @FXML
    private void handleRowSelected(){
        selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void clearFields(){
        clearDates();
        minPriceField.setText("");
        maxPriceField.setText("");
        statusChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void filterOrders(){
        setDates();
        setPrices();
        setStatus();

        if (allFiltersEmpty()){
            getOrders();
            loadOrdersTable();
            return;
        }

        List<Order> newOrders = new ArrayList<>();
        getOrders();

        boolean datesChecked = false;
        if (fromDate != null && toDate != null){
            datesChecked = true;
            for (Order order : orders){
                if (fromDate.isBefore(order.getOrderDate().toLocalDate()) && toDate.isAfter(order.getOrderDate().toLocalDate())){
                    newOrders.add(order);
                }
            }
        } else {
            if (!bothDatesNull()){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Date select", "Interval for date must be chosen.");
                return;
            }
        }

        List<Order> fromWhichOrders = orders;
        if (datesChecked){
            fromWhichOrders = new ArrayList<>(newOrders);
        }

        boolean pricesChecked = false;
        if (minPrice != -1 && maxPrice != -1){
            newOrders.clear();
            pricesChecked = true;
            for (Order order : fromWhichOrders){
                if (minPrice < order.getTotalPrice() && order.getTotalPrice() < maxPrice){
                    newOrders.add(order);
                }
            }
        } else {
            if (!bothPricesEmpty()){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Price select", "Interval for price must be chosen.");
                return;
            }
        }

        if (datesChecked || pricesChecked){
            fromWhichOrders = new ArrayList<>(newOrders);
        }

        if (!statusEmpty()){
            newOrders.clear();
            for (Order order : fromWhichOrders){
                if (order.getStatus().equals(selectedStatus)){
                    newOrders.add(order);
                }
            }
        }

        loadOrdersTableWithFilters(newOrders);
    }

    private boolean allFiltersEmpty(){
        if (fromDate == null && toDate == null && minPrice == -1 && maxPrice == -1 && selectedStatus == null){
            return true;
        }
        return false;
    }

    private void setStatus(){
        selectedStatus = statusChoiceBox.getValue();
    }

    private void setDates(){
        fromDate = fromDateField.getValue();
        toDate = toDateField.getValue();
    }

    private void setPrices(){
        if (minPriceField.getText().isEmpty()){
            minPrice = -1;
        } else {
            minPrice = Double.parseDouble(minPriceField.getText());
        }
        if (maxPriceField.getText().isEmpty()){
            maxPrice = -1;
        } else {
            maxPrice = Double.parseDouble(maxPriceField.getText());
        }
    }

    private boolean bothDatesNull(){
        return fromDate == null && toDate == null;
    }

    private boolean bothPricesEmpty(){
        return minPrice == -1 && maxPrice == -1;
    }

    private boolean statusEmpty(){
        return selectedStatus == null;
    }

    @FXML
    private void clearDates(){
        fromDateField.setValue(null);
        toDateField.setValue(null);
    }

    @FXML
    private void clearStatus(){
        statusChoiceBox.getSelectionModel().clearSelection();
    }

    private void setStatusChoiceBox(){
        statusChoiceBox.getItems().add("Waiting for payment");
        statusChoiceBox.getItems().add("Canceled");
        statusChoiceBox.getItems().add("In progress");
        statusChoiceBox.getItems().add("Delivered");
    }
}
