/*
 * Esta clase representa la tabla Compra.
 */
package AppTallerCostura;

/**
 *
 * @author Morcado
 */
public class Compra  extends Tabla {
    public Compra() {
        super("Compra");    //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("IdProveedor");
        columnasInsert.add("FechaCompra");
        columnasInsert.add("Total");
        
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(0);
        tipos.add(5);
        tipos.add(2);
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdCompra");
        columnasSelect.add("IdProveedor");
        columnasSelect.add("FechaCompra");
        columnasSelect.add("Total");
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        //A veces la vista de la tabla necesita de información adicional, por lo que se crea una sentencia
        //que agrege esa información adicional.
        selectSQL = "SELECT c.IdCompra, c.IdProveedor, c.FechaCompra, c.Total FROM Taller.Compra AS c ";
    }
}
