module org.onebeartoe.tours.jtop.fx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.onebeartoe.tours.jtop.fx to javafx.fxml;
    exports org.onebeartoe.tours.jtop.fx;
}
