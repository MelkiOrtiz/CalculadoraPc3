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
        btnSustitucion.setOnAction(event -> handleButton(event, "1"));
        btnPorPartes.setOnAction(event -> handleButton(event, "2"));
        btnTrigonometricas.setOnAction(event -> handleButton(event, "3"));
        btnImpropias.setOnAction(event -> handleButton(event, "4"));
        btnAreaBajoCurva.setOnAction(event -> handleButton(event, "5"));
        btnVolumenSolidos.setOnAction(event -> handleButton(event, "6"));
        btnAreaSuperficieRevolucion.setOnAction(event -> handleButton(event, "7"));
        btnValorPromedio.setOnAction(event -> handleButton(event, "8"));
        btnCentroides.setOnAction(event -> handleButton(event, "9"));
        btnDerivadasParciales.setOnAction(event -> handleButton(event, "10"));
        btnReglaCadenaDosVariables.setOnAction(event -> handleButton(event, "11"));
    }

    private void handleButton(ActionEvent event, String mode) {
        try {
            if(!mode.equals("6")) {
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
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("comboBoxVolumen.fxml"));
                Parent root = loader.load();

                ComboBoxVolumenController controller = loader.getController();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Métodos Volumenes");
                stage.setScene(scene);
                stage.show();

                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

