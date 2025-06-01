package com.gigabank.controller;

import javafx.scene.control.Alert;

public class Modal {
  public static void displayError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Error");
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void displayInfo(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Información");
    alert.setHeaderText("Información del Sistema");
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void displaySuccess(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Éxito");
    alert.setHeaderText("Registro Exitoso");
    alert.setContentText(message);
    alert.showAndWait();
  }
}
