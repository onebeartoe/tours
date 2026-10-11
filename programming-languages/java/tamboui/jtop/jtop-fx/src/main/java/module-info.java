module org.onebeartoe.tours.jtop.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.oshi;

    opens org.onebeartoe.tours.jtop.fx to javafx.fxml, javafx.base;
    exports org.onebeartoe.tours.jtop.fx;
}
