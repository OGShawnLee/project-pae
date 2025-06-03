package com.gigabank.model.db.branch;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.BranchDTO;

import java.io.IOException;
import java.util.ArrayList;

public class BranchDBProxy implements BranchDBServiceShape {
  private static BranchDBProxy uniqueInstance;
  private BranchDBService dbService;

  private BranchDBProxy () {}

  public static BranchDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new BranchDBProxy()) : uniqueInstance;
  }

  private BranchDBService getDBService() {
    if (dbService == null) {
      dbService = new BranchDBService();
    }

    return dbService;
  }

  @Override
  public BranchDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public BranchDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public ArrayList<BranchDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public BranchDTO createOne(BranchDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(accountDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }
}
