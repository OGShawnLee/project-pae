package com.gigabank.controller;

import com.gigabank.model.data.ClientDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.client.ClientDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterClientController extends Controller {
  @FXML
  protected TextField nameField;
  @FXML
  protected TextField lastNameField;
  @FXML
  protected TextField emailField;
  @FXML
  protected TextField addressField;
  @FXML
  protected TextField nationalityField;
  @FXML
  protected TextField phoneField;
  @FXML
  protected DatePicker bornAtDatePicker;

  @FXML
  private void handleRegister() {
    try {
      ClientDBProxy.getInstance().createOne(
        new ClientDTO.ClientBuilder()
          .setAddress(addressField.getText())
          .setBornAt(bornAtDatePicker.getValue())
          .setName(nameField.getText())
          .setLastName(lastNameField.getText())
          .setNationality(nationalityField.getText())
          .setPhone(phoneField.getText())
          .setEmail(emailField.getText())
          .build()
      );

      Modal.displaySuccess("El Cliente ha sido registrado exitosamente.");
    } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}
