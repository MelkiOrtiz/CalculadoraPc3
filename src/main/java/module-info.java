module umg.progra2.calculadorapc {
    requires javafx.controls;
    requires javafx.fxml;
    requires CalculadoraFinal;


    opens umg.progra2.calculadorapc to javafx.fxml;
    exports umg.progra2.calculadorapc;
}