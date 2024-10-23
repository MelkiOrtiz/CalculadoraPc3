package umg.progra2.calculadorapc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuPcController {

    @FXML
    private Button btnSustitucion;
    @FXML
    private Button btnPorPartes;
    @FXML
    private Button btnTrigonometricas;
    @FXML
    private Button btnImpropias;
    @FXML
    private Button btnAreaBajoCurva;
    @FXML
    private Button btnVolumenSolidos;
    @FXML
    private Button btnAreaSuperficieRevolucion;
    @FXML
    private Button btnValorPromedio;
    @FXML
    private Button btnCentroides;
    @FXML
    private Button btnDerivadasParciales;
    @FXML
    private Button btnReglaCadenaDosVariables;
    private Stage stage;

    @FXML
    public void initialize() {
        btnSustitucion.setOnAction(this::handleSustitucion);
        btnPorPartes.setOnAction(this::handlePorPartes);
        btnTrigonometricas.setOnAction(this::handleTrigonometricas);
        btnImpropias.setOnAction(this::handleImpropias);
        btnAreaBajoCurva.setOnAction(this::handleAreaBajoCurva);
        btnVolumenSolidos.setOnAction(this::handleVolumenSolidos);
        btnAreaSuperficieRevolucion.setOnAction(this::handleAreaSuperficieRevolucion);
        btnValorPromedio.setOnAction(this::handleValorPromedio);
        btnCentroides.setOnAction(this::handleCentroides);
        btnDerivadasParciales.setOnAction(this::handleDerivadasParciales);
        btnReglaCadenaDosVariables.setOnAction(this::handleReglaCadenaDosVariables);
    }

    private void handleSustitucion(ActionEvent event) {
        abrirNuevaVentana();
        cerrarVentana(event);
        int opcion = 1;

    }

    private void handlePorPartes(ActionEvent event) {

    }

    private void handleTrigonometricas(ActionEvent event) {

    }

    private void handleImpropias(ActionEvent event) {

    }

    private void handleAreaBajoCurva(ActionEvent event) {

    }

    private void handleVolumenSolidos(ActionEvent event) {

    }

    private void handleAreaSuperficieRevolucion(ActionEvent event) {

    }

    private void handleValorPromedio(ActionEvent event) {

    }

    private void handleCentroides(ActionEvent event) {

    }

    private void handleDerivadasParciales(ActionEvent event) {

    }

    private void handleReglaCadenaDosVariables(ActionEvent event) {

    }

    @FXML
    private void abrirNuevaVentana() {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo stage (ventana)
            Stage stage = new Stage();
            stage.setTitle("Calculadora Integrales");
            stage.setScene(scene);

            // Mostrar la nueva ventana
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
