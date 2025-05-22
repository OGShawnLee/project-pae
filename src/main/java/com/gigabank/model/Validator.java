package com.gigabank.model;

import java.time.LocalDateTime;

public class Validator {
  private static final String CURP_REGEX = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$";
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NAME_REGEX_SPANISH = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\\s]+$";
  private static final String PHONE_REGEX = "^[0-9]{8}$";
  private static final String RFC_REGEX = "^[A-Z]{3,4}[0-9]{6}[A-Z0-9]{3}$";

  private static boolean isValidString(String str, int minLength, int maxLength) {
    return str != null && str.trim().length() >= minLength && str.trim().length() <= maxLength;
  }

  private static boolean isValidString(String str, int minLength, int maxLength, String regex) {
    return isValidString(str, minLength, maxLength) && str.matches(regex);
  }

  public static String getValidString(String str, int minLength, int maxLength, String fieldName) {
    if (isValidString(str, minLength, maxLength)) {
      return str.trim();
    }

    throw new IllegalArgumentException(fieldName + " must be between" + minLength + " and " + maxLength + " characters long.");
  }

  public static String getValidString(String str, int minLength, int maxLength, String fieldName, String regex, String formatMessage) {
    String trimmedStr = getValidString(str, minLength, maxLength, fieldName);

    if (trimmedStr.matches(regex)) {
      return trimmedStr;
    }

    throw new IllegalArgumentException("Value doesn't follow the format of a " + formatMessage + ".");
  }

  public static LocalDateTime getValidDateOfBirth(LocalDateTime dateOfBirth) {
    if (dateOfBirth == null) {
      throw new IllegalArgumentException("Date of birth cannot be null.");
    }

    LocalDateTime now = LocalDateTime.now();
    if (dateOfBirth.isAfter(now)) {
      throw new IllegalArgumentException("Date of birth cannot be in the future.");
    }

    if (dateOfBirth.isAfter(now.minusYears(18))) {
      throw new IllegalArgumentException("Date of birth must be at least 18 years ago.");
    }

    return dateOfBirth;
  }

  public static String getValidCURP(String curp) {
    if (isValidString(curp, 18, 18, CURP_REGEX)) {
      return curp.trim();
    }

    throw new IllegalArgumentException("CURP must be 18 characters long and follow the CURP format.");
  }

  public static String getValidEmail(String email) {
    if (isValidString(email, 5, 64, EMAIL_REGEX)) {
      return email.trim();
    }

    throw new IllegalArgumentException("Email must be between 5 and 64 characters long and follow the email format.");
  }

  public static String getValidName(String name, int minLength, int maxLength, String fieldName) {
    return getValidString(name, minLength, maxLength, fieldName, NAME_REGEX_SPANISH, "name");
  }

  public static String getValidPhone(String phone) {
    if (isValidString(phone, 8, 8, PHONE_REGEX)) {
      return phone.trim();
    }

    throw new IllegalArgumentException("Phone must be 8 characters long and follow the phone format.");
  }

  public static String getValidRFC(String rfc) {
    if (isValidString(rfc, 12, 13, RFC_REGEX)) {
      return rfc.trim();
    }

    throw new IllegalArgumentException("RFC must be between 12 and 13 characters long and follow the RFC format.");
  }
}
