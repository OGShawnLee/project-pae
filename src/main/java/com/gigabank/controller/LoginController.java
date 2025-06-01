package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.account.AccountDBProxy;
import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class LoginController extends Controller {
  @FXML
  TextField displayNameField;
  @FXML
  PasswordField passwordField;

  public void initialize() {
    if (AccountDBProxy.getInstance().hasAdminAccount()) return;

    Modal.displayInfo("Welcome to GigaBank! Please create an Admin Account to get stated.");
  }

  public void handleAdminAccount() throws InvalidFieldException, DuplicateRecordException, IOException {
    AccountDTO dataObject = AccountDBProxy.getInstance().createAccount(
      new AccountDTO(
        displayNameField.getText(),
        passwordField.getText(),
        AccountDTO.Role.ADMIN
      )
    );

    displayNameField.clear();
    passwordField.clear();

    Modal.displaySuccess("Admin Account created successfully. Welcome " + dataObject.getDisplayName() + " to GigaBank!");
    Modal.displaySuccess("Please login to continue.");
  }

  public void handleLoginAccount() throws InvalidFieldException, NotFoundRecordException {
    String displayName = Validator.getValidDisplayName(displayNameField.getText());
    String password = Validator.getValidPassword(passwordField.getText());
    AccountDTO dataObject = AccountDBProxy.getInstance().getAccountByDisplayName(displayName);

    if (dataObject.hasPasswordMatch(password)) {
      AuthClient.getInstance().setCurrentUser(dataObject);
      navigateFromThisPageTo("Landing Page", "LandingPage");
    } else {
      Modal.displayError("Invalid credentials. Please try again.");
    }
  }

  @FXML
  public void handleLogin() {
    try {
      boolean isThereAnAdminAccount = AccountDBProxy.getInstance().hasAdminAccount();

      if (isThereAnAdminAccount) {
        handleLoginAccount();
      } else {
        handleAdminAccount();
      }

    } catch (DuplicateRecordException | InvalidFieldException | NotFoundRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
