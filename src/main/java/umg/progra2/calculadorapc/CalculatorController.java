package umg.progra2.calculadorapc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.matheclipse.core.eval.ExprEvaluator;
import umg.progra2.Metodos.AreaBajoCurva.LogicaAreaBajoCurva;
import umg.progra2.Metodos.CentroidesyCentroides.LogicaCentroides;
import umg.progra2.Metodos.Definidas.LogicaDefinidas;
import umg.progra2.Metodos.Impropias.LogicaImpropias;
import umg.progra2.Metodos.IntegralValorMedio.LogicaValorMedio;
import umg.progra2.Metodos.Sustitucion.LogicaSustitucion;
import umg.progra2.Metodos.Trigonometricas.LogicaTrigonometrica;
import umg.progra2.Metodos.VolumenSolido.Arandelas;
import umg.progra2.Metodos.VolumenSolido.Cascarones;
import umg.progra2.Metodos.utilidades.GraficadorArandelas;
import umg.progra2.Metodos.utilidades.GraficadorCascarones;
import umg.progra2.Metodos.utilidades.GraficadorFunciones;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorController implements Initializable {

    public TextField display2;
    @FXML
    public Button regresar;
    @FXML
    private TextField display;
    @FXML
    public Button igual;
    @FXML
    public Button siguiente;


    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";
    private boolean start = true;
    private double memory = 0;
    private double answer = 0;
    private String expresionGuardadaF = "";
    private String expresionGuardadaG = "";
    private Double valorA = null;
    private Double valorB = null;
    private int n = 0;
    private String eje = "x";
    private char ejefinal = '\u0000';
    private int paso = 0;  // Para controlar el flujo de entrada
    private String mode = "0"; // Default mode

    public void setMode(String mode) {
        this.mode = mode;
        MensajeInicial(); // Update the message when mode changes
    }
    public void MensajeInicial() {
        switch (this.mode) {
            case "1":
                display.setText("");
                display2.setText("Ingresa la función para integrar por sustitución (≧∇≦)ﾉ");
                siguiente.setVisible(false);
                break;
            case "2":
                display.setText("");
                display2.setText("Ingresa la función para integrar por partes (≧∇≦)ﾉ");
                siguiente.setVisible(false);
                break;
            case "3":
                display.setText("");
                display2.setText("Ingresa la función trigonométrica para integrar (≧∇≦)ﾉ");
                siguiente.setVisible(false);
                break;
            case "4":
                display.setText("");
                display2.setText("Ingresa la función para la integral impropia (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "5":
                display.setText("");
                display2.setText("Ingresa el eje para calcular el área bajo la curva (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "6":
                display.setText("");
                display2.setText("Ingresa el eje para calcular el volumen del sólido (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "7":
                display.setText("");
                display2.setText("Ingresa el eje para calcular la integral definida (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "8":
                display.setText("");
                display2.setText("Ingresa la función para calcular el valor promedio (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "9":
                display.setText("");
                display2.setText("Ingresa la función para calcular los centroides (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            case "10":
                display.setText("");
                display2.setText("Ingresa la función para calcular las derivadas parciales (≧∇≦)ﾉ");
                siguiente.setVisible(false);
                break;
            case "11":
                display.setText("");
                display2.setText("Ingresa la función para aplicar la regla de la cadena (≧∇≦)ﾉ");
                igual.setVisible(false);
                break;
            default:
                display.setText("");
                display2.setText("¡Bienvenido a la calculadora! (≧∇≦)ﾉ");
                break;
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    private void handleButtonAction(MouseEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        switch (buttonText) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
            case ".":
                handleNumber(buttonText);
                break;
            case "+":
                handleAddition();
                break;
            case "-":
                handleSubtraction();
                break;
            case "*":
                handlemultiplicacion();
                break;
            case "÷":
                handleDivision();
                break;
            case "=":
                switch (this.mode){
                    case "1":
                        calcularSustitucion();
                        break;
                    case "2", "3":
                        calcularTrigonometricasyPartes();
                        break;
                    case "10":
                        calcularParciales();
                        break;
                    case "11":

                        break;
                }
                break;
            case "DEL":
                handleDelete();
                break;
            case "AC":
                clearAll();
                break;
            case "sen":
                handleSeno();
//                handleTrigonometric("sin");
                break;
            case "cos":
                handleCos();
//                handleTrigonometric("cos");
                break;
            case "ln":
                handleLogarithm();
                break;
            case "π":
                handlePi();
                break;
            case "e":
                handleE();
                break;
            case "xⁿ":
                handlePower();
                break;
            case "√":
                handleSquareRoot();
                break;
            case "!":
                abrirEasterEgg();
                break;
            case "(":
                handleOpenParenthesis();
                break;
            case ")":
                handleCloseParenthesis();
                break;
            case "x":
                handleMultiplication();
                break;
            case "y":
                handleVariableY();
                break;
            case "∞":
                handleIntegral();
                break;
            case "d□":
                handleDx();
                break;
            case "->":
                switch (this.mode){
                    case "4":
                        calcularImpropias();
                        break;
                    case "5":
                        calcularAreaBajoCurva();
                        break;
                    case "6":
                        calcularVolumenCascarones();
                        break;
                    case "7":
                        calcularDefinidas();
                        break;
                    case "8":
                        calcularValorMedio();
                        break;
                    case "9":
                        calcularCentroides();
                        break;
                }
                break;
            case "Cre":
                abrirCreditos();
                break;
        }
    }

    @FXML
    private void handleNumber(String number) {

            display.setText(display.getText() + number);
    }

    @FXML
    private void handleReturnToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPc.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = new Stage();
            currentStage.setScene(scene);
            currentStage.setTitle("Menú Principal");
            currentStage.centerOnScreen();
            currentStage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBasicOperator(String op) {
        if (!operator.isEmpty()) {
            calculateResult();
        }
        num1 = Double.parseDouble(display.getText());
        operator = op;
        start = true;
    }

    private void calculateResult() {
        if (operator.isEmpty()) return;

        num2 = Double.parseDouble(display.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "×":
                result = num1 * num2;
                break;
            case "÷":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        display.setText(String.valueOf(result));
        operator = "";
        start = true;
        answer = result;
    }

    public void calcularAreaBajoCurva(){
        try {
            String textoActual = display.getText().trim();
            //AQUI SE TOMA COMO QUE AL INICIAR LA CALCULADORA TE PIDE QUE INGRESES EL EJE
            switch (paso) {
                case 0:
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar el eje! (╯°□°）╯");
                        return;
                    }
                    eje = textoActual;
                    if (eje.equals("y")){
                        display2.setText("Ingresa la función en términos de y (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else if (eje.equals("x")){
                        display2.setText("Ingresa la función en términos de x (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else {
                        display2.setText("¡Debes ingresar un eje valido! (╯°□°）╯");
                        return;
                    }
                    break;

                case 1:
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 2:
                    if (textoActual.equalsIgnoreCase("-∞")) {
                        valorA = Double.NEGATIVE_INFINITY;
                    } else {
                        try {
                            valorA = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '-∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    display2.setText("Ingresa el valor de b (límite superior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 3:
                    if (textoActual.equalsIgnoreCase("∞")) {
                        valorB = Double.POSITIVE_INFINITY;
                    } else {
                        try {
                            valorB = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    LogicaAreaBajoCurva logicaAreaBajoCurva = new LogicaAreaBajoCurva();
                    display2.setText("El resultado es:");
                    display.setText(logicaAreaBajoCurva.calcularAreaBajoLaCurva(expresionGuardadaF, valorA, valorB, eje)+" u²");
            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
            n = 0;
        }
    }

    public void calcularVolumenCascarones() {
        char variable='\u0000';

        try {
            String textoActual = display.getText().trim();
            //AQUI SE TOMA COMO QUE AL INICIAR LA CALCULADORA TE PIDE QUE INGRESES EL EJE
            switch (paso) {
                case 0:
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar el eje! (╯°□°）╯");
                        return;
                    }
                    eje = textoActual;
                    ejefinal =eje.charAt(0);
                    if (ejefinal=='x'){
                        variable='y';
                        display2.setText("Ingresa la función en términos de y (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else if (ejefinal=='y'){
                        variable='x';
                        display2.setText("Ingresa la función en términos de x (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else {
                        display2.setText("¡Debes ingresar un eje valido! (╯°□°）╯");
                        return;
                    }
                    break;

                case 1:
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 2:
                    if (textoActual.equalsIgnoreCase("-∞")) {
                        valorA = Double.NEGATIVE_INFINITY;
                    } else {
                        try {
                            valorA = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '-∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    display2.setText("Ingresa el valor de b (límite superior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 3:
                    if (textoActual.equalsIgnoreCase("∞")) {
                        valorB = Double.POSITIVE_INFINITY;
                    } else {
                        try {
                            valorB = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    Cascarones cascarones = new Cascarones();
                    UnivariateFunction funcion = cascarones.crearFuncion(expresionGuardadaF,ejefinal);

                    double volumen = cascarones.calcularVolumen(funcion,  valorA, valorB,variable);
                    double volumenRedondeado = Math.round(volumen * 1000.0) / 1000.0;
                    display2.setText("El resultado es:");
                    display.setText(String.valueOf(volumenRedondeado)+" u³");
                    GraficadorCascarones graficador = new GraficadorCascarones(expresionGuardadaF, valorA, valorB, ejefinal, variable);
                    graficador.mostrarGrafica();

            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
            n = 0;
        }
    }

    public void calcularDefinidas(){
        try {
            String textoActual = display.getText().trim();
            //AQUI SE TOMA COMO QUE AL INICIAR LA CALCULADORA TE PIDE QUE INGRESES EL EJE
            switch (paso) {
                case 0:
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar el eje! (╯°□°）╯");
                        return;
                    }
                    eje = textoActual;
                    if (eje.equals("y")){
                        display2.setText("Ingresa la función en términos de y (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else if (eje.equals("x")){
                        display2.setText("Ingresa la función en términos de x (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } else {
                        display2.setText("¡Debes ingresar un eje valido! (╯°□°）╯");
                        return;
                    }
                    break;

                case 1:
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 2:
                    if (textoActual.equalsIgnoreCase("-∞")) {
                        valorA = Double.NEGATIVE_INFINITY;
                    } else {
                        try {
                            valorA = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '-∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    display2.setText("Ingresa el valor de b (límite superior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 3:
                    if (textoActual.equalsIgnoreCase("∞")) {
                        valorB = Double.POSITIVE_INFINITY;
                    } else {
                        try {
                            valorB = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    LogicaDefinidas logicaDefinidas = new LogicaDefinidas();
                    BiFunction<Double, Double, Double> funcion = logicaDefinidas.convertirFuncion(expresionGuardadaF);

                    double resultado = 0;

                    if (eje.equals("x")) {
                        // Integrar respecto a x, y se mantiene constante
                        resultado = logicaDefinidas.integrar((x, y) -> funcion.apply(x, 0.0), valorA, valorB, 0, 0, "x");
                    } else if (eje.equals("y")) {
                        // Integrar respecto a y, x se mantiene constante
                        resultado = logicaDefinidas.integrar((x, y) -> funcion.apply(0.0, y), 0, 0, valorA, valorB, "y");
                    }
                    double resultadoRedondeado = Math.round(resultado * 1000.0) / 1000.0;
                    display2.setText("El resultado es:");
                    display.setText(String.valueOf(resultadoRedondeado));

            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
            n = 0;
        }
    }

    private void calcularTrigonometricasyPartes(){
        String expression = display.getText();
        String[] partes = expression.split(" ");
        char diferencial = partes[1].charAt(1);
        String termino = partes[0];
        LogicaTrigonometrica logicaTrigonometrica = new LogicaTrigonometrica();
        display2.setText("El resultado es:");
        display.setText(logicaTrigonometrica.ResolverIntegral(termino,diferencial));
    }

    private void calcularSustitucion(){
        String expression = display.getText();
        LogicaSustitucion logicaSustitucion = new LogicaSustitucion();
        expression = logicaSustitucion.calcularIntegral(expression);
        display2.setText("El resultado es:");
        display.setText(expression+" +C");
    }

    public void calcularVolumenArandelasDiscos() {
        try {
            String textoActual = display.getText().trim();
            //AQUI SE TOMA COMO QUE AL INICIAR LA CALCULADORA TE PIDE QUE INGRESES EL EJE
            switch (paso) {
                case 0:
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar el eje! (╯°□°）╯");
                        return;
                    }
                    eje = textoActual;
                    ejefinal =eje.charAt(0);
                    display2.setText("Ingresa la función F (｀∀´)Ψ");
                    display.setText("");
                    paso++;
                    break;

                case 1:
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa la función G (｀∀´)Ψ");
                    display.setText("");
                    paso++;
                    break;

                case 2:
                    expresionGuardadaG = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 3:
                    if (textoActual.equalsIgnoreCase("-∞")) {
                        valorA = Double.NEGATIVE_INFINITY;
                    } else {
                        try {
                            valorA = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '-∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    display2.setText("Ingresa el valor de b (límite superior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 4:
                    if (textoActual.equalsIgnoreCase("∞")) {
                        valorB = Double.POSITIVE_INFINITY;
                    } else {
                        try {
                            valorB = Double.parseDouble(textoActual);
                        } catch (NumberFormatException e) {
                            display2.setText("¡Ingresa un número válido o '∞'! (╯°□°）╯");
                            return;
                        }
                    }
                    Arandelas arandelas = new Arandelas();
                    UnivariateFunction funcion = arandelas.crearFuncion(expresionGuardadaF,expresionGuardadaG,ejefinal);

                    double volumen = arandelas.calcularVolumen(funcion,  valorA, valorB);
                    double volumenRedondeado = Math.round(volumen * 1000.0) / 1000.0;
                    display2.setText("El resultado es:");
                    display.setText(String.valueOf(volumenRedondeado)+" u³");
                    GraficadorArandelas.graficarFunciones(expresionGuardadaF, expresionGuardadaG, valorA, valorB, ejefinal, arandelas);


            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
            n = 0;
        }
    }

    public void calcularParciales() {
        ExprEvaluator eval = new ExprEvaluator();
        StringBuilder resultado = new StringBuilder(); // Usamos un StringBuilder para concatenar el resultado
        try {
            // Obtener la expresión desde el TextField "display" o un campo similar
            String expresion = display.getText();
            try {
                String derivadaRespectoX = eval.evaluate("D(" + expresion + ", x)").toString();
                resultado.append("Derivada respecto a x: ").append(derivadaRespectoX).append("\n");
            } catch (Exception e) {
                resultado.append("Error al calcular la derivada respecto a x: ").append(e.getMessage()).append("\n");
            }
            try {
                String derivadaRespectoY = eval.evaluate("D(" + expresion + ", y)").toString();
                resultado.append(" Derivada respecto a y: ").append(derivadaRespectoY).append("\n");
            } catch (Exception e) {
                resultado.append("Error al calcular la derivada respecto a y: ").append(e.getMessage()).append("\n");
            }
        } catch (Exception e) {
            resultado.append("Error en la entrada de datos o ejecución: ").append(e.getMessage()).append("\n");
        }

        // Mostrar el resultado en el TextArea o Label (en lugar de imprimirlo en consola)
        display2.setText("El resultado es: ");
        display.setText(resultado.toString()); // Aquí actualizas el display con los resultados concatenados
    }

    public void calcularCentroides() {
        try {
            String textoActual = display.getText().trim();

            switch (paso) {
                case 0:  // Pedir la función
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar una función! (╯°□°）╯");
                        return;
                    }
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 1:  // Pedir límite inferior
                    try {
                        valorA = Double.parseDouble(textoActual);
                        display2.setText("Ingresa el valor de b (límite superior) (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
                    }
                    break;

                case 2:  // Pedir límite superior y calcular
                    try {
                        valorB = Double.parseDouble(textoActual);

                        // Crear la función usando el método existente
                        UnivariateFunction funcion = x -> LogicaCentroides.evaluarFuncion(expresionGuardadaF, x);

                        // Calcular el área
                        double area = LogicaCentroides.evaluarIntegral(funcion, valorA, valorB);

                        if (!Double.isNaN(area) && area != 0) {
                            // Calcular centroides
                            double xBar = LogicaCentroides.evaluarIntegral(x -> x * funcion.value(x), valorA, valorB) / area;
                            double yBar = LogicaCentroides.evaluarIntegral(funcion, valorA, valorB) / area;

                            // Mostrar resultados
                            display2.setText("El resultado es:");
                            display.setText(String.format("Área: %.2f, Centro de masa (x̄, ȳ): (%.2f, %.2f)",
                                    area, xBar, yBar));

                            // Graficar la función
                            GraficadorFunciones.mostrarGrafica(expresionGuardadaF, funcion, valorA, valorB);

                        } else {
                            display2.setText("Error: El área es 0 o no se pudo calcular.");
                            display.setText("");
                        }

                        // Reiniciar para nuevo cálculo
                        paso = 0;
                        expresionGuardadaF = "";
                        valorA = null;
                        valorB = null;

                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
                    }
                    break;
            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
        }
    }

    public void calcularValorMedio() {
        try {
            String textoActual = display.getText().trim();

            switch (paso) {
                case 0:  // Pedir la función
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar una función! (╯°□°）╯");
                        return;
                    }
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 1:  // Pedir límite inferior
                    try {
                        valorA = Double.parseDouble(textoActual);
                        display2.setText("Ingresa el valor de b (límite superior) (｀∀´)Ψ");
                        display.setText("");
                        paso++;
                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
                    }
                    break;

                case 2:  // Pedir límite superior y calcular
                    try {
                        valorB = Double.parseDouble(textoActual);

                        // Crear la función usando la clase FuncionUsuario de LogicaValorMedio
                        UnivariateFunction funcion = new LogicaValorMedio.FuncionUsuario(expresionGuardadaF);

                        // Calcular el valor medio
                        double resultado = LogicaValorMedio.calcularIntegralPromedio(funcion, valorA, valorB);

                        if (!Double.isNaN(resultado)) {
                            // Mostrar resultados
                            display2.setText("El valor promedio de la integral es:");
                            display.setText(String.format("%.4f", resultado));

                            // Graficar la función
                            GraficadorFunciones.mostrarGrafica(expresionGuardadaF, funcion, valorA, valorB);

                        } else {
                            display2.setText("Error: No se pudo calcular el valor medio.");
                            display.setText("");
                        }

                        // Reiniciar para nuevo cálculo
                        paso = 0;
                        expresionGuardadaF = "";
                        valorA = null;
                        valorB = null;

                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
                    }
                    break;
            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
        }
    }

    public void calcularImpropias() {
        try {
            String textoActual = display.getText().trim();

            switch (paso) {
                case 0:  // Pedir función
                    if (textoActual.isEmpty()) {
                        display2.setText("Ingresa la función a integrar (｀∀´)Ψ");
                        return;
                    }
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) -∞ para infinito negativo (⌐■_■)");
                    display.setText("");
                    paso++;
                    break;

                case 1:  // Pedir límite inferior
                    try {
                        valorA = LogicaImpropias.parseInput(textoActual);
                        display2.setText("Ingresa el valor de b (límite superior) ∞ para infinito positivo (⌐■_■)");
                        display.setText("");
                        paso++;
                    } catch (IllegalArgumentException e) {
                        display2.setText("¡Valor inválido! Ingresa un número, -∞ o π o e (╯°□°）╯");
                        return;
                    }
                    break;

                case 2:  // Pedir límite superior y calcular
                    try {
                        valorB = LogicaImpropias.parseInput(textoActual);

                        LogicaImpropias logicaImpropias = new LogicaImpropias();
                        BiFunction<Double, Double, Double> funcion = logicaImpropias.convertirFuncion(expresionGuardadaF);
                        double resultado = logicaImpropias.integrar(funcion, valorA, valorB, 0.0, 0.0, "x");

                        display2.setText("El resultado de la integral impropia es:");
                        display.setText(String.format("%.4f", resultado));

                        // Reiniciar variables para el siguiente cálculo
                        paso = 0;
                        expresionGuardadaF = "";
                        valorA = null;
                        valorB = null;

                    } catch (IllegalArgumentException e) {
                        display2.setText("¡Valor inválido! Ingresa un número, ∞ o π o e (╯°□°）╯");
                        return;
                    } catch (Exception e) {
                        display2.setText("Error al calcular la integral: " + e.getMessage());
                        paso = 0;  // Reiniciar en caso de error
                    }
                    break;
            }
        } catch (Exception e) {
            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
            // Reiniciar todo en caso de error
            paso = 0;
            expresionGuardadaF = "";
            valorA = null;
            valorB = null;
        }
    }



    private void handleDelete() {
        String text = display.getText();
        if (text.length() > 0) {
            display.setText(text.substring(0, text.length() - 1));
            if (display.getText().isEmpty()) {
                display.setText("0");
                start = true;
            }
        }
    }

    private void clearAll() {
        MensajeInicial();
        display.setText("");
        operator = "";
        start = true;
        num1 = 0;
        num2 = 0;
    }

    private void handleSeno(){
        display.setText(display.getText()+"sin");
        start = true;
    }
    private void handleCos(){
        display.setText(display.getText()+"cos");
        start = true;
    }

    private void handleTrigonometric(String function) {
        double value = Double.parseDouble(display.getText());
        double result = 0;
        // Convertir a radianes si es necesario
        value = Math.toRadians(value);

        switch (function) {
            case "sin":
                result = Math.sin(value);
                break;
            case "cos":
                result = Math.cos(value);
                break;
        }

        display.setText(String.valueOf(result));
        start = true;
    }

    private void handleLogarithm() {
       display.setText(display.getText()+"ln");
        start = true;
    }

    private void handlePi() {
        display.setText(display.getText()+"π");
        start = true;
    }

    private void handleE() {
        display.setText(display.getText()+"e");
        start = true;
    }

    //REVISAR
    private void handlePower() {
        display.setText(display.getText()+"^");
        start = true;
    }

    private void handleSquareRoot() {
        display.setText(display.getText()+"√");
        start = true;
    }

    private void handleFactorial() {
        double value = Double.parseDouble(display.getText());
        if (value >= 0 && value == Math.floor(value) && value <= 170) {
            double result = factorial((int)value);
            display.setText(String.valueOf(result));
        } else {
            display.setText("Error");
        }
        start = true;
    }

    private double factorial(int n) {
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    // Para multiplicación
    private void handleMultiplication() {
        display.setText(display.getText()+"x");
        start = true;
    }

    // Para suma
    private void handleAddition() {
        display.setText(display.getText()+"+");
        start = true;
    }

    // Para resta (-)
    private void handleSubtraction() {
        display.setText(display.getText()+"-");
        start = true;
    }

    // Para división (/)
    private void handleDivision() {
        display.setText(display.getText()+"÷");
        start = true;
    }

    // Para paréntesis de apertura (
    private void handleOpenParenthesis() {
        display.setText(display.getText()+"(");
        start = true;
    }

    // Para paréntesis de cierre )
    private void handleCloseParenthesis() {
        display.setText(display.getText()+")");
        start = true;
    }

    // Para variable y
    private void handleVariableY() {
        display.setText(display.getText()+"y");
        start = true;
    }

    // Para integral ∫
    private void handleIntegral() {
        display.setText(display.getText()+"∞");
        start = true;
    }

    private void handleDx(){
        display.setText(display.getText()+" d");
        start = true;
    }

    private void handlemultiplicacion() {
        display.setText(display.getText()+"*");
        start = true;
    }

    @FXML
    private void abrirEasterEgg() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EasterEgg.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("¡Easter Egg!");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirCreditos() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Creditos.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Créditos");
            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.BLACK);
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}