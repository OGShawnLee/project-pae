package com.gigabank.controller;

import com.gigabank.model.Util;
import com.gigabank.model.data.BankAccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.data.ClientDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.bank_account.BankAccountDBProxy;
import com.gigabank.model.db.branch.BranchDBProxy;
import com.gigabank.model.db.client.ClientDBProxy;
import com.gigabank.model.validation.InvalidFieldException;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterBankAccountController extends Controller {
  @FXML
  private ChoiceBox<ClientDTO> clientChoiceBox;
  @FXML
  private ChoiceBox<BranchDTO> branchChoiceBox;
  @FXML
  private ChoiceBox<BankAccountDTO.AccountType> accountTypeChoiceBox;
  @FXML
  private TextField balanceField;
  @FXML
  private TextField limitField;

  public void initialize() {
    loadClientChoiceBox(clientChoiceBox);
    loadBranchChoiceBox(branchChoiceBox);
    loadAccountTypeChoiceBox(accountTypeChoiceBox);
  }

  public static void loadClientChoiceBox(ChoiceBox<ClientDTO> clientChoiceBox) {
    ArrayList<ClientDTO> clients = ClientDBProxy.getInstance().getAll();
    if (clients.isEmpty()) {
      Modal.displayError("No existe un Cliente registrado. Por favor, registre un Cliente antes de registrar una Cuenta Bancaria.");
    } else {
      clientChoiceBox.getItems().setAll(clients);
      clientChoiceBox.setValue(clients.getFirst());
    }
  }

  public static void loadBranchChoiceBox(ChoiceBox<BranchDTO> branchChoiceBox) {
    ArrayList<BranchDTO> branches = BranchDBProxy.getInstance().getAll();
    if (branches.isEmpty()) {
      Modal.displayError("No existe una Sucursal registrada. Por favor, registre una Sucursal antes de registrar una Cuenta Bancaria.");
    } else {
      branchChoiceBox.getItems().setAll(branches);
      branchChoiceBox.setValue(branches.getFirst());
    }
  }

  public static void loadAccountTypeChoiceBox(ChoiceBox<BankAccountDTO.AccountType> accountTypeChoiceBox) {
    accountTypeChoiceBox.getItems().setAll(BankAccountDTO.AccountType.values());
    accountTypeChoiceBox.setValue(BankAccountDTO.AccountType.COMMON);
  }

  @FXML
  private void handleRegister() {
    try {
      BankAccountDBProxy.getInstance().createOne(
        new BankAccountDTO(
          Util.generateRandomUUID(),
          clientChoiceBox.getValue(),
          branchChoiceBox.getValue(),
          balanceField.getText(),
          accountTypeChoiceBox.getValue(),
          limitField.getText()
        )
      );

      Modal.displaySuccess("La Cuenta Bancaria ha sido registrada exitosamente.");
    } catch (InvalidFieldException | DuplicateRecordException | IOException e) {
      Modal.displayError(e.getMessage());
    }
  }
}