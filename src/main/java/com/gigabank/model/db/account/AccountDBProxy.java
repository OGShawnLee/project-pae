package com.gigabank.model.db.account;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.AccountDTO;

import java.io.IOException;

public class AccountDBProxy implements AccountDBServiceShape {
  private static AccountDBProxy instance;
  private AccountDBService dbService;

  private AccountDBProxy () {}

  public static AccountDBProxy getInstance() {
    return instance == null ? (instance = new AccountDBProxy()) : instance;
  }

  private AccountDBService getDBService() {
    if (dbService == null) {
      dbService = new AccountDBService();
    }

    return dbService;
  }

  @Override
  public AccountDTO getAccountByDisplayName(String displayName) throws NotFoundRecordException {
    return getDBService().getAccountByDisplayName(displayName);
  }

  @Override
  public AccountDTO createAccount(AccountDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createAccount(accountDTO);
  }

  @Override
  public boolean hasAdminAccount() {
    return getDBService().hasAdminAccount();
  }
}
