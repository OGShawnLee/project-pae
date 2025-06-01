package com.gigabank.model.db.account;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.AccountDTO;

import java.io.IOException;

interface AccountDBServiceShape {
  AccountDTO getAccountByDisplayName(String displayName) throws NotFoundRecordException;

  AccountDTO createAccount(AccountDTO accountDTO) throws DuplicateRecordException, IOException;

  boolean hasAdminAccount();
}
