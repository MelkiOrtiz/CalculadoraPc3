package umg.progra2.Metodos.AreaBajoCurva;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import static umg.progra2.Metodos.utilidades.GraficadorFunciones.mostrarGrafica;


public class LogicaAreaBajoCurva {

    public String calcularAreaBajoLaCurva(String funcion, double limiteInferior, double limiteSuperior, String variable) {
        UnivariateFunction function = v -> {
            ExprEvaluator util = new ExprEvaluator();
            IExpr expr = util.evaluate(funcion.replace(variable, "(" + v + ")"));
            return expr.evalDouble();
        };

        SimpsonIntegrator integrator = new SimpsonIntegrator();
        String resultado;

        try {
            double area = integrator.integrate(1000, function, limiteInferior, limiteSuperior);
            resultado = String.valueOf(area);
        } catch (Exception e) {
            resultado = "Error al calcular el área bajo la curva.";
        }
        mostrarGrafica(funcion, function, limiteInferior, limiteSuperior);
        return resultado;


    }

}
