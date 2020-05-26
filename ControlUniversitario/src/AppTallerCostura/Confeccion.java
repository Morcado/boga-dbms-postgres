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
public class Confeccion extends Tabla {
    public Confeccion() {
        super("Confeccion");
        columnasInsert.add("CostoTotal");
        columnasInsert.add("FechaPedido");
        columnasInsert.add("FechaEntrega");
        columnasInsert.add("Anticipo");
        columnasInsert.add("IdCliente");
        
        columnasSelect.add("IdConfeccion");
        columnasSelect.add("CostoTotal");
        columnasSelect.add("FechaPedido");
        columnasSelect.add("FechaEntrega");
        columnasSelect.add("Anticipo");
        columnasSelect.add("IdCliente");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = bool, 5 = fecha actual
        tipos.add(2);
        tipos.add(5);
        tipos.add(3);
        tipos.add(2);
        tipos.add(0);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}