package com.gigabank.model.db.account;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.AccountDTO;

import java.io.IOException;
import java.util.ArrayList;

public class AccountDBProxy implements AccountDBServiceShape {
  private static AccountDBProxy uniqueInstance;
  private AccountDBService dbService;

  private AccountDBProxy () {}

  public static AccountDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new AccountDBProxy()) : uniqueInstance;
  }

  private AccountDBService getDBService() {
    if (dbService == null) {
      dbService = new AccountDBService();
    }

    return dbService;
  }

  @Override
  public AccountDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public ArrayList<AccountDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public AccountDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public void updateOne(AccountDTO accountDTO) throws NotFoundRecordException, IOException {
    getDBService().updateOne(accountDTO);
  }

  @Override
  public AccountDTO createOne(AccountDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(accountDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }

  @Override
  public boolean hasAdminAccount() {
    return getDBService().hasAdminAccount();
  }
}
