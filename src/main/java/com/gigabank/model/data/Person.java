package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Person implements Serializable {
  private String name;
  private String address;
  private LocalDate bornAt;

  public Person(PersonBuilder<?> builder) {
    this.name = builder.name;
    this.address = builder.address;
    this.bornAt = builder.bornAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) throws InvalidFieldException {
    this.name = Validator.getValidName(name, 3, 64, "Name");
  }

  public void setAddress(String address) throws InvalidFieldException {
    this.address = Validator.getValidName(address, 3, 128, "Address");
  }

  public void setBornAt(LocalDate bornAt) throws InvalidFieldException {
    this.bornAt = Validator.getValidDateOfBirth(bornAt);
  }

  public String getAddress() {
    return address;
  }

  public LocalDate getBornAt() {
    return bornAt;
  }

  @Override
  public boolean equals(Object instance) {
    if (this == instance) return true;
    if (instance == null || getClass() != instance.getClass()) return false;

    Person that = (Person) instance;

    return name.equals(that.name) && address.equals(that.address) && bornAt.equals(that.bornAt);
  }

  public static abstract class PersonBuilder<T extends PersonBuilder<T>> {
    protected String name;
    protected String address;
    protected LocalDate bornAt;

    public T self() {
      return (T) this;
    }

    public T setName(String name) throws InvalidFieldException {
      this.name = Validator.getValidName(name, 3, 64, "Name");
      return self();
    }

    public T setAddress(String address) throws InvalidFieldException {
      this.address = Validator.getValidName(address, 3, 128, "Address");
      return self();
    }

    public T setBornAt(LocalDate bornAt) throws InvalidFieldException {
      this.bornAt = Validator.getValidDateOfBirth(bornAt);
      return self();
    }

    public abstract Person build();
  }
}