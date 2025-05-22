package com.gigabank.model.dto;

import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;

public class ClientDTO extends Person {
  private final String lastName;
  private final String nationality;
  private final String email;
  private final String phone;
  private final String curp;
  private final String rfc;

  public ClientDTO(ClientBuilder builder) {
    super(builder);
    this.lastName = builder.lastName;
    this.nationality = builder.nationality;
    this.email = builder.email;
    this.phone = builder.phone;
    this.curp = builder.curp;
    this.rfc = builder.rfc;
  }

  public String getLastName() {
    return lastName;
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

  public String getCURP() {
    return curp;
  }

  public String getRFC() {
    return rfc;
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
           phone.equals(that.phone) &&
           curp.equals(that.curp) &&
           rfc.equals(that.rfc);
  }

  public static class ClientBuilder extends PersonBuilder<ClientBuilder> {
    private String lastName;
    private String nationality;
    private String email;
    private String phone;
    private String curp;
    private String rfc;

    public ClientBuilder setLastName(String lastName) throws InvalidFieldException {
      this.lastName = Validator.getValidName(lastName, 3, 64, "Lastname");
      return this;
    }

    public ClientBuilder setNationality(String nationality) throws InvalidFieldException {
      this.nationality = Validator.getValidName(nationality, 3, 64, "Nationality");
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

    public ClientBuilder setCURP(String curp) throws InvalidFieldException {
      this.curp = Validator.getValidCURP(curp);
      return this;
    }

    public ClientBuilder setRFC(String rfc) throws InvalidFieldException {
      this.rfc = Validator.getValidRFC(rfc);
      return this;
    }

    @Override
    public ClientDTO build() {
      return new ClientDTO(this);
    }
  }
}