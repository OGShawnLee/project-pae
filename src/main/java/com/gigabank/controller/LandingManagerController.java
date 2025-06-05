package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingManagerController extends Controller {
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
      System.out.println(AuthClient.getInstance().getCurrentUser());
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

  public void navigateToReviewBranchListPage() {
    ReviewBranchListController.navigateToBranchListPage(getScene());
  }

  public void navigateToReviewEmployeeListPage() {
    ReviewEmployeeListController.navigateToEmployeeListPage(getScene());
  }

  public void navigateToReviewClientListPage() {
    ReviewClientListController.navigateToClientListPage(getScene());
  }

  public void navigateToReviewBankAccountListPage() {
    ReviewBankAccountListController.navigateToBankAccountListPage(getScene());
  }

  public void navigateToReviewTransactionListPage() {
    ReviewTransactionListController.navigateToTransactionListPage(getScene());
  }
}