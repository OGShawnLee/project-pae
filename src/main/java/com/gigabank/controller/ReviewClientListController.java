package com.gigabank.controller;

import com.gigabank.model.data.ClientDTO;
import com.gigabank.model.db.client.ClientDBProxy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewClientListController extends Controller {
    @FXML
    private TableView<ClientDTO> tableClient;
    @FXML
    private TableColumn<ClientDTO, String> columnDisplayName;
    @FXML
    private TableColumn<ClientDTO, String> columnName;
    @FXML
    private TableColumn<ClientDTO, String> columnLastName;
    @FXML
    private TableColumn<ClientDTO, String> columnNationality;
    @FXML
    private TableColumn<ClientDTO, String> columnEmail;
    @FXML
    private TableColumn<ClientDTO, String> columnPhone;

    @FXML
    private void initialize() {
        loadTableColumns();
        loadClientList();
    }

    private void loadTableColumns() {
        columnDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void loadClientList() {
        tableClient.setItems(
                FXCollections.observableList(
                        ClientDBProxy.getInstance().getAll()
                )
        );
    }

    public void handleOpenRegisterClient() {
        Modal.display("Registrar Cliente", "RegisterClientModal", this::loadClientList);
    }

    public static void navigateToClientListPage(Stage currentStage) {
        navigateTo(currentStage, "Lista de Clientes", "ReviewClientListPage");
    }
}