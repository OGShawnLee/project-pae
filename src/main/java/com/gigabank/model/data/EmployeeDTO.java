package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

public class EmployeeDTO extends Person {
  private final String displayName;
  private Gender gender;
  private float wage;
  private final AccountDTO.Role role;
  private BranchDTO branch;

  private EmployeeDTO(EmployeeBuilder builder) {
    super(builder);
    this.displayName = builder.displayName;
    this.gender = builder.gender;
    this.wage = builder.wage;
    this.role = builder.role;
    this.branch = builder.branch;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public float getWage() {
    return wage;
  }

  public void setWage(float wage) {
    this.wage = wage;
  }

  public BranchDTO getBranch() {
    return branch;
  }

  public void setBranch(BranchDTO branch) {
    this.branch = branch;
  }

  public AccountDTO.Role getRole() {
    return role;
  }

  @Override
  public boolean equals(Object instance) {
    if (this == instance) return true;
    if (instance == null || getClass() != instance.getClass()) return false;

    EmployeeDTO that = (EmployeeDTO) instance;

    return super.equals(that) &&
      displayName.equals(that.displayName) &&
      gender == that.gender &&
      wage == that.wage;
  }

  public static class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    private String id;
    private String displayName;
    private Gender gender;
    private float wage;
    private AccountDTO.Role role;
    private BranchDTO branch;

    public EmployeeBuilder setDisplayName(String displayName) throws InvalidFieldException {
      this.displayName = Validator.getValidName(displayName, 3, 32, "Nombre de Usuario");
      return this;
    }

    public EmployeeBuilder setGender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public EmployeeBuilder setWage(String wage) throws InvalidFieldException {
      this.wage = Validator.getValidWage(wage);
      return this;
    }

    public EmployeeBuilder setRole(AccountDTO.Role role) {
      this.role = role;
      return this;
    }

    public EmployeeBuilder setBranch(BranchDTO branch) {
      this.branch = branch;
      return this;
    }

    @Override
    public EmployeeDTO build() {
      return new EmployeeDTO(this);
    }
  }
}