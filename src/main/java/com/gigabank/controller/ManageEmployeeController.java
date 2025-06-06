package com.gigabank.controller;

import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.data.Gender;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;
import com.gigabank.model.validation.InvalidFieldException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class ManageEmployeeController extends ManageController<EmployeeDTO> {
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

    public void initialize(EmployeeDTO branch) {
        super.initialize(branch);
        RegisterManagerController.loadGenderChoiceBox(genderChoiceBox);
        loadObjectDataFields(branch);
    }

    @Override
    protected void loadObjectDataFields(EmployeeDTO dataObject) {
        displayNameField.setText(dataObject.getDisplayName());
        nameField.setText(dataObject.getName());
        addressField.setText(dataObject.getAddress());
        wageField.setText(String.valueOf(dataObject.getWage()));
        bornAtDatePicker.setValue(dataObject.getBornAt());
        genderChoiceBox.setValue(dataObject.getGender());
    }

    @FXML
    protected void handleManage() {
        try {
            EmployeeDBProxy.getInstance().updateOne(
                new EmployeeDTO.EmployeeBuilder()
                   .setDisplayName(displayNameField.getText())
                   .setName(nameField.getText())
                   .setAddress(addressField.getText())
                   .setBornAt(bornAtDatePicker.getValue())
                   .setGender(genderChoiceBox.getValue())
                   .setWage(wageField.getText())
                   .build()
            );

            Modal.displaySuccess("El empleado ha sido actualizado con éxito.");
        } catch (InvalidFieldException | NotFoundRecordException | IOException e) {
            Modal.displayError("Error al actualizar empleado: " + e.getMessage());
        }
    }
}