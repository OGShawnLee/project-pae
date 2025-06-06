package com.gigabank.controller;

import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.branch.BranchDBProxy;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReviewBranchListController extends Controller implements FileExporter {
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

  @Override
  public void handleExportToCSV() {
    ArrayList<BranchDTO> branches = BranchDBProxy.getInstance().getAll();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Lista de Sucursales");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    File file = fileChooser.showSaveDialog(container.getScene().getWindow());

    if (file != null) {
      try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
        writer.write("Nombre,Email,Dirección,Teléfono\n");
        for (BranchDTO branch : branches) {
          writer.write(String.format("%s,%s,%s,%s\n",
            branch.getName(),
            branch.getEmail(),
            branch.getAddress(),
            branch.getPhone()
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
      "Exportar Lista de Sucursales",
      container.getScene().getWindow()
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

  public static void navigateToBranchListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Sucursales", "ReviewBranchListPage");
  }
}
