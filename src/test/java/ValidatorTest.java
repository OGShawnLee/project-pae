import com.gigabank.model.validation.InvalidFieldException;
import com.gigabank.model.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ValidatorTest {
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
