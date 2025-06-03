package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import java.io.Serializable;

public class BranchDTO implements Serializable {
  private String name;
  private String email;
  private String address;
  private String phone;

  public BranchDTO(String name, String email, String address, String phone) throws InvalidFieldException {
    this.name = Validator.getValidName(name, 8, 64, "Branch Name");
    this.email = Validator.getValidEmail(email);
    this.address = Validator.getValidString(address, 8, 64, "Branch Address");
    this.phone = Validator.getValidPhone(phone);
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }
}