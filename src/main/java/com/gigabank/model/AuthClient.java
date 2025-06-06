package com.gigabank.model;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.db.employee.EmployeeDBProxy;

public class AuthClient {
  private AccountDTO currentUser;
  private static AuthClient instance;

  private AuthClient() {
    this.currentUser = null;
  }

  public static AuthClient getInstance() {
    if (instance == null) {
      instance = new AuthClient();
    }

    return instance;
  }

  public AccountDTO getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(AccountDTO currentUser) {
    this.currentUser = currentUser;
  }

  public BranchDTO getCurrentBranch() throws NotFoundRecordException {
    if (currentUser != null) {
      return EmployeeDBProxy
        .getInstance()
        .getOne(AuthClient.getInstance().getCurrentUser().getDisplayName())
        .getBranch();
    }

    throw new NotFoundRecordException("No ha sido posible encontrar sucursal debido a que no hay usuario autenticado.");
  }
}
