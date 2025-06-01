package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

public class EmployeeDTO extends Person {
  private final String displayName;
  private final Gender gender;
  private final float wage;

  private EmployeeDTO(EmployeeBuilder builder) {
    super(builder);
    this.displayName = builder.displayName;
    this.gender = builder.gender;
    this.wage = builder.wage;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Gender getGender() {
    return gender;
  }

  public float getWage() {
    return wage;
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

    public EmployeeBuilder setDisplayName(String displayName) throws InvalidFieldException {
      this.displayName = Validator.getValidName(displayName, 3, 32, "Username");
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

    @Override
    public EmployeeDTO build() {
      return new EmployeeDTO(this);
    }
  }
}