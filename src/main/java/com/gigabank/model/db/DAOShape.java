package com.gigabank.model.db;

import com.gigabank.model.validation.InvalidFieldException;

import java.io.IOException;
import java.util.ArrayList;

public interface DAOShape<T, F> {
  T findOne(F filter);

  T getOne(F filter) throws NotFoundRecordException;

  ArrayList<T> getAll();

  T createOne(T entity) throws DuplicateRecordException, IOException;

  void updateOne(T entity) throws NotFoundRecordException, IOException, InvalidFieldException;

  void deleteOne(F filter) throws IOException;
}
