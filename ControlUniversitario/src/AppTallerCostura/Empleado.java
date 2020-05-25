/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;

/**
 *
 * @author luis
 */
public class Empleado extends Tabla {
    public Empleado() {
        super("Empleado");
        columnasInsert.add("Nombre");
        columnasInsert.add("ApellidoPaterno");
        columnasInsert.add("ApellidoMaterno");
        columnasInsert.add("Telefono");
        columnasInsert.add("Direccion");
        
        columnasSelect.add("IdEmpleado");
        columnasSelect.add("Nombre");
        columnasSelect.add("ApellidoPaterno");
        columnasSelect.add("ApellidoMaterno");
        columnasSelect.add("Telefono");
        columnasSelect.add("Direccion");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);
        tipos.add(1);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
