package com.gigabank.controller;

import com.gigabank.model.data.TransactionDTO;
import com.gigabank.model.db.transaction.TransactionDBProxy;
import javafx.beans.property.SimpleStringProperty;
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

public class ReviewTransactionListController extends Controller implements FileExporter {
  @FXML
  private TableView<TransactionDTO> tableTransaction;
  @FXML
  private TableColumn<TransactionDTO, String> columnID;
  @FXML
  private TableColumn<TransactionDTO, String> columnSourceAccount;
  @FXML
  private TableColumn<TransactionDTO, String> columnDestinationAccount;
  @FXML
  private TableColumn<TransactionDTO, String> columnType;
  @FXML
  private TableColumn<TransactionDTO, Double> columnAmount;

  @FXML
  private void initialize() {
    loadTableColumns();
    loadTransactionList();
  }

  private void loadTableColumns() {
    columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
    columnSourceAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSourceAccount() != null ? cellData.getValue().getSourceAccount().getID() : "-"));
    columnDestinationAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestinationAccount() != null ? cellData.getValue().getDestinationAccount().getID() : "-"));
    columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
    columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
  }

  private void loadTransactionList() {
    tableTransaction.setItems(FXCollections.observableList(TransactionDBProxy.getInstance().getAll()));
  }

  @Override
  public void handleExportToCSV() {
    ArrayList<TransactionDTO> transactions = TransactionDBProxy.getInstance().getAll();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Lista de Transacciones");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

    File file = fileChooser.showSaveDialog(tableTransaction.getScene().getWindow());

    if (file != null) {
      try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
        writer.write("ID,Cuenta Origen,Cuenta Destino,Tipo,Monto\n");
        for (TransactionDTO transaction : transactions) {
          writer.write(String.format("%s,%s,%s,%s,%.2f\n",
            transaction.getID(),
            transaction.getSourceAccount() != null ? transaction.getSourceAccount().getID() : "-",
            transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getID() : "-",
            transaction.getType().name(),
            transaction.getAmount()
          ));
        }
      } catch (IOException e) {
        Modal.displayError("Error al exportar la lista de transacciones.");
      }
    }
  }

  public void handleExportToJSON() {
    handleExportToJSON(
      TransactionDBProxy.getInstance().getAll(),
      "Exportar Lista de Transacciones",
      tableTransaction.getScene().getWindow()
    );
  }

  public void handleOpenRegisterTransaction() {
    Modal.display("Registrar Transacción", "RegisterTransactionModal", this::loadTransactionList);
  }

  public static void navigateToTransactionListPage(Stage currentStage) {
    navigateTo(currentStage, "Lista de Transacciones", "ReviewTransactionListPage");
  }
}