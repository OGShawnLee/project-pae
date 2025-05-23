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
    final String[] INVALID_FORMATS = {
      // NOT 4 LETTERS AT THE BEGINNING
      "GOD123456HDFLNS099",
      // NOT 6 NUMBERS AFTER THE FIRST 4 LETTERS
      "GODE123F56HDFLNS09",
      // NOT H OR M AFTER THE FIRST 10 CHARACTERS
      "GODE123456LDFLNS09",
      // NOT 5 LETTERS AFTER THE FIRST 12 CHARACTERS
      "GODE123456HD2LNS02",
      // NOT 2 NUMBERS AT THE END
      "GODE123456HDFLNS0A",
    };
    for (String invalidFormat : INVALID_FORMATS) {
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

  @Test
  public void testGetValidRFC() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "GODE123456HDF",
          Validator.getValidRFC("GODE123456HDF"),
          "Valid RFC should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidRFCWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "GODE123456HDF",
          Validator.getValidRFC("   GODE123456HDF   "),
          "Valid RFC should be returned with spaces trimmed"
        );
      }
    );
  }

  @Test
  public void testGetValidRFCWithNull() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidRFC(null),
      "RFC cannot be null"
    );
  }

  @Test
  public void testGetValidRFCWithEmpty() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidRFC(""),
      "RFC cannot be empty"
    );
  }

  @Test
  public void testGetValidRFCWithInvalidFormat() {
    final String[] INVALID_FORMATS = {
      // NOT 3 LETTERS AT THE BEGINNING
      "GO12123456HDF",
      // NOT 6 NUMBERS AFTER THE FIRST 4 LETTERS
      "GODE123F56HDF",
      // NOT 3 LETTERS OR NUMBERS AFTER THE FIRST 12 CHARACTERS
      "GODE123456HD&",
    };
    for (String invalidFormat : INVALID_FORMATS) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidRFC(invalidFormat),
        "RFC must be in a valid format"
      );
    }
  }

  @Test
  public void testGetValidRFCWithInvalidLength() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidRFC("GODE123456HDFL"),
      "RFC must be 13 characters long"
    );
  }

  @Test
  public void testGetValidRFCWithInvalidCharacters() {
    String[] invalidCharacters = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", "?", "/", "~"};
    for (String invalidCharacter : invalidCharacters) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidRFC("GODE123456HDF" + invalidCharacter),
        "RFC cannot contain special characters"
      );
    }
  }

  @Test
  public void testGetValidPhone() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "12345678",
          Validator.getValidPhone("12345678"),
          "Valid phone should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidPhoneWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "12345678",
          Validator.getValidPhone("   12345678   "),
          "Valid phone should be returned with spaces trimmed"
        );
      }
    );
  }

  @Test
  public void testGetValidPhoneWithNull() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidPhone(null),
      "Phone cannot be null"
    );
  }

  @Test
  public void testGetValidPhoneWithEmpty() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidPhone(""),
      "Phone cannot be empty"
    );
  }

  @Test
  public void testGetValidPhoneWithInvalidFormat() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidPhone("1234567"),
      "Phone must be in a valid format"
    );
  }

  @Test
  public void testGetValidPhoneWithInvalidLength() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidPhone("123456789"),
      "Phone must be 8 characters long"
    );
  }

  @Test
  public void testGetValidPhoneWithInvalidCharacters() {
    String[] invalidCharacters = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", "?", "/", "~"};
    for (String invalidCharacter : invalidCharacters) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidPhone("12345678" + invalidCharacter),
        "Phone cannot contain special characters"
      );
    }
  }

  @Test
  public void testGetValidName() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "John Doe",
          Validator.getValidName("John Doe", 3, 64, "Name"),
          "Valid name should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidNameWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "John Doe",
          Validator.getValidName("   John Doe   ", 3, 64, "Name"),
          "Valid name should be returned with spaces trimmed"
        );
      }
    );
  }

  @Test
  public void testGetValidNameWithNull() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidName(null, 3, 64, "Name"),
      "Name cannot be null"
    );
  }

  @Test
  public void testGetValidNameWithEmpty() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidName("", 3, 64, "Name"),
      "Name cannot be empty"
    );
  }

  @Test
  public void testGetValidNameWithInvalidLength() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidName("Jo", 3, 64, "Name"),
      "Name must be between 3 and 64 characters long"
    );
  }

  @Test
  public void testGetValidNameWithInvalidFormat() {
    Assertions.assertThrows(
      InvalidFieldException.class,
      () -> Validator.getValidName("John123", 3, 64, "Name"),
      "Name must be in a valid format"
    );
  }

  @Test
  public void testGetValidNameWithInvalidCharacters() {
    String[] invalidCharacters = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", "?", "/", "~"};
    for (String invalidCharacter : invalidCharacters) {
      Assertions.assertThrows(
        InvalidFieldException.class,
        () -> Validator.getValidName("John Doe" + invalidCharacter, 3, 64, "Name"),
        "Name cannot contain special characters"
      );
    }
  }

  @Test
  public void testGetValidNameSpanish() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "José María Nuñez",
          Validator.getValidName("José María Nuñez", 3, 64, "Name"),
          "Valid name should be returned"
        );
      }
    );
  }

  @Test
  public void testGetValidNameSpanishWithSpaces() {
    assertDoesNotThrow(
      () -> {
        Assertions.assertEquals(
          "José María Nuñez",
          Validator.getValidName("   José María Nuñez   ", 3, 64, "Name"),
          "Valid name should be returned with spaces trimmed"
        );
      }
    );
  }
}
