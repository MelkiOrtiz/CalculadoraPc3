package umg.progra2.calculadorapc;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onHolaButtonClick(){welcomeText.setText("HOliwis");}
    //este comentario es nomas pa que me deje hacer el commit :)
}