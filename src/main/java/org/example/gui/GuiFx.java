package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import Bd.dao.ProdusDao;
import org.example.input.XlsxReader;
import org.example.model.Produs;

import java.io.File;
import java.util.List;

public class GuiFx extends Application {

    private File loadedFile;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 600);

        Button butonIncarca = createLoadButton();
        Button butonImport = createImportButton();
        root.getChildren().addAll(butonIncarca, butonImport);

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

    private Button createImportButton() {
        Button btn = new Button("importa in DB");
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setOnAction(e -> {
            if (loadedFile != null) {
                importFileToDb(loadedFile);
            } else {
                System.out.println("Niciun fisier incarcat");
            }
        });
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
        loadedFile = file;
        System.out.println("Fisier incarcat: " + file.getAbsolutePath());
    }

    private void importFileToDb(File file) {
        System.out.println("Import in DB din: " + file.getAbsolutePath());
        List<List<String>> rows = XlsxReader.read(file.toPath());
        boolean first = true;
        for (List<String> row : rows) {
            if (first) { first = false; continue; }
            if (row.size() < 3) continue;

            String codIntern = row.get(0);
            String ean = row.get(1);
            String nume = row.get(2);
            Integer unitate = parseInt(row, 3);
            Integer stoc = parseInt(row, 4);

            Produs produs = new Produs(ean, nume, codIntern, unitate, stoc);
            ProdusDao.getInstance().create(produs);
        }
        System.out.println("Import finalizat");
    }

    private Integer parseInt(List<String> row, int index) {
        if (row.size() > index) {
            try {
                return Integer.parseInt(row.get(index));
            } catch (NumberFormatException ignore) {
            }
        }
        return 0;
    }

    /**
     * Punct de intrare pentru JavaFX, apelat din Main.main(...)
     */
    public static void launchApp(String[] args) {
        launch(args);
    }
}
