package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ManageEmployeeController extends ManageController<EmployeeDTO> {
  @FXML
  protected TextField nameField;
  @FXML
  protected TextField addressField;
  @FXML
  protected DatePicker bornAtDatePicker;
  @FXML
  private TextField displayNameField;
  @FXML
  private ChoiceBox<BranchDTO> branchChoiceBox;
  @FXML
  private ChoiceBox<Gender> genderChoiceBox;
  @FXML
  private TextField wageField;
  @FXML
  private ChoiceBox<AccountDTO.Role> roleChoiceBox;

  public void initialize(EmployeeDTO branch) {
    super.initialize(branch);
    RegisterManagerController.loadGenderChoiceBox(genderChoiceBox);
    RegisterManagerController.loadBranchChoiceBox(branchChoiceBox);
    RegisterEmployeeController.loadRoleChoiceBox(roleChoiceBox);
    loadObjectDataFields(branch);
  }

  protected void loadObjectDataFields(EmployeeDTO dataObject) {
    nameField.setText(dataObject.getName());
    addressField.setText(dataObject.getAddress());
    displayNameField.setText(dataObject.getDisplayName());
    bornAtDatePicker.setValue(dataObject.getBornAt());
    branchChoiceBox.setValue(dataObject.getBranch());
    genderChoiceBox.setValue(dataObject.getGender());
    wageField.setText(String.valueOf(dataObject.getWage()));
    roleChoiceBox.setValue(dataObject.getRole());
  }

  @FXML
  protected void handleManage() {
    try {
      EmployeeDBProxy.getInstance().updateOne(
        new EmployeeDTO.EmployeeBuilder()
          .setName(nameField.getText())
          .setAddress(addressField.getText())
          .setBornAt(bornAtDatePicker.getValue())
          .setDisplayName(getCurrentDataObject().getDisplayName())
          .setBranch(branchChoiceBox.getValue())
          .setGender(genderChoiceBox.getValue())
          .setWage(wageField.getText())
          .setRole(roleChoiceBox.getValue())
          .build()
      );

      Modal.displaySuccess("El Empleado ha sido actualizado con éxito.");
    } catch (InvalidFieldException | NotFoundRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
