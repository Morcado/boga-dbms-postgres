/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;

/**
 *
 * @author Morcado
 */
public class TipoTrabajo extends Tabla {
    public TipoTrabajo() {
        super("TipoTrabajo");
        columnasInsert.add("Costo");
        columnasInsert.add("Nombre");
        
        columnasSelect.add("idTipoTrabajo");
        columnasSelect.add("Costo");
        columnasSelect.add("Nombre");
                
        tipos.add(0);
        tipos.add(2);
        tipos.add(1);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
