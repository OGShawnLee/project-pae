package com.gigabank.model.data;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

public class ClientDTO extends Person {
  private final String lastName;
  private final String nationality;
  private final String email;
  private final String phone;

  public ClientDTO(ClientBuilder builder) {
    super(builder);
    this.lastName = builder.lastName;
    this.nationality = builder.nationality;
    this.email = builder.email;
    this.phone = builder.phone;
  }

  @Override
  public String toString() {
    return getFullName() + " (" + getEmail() + ")";
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return getName() + " " + getLastName();
  }

  public String getNationality() {
    return nationality;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  @Override
  public boolean equals(Object instance) {
    if (this == instance) return true;
    if (instance == null || getClass() != instance.getClass()) return false;

    ClientDTO that = (ClientDTO) instance;

    return super.equals(that) &&
           lastName.equals(that.lastName) &&
           nationality.equals(that.nationality) &&
           email.equals(that.email) &&
           phone.equals(that.phone);
  }

  public static class ClientBuilder extends PersonBuilder<ClientBuilder> {
    private String lastName;
    private String nationality;
    private String email;
    private String phone;

    public ClientBuilder setLastName(String lastName) throws InvalidFieldException {
      this.lastName = Validator.getValidName(lastName, 3, 64, "Apellido");
      return this;
    }

    public ClientBuilder setNationality(String nationality) throws InvalidFieldException {
      this.nationality = Validator.getValidName(nationality, 3, 64, "Nacionalidad");
      return this;
    }

    public ClientBuilder setEmail(String email) throws InvalidFieldException {
      this.email = Validator.getValidEmail(email);
      return this;
    }

    public ClientBuilder setPhone(String phone) throws InvalidFieldException {
      this.phone = Validator.getValidPhone(phone);
      return this;
    }

    @Override
    public ClientDTO build() {
      return new ClientDTO(this);
    }
  }
}