package com.gigabank.model.db.employee;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.validation.InvalidFieldException;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeDBProxy implements EmployeeDBServiceShape {
  private static EmployeeDBProxy uniqueInstance;
  private EmployeeDBService dbService;

  private EmployeeDBProxy () {}

  public static EmployeeDBProxy getInstance() {
    return uniqueInstance == null ? (uniqueInstance = new EmployeeDBProxy()) : uniqueInstance;
  }

  private EmployeeDBService getDBService() {
    if (dbService == null) {
      dbService = new EmployeeDBService();
    }

    return dbService;
  }

  @Override
  public EmployeeDTO findOne(String displayName) {
    return getDBService().findOne(displayName);
  }

  @Override
  public EmployeeDTO getOne(String displayName) throws NotFoundRecordException {
    return getDBService().getOne(displayName);
  }

  @Override
  public ArrayList<EmployeeDTO> getAll() {
    return getDBService().getAll();
  }

  @Override
  public ArrayList<EmployeeDTO> getAllByRole(AccountDTO.Role role) {
    return getDBService().getAllByRole(role);
  }

  @Override
  public void updateOne(EmployeeDTO employeeDTO) throws NotFoundRecordException, IOException, InvalidFieldException {
    getDBService().updateOne(employeeDTO);
  }

  @Override
  public void updateProfile(EmployeeDTO employeeDTO) throws NotFoundRecordException, IOException, InvalidFieldException {
    getDBService().updateProfile(employeeDTO);
  }

  @Override
  public EmployeeDTO createOne(EmployeeDTO employeeDTO) throws DuplicateRecordException, IOException {
    return getDBService().createOne(employeeDTO);
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBService().deleteOne(displayName);
  }
}
