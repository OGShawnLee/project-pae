package com.gigabank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public interface FileExporter {
  void handleExportToCSV();

  default void handleExportToJSON(Object instance, String title,Window window
  ) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Exportar Lista de Gerentes");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos JSON", "*.json"));

    File file = fileChooser.showSaveDialog(window);

    if (file != null) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        objectMapper.writeValue(file, instance);
        Modal.displaySuccess("Archivo JSON " + file.getName() + " exportado correctamente.");
      } catch (IOException e) {
        Modal.displayError("Error al exportar la lista de gerentes.");
      }
    }
  }
}
