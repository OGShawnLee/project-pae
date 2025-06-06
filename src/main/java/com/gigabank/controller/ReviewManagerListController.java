package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.branch.BranchDBProxy;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReviewManagerListController extends Controller implements FileExporter {
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

  @Override
  public void handleExportToCSV() {
    ArrayList<EmployeeDTO> branches = EmployeeDBProxy.getInstance().getAll();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Lista de Gerentes");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    File file = fileChooser.showSaveDialog(container.getScene().getWindow());

    if (file != null) {
      try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
        writer.write("Nombre,Nombre de Usuario,Dirección,Género,Fecha de Nacimiento,Sueldo,Sucursal\n");
        for (EmployeeDTO manager : branches) {
          writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
            manager.getName(),
            manager.getDisplayName(),
            manager.getAddress(),
            manager.getGender(),
            manager.getBornAt().toString(),
            manager.getWage(),
            manager.getBranch()
          ));
        }
      } catch (IOException e) {
        Modal.displayError("Error al exportar la lista de sucursales.");
      }
    }
  }

  public void handleExportToJSON() {
    handleExportToJSON(
      BranchDBProxy.getInstance().getAll(),
      "Exportar Lista de Gerentes",
      container.getScene().getWindow()
    );
  }

  private void reload() {
    navigateFromThisPageTo("Lista de Gerentes", "ReviewManagerListPage");
  }

  public void handleOpenRegisterManager() {
    Modal.display("Registrar Gerente", "RegisterManagerModal", this::loadManagerList);
  }

  public void handleEditManager() {
    // Lógica para editar empleado
  }

  public void handleDeleteManager() {
    // Lógica para eliminar empleado
  }

  public void handleExportManagers() {
    // Lógica para exportar empleados
  }

  public static void navigateToManagerListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Gerentes", "ReviewManagerListPage");
  }
}