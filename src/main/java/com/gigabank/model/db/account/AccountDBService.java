package com.gigabank.model.db.account;

import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.AccountDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class AccountDBService extends DBService<HashMap<String, AccountDTO>> implements AccountDBServiceShape {
  public AccountDBService() {
    super("account", new HashMap<>());
  }

  @Override
  public AccountDTO findOne(String displayName) {
    return getDBStore().get(displayName);
  }

  @Override
  public AccountDTO getOne(String displayName) throws NotFoundRecordException {
    AccountDTO instance = getDBStore().get(displayName);

    if (instance == null) {
      throw new NotFoundRecordException("Account with display name '" + displayName + "' not found.");
    }

    return instance;
  }

  @Override
  public ArrayList<AccountDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public AccountDTO createOne(AccountDTO accountDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(accountDTO.getDisplayName())) {
      throw new DuplicateRecordException("Account with this display name already exists.");
    }

    getDBStore().put(accountDTO.getDisplayName(), accountDTO);
    writeToFile();
    return accountDTO;
  }

  public void deleteOne(String displayName) throws IOException {
    getDBStore().remove(displayName);
    writeToFile();
  }

  @Override
  public boolean hasAdminAccount() {
    for (AccountDTO account : getDBStore().values()) {
      if (account.getRole() == AccountDTO.Role.ADMIN) {
        return true;
      }
    }

    return false;
  }
}
