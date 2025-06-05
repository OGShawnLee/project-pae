package com.gigabank.model.data;

import java.io.Serializable;

public class TransactionDTO implements Serializable {
  public enum Type {
    DEPOSIT,
    TRANSFER,
    WITHDRAW,
  }

  private String id;
  private BankAccountDTO sourceAccount;
  private BankAccountDTO destinationAccount;
  private Type type;
  private double amount;

  public TransactionDTO(
    String id,
    BankAccountDTO sourceAccount,
    BankAccountDTO destinationAccount,
    Type type,
    double amount
  ) {
    if (type == Type.TRANSFER) {
      if (sourceAccount == null || destinationAccount == null) {
        throw new IllegalArgumentException("Las cuentas de destino y origen deben ser definidas cuando la transacción sea una Transferencia.");
      }

      if (sourceAccount.equals(destinationAccount)) {
        throw new IllegalArgumentException("Las cuentas deben ser distintas para una Transferencia.");
      }
    } else if (type == Type.DEPOSIT || type == Type.WITHDRAW) {
      if (sourceAccount == null) {
        throw new IllegalArgumentException("La cuenta de origen debe estar definida.");
      }
    }

    this.id = id;
    this.sourceAccount = sourceAccount;
    this.destinationAccount = destinationAccount;
    this.type = type;
    this.amount = amount;
  }

  public String getID() {
    return id;
  }

  public BankAccountDTO getSourceAccount() {
    return sourceAccount;
  }

  public BankAccountDTO getDestinationAccount() {
    return destinationAccount;
  }

  public Type getType() {
    return type;
  }

  public double getAmount() {
    return amount;
  }
}
