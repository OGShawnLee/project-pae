package com.gigabank.model.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {
  public static void writeToFile(String path, Object object) throws IOException {
    try (
      FileOutputStream fos = new FileOutputStream(path);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
    ) {
      oos.writeObject(object);
    }
  }

  private static <T> T readFileToObject(String path) throws IOException, ClassNotFoundException {
    try (
      FileInputStream fis = new FileInputStream(path);
      ObjectInputStream ois = new ObjectInputStream(fis);
    ) {
      return (T) ois.readObject();
    }
  }

  public static <T> T readFileToObjectOrDefault(String path, T defaultObject) {
    try {
      return readFileToObject(path);
    } catch (IOException | ClassNotFoundException e) {
      return defaultObject;
    }
  }
}
