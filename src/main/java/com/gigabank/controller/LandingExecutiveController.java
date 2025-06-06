package com.gigabank.controller;


import com.gigabank.model.AuthClient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingExecutiveController extends Controller {
  @FXML
  private Label labelDisplayName;

  public void initialize() {
    labelDisplayName.setText("@" + AuthClient.getInstance().getCurrentUser().getDisplayName());
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

  public void handleOpenRegisterClient() {
    Modal.display("Registrar Cliente", "RegisterClientModal");
  }

  public void handleOpenRegisterBankAccount() {
    Modal.display("Registrar Cuenta", "RegisterBankAccountModal");
  }

  public void handleLogOut() {
    AuthClient.getInstance().setCurrentUser(null);
    navigateFromThisPageTo("Login Page", "LoginPage");
  }
}

