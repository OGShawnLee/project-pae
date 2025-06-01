package com.gigabank.model.db.employee;

import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;

import java.io.IOException;
import java.util.HashMap;

class EmployeeDBService extends DBService<HashMap<String, EmployeeDTO>> implements EmployeeDBServiceShape {
  public EmployeeDBService() {
    super("employee", new HashMap<>());
  }

  @Override
  public EmployeeDTO findOne(String displayName) {
    return getDBStore().get(displayName);
  }

  @Override
  public EmployeeDTO getOne(String displayName) throws NotFoundRecordException {
    EmployeeDTO instance = getDBStore().get(displayName);

    if (instance == null) {
      throw new NotFoundRecordException("Employee with the given display name '" + displayName + "' not found.");
    }

    return instance;
  }

  @Override
  public EmployeeDTO createOne(EmployeeDTO employeeDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(employeeDTO.getDisplayName())) {
      throw new DuplicateRecordException("Employee with the given display name already exists.");
    }

    getDBStore().put(employeeDTO.getDisplayName(), employeeDTO);
    writeToFile();
    return employeeDTO;
  }
}
