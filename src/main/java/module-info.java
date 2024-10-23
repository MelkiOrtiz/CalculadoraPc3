module umg.progra2.calculadorapc {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;
    requires org.jfree.jfreechart;
    requires java.desktop;
    requires matheclipse.core;
    requires matheclipse.parser;
    requires exp4j;
    requires java.logging;


    opens umg.progra2.calculadorapc to javafx.fxml;
    exports umg.progra2.calculadorapc;
}



