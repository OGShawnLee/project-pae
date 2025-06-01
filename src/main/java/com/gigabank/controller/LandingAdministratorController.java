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

  public void handleOpenRegisterManager() {
    Modal.display("Registrar Gerente", "RegisterManagerModal");
  }

  public void handleLogOut() {
    AuthClient.getInstance().setCurrentUser(null);
    navigateFromThisPageTo("Login Page", "LoginPage");
  }
}
