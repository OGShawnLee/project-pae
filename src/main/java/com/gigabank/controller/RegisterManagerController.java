package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.account.AccountDBProxy;
import com.gigabank.model.db.branch.BranchDBProxy;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterManagerController extends Controller {
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

  public void initialize() {
    this.genderChoiceBox.getItems().setAll(Gender.values());
    this.genderChoiceBox.setValue(Gender.MALE);
    loadBranchChoiceBox();
  }

  private void loadBranchChoiceBox() {
    ArrayList<BranchDTO> branches = BranchDBProxy.getInstance().getAll();
    if (branches.isEmpty()) {
      Modal.displayError("No existe una Sucursal registrada. Por favor, registre una Sucursal antes de registrar un Gerente.");
      Modal.display("Registrar Sucursal", "RegisterBranchModal", this::loadBranchChoiceBox);
    } else {
      this.branchChoiceBox.getItems().setAll(branches);
      this.branchChoiceBox.setValue(branches.getFirst());
    }
  }

  @FXML
  private void handleRegister() {
    try {
      String displayName = Validator.getValidDisplayName(displayNameField.getText());
      AccountDTO duplicateAccount = AccountDBProxy.getInstance().findOne(displayName);

      if (duplicateAccount != null) {
        throw new DuplicateRecordException(
          "An account with this display name already exists. Please choose a different display name."
        );
      }

      EmployeeDTO duplicateEmployee = EmployeeDBProxy.getInstance().findOne(displayName);

      if (duplicateEmployee != null) {
        throw new DuplicateRecordException(
          "An employee with this display name already exists. Please choose a different display name."
        );
      }

      AccountDTO accountDTO = new AccountDTO(
        displayName,
        displayName + "@Password",
        AccountDTO.Role.MANAGER
      );
      EmployeeDTO employeeDTO = new EmployeeDTO.EmployeeBuilder()
        .setAddress(addressField.getText())
        .setBornAt(bornAtDatePicker.getValue().atStartOfDay())
        .setGender(genderChoiceBox.getValue())
        .setName(nameField.getText())
        .setBranch(branchChoiceBox.getValue())
        .setDisplayName(displayName)
        .setWage(wageField.getText())
        .setRole(AccountDTO.Role.MANAGER)
        .build();

      AccountDBProxy.getInstance().createOne(accountDTO);
      EmployeeDBProxy.getInstance().createOne(employeeDTO);

      Modal.displaySuccess("Manager created successfully.");
    } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
