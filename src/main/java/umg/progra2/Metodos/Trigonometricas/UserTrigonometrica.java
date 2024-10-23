package umg.progra2.Metodos.Trigonometricas;

import java.util.Scanner;

public class UserTrigonometrica  {

    static LogicaTrigonometrica integrales = new LogicaTrigonometrica();

    public UserTrigonometrica() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresa la integral que deseas resolver");
        String expression = sc.nextLine();
        String[] partes = expression.split(" ");
        char diferencial = partes[1].charAt(1);
        String termino = partes[0];
        System.out.println("El resultado es: "+integrales.ResolverIntegral(termino,diferencial));
    }



}
