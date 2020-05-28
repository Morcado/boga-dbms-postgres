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
public class Compra  extends Tabla {
    public Compra() {
        super("Compra");
        columnasInsert.add("IdProveedor");
        columnasInsert.add("FechaCompra");
        columnasInsert.add("Total");
        
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = bool, 5 = fecha actual
        tipos.add(0);
        tipos.add(5);
        tipos.add(2);
        
        columnasSelect.add("IdCompra");
        columnasSelect.add("IdProveedor");
        columnasSelect.add("FechaCompra");
        columnasSelect.add("Total");
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        selectSQL = "SELECT c.IdCompra, c.IdProveedor, c.FechaCompra, c.Total FROM Taller.Compra AS c ";
    }
}
