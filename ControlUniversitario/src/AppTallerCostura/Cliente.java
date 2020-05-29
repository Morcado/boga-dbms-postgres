/*
 * Esta clase representa la tabla Cliente.
 */
package AppTallerCostura;
import java.util.*;  
/**
 *
 * @author LuisN
 */
public class Cliente extends Tabla {
    public Cliente() {
        super("Cliente");   //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("Nombre");
        columnasInsert.add("ApellidoPaterno");
        columnasInsert.add("ApellidoMaterno");
        columnasInsert.add("Telefono");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("idCliente");
        columnasSelect.add("Nombre");
        columnasSelect.add("ApellidoPaterno");
        columnasSelect.add("ApellidoMaterno");
        columnasSelect.add("Telefono");
        
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
