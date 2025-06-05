package org.example.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TxtReader {

    /**
     * Citeste un fisier text CSV de pe disc (dupa path),
     * elimin liniile goale spatiile inceput sfarsit
     * iar rezultatul este o List<List<String>> în care fiecare sub‐listă
     * reprezintă o linie împărțită la virgule.
     *
     * @param path calea catre fisierul .txt pe care il citesc
     * @return lista de lista de stringuri
     */
    public static List<List<String>> read(Path path) {
        List<List<String>> result = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                // Elimin spatiile
                line = line.trim();
                if (line.isEmpty()) {
                    // Sar liniile goale
                    continue;
                }

                // impart linia dupa virgule
                String[] tokens = line.split(",", -1);

                // Construiesc lista cu rezultate
                List<String> row = new ArrayList<>(tokens.length);
                for (String token : tokens) {
                    row.add(token.trim());
                }
                result.add(row);
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
