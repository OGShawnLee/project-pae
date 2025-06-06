package com.gigabank.model.db.branch;

import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.validation.InvalidFieldException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class BranchDBService extends DBService<HashMap<String, BranchDTO>> implements BranchDBServiceShape {
  public BranchDBService() {
    super("branch", new HashMap<>());
  }

  @Override
  public BranchDTO findOne(String email) {
    return getDBStore().get(email);
  }

  @Override
  public BranchDTO getOne(String email) throws NotFoundRecordException {
    BranchDTO instance = getDBStore().get(email);

    if (instance == null) {
      throw new NotFoundRecordException("Sucursal con el correo electrónico: '" + email + "' no ha sido encontrado.");
    }

    return instance;
  }

  @Override
  public ArrayList<BranchDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public void updateOne(BranchDTO branchDTO) throws NotFoundRecordException, IOException, InvalidFieldException {
    if (getDBStore().containsKey(branchDTO.getEmail())) {
      BranchDTO initial = getDBStore().get(branchDTO.getEmail());

      initial.setName(branchDTO.getName());
      initial.setAddress(branchDTO.getAddress());
      initial.setPhone(branchDTO.getPhone());

      writeToFile();
    } else {
      throw new NotFoundRecordException("No ha sido posible actualizar la sucursal porque no ha sido encontrado.");
    }
  }

  @Override
  public BranchDTO createOne(BranchDTO branchDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(branchDTO.getEmail())) {
      throw new DuplicateRecordException("Sucursal con el correo electrónico ya existe.");
    }

    getDBStore().put(branchDTO.getEmail(), branchDTO);
    writeToFile();
    return branchDTO;
  }

  public void deleteOne(String displayName) throws IOException {
    getDBStore().remove(displayName);
    writeToFile();
  }
}
