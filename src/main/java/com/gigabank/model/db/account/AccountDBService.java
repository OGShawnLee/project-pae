package com.gigabank.model.db.account;

import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.dto.AccountDTO;

import java.io.IOException;
import java.util.HashMap;

class AccountDBService extends DBService<HashMap<String, AccountDTO>> implements AccountDBServiceShape {
  public AccountDBService() {
    super("account", new HashMap<>());
  }

  @Override
  public AccountDTO getAccountByDisplayName(String displayName) throws NotFoundRecordException {
    AccountDTO instance = getDBStore().get(displayName);

    if (instance == null) {
      throw new NotFoundRecordException("Account with display name '" + displayName + "' not found.");
    }

    return instance;
  }

  @Override
  public AccountDTO createAccount(AccountDTO accountDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(accountDTO.getDisplayName())) {
      throw new DuplicateRecordException("Account with this display name already exists.");
    }

    getDBStore().put(accountDTO.getDisplayName(), accountDTO);
    writeToFile();
    return accountDTO;
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
