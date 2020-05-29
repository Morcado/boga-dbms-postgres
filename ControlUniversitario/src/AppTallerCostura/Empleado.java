/*
 * Esta clase representa la tabla Empleado.
 */
package AppTallerCostura;

/**
 *
 * @author luis
 */
public class Empleado extends Tabla {
    public Empleado() {
        super("Empleado");  //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("Nombre");
        columnasInsert.add("ApellidoPaterno");
        columnasInsert.add("ApellidoMaterno");
        columnasInsert.add("Telefono");
        columnasInsert.add("Direccion");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdEmpleado");
        columnasSelect.add("Nombre");
        columnasSelect.add("ApellidoPaterno");
        columnasSelect.add("ApellidoMaterno");
        columnasSelect.add("Telefono");
        columnasSelect.add("Direccion");
                
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(1);
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
