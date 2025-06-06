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

public class ReviewBankAccountListController extends Controller {
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

    public void handleOpenRegisterBankAccount() {
        Modal.display("Registrar Cuenta", "RegisterBankAccountModal", this::loadBankAccountList);
    }

    public void handleEditBankAccount() {
    }

    public void handleDeleteBankAccount() {
    }

    public void handleExportBankAccounts() {
    }

    public static void navigateToBankAccountListPage(Stage currentStage) {
        navigateTo(currentStage, "Lista de Cuentas Bancarias", "ReviewBankAccountListPage");
    }
}