package umg.progra2.calculadorapc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import org.apache.commons.math3.analysis.UnivariateFunction;
import umg.progra2.Metodos.AreaBajoCurva.LogicaAreaBajoCurva;
import umg.progra2.Metodos.Definidas.LogicaDefinidas;
import umg.progra2.Metodos.Impropias.LogicaImpropias;
import umg.progra2.Metodos.Sustitucion.LogicaSustitucion;
import umg.progra2.Metodos.Trigonometricas.LogicaTrigonometrica;
import umg.progra2.Metodos.VolumenSolido.Arandelas;
import umg.progra2.Metodos.VolumenSolido.Cascarones;
import umg.progra2.Metodos.utilidades.GraficadorArandelas;
import umg.progra2.Metodos.utilidades.GraficadorCascarones;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CalculatorController implements Initializable {

    public TextField display2;
    @FXML
    private TextField display;


    private double num1 = 0;
    private double num2 = 0;
    private String operator = "";
    private boolean start = true;
    private double memory = 0;
    private double answer = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        display.setText("");
        display2.setText("Ingresa la funcion que deseas integrar (≧∇≦)ﾉ");
        display2.setText("Ingresa la función para integrar (usa 'x' como variable, por ejemplo: exp(-x)) (≧∇≦)ﾉ");

        if (display.toString().equals("1")){
            display2.setText("Ingresa la función para integrar (usa 'x' como variable, por ejemplo: exp(-x)) (≧∇≦)ﾉ");
        }


//        Platform.runLater(() -> {
//            display.positionCaret(display.getText().length());
//        });
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
//                calculateResult();
//                calcularTrigonometricas();
                calcularSustitucion();
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
                handleFactorial();
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
//                calcularImpropia();
//                calcularVolumenArandelasDiscos();
//                calcularAreaBajoCurva();
//                calcularDefinidas();
//                calcularVolumenCascarones();
                break;
        }
    }

    private void handleNumber(String number) {

            display.setText(display.getText() + number);

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


    private String expresionGuardadaF = "";
    private String expresionGuardadaG = "";
    private Double valorA = null;
    private Double valorB = null;
    private int n = 0;
    private String eje = "x";
    private char ejefinal = '\u0000';
    private int paso = 0;  // Para controlar el flujo de entrada


    //REVISAR PQ NO FUNCA, YA SE PORQUE NO FUNCA PERO TA PERRO ARREGLARLO
    public void calcularImpropia() {
        try {
            String textoActual = display.getText().trim();

            switch (paso) {
                case 0:  // Guardar la expresión
                    if (textoActual.isEmpty()) {
                        display2.setText("¡Debes ingresar una función! (╯°□°）╯");
                        return;
                    }
                    expresionGuardadaF = textoActual;
                    display2.setText("Ingresa el valor de a (límite inferior) (｀∀´)Ψ");
                    display.setText("");
                    paso++;
                    break;

                case 1:  // Guardar límite inferior
                    try {
                        valorA = Double.parseDouble(textoActual);
                        display2.setText("Ingresa el valor de b (límite superior) (⌐■_■)");
                        display.setText("");
                        paso++;
                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
                    }
                    break;

                case 2:  // Guardar límite superior
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
                    display2.setText("Ingresa el número de divisiones (n) para la precisión del cálculo ᕦ(ò_óˇ)ᕤ");
                    display.setText("");
                    paso++;
                    break;

                case 3:  // Procesar número de divisiones y calcular
                    try {
                        n = Integer.parseInt(textoActual);
                        if (n <= 0) {
                            display2.setText("¡El número de divisiones debe ser mayor a 0! (╯°□°）╯");
                            return;
                        }

                        // Realizar el cálculo
                        Function<Double, Double> funcion = LogicaImpropias.obtenerFuncion(expresionGuardadaF);
                        double resultado = LogicaImpropias.calcularIntegralImpropia(funcion, valorA, valorB, n);

                        // Mostrar resultado
                        display.setText(String.valueOf(resultado));
                        display2.setText("¡Cálculo completado! (づ｡◕‿‿◕｡)づ");

                        // Reiniciar para nuevo cálculo
                        paso = 0;
                        expresionGuardadaF = "";
                        valorA = null;
                        valorB = null;
                        n = 0;

                    } catch (NumberFormatException e) {
                        display2.setText("¡Ingresa un número válido para las divisiones! (╯°□°）╯");
                    } catch (Exception e) {
                        display2.setText("Error: " + e.getMessage() + " (╯°□°）╯");
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
            n = 0;
        }
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

    // Para multiplicación (x)
    private void handleMultiplication() {
        display.setText(display.getText()+"x");
        start = true;
    }

    // Para suma (+)
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
}