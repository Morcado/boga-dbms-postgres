/*
 * Esta clase representa la tabla Material.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class Material extends Tabla {
    public Material()   {
        super("Material");  //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("Descripcion");
        columnasInsert.add("PrecioCliente");
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("PrecioCliente");
                
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(1);
        tipos.add(0);
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
