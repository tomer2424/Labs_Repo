module org.example.lab_2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.lab_2 to javafx.fxml;
    exports org.example.lab_2;
}