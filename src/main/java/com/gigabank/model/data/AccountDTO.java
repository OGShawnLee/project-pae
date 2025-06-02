package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import java.io.Serializable;

public class AccountDTO implements Serializable {
  public enum Role {
    ADMIN, MANAGER, EXECUTIVE, TELLER
  }

  private String displayName;
  private String password;
  private Role role;

  public AccountDTO(String displayName, String password, Role role) throws InvalidFieldException {
    this.displayName = Validator.getValidDisplayName(displayName);
    this.password = Validator.getValidPassword(password);
    this.role = role;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Role getRole() {
    return role;
  }

  public boolean hasPasswordMatch(String candidate) {
    return this.password.equals(candidate);
  }

  @Override
  public boolean equals(Object instance) {
    if (this == instance) return true;
    if (instance == null || getClass() != instance.getClass()) return false;

    AccountDTO that = (AccountDTO) instance;

    return displayName.equals(that.displayName) &&
           password.equals(that.password) &&
           role == that.role;
  }
}
