package com.gigabank.controller;

import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.branch.BranchDBProxy;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewBranchListController extends Controller {
  @FXML
  private TableView<BranchDTO> tableBranch;
  @FXML
  private TableColumn<BranchDTO, String> columnName;
  @FXML
  private TableColumn<BranchDTO, String> columnEmail;
  @FXML
  private TableColumn<BranchDTO, String> columnAddress;
  @FXML
  private TableColumn<BranchDTO, String> columnPhone;

  @FXML
  private void initialize() {
    loadTableColumns();
    loadBranchList();
    loadRowDoubleClickHandler();
  }

  private void loadTableColumns() {
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
  }

  private void loadBranchList() {
    tableBranch.setItems(
      FXCollections.observableList(
        BranchDBProxy.getInstance().getAll()
      )
    );
  }

  private void loadRowDoubleClickHandler() {
    setRowDoubleClickHandler(
      tableBranch,
      (dataObject) -> {
        Modal.displayManageModal(
          "Administrar Sucursal",
          "ManageBranchModal",
          this::reload,
          dataObject
        );
        return null;
      }
    );
  }

  private void reload() {
    navigateFromThisPageTo("Lista de Sucursales", "ReviewBranchListPage");
  }

  public void handleOpenRegisterBranch() {
    Modal.display("Registrar Sucursal", "RegisterBranchModal", this::loadBranchList);
  }

  public void handleEditBranch() {
    // Lógica para editar empleado
  }

  public void handleDeleteBranch() {
    // Lógica para eliminar empleado
  }

  public void handleExportBranches() {
    // Lógica para exportar empleados
  }

  public static void navigateToBranchListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Sucursales", "ReviewBranchListPage");
  }
}
