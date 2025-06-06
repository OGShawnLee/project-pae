package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.account.AccountDBProxy;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.gigabank.model.data.AccountDTO.Role.EXECUTIVE;
import static com.gigabank.model.data.AccountDTO.Role.TELLER;

public class RegisterEmployeeController extends Controller {
  @FXML
  private TextField displayNameField;
  @FXML
  private TextField nameField;
  @FXML
  private TextField wageField;
  @FXML
  private TextField addressField;
  @FXML
  private DatePicker bornAtDatePicker;
  @FXML
  private ChoiceBox<BranchDTO> branchChoiceBox;
  @FXML
  private ChoiceBox<Gender> genderChoiceBox;
  @FXML
  private ChoiceBox<AccountDTO.Role> roleChoiceBox;

  @FXML
  private void initialize() {
    RegisterManagerController.loadGenderChoiceBox(genderChoiceBox);
    RegisterManagerController.loadBranchChoiceBox(branchChoiceBox);
    loadRoleChoiceBox(roleChoiceBox);
  }

  public static void loadRoleChoiceBox(ChoiceBox<AccountDTO.Role> roleChoiceBox) {
    roleChoiceBox.getItems().addAll(EXECUTIVE, TELLER);
    roleChoiceBox.setValue(TELLER);
  }

  @FXML
  private void handleRegister() {
    try {
      String displayName = Validator.getValidDisplayName(displayNameField.getText());
      AccountDTO duplicateAccount = AccountDBProxy.getInstance().findOne(displayName);

      if (duplicateAccount != null) {
        throw new DuplicateRecordException(
          "Una Cuenta existe con este nombre de usuario. Por favor, elija un nombre de usuario diferente."
        );
      }

      EmployeeDTO duplicateEmployee = EmployeeDBProxy.getInstance().findOne(displayName);

      if (duplicateEmployee != null) {
        throw new DuplicateRecordException(
          "Un Empleado existe con este nombre de usuario. Por favor, elija un nombre de usuario diferente."
        );
      }

      AccountDTO accountDTO = new AccountDTO(
        displayName,
        displayName + "@Password",
        roleChoiceBox.getValue()
      );

      EmployeeDTO employeeDTO = new EmployeeDTO.EmployeeBuilder()
        .setAddress(addressField.getText())
        .setBornAt(bornAtDatePicker.getValue())
        .setGender(genderChoiceBox.getValue())
        .setName(nameField.getText())
        .setBranch(branchChoiceBox.getValue())
        .setDisplayName(displayName)
        .setWage(wageField.getText())
        .setRole(roleChoiceBox.getValue())
        .build();

      AccountDBProxy.getInstance().createOne(accountDTO);
      EmployeeDBProxy.getInstance().createOne(employeeDTO);

      Modal.displaySuccess("El Empleado ha sido registrado exitosamente.");
    } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}