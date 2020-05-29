/*
 * Esta clase representa la tabla Prenda.
 */
package AppTallerCostura;

/**
 *
 * @author luis
 */
public class Prenda extends Tabla {
    public Prenda() {
        super("Prenda");    //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("IdConfeccion");
        columnasInsert.add("CostoTrabajo");
        columnasInsert.add("CostoMaterial");
        columnasInsert.add("Finalizado");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdPrenda");
        columnasSelect.add("IdConfeccion");
        columnasSelect.add("InfoConfeccion");
        columnasSelect.add("CostoTrabajo");
        columnasSelect.add("CostoMaterial");
        columnasSelect.add("Finalizado");
                
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(0);
        tipos.add(2);
        tipos.add(2);
        tipos.add(4);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        //A veces la vista de la tabla necesita de información adicional, por lo que se crea una sentencia
        //que agrege esa información adicional.
        selectSQL = "SELECT p.IdPrenda, " +
        "p.IdConfeccion, " +
        "CONCAT( cl.Nombre,' ',cl.ApellidoPaterno,' ',cl.ApellidoMaterno,' ',TO_CHAR( c.FechaPedido,'HH12:MI:SS') ) AS InfoConfeccion, " +
        "CostoTrabajo, CostoMaterial, Finalizado " +
        "FROM Taller.Prenda AS p " +  
        "INNER JOIN Taller.Confeccion AS c ON p.IdConfeccion = c.IdConfeccion " +
        "INNER JOIN Taller.Cliente AS cl ON c.IdCliente = cl.IdCliente" ;
    }
}