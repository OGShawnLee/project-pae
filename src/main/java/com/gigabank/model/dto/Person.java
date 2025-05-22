package com.gigabank.model.dto;

import com.gigabank.model.Validator;

import java.time.LocalDateTime;

public abstract class Person {
  private final String name;
  private final String address;
  private final LocalDateTime bornAt;

  public Person(PersonBuilder<?> builder) {
    this.name = builder.name;
    this.address = builder.address;
    this.bornAt = builder.bornAt;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public LocalDateTime getBornAt() {
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
    protected LocalDateTime bornAt;

    public T self() {
      return (T) this;
    }

    public T setName(String name) throws IllegalArgumentException {
      this.name = Validator.getValidName(name, 3, 64, "Name");
      return self();
    }

    public T setAddress(String address) throws IllegalArgumentException {
      this.address = Validator.getValidName(address, 3, 128, "Address");
      return self();
    }

    public T setBornAt(LocalDateTime bornAt) {
      this.bornAt = Validator.getValidDateOfBirth(bornAt);
      return self();
    }

    public abstract Person build();
  }
}