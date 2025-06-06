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

public class ReviewEmployeeListController extends Controller implements FileExporter {
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
  private TableColumn<EmployeeDTO, AccountDTO.Role> columnRole;

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
    columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    columnBornAt.setCellValueFactory(new PropertyValueFactory<>("bornAt"));
    columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));
  }

  private void loadEmployeeList() {
    tableEmployee.setItems(
      FXCollections.observableList(
        EmployeeDBProxy.getInstance().getAll()
      )
    );
  }

  @Override
  public void handleExportToCSV() {
    var employees = EmployeeDBProxy.getInstance().getAll();

    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
    fileChooser.setTitle("Exportar Lista de Empleados");
    fileChooser.getExtensionFilters().add(
            new javafx.stage.FileChooser.ExtensionFilter("CSV Files", "*.csv")
    );

    java.io.File file = fileChooser.showSaveDialog(tableEmployee.getScene().getWindow());

    if (file != null) {
      try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
              new java.io.FileOutputStream(file), java.nio.charset.StandardCharsets.UTF_8)) {
        writer.write("Nombre de Usuario,Nombre,Género,Sueldo,Dirección,Fecha de Nacimiento,Rol\n");
        for (var emp : employees) {
          writer.write(String.format("%s,%s,%s,%.2f,%s,%s,%s\n",
                  emp.getDisplayName(),
                  emp.getName(),
                  emp.getGender(),
                  emp.getWage(),
                  emp.getAddress(),
                  emp.getBornAt().toString(),
                  emp.getRole() != null ? emp.getRole().name() : "-"
          ));
        }
      } catch (java.io.IOException e) {
        Modal.displayError("Error al exportar la lista de empleados.");
      }
    }
  }

  public void handleExportToJSON() {
    handleExportToJSON(
            EmployeeDBProxy.getInstance().getAll(),
            "Exportar Lista de Empleados",
            tableEmployee.getScene().getWindow()
    );
  }

  public void handleOpenRegisterEmployee() {
    Modal.display("Registrar Empleado", "RegisterEmployeeModal", this::loadEmployeeList);
  }

  public void handleEditEmployee() {
    EmployeeDTO selected = tableEmployee.getSelectionModel().getSelectedItem();
    if (selected == null) {
      Modal.displayError("Seleccione un empleado para editar.");
      return;
    }
    Modal.displayManageModal("Gestionar Empleado", "ManageEmployeeModal", this::loadEmployeeList, selected);
  }

  public void handleDeleteEmployee() {
    // Lógica para eliminar empleado
  }

  public static void navigateToEmployeeListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Empleados", "ReviewEmployeeListPage");
  }
}