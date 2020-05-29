/*
 * Este es el punto de entrada de la aplicación.
 */
package AppTallerCostura;

/**
 *
 * @author Morcado
 */
public class AppTallerCostura {

    /**
     * @param args son los argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        Interfaz manejador = new Interfaz();    //Crea nuestra ventana.
        manejador.setVisible(true);             //La muestra.
    }
    
}
