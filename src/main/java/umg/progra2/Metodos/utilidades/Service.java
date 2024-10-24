package umg.progra2.Metodos.utilidades;

public class Service {

    private final Controlador controlador;

    public Service() {
        this.controlador = new Controlador();
    }

    // Método para establecer el modo de la calculadora
    public void establecerModoCalculadora(String modo) {
        if (modo == null || modo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modo de la calculadora no puede estar vacío");
        }

        controlador.setModoCalculadora(modo.trim());
    }

    // Método para obtener el modo actual de la calculadora
    public String obtenerModoCalculadora() {
        String modo = controlador.getModoCalculadora();
        if (modo == null || modo.trim().isEmpty()) {
            return "Normal"; // Valor por defecto si no se ha establecido un modo
        }
        return modo;
    }
}
