module org.onebeartoe.tours.javafx.fonts {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.onebeartoe.tours.javafx.fonts to javafx.fxml;
    exports org.onebeartoe.tours.javafx.fonts;
}
