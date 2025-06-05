package com.gigabank.controller;

import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.branch.BranchDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ManageBranchController extends ManageController<BranchDTO> {
  @FXML
  protected TextField nameField;
  @FXML
  protected TextField emailField;
  @FXML
  protected TextField addressField;
  @FXML
  protected TextField phoneField;

  public void initialize(BranchDTO branch) {
    super.initialize(branch);
    loadObjectDataFields(branch);
  }

  @Override
  protected void loadObjectDataFields(BranchDTO dataObject) {
    nameField.setText(dataObject.getName());
    emailField.setText(dataObject.getEmail());
    addressField.setText(dataObject.getAddress());
    phoneField.setText(dataObject.getPhone());
  }

  @FXML
  protected void handleManage() {
    try {
      BranchDBProxy.getInstance().updateOne(
        new BranchDTO(
          nameField.getText(),
          getCurrentDataObject().getEmail(),
          addressField.getText(),
          phoneField.getText()
        )
      );

      Modal.displaySuccess("La Sucursal ha sido actualizada con éxito.");
    } catch (InvalidFieldException | NotFoundRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
