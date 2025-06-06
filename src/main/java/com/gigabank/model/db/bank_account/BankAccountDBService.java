package com.gigabank.model.db.bank_account;

import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.BankAccountDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class BankAccountDBService extends DBService<HashMap<String, BankAccountDTO>> implements BankAccountDBServiceShape {
  public BankAccountDBService() {
    super("bank-account", new HashMap<>());
  }

  @Override
  public BankAccountDTO findOne(String id) {
    return getDBStore().get(id);
  }

  @Override
  public BankAccountDTO getOne(String id) throws NotFoundRecordException {
    BankAccountDTO instance = getDBStore().get(id);

    if (instance == null) {
      throw new NotFoundRecordException("Cuenta Bancaría con la identificación '" + id + "' no ha sido encontrada.");
    }

    return instance;
  }

  @Override
  public ArrayList<BankAccountDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public ArrayList<BankAccountDTO> getAllByBranch(BranchDTO branch) {
    ArrayList<BankAccountDTO> accounts = new ArrayList<>();

    for (BankAccountDTO account : getDBStore().values()) {
      if (account.getBranch().getEmail().equals(branch.getEmail())) {
        accounts.add(account);
      }
    }

    return accounts;
  }

  @Override
  public void updateOne(BankAccountDTO bankAccountDTO) throws NotFoundRecordException, IOException {
    if (getDBStore().containsKey(bankAccountDTO.getID())) {
      getDBStore().put(bankAccountDTO.getID(), bankAccountDTO);
      writeToFile();
    } else {
      throw new NotFoundRecordException("No ha sido posible actualizar la cuenta bancaría porque no ha sido encontrada.");
    }
  }

  @Override
  public BankAccountDTO createOne(BankAccountDTO bankAccountDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(bankAccountDTO.getID())) {
      throw new DuplicateRecordException("Cuenta Bancaria con la identificación introducida ya existe.");
    }

    getDBStore().put(bankAccountDTO.getID(), bankAccountDTO);
    writeToFile();
    return bankAccountDTO;
  }

  public void deleteOne(String id) throws IOException {
    getDBStore().remove(id);
    writeToFile();
  }
}
