package com.gigabank.model.db.branch;

import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.BranchDTO;

import java.io.IOException;
import java.util.HashMap;

class BranchDBService extends DBService<HashMap<String, BranchDTO>> implements BranchDBServiceShape {
  public BranchDBService() {
    super("branch", new HashMap<>());
  }

  @Override
  public BranchDTO findOne(String email) {
    return getDBStore().get(email);
  }

  @Override
  public BranchDTO getOne(String email) throws NotFoundRecordException {
    BranchDTO instance = getDBStore().get(email);

    if (instance == null) {
      throw new NotFoundRecordException("Branch with email '" + email + "' not found.");
    }

    return instance;
  }

  @Override
  public BranchDTO createOne(BranchDTO branchDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(branchDTO.getEmail())) {
      throw new DuplicateRecordException("Branch with given email already exists.");
    }

    getDBStore().put(branchDTO.getEmail(), branchDTO);
    writeToFile();
    return branchDTO;
  }

  public void deleteOne(String displayName) throws IOException {
    getDBStore().remove(displayName);
    writeToFile();
  }
}
