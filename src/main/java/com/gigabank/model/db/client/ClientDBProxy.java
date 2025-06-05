package com.gigabank.model.db.client;

import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.ClientDTO;

import java.io.IOException;
import java.util.ArrayList;

public class ClientDBProxy implements ClientDBServiceShape {
  private static ClientDBProxy uniqueInstance;
  private ClientDBService dbService;

  private ClientDBProxy () {}

  public static ClientDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new ClientDBProxy()) : uniqueInstance;
  }

  private ClientDBService getDBService() {
    if (dbService == null) {
      dbService = new ClientDBService();
    }

    return dbService;
  }

  @Override
  public ClientDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public ClientDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public ArrayList<ClientDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public void updateOne(ClientDTO accountDTO) throws NotFoundRecordException, IOException {
    getDBService().updateOne(accountDTO);
  }

  @Override
  public ClientDTO createOne(ClientDTO accountDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(accountDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }
}
