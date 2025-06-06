package com.gigabank.controller;

import com.gigabank.model.data.ClientDTO;
import com.gigabank.model.db.client.ClientDBProxy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReviewClientListController extends Controller implements FileExporter {
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

    @Override
    public void handleExportToCSV() {
        var clients = ClientDBProxy.getInstance().getAll();

        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Exportar Lista de Clientes");
        fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        java.io.File file = fileChooser.showSaveDialog(tableClient.getScene().getWindow());

        if (file != null) {
            try (java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(file), java.nio.charset.StandardCharsets.UTF_8)) {
                writer.write("Nombre de Usuario,Nombre,Apellido,Nacionalidad,Email,Teléfono\n");
                for (var client : clients) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                            client.getName(),
                            client.getLastName(),
                            client.getNationality(),
                            client.getEmail(),
                            client.getPhone()
                    ));
                }
            } catch (java.io.IOException e) {
                Modal.displayError("Error al exportar la lista de clientes.");
            }
        }
    }

    public void handleExportToJSON() {
        handleExportToJSON(
                ClientDBProxy.getInstance().getAll(),
                "Exportar Lista de Clientes",
                tableClient.getScene().getWindow()
        );
    }

    public void handleEditClient() {
    }

    public void handleDeleteClient() {
    }

    public static void navigateToClientListPage(Stage currentStage) {
        navigateTo(currentStage, "Lista de Clientes", "ReviewClientListPage");
    }
}