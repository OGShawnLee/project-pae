package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.branch.BranchDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterBranchController extends Controller {
  @FXML
  protected TextField nameField;
  @FXML
  protected TextField emailField;
  @FXML
  protected TextField addressField;
  @FXML
  protected TextField phoneField;

  @FXML
  private void handleRegister() {
    try {
      BranchDTO duplicateBranch = BranchDBProxy.getInstance().findOne(emailField.getText());

      if (duplicateBranch != null) {
        throw new DuplicateRecordException(
          "Una Sucursal ya existe con este correo electrónico. Intente con otro correo electrónico."
        );
      }

      BranchDBProxy.getInstance().createOne(
        new BranchDTO(
          nameField.getText(),
          emailField.getText(),
          addressField.getText(),
          phoneField.getText()
        )
      );

      Modal.displaySuccess("La Sucursal ha sido creada con éxito.");
    } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
