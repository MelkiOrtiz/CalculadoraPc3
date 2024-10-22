package umg.progra2.calculadorapc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import umg.dem1.Metodos.Sustitucion.LogicaSustitucion;
import umg.dem1.Metodos.Trigonometricas.LogicaTrigonometrica;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

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
            case "∫":
                handleIntegral();
                break;
            case "d□":
                handleDx();
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
        display.setText(display.getText()+"∫");
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