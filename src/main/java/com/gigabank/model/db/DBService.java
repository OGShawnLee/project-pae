package com.gigabank.model.db;

import java.io.IOException;

public abstract class DBService<T> {
  private final static String DB_PATH = "src/main/resources";
  protected final T DB_STORE;
  protected String SERVICE_DB_PATH;

  protected DBService(String serviceName, T DB_STORE) {
    this.DB_STORE = Serializer.readFileToObjectOrDefault(DB_PATH + "/" + serviceName + "-db.ser", DB_STORE);
    this.SERVICE_DB_PATH = DB_PATH + "/" + serviceName + "-db.ser";
  }

  protected T getDBStore() {
    return DB_STORE;
  }

  protected void writeToFile() throws IOException {
    Serializer.writeToFile(SERVICE_DB_PATH, getDBStore());
  }
}
