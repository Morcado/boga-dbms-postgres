/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;

/**
 *
 * @author LuisN
 */
public class Proveedor extends Tabla {
    public Proveedor()  {
        super("Proveedor");
        columnasInsert.add("Nombre");
        columnasInsert.add("Telefono");
        columnasInsert.add("Direccion");
        
        columnasSelect.add("IdProveedor");
        columnasSelect.add("Nombre");
        columnasSelect.add("Telefono");
        columnasSelect.add("Direccion");

        //0 = int, 1 = string, 2 = double o float, 3 = date
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);

        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
