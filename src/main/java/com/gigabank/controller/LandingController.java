package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LandingController extends Controller {
  @FXML
  Label labelDisplayName;

  public void initialize() {
    labelDisplayName.setText("Welcome " + AuthClient.getInstance().getCurrentUser().getDisplayName() + " to GigaBank!");
  }

  public void handleLogOut() {
    AuthClient.getInstance().setCurrentUser(null);
    navigateFromThisPageTo("Login Page", "LoginPage");
  }
}
