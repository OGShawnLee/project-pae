package com.gigabank.model.db.bank_account;

import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.BankAccountDTO;

import java.io.IOException;
import java.util.ArrayList;

public class BankAccountDBProxy implements BankAccountDBServiceShape {
  private static BankAccountDBProxy uniqueInstance;
  private BankAccountDBService dbService;

  private BankAccountDBProxy () {}

  public static BankAccountDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new BankAccountDBProxy()) : uniqueInstance;
  }

  private BankAccountDBService getDBService() {
    if (dbService == null) {
      dbService = new BankAccountDBService();
    }

    return dbService;
  }

  @Override
  public BankAccountDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public ArrayList<BankAccountDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public ArrayList<BankAccountDTO> getAllByBranch(BranchDTO branchDTO) {
    return getDBService().getAllByBranch(branchDTO);
  }

  @Override
  public BankAccountDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public void updateOne(BankAccountDTO accountDTO) throws NotFoundRecordException, IOException {
    getDBService().updateOne(accountDTO);
  }

  @Override
  public BankAccountDTO createOne(BankAccountDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(accountDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }
}
