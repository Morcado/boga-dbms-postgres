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
public class MaterialParaTrabajo extends Tabla {
    public MaterialParaTrabajo()    {
        super("MaterialParaTrabajo");
        columnasInsert.add("IdTrabajo");
        columnasInsert.add("IdMaterial");
        columnasInsert.add("Cantidad");
        
        columnasSelect.add("IdTrabajo");
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("Cantidad");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = boolean
        tipos.add(0);
        tipos.add(0);
        tipos.add(0);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        selectSQL = "SELECT IdTrabajo, mt.IdMaterial, m.Descripcion, Cantidad FROM Taller.MaterialParaTrabajo AS mt " +
        "INNER JOIN Taller.Material AS m on mt.IdMaterial = m.IdMaterial" ;
    }
}
