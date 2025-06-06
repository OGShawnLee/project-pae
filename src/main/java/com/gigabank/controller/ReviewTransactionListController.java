package com.gigabank.controller;

import com.gigabank.model.data.TransactionDTO;
import com.gigabank.model.db.transaction.TransactionDBProxy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewTransactionListController extends Controller {
    @FXML
    private TableView<TransactionDTO> tableTransaction;
    @FXML
    private TableColumn<TransactionDTO, String> columnId;
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
        columnId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnSourceAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSourceAccount() != null ? cellData.getValue().getSourceAccount().getID() : "-"));
        columnDestinationAccount.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestinationAccount() != null ? cellData.getValue().getDestinationAccount().getID() : "-"));
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().name()));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadTransactionList() {
        tableTransaction.setItems(FXCollections.observableList(TransactionDBProxy.getInstance().getAll()));
    }

    public void handleEditTransaction() {

    }

    public void handleDeleteTransaction() {

    }

    public void handleExportTransactions() {
    }

    public static void navigateToTransactionListPage(Stage currentStage) {
        navigateTo(currentStage, "Lista de Transacciones", "ReviewTransactionListPage");
    }
}