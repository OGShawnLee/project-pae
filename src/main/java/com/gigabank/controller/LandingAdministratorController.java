package com.gigabank.controller;

import com.gigabank.model.AuthClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingAdministratorController extends Controller {
  @FXML
  Label labelDisplayName;

  public void initialize() {
    labelDisplayName.setText("@" + AuthClient.getInstance().getCurrentUser().getDisplayName());
  }

  public void handleOpenRegisterBranch() {
    Modal.display("Registrar Sucursal", "RegisterBranchModal");
  }

  public void handleOpenRegisterManager() {
    Modal.display("Registrar Gerente", "RegisterManagerModal");
  }

  public void handleLogOut() {
    AuthClient.getInstance().setCurrentUser(null);
    navigateFromThisPageTo("Login Page", "LoginPage");
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
