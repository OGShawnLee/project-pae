package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewManagerListController extends Controller {
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
  private TableColumn<EmployeeDTO, String> columnAddress;
  @FXML
  private TableColumn<EmployeeDTO, String> columnBornAt;

  @FXML
  private void initialize() {
    loadTableColumns();
    loadManagerList();
    loadRowDoubleClickHandler();
  }

  private void loadTableColumns() {
    columnDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    columnWage.setCellValueFactory(new PropertyValueFactory<>("wage"));
    columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    columnBornAt.setCellValueFactory(new PropertyValueFactory<>("bornAt"));
  }

  private void loadManagerList() {
    tableEmployee.setItems(
      FXCollections.observableList(
        EmployeeDBProxy.getInstance().getAllByRole(AccountDTO.Role.MANAGER)
      )
    );
  }

  private void loadRowDoubleClickHandler() {
    setRowDoubleClickHandler(
      tableEmployee,
      (dataObject) -> {
        Modal.displayManageModal(
          "Administrar Gerente",
          "ManageManagerModal",
          this::reload,
          dataObject
        );
        return null;
      }
    );
  }

  private void reload() {
    navigateFromThisPageTo("Lista de Gerentes", "ReviewManagerListPage");
  }

  public void handleOpenRegisterManager() {
    Modal.display("Registrar Gerente", "RegisterManagerModal", this::loadManagerList);
  }

  public static void navigateToManagerListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Gerentes", "ReviewManagerListPage");
  }
}