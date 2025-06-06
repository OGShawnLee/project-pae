package com.gigabank.controller;

import com.gigabank.model.data.BankAccountDTO;
import com.gigabank.model.db.bank_account.BankAccountDBProxy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewBankAccountListController extends Controller implements FileExporter {
  @FXML
  private TableView<BankAccountDTO> tableBankAccount;
  @FXML
  private TableColumn<BankAccountDTO, String> columnId;
  @FXML
  private TableColumn<BankAccountDTO, String> columnClient;
  @FXML
  private TableColumn<BankAccountDTO, String> columnBranch;
  @FXML
  private TableColumn<BankAccountDTO, String> columnType;
  @FXML
  private TableColumn<BankAccountDTO, Double> columnBalance;
  @FXML
  private TableColumn<BankAccountDTO, Double> columnLimit;

  @FXML
  private void initialize() {
    loadTableColumns();
    loadBankAccountList();
  }

  private void loadTableColumns() {
    columnId.setCellValueFactory(new PropertyValueFactory<>("ID"));
    columnClient.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getName()));
    columnBranch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBranch().getName()));
    columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().name()));
    columnBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    columnLimit.setCellValueFactory(new PropertyValueFactory<>("limit"));
  }

  private void loadBankAccountList() {
    tableBankAccount.setItems(
      FXCollections.observableList(
        BankAccountDBProxy.getInstance().getAll()
      )
    );
  }

  @Override
  public void handleExportToCSV() {
    var accounts = BankAccountDBProxy.getInstance().getAll();

    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
    fileChooser.setTitle("Exportar Lista de Cuentas Bancarias");
    fileChooser.getExtensionFilters().add(
      new javafx.stage.FileChooser.ExtensionFilter("CSV Files", "*.csv")
    );

    java.io.File file = fileChooser.showSaveDialog(tableBankAccount.getScene().getWindow());

    if (file != null) {
      try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
        new java.io.FileOutputStream(file), java.nio.charset.StandardCharsets.UTF_8)) {
        writer.write("ID,Cliente,Sucursal,Tipo,Saldo,Límite\n");
        for (var acc : accounts) {
          writer.write(String.format("%s,%s,%s,%s,%.2f,%.2f\n",
            acc.getID(),
            acc.getClient() != null ? acc.getClient().getName() : "-",
            acc.getBranch() != null ? acc.getBranch().getName() : "-",
            acc.getType() != null ? acc.getType().name() : "-",
            acc.getBalance(),
            acc.getLimit()
          ));
        }
      } catch (java.io.IOException e) {
        Modal.displayError("Error al exportar la lista de cuentas bancarias.");
      }
    }
  }

  public void handleExportToJSON() {
    handleExportToJSON(
      BankAccountDBProxy.getInstance().getAll(),
      "Exportar Lista de Cuentas Bancarias",
      tableBankAccount.getScene().getWindow()
    );
  }

  public void handleOpenRegisterBankAccount() {
    Modal.display("Registrar Cuenta", "RegisterBankAccountModal", this::loadBankAccountList);
  }

  public void handleEditBankAccount() {
  }

  public void handleDeleteBankAccount() {
  }

  public static void navigateToBankAccountListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Cuentas Bancarias", "ReviewBankAccountListPage");
  }
}