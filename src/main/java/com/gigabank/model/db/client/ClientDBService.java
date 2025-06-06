package com.gigabank.model.db.client;

import com.gigabank.model.data.ClientDTO;
import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class ClientDBService extends DBService<HashMap<String, ClientDTO>> implements ClientDBServiceShape {
  public ClientDBService() {
    super("client", new HashMap<>());
  }

  @Override
  public ClientDTO findOne(String displayName) {
    return getDBStore().get(displayName);
  }

  @Override
  public ClientDTO getOne(String displayName) throws NotFoundRecordException {
    ClientDTO instance = getDBStore().get(displayName);

    if (instance == null) {
      throw new NotFoundRecordException("Cliente con el nombre de usuario: '" + displayName + "' no existe.");
    }

    return instance;
  }

  @Override
  public ArrayList<ClientDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public void updateOne(ClientDTO employeeDTO) throws NotFoundRecordException, IOException {
    if (getDBStore().containsKey(employeeDTO.getEmail())) {
      getDBStore().put(employeeDTO.getEmail(), employeeDTO);
      writeToFile();
    } else {
      throw new NotFoundRecordException("No ha sido posible actualizar al Cliente porque no ha sido encontrado.");
    }
  }

  @Override
  public ClientDTO createOne(ClientDTO employeeDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(employeeDTO.getEmail())) {
      throw new DuplicateRecordException("Cliente con el nombre de usuario introducido ya existe.");
    }

    getDBStore().put(employeeDTO.getEmail(), employeeDTO);
    writeToFile();
    return employeeDTO;
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBStore().remove(displayName);
    writeToFile();
  }
}
