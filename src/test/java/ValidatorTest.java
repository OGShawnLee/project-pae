import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ValidatorTest {
  @Test
  public void testGetValidCURP() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "GODE123456HDFLNS09",
          Validator.getValidCURP("GODE123456HDFLNS09"),
          "Valid CURP should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidCURPWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "GODE123456HDFLNS09",
          Validator.getValidCURP("   GODE123456HDFLNS09   "),
          "Valid CURP should be returned with spaces trimmed"
        );
      }
    );
  }

  @Test
  public void testGetValidCURPWithNull() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidCURP(null),
      "CURP cannot be null"
    );
  }

  @Test
  public void testGetValidCURPWithEmpty() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidCURP(""),
      "CURP cannot be empty"
    );
  }

  @Test
  public void testGetValidCURPWithInvalidFormat() {
    String[] invalidFormats = {
      // NOT 4 LETTERS AT THE BEGINNING
      "GOD123456HDFLNS099",
      // NOT 6 NUMBERS AFTER THE FIRST 4 LETTERS
      "GODE123F56HDFLNS09",
      // NOT H OR M AFTER THE FIRST 10 CHARACTERS
      "GODE123456lDFLNS09",
      // NOT 5 LETTERS AFTER THE FIRST 12 CHARACTERS
      "GODE123456HD2LNS02",
      // NOT 2 NUMBERS AT THE END
      "GODE123456HDFLNS0A",
    };
    for (String invalidFormat : invalidFormats) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidCURP(invalidFormat),
        "CURP must be in a valid format"
      );
    }
  }

  @Test
  public void testGetValidCURPWithInvalidLength() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidCURP("GODE123456HDFLNS0"),
      "CURP must be 18 characters long"
    );
  }

  @Test
  public void testGetValidCURPWithInvalidCharacters() {
    String[] invalidCharacters = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", "?", "/", "~"};
    for (String invalidCharacter : invalidCharacters) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidCURP("GODE123456HDFLNS0" + invalidCharacter),
        "CURP cannot contain special characters"
      );
    }
  }

  @Test
  public void testGetValidEmail() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "OGJohnDoe@gmail.com",
          Validator.getValidEmail("OGJohnDoe@gmail.com"),
          "Valid email should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidEmailWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "OGJohnDoe@gmail.com",
          Validator.getValidEmail("   OGJohnDoe@gmail.com   "),
          "Valid email should be returned with spaces trimmed"
        );
      }
    );
  }

  @Test
  public void testGetValidEmailWithNull() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidEmail(null),
      "Email cannot be null"
    );
  }

  @Test
  public void testGetValidEmailWithEmpty() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidEmail(""),
      "Email cannot be empty"
    );
  }

  @Test
  public void testGetValidEmailWithInvalidFormat() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidEmail("invalid-email"),
      "Email must be in a valid format"
    );
  }

  @Test
  public void testGetValidEmailWithInvalidDomain() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidEmail("user@invalid-domain"),
      "Email must be in a valid format"
    );
  }

  @Test
  public void testGetValidEmailWithInvalidLength() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidEmail("a@b.c"),
      "Email must be between 6 and 64 characters"
    );
  }

  @Test
  public void testGetValidEmailWithInvalidCharacters() {
    String[] invalidCharacters = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", "?", "/", "~"};
    for (String invalidCharacter : invalidCharacters) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidEmail("JohnDoe" + invalidCharacter + "@gmail.com"),
        "Email cannot contain special characters"
      );
    }
  }
}
