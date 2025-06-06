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
  public ClientDTO findOne(String email) {
    return getDBService().findOne(email);
  }

  @Override
  public ClientDTO getOne(String email) throws NotFoundRecordException {
    return getDBService().getOne(email);
  }

  @Override
  public ArrayList<ClientDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public void updateOne(ClientDTO clientDTO) throws NotFoundRecordException, IOException {
    getDBService().updateOne(clientDTO);
  }

  @Override
  public ClientDTO createOne(ClientDTO clientDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(clientDTO);
  }

  @Override
  public void deleteOne(String email) throws IOException {
    getDBService().deleteOne(email);
  }
}
