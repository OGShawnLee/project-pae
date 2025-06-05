package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

public class BankAccountDTO {
  public enum AccountType {
    COMMON, BUSINESS, SAVINGS
  }

  private String id;
  private double balance;
  private double limit;
  private AccountType type;
  private ClientDTO client;
  private BranchDTO branch;

  public BankAccountDTO(
    String id,
    ClientDTO client,
    BranchDTO branch,
    String balance,
    AccountType type,
    String limit
  ) throws InvalidFieldException {
    this.id = id;
    this.branch = branch;
    this.client = client;
    this.balance = Validator.getValidAmount(balance, "Saldo");
    this.type = type;
    this.limit = Validator.getValidAmount(limit, "Limite de Crédito");
  }

  public String getID() {
    return id;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public double getLimit() {
    return limit;
  }

  public ClientDTO getClient() {
    return client;
  }

  public BranchDTO getBranch() {
    return branch;
  }

  public AccountType getType() {
    return type;
  }
}
