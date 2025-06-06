package com.gigabank.model.db.bank_account;

import com.gigabank.model.data.BankAccountDTO;
import com.gigabank.model.data.BranchDTO;
import com.gigabank.model.db.DAOShape;

import java.util.ArrayList;

public interface BankAccountDBServiceShape extends DAOShape<BankAccountDTO, String> {
  ArrayList<BankAccountDTO> getAllByBranch(BranchDTO branch);
}
