package com.gigabank;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
  public static class App1 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);

      Text text = new Text("Hello, JavaFX!");
      Button button = new Button("Click Me");

      container.getChildren().addAll(text, button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 1");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App2 extends Application {
    @Override
    public void start(Stage stage) {
      AnchorPane body = new AnchorPane();

      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);
      container.setMinWidth(100);

      Text text = new Text("Hello, JavaFX!");
      Button button = new Button("Click Me");

      AnchorPane.setTopAnchor(container, 60.0);
      container.getChildren().addAll(text, button);
      body.getChildren().addAll(container);

      Scene scene = new Scene(body, 300, 200);
      stage.setTitle("JavaFX Example - App 2");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App3 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);
      container.setMinWidth(100);

      Text text = new Text("Hello, JavaFX!");
      Button button = new Button("Click Me");
      button.setOnAction(e -> System.out.println("Button Clicked!"));
      button.setOnKeyPressed(e -> System.out.println("Key Pressed: " + e.getCode()));

      container.getChildren().addAll(text, button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 3");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App4 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);
      container.setMinWidth(100);

      TitledPane pane = new TitledPane("Title", new Text("Content goes here"));

      Text text = new Text("Hello, JavaFX!");
      Button button = new Button("Click Me");
      button.setOnAction(e -> System.out.println("Button Clicked!"));
      button.setOnKeyPressed(e -> System.out.println("Key Pressed: " + e.getCode()));

      container.getChildren().addAll(pane, text, button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 3");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App5 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container1 = new VBox();
      container1.setAlignment(Pos.CENTER);
      container1.setSpacing(16);
      container1.setMinWidth(100);

      Text text1 = new Text("Hello, JavaFX!");
      Button button1 = new Button("Click Me 1");
      button1.setOnAction(e -> System.out.println("Button 1 Clicked!"));

      container1.getChildren().addAll(text1, button1);

      VBox container2 = new VBox();
      container2.setAlignment(Pos.CENTER);
      container2.setSpacing(16);
      container2.setMinWidth(100);

      Text text2 = new Text("Hello, JavaFX!");
      Button button2 = new Button("Click Me 2");
      button2.setOnAction(e -> System.out.println("Button 2 Clicked!"));

      container2.getChildren().addAll(text2, button2);

      VBox container3 = new VBox();
      container3.setAlignment(Pos.CENTER);
      container3.setSpacing(16);
      container3.setMinWidth(100);

      Text text3 = new Text("Hello, JavaFX!");
      Button button3 = new Button("Click Me 3");
      button3.setOnAction(e -> System.out.println("Button 3 Clicked!"));

      container3.getChildren().addAll(text3, button3);

      Tab tab1 = new Tab("Tab 1", container1);
      Tab tab2 = new Tab("Tab 2", container2);
      Tab tab3 = new Tab("Tab 3", container3);
      TabPane container = new TabPane(tab1, tab2, tab3);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 5");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App6 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);
      container.setMinWidth(100);

      String[] languages = {"English", "Spanish", "French"};
      Text text = new Text("Selected Language: Spanish");
      Button button = new Button("Click Me");
      button.setOnAction(e -> {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Spanish", languages);
        dialog.setTitle("Language Selection");
        dialog.setHeaderText("Choose a language");
        dialog.setContentText("Available languages:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(language -> {
          text.setText("Selected Language: " + language);
          System.out.println("Language Selected: " + language);
        });
      });

      container.getChildren().addAll(text, button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 6");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App7 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);

      Text text = new Text("You have said: Nothing");
      Button button = new Button("Say Something");
      button.setOnMouseClicked(e -> {
        TextInputDialog dialog = new TextInputDialog("Nothing");
        dialog.setTitle("Writing Something");
        dialog.setHeaderText("Write Something");
        dialog.setContentText("Something: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(something -> {
          text.setText("You have said: " + something);
        });
      });
      container.getChildren().addAll(text, button);

      Scene scene = new Scene(container, 300, 300);
      stage.setTitle("TabPane");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class ButtonHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
      System.out.println("Button Clicked!");
    }
  }

  public static class App8 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);

      Text text = new Text("Hello, JavaFX!");
      Button button = new Button("Click Me");
      button.setOnMouseClicked(new ButtonHandler());

      container.getChildren().addAll(text, button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 8");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App9 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);
      container.setSpacing(16);

      Text text = new Text("Hello, JavaFX!");
      ComboBox<String> comboBox = new ComboBox<>();
      comboBox.getItems().addAll("iPhone", "Galaxy", "Pixel");
      comboBox.setValue("iPhone");
      comboBox.setOnAction(e -> {
        String selected = comboBox.getValue();
        text.setText("Selected: " + selected);
        System.out.println("ComboBox Selected: " + selected);
      });

      container.getChildren().addAll(text, comboBox);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("JavaFX Example - App 9");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App10 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);

      Button button = new Button("Open Alert");
      button.setAlignment(Pos.CENTER);
      button.setOnAction(e -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Alert Dialog");
        alert.setContentText("This is an error message and everything went down just because.");
        alert.show();
      });

      container.getChildren().add(button);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("Java FX Example - App 10");
      stage.setScene(scene);
      stage.show();
    }
  }

  public static class App11 extends Application {
    @Override
    public void start(Stage stage) {
      VBox container = new VBox();
      container.setAlignment(Pos.CENTER);

      DatePicker picker = new DatePicker();
      picker.setOnAction(e -> {
        LocalDate date = picker.getValue();

        if (date != null) {
          System.out.println("The date is " + date);
        }
      });

      container.getChildren().add(picker);

      Scene scene = new Scene(container, 300, 200);
      stage.setTitle("Java FX Example - App 11");
      stage.setScene(scene);
      stage.show();
    }
  }

  static class LocalRunnable implements Runnable {
    @Override
    public void run() {
      synchronized (this) {
        System.out.println("Runnable is running!");

        for (int i = 0; i < 3; i++) {
          System.out.println("Runnable thread: " + i + " " + Thread.currentThread().getName());
        }
      }
    }
  }

  static class LocalThread extends Thread {
    @Override
    public void run() {
      synchronized (this) {
        System.out.println("Thread is running!");

        for (int i = 0; i < 3; i++) {
          System.out.println("Thread class: " + i + " " + Thread.currentThread().getName());
        }
      }
    }
  }

  public static void handleThreading() {
    LocalRunnable running = new LocalRunnable();
    running.run();

    Thread runnableThread = new Thread(running);
    runnableThread.start();
  }

  public static void main(String[] args) {
    handleThreading();
    // Application.launch(App11.class);
  }
}