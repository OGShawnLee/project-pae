package com.gigabank.model.db.employee;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.DBService;
import com.gigabank.model.db.DuplicateRecordException;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.validation.InvalidFieldException;

import java.io.IOException;
import java.util.ArrayList;
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
      throw new NotFoundRecordException("Empleado con el nombre de usuario: '" + displayName + "' no ha sido encontrado.");
    }

    return instance;
  }

  @Override
  public ArrayList<EmployeeDTO> getAll() {
    return new ArrayList<>(getDBStore().values());
  }

  @Override
  public ArrayList<EmployeeDTO> getAllByRole(AccountDTO.Role role) {
    ArrayList<EmployeeDTO> employees = new ArrayList<>();

    for (EmployeeDTO employee : getAll()) {
      if (employee.getRole() == role) {
        employees.add(employee);
      }
    }

    return employees;
  }

  @Override
  public void updateOne(EmployeeDTO employeeDTO) throws NotFoundRecordException, IOException, InvalidFieldException {
    if (getDBStore().containsKey(employeeDTO.getDisplayName())) {
      EmployeeDTO initial = getDBStore().get(employeeDTO.getDisplayName());

      initial.setName(employeeDTO.getName());
      initial.setAddress(employeeDTO.getAddress());
      initial.setBornAt(employeeDTO.getBornAt());
      initial.setBranch(employeeDTO.getBranch());
      initial.setGender(employeeDTO.getGender());
      initial.setWage(employeeDTO.getWage());
      initial.setRole(employeeDTO.getRole());

      writeToFile();
    } else {
      throw new NotFoundRecordException("No ha sido posible actualizar Empleado porque no ha sido encontrado.");
    }
  }

  @Override
  public void updateProfile(EmployeeDTO employeeDTO) throws NotFoundRecordException, IOException, InvalidFieldException {
    if (getDBStore().containsKey(employeeDTO.getDisplayName())) {
      EmployeeDTO initial = getDBStore().get(employeeDTO.getDisplayName());

      initial.setName(employeeDTO.getName());
      initial.setAddress(employeeDTO.getAddress());

      writeToFile();
    } else {
      throw new NotFoundRecordException("No ha sido posible actualizar Empleado porque no ha sido encontrado.");
    }
  }

  @Override
  public EmployeeDTO createOne(EmployeeDTO employeeDTO) throws DuplicateRecordException, IOException {
    if (getDBStore().containsKey(employeeDTO.getDisplayName())) {
      throw new DuplicateRecordException("Empleado con el nombre de usuario introducido ya existe.");
    }

    getDBStore().put(employeeDTO.getDisplayName(), employeeDTO);
    writeToFile();
    return employeeDTO;
  }

  @Override
  public void deleteOne(String displayName) throws IOException {
    getDBStore().remove(displayName);
    writeToFile();
  }
}
