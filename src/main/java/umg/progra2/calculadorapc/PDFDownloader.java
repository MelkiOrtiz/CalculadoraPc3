package umg.progra2.calculadorapc;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PDFDownloader {

    @FXML
    private Button btnDescargarPDF;

    // Hacemos el método estático para poder llamarlo desde otras clases
    public static void descargarDocumentacion() {
        try {
            // Obtener el recurso del PDF desde el classpath
            InputStream inputStream = PDFDownloader.class.getResourceAsStream("/umg/progra2/calculadorapc/DocumentacionCalculadoraPDF.pdf");

            if (inputStream == null) {
                throw new Exception("No se pudo encontrar el archivo PDF");
            }

            // Crear un FileChooser para que el usuario elija dónde guardar el archivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Documentación PDF");
            fileChooser.setInitialFileName("DocumentacionCalculadora.pdf");

            // Agregar filtro para archivos PDF
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Mostrar el diálogo de guardar
            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                // Copiar el archivo al destino seleccionado
                Files.copy(inputStream, selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías mostrar un diálogo de error al usuario
        }
    }

    @FXML
    private void descargarPDF() {
        descargarDocumentacion();
    }

    @FXML
    public void initialize() {
        // Inicialización si es necesaria
    }
}