package umg.progra2.calculadorapc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ComboBoxVolumenController {

    @FXML
    private Button btnAbrirMetodo;
    @FXML
    private ComboBox<String> comboBoxMetodos;

    private String mode;

    @FXML
    public void initialize() {
        comboBoxMetodos.getItems().addAll("Método de discos", "Método de arandelas", "Método de cascarones");

        comboBoxMetodos.setOnAction(event -> {
            String selectedItem = comboBoxMetodos.getSelectionModel().getSelectedItem();

            if (selectedItem.equals("Método de discos")) {
                mode = "12";
            } else if (selectedItem.equals("Método de arandelas")) {
                mode = "13";
            } else if (selectedItem.equals("Método de cascarones")) {
                mode = "6";
            }
        });

        btnAbrirMetodo.setOnAction(event -> handleButton(event, mode));
    }

    private void handleButton(ActionEvent event, String mode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            CalculatorController controller = loader.getController();
            controller.setMode(mode);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Calculadora Integrales");
            stage.setScene(scene);
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
