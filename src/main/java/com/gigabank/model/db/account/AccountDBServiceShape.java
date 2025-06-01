package com.gigabank.model.db.account;

import com.gigabank.model.db.DAOShape;
import com.gigabank.model.data.AccountDTO;

interface AccountDBServiceShape extends DAOShape<AccountDTO, String> {
  boolean hasAdminAccount();
}
