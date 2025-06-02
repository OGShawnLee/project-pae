package db;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.account.AccountDBProxy;
import com.gigabank.model.validation.InvalidFieldException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountDBProxyTest {
  private static final AccountDTO ACCOUNT_DTO;

  static {
    try {
      ACCOUNT_DTO = new AccountDTO("testAccount", "testPassword", AccountDTO.Role.TELLER);
    } catch (InvalidFieldException e) {
      throw new RuntimeException(e);
    }
  }

  public static AccountDTO createOneTestAccount() throws DuplicateRecordException, IOException {
    return AccountDBProxy.getInstance().createOne(ACCOUNT_DTO);
  }

  public static void deleteOneTestAccount() throws IOException {
    AccountDBProxy.getInstance().deleteOne(ACCOUNT_DTO.getDisplayName());
  }

  @AfterEach
  public void tearDown() throws IOException {
    deleteOneTestAccount();
  }

  @Test
  public void createOneAccountTest() {
    assertDoesNotThrow(() -> {
      AccountDTO dataObject = createOneTestAccount();
      assertEquals(ACCOUNT_DTO, dataObject);
    });
  }

  @Test
  public void findOneAccountTestExists() {
    assertDoesNotThrow(() -> {
      AccountDTO dataObject = createOneTestAccount();
      AccountDTO retrievedObject = AccountDBProxy.getInstance().findOne(ACCOUNT_DTO.getDisplayName());
      assertEquals(dataObject, retrievedObject);
    });
  }

  @Test
  public void findOneAccountTestNotExists() {
    assertDoesNotThrow(() -> {
      AccountDTO retrievedObject = AccountDBProxy.getInstance().findOne("nonExistentAccount");
      assertNull(retrievedObject);
    });
  }

  @Test
  public void getOneAccountTest() {
    assertDoesNotThrow(() -> {
      AccountDTO dataObject = createOneTestAccount();
      AccountDTO retrievedObject = AccountDBProxy.getInstance().getOne(ACCOUNT_DTO.getDisplayName());
      assertEquals(dataObject, retrievedObject);
    });
  }

  @Test
  public void deleteOneAccountTest() {
    assertDoesNotThrow(() -> {
      createOneTestAccount();
      AccountDBProxy.getInstance().deleteOne(ACCOUNT_DTO.getDisplayName());
      assertNull(AccountDBProxy.getInstance().findOne(ACCOUNT_DTO.getDisplayName()));
    });
  }
}
