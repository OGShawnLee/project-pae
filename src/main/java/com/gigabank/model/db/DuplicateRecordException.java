package com.gigabank.model.db;

public class DuplicateRecordException extends Exception {
  public DuplicateRecordException(String message) {
    super(message);
  }
}
