/*
 * Esta clase representa la tabla DetalleCompra.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class DetalleCompra extends Tabla {
    public DetalleCompra()  {
        super("DetalleCompra"); //Constructor de la clase padre; inicializa las listas y el nombre de la tabla.

        //Las columnas insert se utilizan para la sentencia INSERT.
        columnasInsert.add("IdCompra");
        columnasInsert.add("IdMaterial");
        columnasInsert.add("CostoUnitario");
        columnasInsert.add("Cantidad");
        columnasInsert.add("Subtotal");
        
        //Se agregan los tipos de datos de cada columna.
        //El orden es el de columnasInsert.
        //0 = int, 1 = string, 2 = float, 3 = date, 4 = boolean, 5 = date
        tipos.add(0);
        tipos.add(0);
        tipos.add(2);
        tipos.add(0);
        tipos.add(2);
        
        //Las columnas select sirven para las columnas de las filas 
        //que se muestran en la vista.
        columnasSelect.add("IdDetalleCompra");
        columnasSelect.add("IdCompra");
        columnasSelect.add("FechaCompra");
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("CostoUnitario");
        columnasSelect.add("Cantidad");
        columnasSelect.add("Subtotal");
        
        //Las sentencias que se utilizan para la base de datos se inicializan programaticamente,
        //con las listas de columnasInsert y columnasSelect.
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        //A veces la vista de la tabla necesita de información adicional, por lo que se crea una sentencia
        //que agrege esa información adicional.
        selectSQL = "SELECT IdDetalleCompra, d.IdCompra, c.FechaCompra, d.IdMaterial, m.Descripcion, CostoUnitario, Cantidad, Subtotal FROM Taller.DetalleCompra AS d " +
"INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial " +
"INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra";
    }
}
