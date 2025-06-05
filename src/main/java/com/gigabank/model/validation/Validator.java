package com.gigabank.model.validation;

import java.time.LocalDate;

public class Validator {
  private static final String CURP_REGEX = "^[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[0-9]{2}$";
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NAME_REGEX_SPANISH = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\\s]+$";
  private static final String PHONE_REGEX = "^[0-9]{8}$";
  private static final String RFC_REGEX = "^[A-Z]{3,4}[0-9]{6}[A-Z0-9]{3}$";
  private static final String DISPLAY_NAME_REGEX = "^[A-Za-zÑñÁáÉéÍíÓóÚúÜü]+$";

  private static boolean isValidCURP(String curp) {
    String trimmed = curp != null ? curp.trim() : null;
    return trimmed != null && trimmed.matches(CURP_REGEX) && trimmed.length() == 18;
  }

  private static boolean isValidRFC(String rfc) {
    String trimmed = rfc != null ? rfc.trim() : null;
    return trimmed != null && trimmed.matches(RFC_REGEX) && (trimmed.length() == 12 || trimmed.length() == 13);
  }

  private static boolean isValidString(String str, int minLength, int maxLength) {
    return str != null && str.trim().length() >= minLength && str.trim().length() <= maxLength;
  }

  private static boolean isValidString(String str, int minLength, int maxLength, String regex) {
    return isValidString(str, minLength, maxLength) && str.trim().matches(regex);
  }

  public static String getValidCURP(String curp, String fieldName) throws InvalidFieldException {
    if (isValidCURP(curp)) {
      return curp.trim();
    }

    throw new InvalidFieldException(fieldName + " must be 18 characters long and follow the CURP format.");
  }

  public static String getValidString(String str, int minLength, int maxLength, String fieldName) throws InvalidFieldException {
    if (isValidString(str, minLength, maxLength)) {
      return str.trim();
    }

    throw new InvalidFieldException(fieldName + " must be between" + minLength + " and " + maxLength + " characters long.");
  }

  public static String getValidString(
    String str, int minLength, int maxLength, String fieldName, String regex, String formatMessage
  ) throws InvalidFieldException {
    String trimmedStr = getValidString(str, minLength, maxLength, fieldName);

    if (trimmedStr.matches(regex)) {
      return trimmedStr;
    }

    throw new InvalidFieldException("Value doesn't follow the format of a " + formatMessage + ".");
  }

  public static LocalDate getValidDateOfBirth(LocalDate dateOfBirth) throws InvalidFieldException {
    if (dateOfBirth == null) {
      throw new InvalidFieldException("Date of birth cannot be null.");
    }

    LocalDate now = LocalDate.now();
    if (dateOfBirth.isAfter(now)) {
      throw new InvalidFieldException("Date of birth cannot be in the future.");
    }

    if (dateOfBirth.isAfter(now.minusYears(18))) {
      throw new InvalidFieldException("Date of birth must be at least 18 years ago.");
    }

    return dateOfBirth;
  }

  public static String getValidDisplayName(String displayName) throws InvalidFieldException {
    if (isValidString(displayName, 3, 64, DISPLAY_NAME_REGEX)) {
      return displayName.trim();
    }

    throw new InvalidFieldException("Display name must be between 3 and 64 characters long and follow the display name format.");
  }

  public static String getValidCURP(String curp) throws InvalidFieldException {
    if (isValidString(curp, 18, 18, CURP_REGEX)) {
      return curp.trim();
    }

    throw new InvalidFieldException("CURP must be 18 characters long and follow the CURP format.");
  }

  public static String getValidEmail(String email) throws InvalidFieldException {
    if (isValidString(email, 5, 64, EMAIL_REGEX)) {
      return email.trim();
    }

    throw new InvalidFieldException("Email must be between 5 and 64 characters long and follow the email format.");
  }

  public static String getValidClientID(String id) throws InvalidFieldException {
    if (isValidCURP(id)) {
      return getValidCURP(id, "Client ID");
    }

    if (isValidRFC(id)) {
      return getValidRFC(id);
    }

    throw new InvalidFieldException("Client ID must be a valid CURP or RFC.");
  }

  public static String getValidName(String name, int minLength, int maxLength, String fieldName) throws InvalidFieldException {
    return getValidString(name, minLength, maxLength, fieldName, NAME_REGEX_SPANISH, "name");
  }

  public static String getValidPhone(String phone) throws InvalidFieldException {
    if (isValidString(phone, 8, 8, PHONE_REGEX)) {
      return phone.trim();
    }

    throw new InvalidFieldException("Phone must be 8 characters long and follow the phone format.");
  }

  public static String getValidRFC(String rfc) throws InvalidFieldException {
    if (isValidRFC(rfc)) {
      return rfc.trim();
    }

    throw new InvalidFieldException("RFC must be between 12 and 13 characters long and follow the RFC format.");
  }

  public static String getValidPassword(String password) throws InvalidFieldException {
    return getValidString(password, 8, 64, "Password");
  }

  public static float getValidWage(String wage) throws InvalidFieldException {
    String trimmedWage = getValidString(wage, 1, 10, "Wage");

    try {
      float wageFloat = Float.parseFloat(trimmedWage);

      if (wageFloat < 1600) {
        throw new InvalidFieldException("Wage must be 1600 or greater.");
      }

      if (wageFloat > 6000) {
        throw new InvalidFieldException("Wage must be 6000 or less.");
      }

      return wageFloat;
    } catch (NumberFormatException e) {
      throw new InvalidFieldException("Wage must be a valid floating number.");
    }
  }

  public static double getValidAmount(String amount, String fieldName) throws InvalidFieldException {
    String trimmedAmount = getValidString(amount, 1, 10, fieldName);

    try {
      double amountDouble = Double.parseDouble(trimmedAmount);

      if (amountDouble < 0) {
        throw new InvalidFieldException("La" + fieldName + " debe ser un número mayor a cero.");
      }

      return amountDouble;
    } catch (NumberFormatException e) {
      throw new InvalidFieldException("La " + fieldName + " debe ser un número flotante valido.");
    }
  }
}
