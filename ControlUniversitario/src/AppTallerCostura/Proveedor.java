/*
 * Esta clase representa la tabla Proveedor.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class Proveedor extends Tabla {
    public Proveedor()  {
        super("Proveedor"); //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("Nombre");
        columnasInsert.add("Telefono");
        columnasInsert.add("Direccion");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdProveedor");
        columnasSelect.add("Nombre");
        columnasSelect.add("Telefono");
        columnasSelect.add("Direccion");

        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
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
