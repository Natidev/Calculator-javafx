module ap.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens ap.calculator to javafx.fxml;
    exports ap.calculator;
}