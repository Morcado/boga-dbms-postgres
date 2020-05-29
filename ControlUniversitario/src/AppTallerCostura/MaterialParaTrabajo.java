/*
 * Esta clase representa la tabla MaterialParaTrabajo.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class MaterialParaTrabajo extends Tabla {
    public MaterialParaTrabajo()    {
        super("MaterialParaTrabajo");   //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("IdTrabajo");
        columnasInsert.add("IdMaterial");
        columnasInsert.add("Cantidad");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdTrabajo");
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("Cantidad");
                
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
        selectSQL = "SELECT IdTrabajo, mt.IdMaterial, m.Descripcion, Cantidad FROM Taller.MaterialParaTrabajo AS mt " +
        "INNER JOIN Taller.Material AS m on mt.IdMaterial = m.IdMaterial" ;
    }
}
