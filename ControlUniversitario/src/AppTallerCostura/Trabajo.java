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
public class Trabajo extends Tabla {
    public Trabajo()    {
        super("Trabajo");
        columnasInsert.add("IdTipoTrabajo");
        columnasInsert.add("IdPrenda");
        columnasInsert.add("IdEmpleado");
        
        columnasSelect.add("IdTrabajo");
        columnasSelect.add("IdTipoTrabajo");
        columnasSelect.add("DescTrabajo");
        columnasSelect.add("IdPrenda");
        columnasSelect.add("IdEmpleado");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = boolean
        tipos.add(0);
        tipos.add(0);
        tipos.add(0);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        selectSQL = "SELECT IdTrabajo, t.IdTipoTrabajo, Nombre AS DescTrabajo, IdPrenda, IdEmpleado FROM Taller.Trabajo AS t " +
        "INNER JOIN Taller.TipoTrabajo as tp ON t.IdTipoTrabajo = tp.IdTipoTrabajo";
    }
}
