package com.gigabank.model.db.employee;

import com.gigabank.model.data.AccountDTO;
import com.gigabank.model.data.EmployeeDTO;
import com.gigabank.model.db.DAOShape;

import java.util.List;

public interface EmployeeDBServiceShape extends DAOShape<EmployeeDTO, String> {
  List<EmployeeDTO> getAllByRole(AccountDTO.Role role);
}
