/*
 * Esta clase representa la tabla Confeccion.
 */
package AppTallerCostura;

/**
 *
 * @author luis
 */
public class Confeccion extends Tabla {
    public Confeccion() {
        super("Confeccion");    //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("CostoTotal");
        columnasInsert.add("FechaPedido");
        columnasInsert.add("FechaEntrega");
        columnasInsert.add("Anticipo");
        columnasInsert.add("IdCliente");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdConfeccion");
        columnasSelect.add("CostoTotal");
        columnasSelect.add("FechaPedido");
        columnasSelect.add("FechaEntrega");
        columnasSelect.add("Anticipo");
        columnasSelect.add("IdCliente");
                
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(2);
        tipos.add(5);
        tipos.add(3);
        tipos.add(2);
        tipos.add(0);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}