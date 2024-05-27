module ap.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ap.calculator to javafx.fxml;
    exports ap.calculator;
}