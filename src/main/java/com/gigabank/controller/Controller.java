package com.gigabank.controller;

import com.gigabank.model.AuthClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller {
  @FXML
  protected AnchorPane container;

  protected Stage getScene() {
    return (Stage) container.getScene().getWindow();
  }

  protected void navigateFromThisPageTo(String pageName, String resourceFileName) {
    navigateTo(getScene(), pageName, resourceFileName);
  }

  @FXML
  protected void navigateToLandingPage() {
    switch (AuthClient.getInstance().getCurrentUser().getRole()) {
      case ADMIN:
        navigateFromThisPageTo("Landing Page", "LandingAdministratorPage");
        break;
      case MANAGER:
        // TODO: Implement Manager Landing Page
        navigateFromThisPageTo("Landing Page", "LandingManagerPage");
        break;
      case EXECUTIVE:
        // TODO: Implement Executive Landing Page
        navigateFromThisPageTo("Landing Page", "LandingExecutivePage");
        break;
      case TELLER:
        // TODO: Implement Teller Landing Page
        navigateFromThisPageTo("Landing Page", "LandingTellerPage");
        break;
    }
  }

  protected static void navigateTo(Stage currentStage, String pageName, String resourceFileName) {
    try {
      Parent newView = FXMLLoader.load(
        Objects.requireNonNull(
          Controller.class.getResource("/" + resourceFileName + ".fxml")
        )
      );
      Scene newScene = new Scene(newView);

      currentStage.setScene(newScene);
      currentStage.show();
    } catch (IOException e) {
      Modal.displayError("Unable to navigate to: " + pageName + " due to a system error.");
    }
  }
}
