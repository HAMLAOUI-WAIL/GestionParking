module com.example.my_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires tess4j;
    requires opencv;
    requires java.datatransfer;
    requires itextpdf;
    requires java.desktop;
    opens com.example.my_project to javafx.fxml;
    exports com.example.my_project;
}