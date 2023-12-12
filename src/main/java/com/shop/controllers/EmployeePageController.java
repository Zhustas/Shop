package com.shop.controllers;

import com.shop.Utils.Utils;
import com.shop.classes.Cart;
import com.shop.classes.Employee;
import com.shop.classes.User;
import com.shop.classes.Warehouse;
import com.shop.hibernateControllers.CRUDHib;
import com.shop.hibernateControllers.UtilsHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.List;

public class EmployeePageController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField leftNameField, rightNameField;
    @FXML
    private TextField leftLastNameField, rightLastNameField;
    @FXML
    private TextField leftEmailField, rightEmailField;
    @FXML
    private TextField leftUsernameField, rightUsernameField;
    @FXML
    private TextField leftPasswordField, rightPasswordField;
    @FXML
    private TextField leftSalaryField, rightSalaryField;
    @FXML
    private ListView<Warehouse> leftWarehouseList, rightWarehouseList;
    @FXML
    private TableView<Employee> employeesTable;
    @FXML
    private TableColumn<Employee, String> employeesColumnName, employeesColumnLastName, employeesColumnUsername, employeesColumnEmail, employeesColumnSalary, employeesColumnWarehouse;

    EntityManagerFactory entityManagerFactory;
    User user;
    Employee selectedEmployee;

    public void setData(EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.user = user;

        updateEmployeesTable();
        leftWarehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        updateLeftWarehouseList();
        rightWarehouseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        updateRightWarehouseList();

        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void updateLeftWarehouseList(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        leftWarehouseList.setItems(FXCollections.observableList(warehouses));
    }

    private void updateRightWarehouseList(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Warehouse> warehouses = utilsHib.getAllRecords(Warehouse.class);

        rightWarehouseList.setItems(FXCollections.observableList(warehouses));
    }

    @FXML
    private void createEmployee(){
        if (!isSuitableToCreate()){
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);
        Employee newEmployee = getNewEmployeeFromFields();

        try {
            Cart cart = new Cart(LocalDate.now());
            crudHib.create(cart);

            newEmployee.setCart(cart);

            crudHib.create(newEmployee);
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Success", "Create employee", "Successfully created employee.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Error", "Create employee", "Error in creating employee.");
        }

        updateEmployeesTable();
    }

    @FXML
    private void updateEmployee(){
        if (!isSuitableToUpdate()){
            return;
        }

        if (selectedEmployee == null){
            Utils.generateAlert(Alert.AlertType.INFORMATION, "Error", "Update employee", "No employee selected to update.");
            return;
        }

        setToUpdateEmployeeFromFields();

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.update(selectedEmployee);
            Utils.generateAlert(Alert.AlertType.WARNING, "Success", "Update employee", "Successfully updated employee.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Update employee", "Error in updating employee.");
        }

        updateEmployeesTable();
    }

    @FXML
    private void deleteEmployee(){
        if (selectedEmployee == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete employee", "No employee is selected to delete.");
            return;
        }

        if (!Utils.generateDialogBox(Alert.AlertType.CONFIRMATION, "Warning", "Delete employee", "Do you really want to delete employee?")){
            return;
        }

        CRUDHib crudHib = new CRUDHib(entityManagerFactory);

        try {
            crudHib.delete(Employee.class, selectedEmployee.getID());
            Utils.generateAlert(Alert.AlertType.WARNING, "Success", "Delete employee", "Successfully deleted employee.");
        } catch (Exception e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Delete employee", "Error in deleting employee.");
        }

        updateEmployeesTable();
    }

    private void setToUpdateEmployeeFromFields(){
        selectedEmployee.setName(rightNameField.getText());
        selectedEmployee.setLastName(rightLastNameField.getText());
        selectedEmployee.setEmail(rightEmailField.getText());
        selectedEmployee.setUsername(rightUsernameField.getText());
        selectedEmployee.setPassword(Utils.encrypt(rightPasswordField.getText()));
        selectedEmployee.setSalary(Double.parseDouble(rightSalaryField.getText()));
        selectedEmployee.setWorksAtWarehouse(rightWarehouseList.getSelectionModel().getSelectedItems());
    }

    private void updateEmployeesTable(){
        UtilsHib utilsHib = new UtilsHib(entityManagerFactory);
        List<Employee> employees = utilsHib.getAllRecords(Employee.class);

        employeesColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeesColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeesColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        employeesColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeesColumnSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeesColumnWarehouse.setCellValueFactory(
                (TableColumn.CellDataFeatures<Employee, String> p) ->
                {
                    List<Warehouse> warehouses = p.getValue().getWorksAtWarehouse();
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

        employeesTable.setItems(FXCollections.observableList(employees));
    }

    private Employee getNewEmployeeFromFields(){
        Employee employee = new Employee();

        employee.setName(leftNameField.getText());
        employee.setLastName(leftLastNameField.getText());
        employee.setEmail(leftEmailField.getText());
        employee.setUsername(leftUsernameField.getText());
        employee.setPassword(Utils.encrypt(leftPasswordField.getText()));
        employee.setUserType("Employee");
        employee.setSalary(Double.parseDouble(leftSalaryField.getText()));

        List<Warehouse> selectedWarehouses = leftWarehouseList.getSelectionModel().getSelectedItems();
        employee.setWorksAtWarehouse(selectedWarehouses);

        return employee;
    }

    private boolean isSuitableToCreate(){
        if (leftNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Name field", "Name field should not be empty.");
            return false;
        }
        if (leftLastNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Last name field", "Last name field should not be empty.");
            return false;
        }
        if (leftEmailField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Email field", "Email field should not be empty.");
            return false;
        }
        if (leftUsernameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Username field", "Username field should not be empty.");
            return false;
        }
        if (leftPasswordField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password field", "Password field should not be empty.");
            return false;
        }
        double salary;
        try {
            salary = Double.parseDouble(leftSalaryField.getText());
            if (salary <= 0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary has to be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary field", "Salary is not a number.");
            return false;
        }
        if (leftWarehouseList.getSelectionModel().getSelectedItem() == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Warehouse field", "At least one warehouse has to be selected.");
            return false;
        }

        return true;
    }

    private boolean isSuitableToUpdate(){
        if (rightNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Name (selected) field", "Name (selected) field should not be empty.");
            return false;
        }
        if (rightLastNameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Last name (selected) field", "Last name (selected) field should not be empty.");
            return false;
        }
        if (rightEmailField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Email (selected) field", "Email (selected) field should not be empty.");
            return false;
        }
        if (rightUsernameField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Username (selected) field", "Username (selected) field should not be empty.");
            return false;
        }
        if (rightPasswordField.getText().isEmpty()){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Password (selected) field", "Password (selected) field should not be empty.");
            return false;
        }
        double salary;
        try {
            salary = Double.parseDouble(rightSalaryField.getText());
            if (salary <= 0){
                Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary (selected) field", "Salary (selected) has to be higher than 0.");
                return false;
            }
        } catch (NumberFormatException e){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Salary (selected) field", "Salary (selected) is not a number.");
            return false;
        }
        if (rightWarehouseList.getSelectionModel().getSelectedItem() == null){
            Utils.generateAlert(Alert.AlertType.ERROR, "Error", "Warehouse (selected) field", "At least one warehouse has to be selected.");
            return false;
        }

        return true;
    }

    @FXML
    private void clearLeftFields(){
        leftNameField.setText("");
        leftLastNameField.setText("");
        leftEmailField.setText("");
        leftUsernameField.setText("");
        leftPasswordField.setText("");
        leftSalaryField.setText("");
        leftWarehouseList.getSelectionModel().clearSelection();
        Utils.determineMenu(entityManagerFactory, user, anchorPane);
    }

    private void setRightFields(){
        rightNameField.setText(selectedEmployee.getName());
        rightLastNameField.setText(selectedEmployee.getLastName());
        rightEmailField.setText(selectedEmployee.getEmail());
        rightUsernameField.setText(selectedEmployee.getUsername());
        rightPasswordField.setText(selectedEmployee.getPassword());
        rightSalaryField.setText(String.valueOf(selectedEmployee.getSalary()));

        deselectRightWarehouseList();
        for (Warehouse w : selectedEmployee.getWorksAtWarehouse()){
            for (int i = 0; i < rightWarehouseList.getItems().size(); i++){
                if (w.getID() == rightWarehouseList.getItems().get(i).getID()){
                    rightWarehouseList.getSelectionModel().select(i);
                    break;
                }
            }
        }
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
    private void handleRowSelected(){
        selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null){
            setRightFields();
        }
    }
}
