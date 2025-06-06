package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import com.gigabank.model.Util;
import com.gigabank.model.data.BankAccountDTO;
import com.gigabank.model.data.TransactionDTO;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.bank_account.BankAccountDBProxy;
import com.gigabank.model.db.transaction.TransactionDBProxy;
import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegisterTransactionController extends Controller {
  @FXML
  private ChoiceBox<TransactionDTO.Type> typeChoiceBox;
  @FXML
  private ChoiceBox<BankAccountDTO> sourceAccountChoiceBox;
  @FXML
  private TextField sourceAccountBalance;
  @FXML
  private ChoiceBox<BankAccountDTO> destinationAccountChoiceBox;
  @FXML
  protected TextField amountField;

  public void initialize() {
    loadTypeChoiceBox();
    loadSourceAccountChoiceBox();
    loadDestinationAccountChoiceBox();
  }

  public void loadTypeChoiceBox() {
    typeChoiceBox.getItems().setAll(TransactionDTO.Type.values());
    typeChoiceBox.setValue(TransactionDTO.Type.WITHDRAW);
    typeChoiceBox.setOnAction(event -> {
      TransactionDTO.Type selectedType = typeChoiceBox.getValue();
      switch (selectedType) {
        case DEPOSIT -> {
          sourceAccountChoiceBox.setDisable(true);
          destinationAccountChoiceBox.setDisable(false);
          sourceAccountBalance.setText("0.00");
          amountField.setPromptText("Introduzca la Cantidad a Depositar");
        }
        case WITHDRAW -> {
          sourceAccountChoiceBox.setDisable(false);
          destinationAccountChoiceBox.setDisable(true);
          sourceAccountBalance.setText(
            String.valueOf(sourceAccountChoiceBox.getValue().getBalance())
          );
          amountField.setPromptText("Introduzca la Cantidad a Retirar");
        }
        case TRANSFER -> {
          sourceAccountChoiceBox.setDisable(false);
          destinationAccountChoiceBox.setDisable(false);
          sourceAccountBalance.setText(
            String.valueOf(sourceAccountChoiceBox.getValue().getBalance())
          );
          amountField.setPromptText("Introduzca la Cantidad a Transferir");
        }
      }
    });
  }

  public void loadSourceAccountChoiceBox() {
    try {
      ArrayList<BankAccountDTO> accounts = BankAccountDBProxy
        .getInstance()
        .getAllByBranch(AuthClient.getInstance().getCurrentBranch());

      if (accounts.isEmpty()) {
        Modal.displayError("No existen cuentas bancarias registradas en esta sucursal.");
        return;
      }

      BankAccountDTO firstAccount = accounts.getFirst();

      sourceAccountChoiceBox.getItems().setAll(accounts);
      sourceAccountChoiceBox.setValue(firstAccount);
      sourceAccountChoiceBox.setOnAction(event -> {
        BankAccountDTO selectedAccount = sourceAccountChoiceBox.getValue();
        if (selectedAccount != null) {
          sourceAccountBalance.setText(String.valueOf(selectedAccount.getBalance()));
          loadDestinationAccountChoiceBox();
        }
      });
      sourceAccountBalance.setText(String.valueOf(firstAccount.getBalance()));
    } catch (NotFoundRecordException e) {
      Modal.displayError(e.getMessage());
    }
  }

  public void loadDestinationAccountChoiceBox() {
    ArrayList<BankAccountDTO> accounts = BankAccountDBProxy.getInstance().getAll();

    if (accounts.isEmpty()) {
      Modal.displayError("No existen cuentas bancarias registradas.");
      return;
    }

    destinationAccountChoiceBox.getItems().setAll(accounts);
    destinationAccountChoiceBox.setValue(accounts.getFirst());
  }

  @FXML
  private void handleRegister() {
    try {
      BankAccountDTO sourceAccount = BankAccountDBProxy.getInstance().getOne(sourceAccountChoiceBox.getValue().getID());
      BankAccountDTO destinationAccount = BankAccountDBProxy.getInstance().findOne(destinationAccountChoiceBox.getValue().getID());

      TransactionDTO transactionDTO = null;

      switch (typeChoiceBox.getValue()) {
        case TRANSFER -> {
          transactionDTO = handleTransfer(sourceAccount, destinationAccount);
        }
        case DEPOSIT -> {
          transactionDTO = handleDeposit(destinationAccount);
        }
        case WITHDRAW -> {
          transactionDTO = handleWithdraw(sourceAccount);
        }
      }

      sourceAccountBalance.setText(String.valueOf(sourceAccount.getBalance()));

      if (transactionDTO != null) {
        handleExportToTXT(transactionDTO);
      }
    } catch (InvalidFieldException | NotFoundRecordException | IOException |
             BalanceException | FailedTransactionException e) {
      Modal.displayError(e.getMessage());
    }
  }

  private TransactionDTO handleTransfer(BankAccountDTO fromAccount, BankAccountDTO toAccount) throws InvalidFieldException, BalanceException, FailedTransactionException, IOException, NotFoundRecordException {
    double balance = fromAccount.getBalance();
    double amount = Validator.getValidAmount(amountField.getText(), "Cantidad a Transferir");

    if (amount > balance) {
      throw new BalanceException("Saldo insuficiente para realizar la transferencia.");
    }

    double limit = toAccount.getLimit();
    double amountTo = toAccount.getBalance();

    if (amountTo + amount > limit) {
      throw new BalanceException("La transferencia excede el límite de la cuenta destino.");
    }

    TransactionDTO transactionDTO = new TransactionDTO(
      Util.generateRandomUUID(),
      fromAccount,
      toAccount,
      TransactionDTO.Type.TRANSFER,
      amountField.getText()
    );

    fromAccount.setBalance(balance - amount);
    toAccount.setBalance(amountTo + amount);

    try {
      BankAccountDBProxy.getInstance().updateOne(fromAccount);
      BankAccountDBProxy.getInstance().updateOne(toAccount);
      TransactionDBProxy.getInstance().createOne(transactionDTO);
    } catch (Exception e) {
      // ROLLBACK
      fromAccount.setBalance(balance);
      toAccount.setBalance(amountTo);
      BankAccountDBProxy.getInstance().updateOne(fromAccount);
      BankAccountDBProxy.getInstance().updateOne(toAccount);
      throw new FailedTransactionException("Error al registrar la transacción de transferencia: " + e.getMessage());
    }

    Modal.displaySuccess("Transferencia realizada correctamente.");
    return transactionDTO;
  }

  private TransactionDTO handleDeposit(BankAccountDTO toAccount)
    throws InvalidFieldException, BalanceException, IOException, NotFoundRecordException, FailedTransactionException {
    double balance = toAccount.getBalance();
    double limit = toAccount.getLimit();
    double amount = Validator.getValidAmount(amountField.getText(), "Cantidad a Depositar");

    if (balance + amount > limit) {
      throw new BalanceException("El depósito excede el límite de la cuenta.");
    }

    TransactionDTO transactionDTO = new TransactionDTO(
      Util.generateRandomUUID(),
      toAccount,
      null,
      TransactionDTO.Type.DEPOSIT,
      amountField.getText()
    );

    toAccount.setBalance(balance + amount);

    try {
      BankAccountDBProxy.getInstance().updateOne(toAccount);
      TransactionDBProxy.getInstance().createOne(transactionDTO);
    } catch (Exception e) {
      // ROLLBACK
      toAccount.setBalance(balance);
      BankAccountDBProxy.getInstance().updateOne(toAccount);
      throw new FailedTransactionException("Error al registrar la transacción de depósito: " + e.getMessage());
    }

    Modal.displaySuccess("Depósito realizado correctamente.");
    return transactionDTO;
  }

  private TransactionDTO handleWithdraw(BankAccountDTO fromAccount)
    throws InvalidFieldException, BalanceException, IOException, NotFoundRecordException, FailedTransactionException {
    double balance = fromAccount.getBalance();
    double amount = Validator.getValidAmount(amountField.getText(), "Cantidad a Retirar");

    if (amount > balance) {
      throw new BalanceException("Saldo insuficiente para realizar el retiro.");
    }

    TransactionDTO transactionDTO = new TransactionDTO(
      Util.generateRandomUUID(),
      fromAccount,
      null,
      TransactionDTO.Type.WITHDRAW,
      amountField.getText()
    );

    fromAccount.setBalance(balance - amount);

    try {
      BankAccountDBProxy.getInstance().updateOne(fromAccount);
      TransactionDBProxy.getInstance().createOne(transactionDTO);
    } catch (Exception e) {
      // ROLLBACK
      fromAccount.setBalance(balance);
      BankAccountDBProxy.getInstance().updateOne(fromAccount);
      throw new FailedTransactionException("Error al registrar la transacción de retiro: " + e.getMessage());
    }

    Modal.displaySuccess("Retiro realizado correctamente.");
    return transactionDTO;
  }

  private void handleExportToTXT(TransactionDTO transactionDTO) {
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setTitle("Guardar Comprobante de Transacción");


    File dir = dirChooser.showDialog(container.getScene().getWindow());

    if (dir == null) {
      return;
    }

    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
    String fileName = String.format("gigabank_ticket_%s_%s.txt", transactionDTO.getID(), date);

    try (FileWriter writer = new FileWriter(new File(dir, fileName))) {
      writer.write("--- Comprobante de Transacción - GigaBank ---\n");
      writer.write("Sucursal: " + AuthClient.getInstance().getCurrentBranch() + "\n");

      writer.write("Fecha: " + date + "\n");
      writer.write("ID: " + transactionDTO.getID() + "\n");
      writer.write("Tipo: " + transactionDTO.getType() + "\n");
      writer.write("Monto: $" + transactionDTO.getAmount() + "\n");

      if (transactionDTO.getSourceAccount() != null) {
        writer.write("Cuenta Origen: " + transactionDTO.getSourceAccount() + "\n");
      }

      if (transactionDTO.getDestinationAccount() != null) {
        writer.write("Cuenta Destino: " + transactionDTO.getDestinationAccount() + "\n");
      }

      writer.write("Gracias por usar Gigabank.\n");
      writer.write("-------------------------------\n");
    } catch (IOException | NotFoundRecordException e) {
      Modal.displayError("Error al guardar el comprobante: " + e.getMessage());
      return;
    }

    Modal.displaySuccess("Comprobante de transacción guardado correctamente.");
  }
}
