package com.gigabank.model.db.employee;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.DAOShape;
import com.gigabank.model.db.NotFoundRecordException;
import com.gigabank.model.validation.InvalidFieldException;

import java.io.IOException;
import java.util.ArrayList;

public interface EmployeeDBServiceShape extends DAOShape<EmployeeDTO, String> {
  ArrayList<EmployeeDTO> getAllByRole(AccountDTO.Role role);

  void updateProfile(EmployeeDTO employeeDTO) throws NotFoundRecordException, IOException, InvalidFieldException;
}
