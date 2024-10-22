package umg.progra2.calculadorapc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import umg.dem1.Metodos.Definidas.LogicaDefinidas;
import umg.dem1.Metodos.Impropias.LogicaImpropias;
import umg.dem1.Metodos.Sustitucion.LogicaSustitucion;
import umg.dem1.Metodos.Trigonometricas.LogicaTrigonometrica;

import javax.swing.*;
import java.awt.*;
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
                calcularImpropia();
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


    private String expresionGuardada = "";
    private Double valorA = null;
    private Double valorB = null;
    private int n = 0;
    private String eje = "x";
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
                    expresionGuardada = textoActual;
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
                        Function<Double, Double> funcion = LogicaImpropias.obtenerFuncion(expresionGuardada);
                        double resultado = LogicaImpropias.calcularIntegralImpropia(funcion, valorA, valorB, n);

                        // Mostrar resultado
                        display.setText(String.valueOf(resultado));
                        display2.setText("¡Cálculo completado! (づ｡◕‿‿◕｡)づ");

                        // Reiniciar para nuevo cálculo
                        paso = 0;
                        expresionGuardada = "";
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
            expresionGuardada = "";
            valorA = null;
            valorB = null;
            n = 0;
        }
    }

    //ESTO DE AQUI ABAJO NO FUNCIONA (YA VEREMOS QUE ROLLO)
    public void calcularDefinida() {
        try {
            String textoActual = display.getText().trim();
            switch (paso) {
                case 0:
                    if (textoActual.isEmpty()) {
                        display2.setText("Debes ingresar una funcion");
                        return;
                    }
                    expresionGuardada = textoActual;
                    display2.setText("ingresa el valor de a (limite inferior)");
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
                    display2.setText("¿En qué eje deseas integrar? (x/y):");
                    display.setText("");
                    paso++;
                    break;
                case 3:
                    eje = textoActual;
                    BiFunction<Double, Double, Double> funcion = (x, yx) -> {
                        Expression e = (new ExpressionBuilder(expresionGuardada)).variables(new String[]{"x", "y", "π", "e"}).build().setVariable("x", x).setVariable("y", yx).setVariable("π", Math.PI).setVariable("e", Math.E);
                        return e.evaluate();
                    };
                    LogicaDefinidas calculadora = new LogicaDefinidas();
                    double resultado;
                    if (eje.equals("x")) {
                        resultado = calculadora.integrar((x, yx) -> {
                            return (Double) funcion.apply(x, 0.0);
                        }, valorA, valorB, 0.0, 0.0, "x");
                    } else {
                        resultado = calculadora.integrar((x, yx) -> {
                            return (Double) funcion.apply(0.0, yx);
                        }, 0.0, 0.0, valorA, valorB, "y");
                    }

                    System.out.printf("Resultado de la integral: %.3f%n", resultado);
                    XYSeries series = new XYSeries("Función");
                    double y;
                    if (eje.equals("x")) {
                        for (y = valorA; y <= valorB; y += (valorB - valorA) / 100.0) {
                            series.add(y, (Number) funcion.apply(y, 0.0));
                        }
                    } else {
                        for (y = valorA; y <= valorB; y += (valorB - valorA) / 100.0) {
                            series.add(y, (Number) funcion.apply(0.0, y));
                        }
                    }

                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(series);
                    JFreeChart chart = ChartFactory.createXYLineChart("Gráfico de la Función", eje.toUpperCase(), "Y", dataset, PlotOrientation.VERTICAL, true, true, false);
                    SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame("Gráfico");
                        frame.setDefaultCloseOperation(3);
                        frame.add(new ChartPanel(chart));
                        frame.pack();
                        frame.setLocationRelativeTo((Component) null);
                        frame.setVisible(true);
                    });
            }
        } catch (Exception var14) {
            Exception e = var14;
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    private void calcularDefinida1() {
//        try {
//            String textoActual = display.getText().trim();
//
//            switch (paso) {
//                case 0:  // Guardar la expresión
//                    if (textoActual.isEmpty()) {
//                        display2.setText("¡Debes ingresar una función! (╯°□°）╯");
//                        return;
//                    }
//                    expresionGuardada = textoActual;
//                    display2.setText("Ingresa el límite inferior para " + ejeIntegracion + " (｀∀´)Ψ");
//                    display.clear();
//                    paso++;
//                    break;
//
//                case 1:  // Guardar límite inferior
//                    try {
//                        valorA = Double.parseDouble(textoActual);
//                        display2.setText("Ingresa el límite superior para " + ejeIntegracion + " (⌐■_■)");
//                        display.clear();
//                        paso++;
//                    } catch (NumberFormatException e) {
//                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
//                    }
//                    break;
//
//                case 2:  // Guardar límite superior y calcular
//                    try {
//                        valorB = Double.parseDouble(textoActual);
//
//                        // Crear la función usando exp4j
//                        BiFunction<Double, Double, Double> funcion = (x, y) -> {
//                            try {
//                                Expression e = new ExpressionBuilder(expresionGuardada)
//                                        .variables("x", "y", "π", "e")
//                                        .build()
//                                        .setVariable("x", x)
//                                        .setVariable("y", y)
//                                        .setVariable("π", Math.PI)
//                                        .setVariable("e", Math.E);
//                                return e.evaluate();
//                            } catch (Exception ex) {
//                                throw new RuntimeException("Error evaluando la función: " + ex.getMessage());
//                            }
//                        };
//
//                        // Calcular la integral usando la clase LogicaDefinidas
//                        LogicaDefinidas calculadora = new LogicaDefinidas();
//                        double resultado;
//
//                        if (ejeIntegracion.equals("x")) {
//                            resultado = calculadora.integrar(
//                                    (x, y) -> funcion.apply(x, 0.0),
//                                    valorA,
//                                    valorB,
//                                    0.0,
//                                    0.0,
//                                    "x"
//                            );
//                        } else {
//                            resultado = calculadora.integrar(
//                                    (x, y) -> funcion.apply(0.0, y),
//                                    0.0,
//                                    0.0,
//                                    valorA,
//                                    valorB,
//                                    "y"
//                            );
//                        }
//
//                        // Mostrar resultado
//                        display.setText(String.format("%.4f", resultado));
//                        display2.setText("¡Cálculo completado! (づ｡◕‿‿◕｡)づ");
//
//                        // Mostrar gráfica
//                        mostrarGrafica(funcion);
//
//                        // Reiniciar para nuevo cálculo
//                        resetearCalculadora();
//
//                    } catch (NumberFormatException e) {
//                        display2.setText("¡Ingresa un número válido! (╯°□°）╯");
//                    } catch (Exception e) {
//                        display2.setText("Error: " + e.getMessage() + " (╯°□°）╯");
//                        resetearCalculadora();
//                    }
//                    break;
//            }
//
//        } catch (Exception e) {
//            display2.setText("Error inesperado: " + e.getMessage() + " (╯°□°）╯");
//            resetearCalculadora();
//        }
//    }
//
//    private void mostrarGrafica(BiFunction<Double, Double, Double> funcion) {
//        NumberAxis xAxis = new NumberAxis();
//        NumberAxis yAxis = new NumberAxis();
//        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
//
//        XYChart.Series<Number, Number> series = new XYChart.Series<>();
//        series.setName("Función: " + expresionGuardada);
//
//        // Calcular puntos para la gráfica
//        double paso = (valorB - valorA) / 100.0;
//        for (double i = valorA; i <= valorB; i += paso) {
//            double y;
//            if (ejeIntegracion.equals("x")) {
//                y = funcion.apply(i, 0.0);
//            } else {
//                y = funcion.apply(0.0, i);
//            }
//            series.getData().add(new XYChart.Data<>(i, y));
//        }
//
//        lineChart.getData().add(series);
//
//        // Mostrar gráfica en nueva ventana
//        Stage graphStage = new Stage();
//        graphStage.setTitle("Gráfica de " + expresionGuardada);
//        graphStage.setScene(new Scene(new VBox(lineChart), 600, 400));
//        graphStage.show();
//    }


    private void calcularTrigonometricasyPartes(){
        String expression = display.getText();
        String[] partes = expression.split(" ");
        char diferencial = partes[1].charAt(1);
        String termino = partes[0];
        LogicaTrigonometrica logicaTrigonometrica = new LogicaTrigonometrica();
        display.setText(logicaTrigonometrica.ResolverIntegral(termino,diferencial));
    }

    private void calcularSustitucion(){
        String expression = display.getText();
        LogicaSustitucion logicaSustitucion = new LogicaSustitucion();
        expression = logicaSustitucion.calcularIntegral(expression);
        display2.setText("El resultado es:           (^///^)");
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