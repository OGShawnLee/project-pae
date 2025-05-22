package com.gigabank.model.validation;

public class InvalidFieldException extends Exception {
  public InvalidFieldException(String message) {
    super(message);
  }
}
