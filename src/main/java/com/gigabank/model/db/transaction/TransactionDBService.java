package com.gigabank.model.db.transaction;

import com.gigabank.model.data.TransactionDTO;
import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionDBService extends DBService<HashMap<String, TransactionDTO>> implements TransactionDBServiceShape {
  public TransactionDBService() {
    super("transaction", new HashMap<>());
  }

  @Override
  public TransactionDTO findOne(String id) {
    return getDBStore().get(id);
  }

  @Override
  public TransactionDTO getOne(String id) throws NotFoundRecordException {
    TransactionDTO instance = getDBStore().get(id);

    if (instance == null) {
      throw new NotFoundRecordException("Transacción con el identificador'" + id + "' no se ha encontrado.");
    }

    return instance;
  }

  @Override
  public ArrayList<TransactionDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public TransactionDTO createOne(TransactionDTO transactionDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(transactionDTO.getID())) {
      throw new DuplicateRecordException("Transacción con el identificador dado ya existe.");
    }

    getDBStore().put(transactionDTO.getID(), transactionDTO);
    writeToFile();
    return transactionDTO;
  }

  @Override
  public void deleteOne(String id) throws IOException {
    getDBStore().remove(id);
    writeToFile();
  }
}
