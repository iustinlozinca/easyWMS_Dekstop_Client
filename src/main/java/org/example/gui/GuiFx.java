package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.List;

public class GuiFx extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        Button butonIncarca = createLoadButton();
        root.getChildren().add(butonIncarca);

        stage.setTitle("Orders");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Buton cu incarca fisier
     */
    private Button createLoadButton() {
        Button btn = new Button("incarca fisier");
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        configureDragAndDrop(btn);

        return btn;
    }

    /**
     * Un buton cu care iau doar primul fisier si dupa il prelucrez in
     * handelfileDrop
     *
     *
     *
     */
    private void configureDragAndDrop(Button button) {
        button.setOnDragOver((DragEvent event) -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        button.setOnDragDropped((DragEvent event) -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasFiles()) {
                success = true;
                List<File> files = dragboard.getFiles();
                File fisier = files.get(0);
                handleFileDropped(fisier);
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }


    private void handleFileDropped(File file) {
        /// metoda separata in care primesc fisierul dupa drop
        System.out.println("Fișier încărcat: " + file.getAbsolutePath());
        // aici  procesarea fisier
    }

    /**
     * Punct de intrare pentru JavaFX, apelat din Main.main(...)
     */
    public static void launchApp(String[] args) {
        launch(args);
    }
}
