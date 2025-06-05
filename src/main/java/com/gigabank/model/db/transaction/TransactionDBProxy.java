package com.gigabank.model.db.transaction;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.TransactionDTO;

import java.io.IOException;
import java.util.ArrayList;

public class TransactionDBProxy implements TransactionDBServiceShape {
  private static TransactionDBProxy uniqueInstance;
  private TransactionDBService dbService;

  private TransactionDBProxy () {}

  public static TransactionDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new TransactionDBProxy()) : uniqueInstance;
  }

  private TransactionDBService getDBService() {
    if (dbService == null) {
      dbService = new TransactionDBService();
    }

    return dbService;
  }

  @Override
  public TransactionDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public TransactionDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public ArrayList<TransactionDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public TransactionDTO createOne(TransactionDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(accountDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }
}
