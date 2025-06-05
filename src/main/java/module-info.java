module Orders2.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.sql;
    opens org.example to javafx.fxml;
    exports org.example;
}