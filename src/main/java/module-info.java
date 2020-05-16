module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
