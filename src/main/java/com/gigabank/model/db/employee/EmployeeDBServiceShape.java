package com.gigabank.model.db.employee;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.DAOShape;

import java.util.ArrayList;

public interface EmployeeDBServiceShape extends DAOShape<EmployeeDTO, String> {
  ArrayList<EmployeeDTO> getAllByRole(AccountDTO.Role role);
}
