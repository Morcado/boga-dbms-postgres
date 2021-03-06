/*
 * Esta clase representa la tabla TipoTrabajo.
 */
package AppTallerCostura;

/**
 *
 * @author Morcado
 */
public class TipoTrabajo extends Tabla {
    public TipoTrabajo() {
        super("TipoTrabajo");   //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("Costo");
        columnasInsert.add("Nombre");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("idTipoTrabajo");
        columnasSelect.add("Costo");
        columnasSelect.add("Nombre");
            
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(2);
        tipos.add(1);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
