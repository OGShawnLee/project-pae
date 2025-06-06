package com.gigabank.controller;

import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewEmployeeListController extends Controller {
    @FXML
    private TableView<EmployeeDTO> tableEmployee;
    @FXML
    private TableColumn<EmployeeDTO, String> columnDisplayName;
    @FXML
    private TableColumn<EmployeeDTO, String> columnName;
    @FXML
    private TableColumn<EmployeeDTO, String> columnGender;
    @FXML
    private TableColumn<EmployeeDTO, Float> columnWage;

    @FXML
    private void initialize() {
        loadTableColumns();
        loadEmployeeList();
    }

    private void loadTableColumns() {
        columnDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        columnWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
    }

    private void loadEmployeeList() {
        tableEmployee.setItems(FXCollections.observableList(EmployeeDBProxy.getInstance().getAll()));
    }

    public void handleOpenRegisterEmployee() {
        Modal.display("Registrar Empleado", "RegisterEmployeeModal", this::loadEmployeeList);
    }

    public static void navigateToEmployeeListPage(Stage currentStage) {
        navigateTo(currentStage, "Lista de Empleados", "ReviewEmployeeListPage");
    }
}