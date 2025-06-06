package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingTellerController extends Controller {
  @FXML
  private Label labelDisplayName;

  public void initialize() {
    labelDisplayName.setText("@" + AuthClient.getInstance().getCurrentUser().getDisplayName());
  }

  public void handleLogOut() {
    AuthClient.getInstance().setCurrentUser(null);
    navigateFromThisPageTo("Login Page", "LoginPage");
  }

  public void handleOpenManageProfile() {
    try {
      Modal.displayManageModal(
        "Administrar Perfil",
        "ManageProfileModal",
        null,
        EmployeeDBProxy.getInstance().getOne(
          AuthClient.getInstance().getCurrentUser().getDisplayName()
        )
      );
    } catch (NotFoundRecordException e) {
      Modal.displayError("No se ha encontrado el perfil del usuario actual.");
    }
  }

  public void handleOpenRegisterTransaction() {
    Modal.display("Registrar Transacción", "RegisterTransactionModal");
  }

  public void handleOpenGenerateBankStatement() {
    Modal.display("Generar Estado de Cuenta", "GenerateBankStatementModal");
  }

  public void navigateToReviewTransactionListPage() {
    ReviewTransactionListController.navigateToTransactionListPage(getScene());
  }
}
