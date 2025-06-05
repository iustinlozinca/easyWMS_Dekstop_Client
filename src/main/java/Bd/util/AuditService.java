package Bd.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final AuditService instance = new AuditService();
    private static final String FILE_NAME = "audit.csv";

    private AuditService() {}

    public static AuditService getInstance() {
        return instance;
    }

    public synchronized void log(String actionName) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            fw.append(actionName).append(",").append(timestamp).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
