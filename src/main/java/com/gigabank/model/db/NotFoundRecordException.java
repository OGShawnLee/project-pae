package com.gigabank.model.db;

public class NotFoundRecordException extends Exception {
  public NotFoundRecordException(String message) {
    super(message);
  }
}
