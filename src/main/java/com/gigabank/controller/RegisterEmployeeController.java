package com.gigabank.controller;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.EmployeeDTO.EmployeeBuilder;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.account.AccountDBProxy;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import com.gigabank.model.validation.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private ChoiceBox<Gender> genderChoiceBox;

    @FXML
    private ChoiceBox<AccountDTO.Role> roleChoiceBox;

    @FXML
    private void initialize() {
        genderChoiceBox.getItems().addAll(Gender.values());
        roleChoiceBox.getItems().addAll(EXECUTIVE, TELLER);
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
                    AccountDTO.Role.valueOf(String.valueOf(roleChoiceBox.getValue()))
            );


            EmployeeDTO employeeDTO = new EmployeeBuilder()
                    .setDisplayName(displayNameField.getText())
                    .setName(nameField.getText())
                    .setAddress(addressField.getText())
                    .setBornAt(bornAtDatePicker.getValue())
                    .setRole(roleChoiceBox.getValue())
                    .setGender(genderChoiceBox.getValue())
                    .setWage(wageField.getText())
                    .build();

            AccountDBProxy.getInstance().createOne(accountDTO);
            EmployeeDBProxy.getInstance().createOne(employeeDTO);

            Modal.displaySuccess("El empleado ha sido registrado exitosamente.");
        } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
            Modal.displayError(e.getMessage());
        }
    }
}