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
public class Material extends Tabla {
    public Material()   {
        super("Material");
        columnasInsert.add("Descripcion");
        columnasInsert.add("PrecioCliente");
        
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("PrecioCliente");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = boolean
        tipos.add(1);
        tipos.add(0);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
