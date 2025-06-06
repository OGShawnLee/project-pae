package com.gigabank.controller;

import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.EmployeeDTO.EmployeeBuilder;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import com.gigabank.model.validation.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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
    private void initialize() {
        genderChoiceBox.getItems().addAll(Gender.values());
    }

    @FXML
    private void handleRegister() {
        try {
            String displayName = Validator.getValidDisplayName(displayNameField.getText());
            EmployeeDTO duplicate = EmployeeDBProxy.getInstance().findOne(displayName);

            if (duplicate != null) {
                throw new DuplicateRecordException("Ya existe un empleado con ese nombre de usuario.");
            }

            EmployeeDTO employee = new EmployeeBuilder()
                    .setDisplayName(displayNameField.getText())
                    .setName(nameField.getText())
                    .setAddress(addressField.getText())
                    .setBornAt(bornAtDatePicker.getValue())
                    .setGender(genderChoiceBox.getValue())
                    .setWage(wageField.getText())
                    .build();

            EmployeeDBProxy.getInstance().createOne(employee);

            Modal.displaySuccess("El empleado ha sido registrado exitosamente.");
        } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
            Modal.displayError(e.getMessage());
        }
    }
}