/*
 * Esta clase representa la tabla Trabajo.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class Trabajo extends Tabla {
    public Trabajo()    {
        super("Trabajo");   //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("IdTipoTrabajo");
        columnasInsert.add("IdPrenda");
        columnasInsert.add("IdEmpleado");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdTrabajo");
        columnasSelect.add("IdTipoTrabajo");
        columnasSelect.add("DescTrabajo");
        columnasSelect.add("IdPrenda");
        columnasSelect.add("IdEmpleado");
                
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(0);
        tipos.add(0);
        tipos.add(0);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        //A veces la vista de la tabla necesita de información adicional, por lo que se crea una sentencia
        //que agrege esa información adicional.
        selectSQL = "SELECT IdTrabajo, t.IdTipoTrabajo, Nombre AS DescTrabajo, IdPrenda, IdEmpleado FROM Taller.Trabajo AS t " +
        "INNER JOIN Taller.TipoTrabajo as tp ON t.IdTipoTrabajo = tp.IdTipoTrabajo";
    }
}
