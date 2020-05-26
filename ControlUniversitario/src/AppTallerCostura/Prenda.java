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
public class Prenda extends Tabla {
    public Prenda() {
        super("Prenda");
        columnasInsert.add("IdConfeccion");
        columnasInsert.add("CostoTrabajo");
        columnasInsert.add("CostoMaterial");
        columnasInsert.add("Finalizado");
        
        columnasSelect.add("IdPrenda");
        columnasSelect.add("IdConfeccion");
        columnasSelect.add("InfoConfeccion");
        columnasSelect.add("CostoTrabajo");
        columnasSelect.add("CostoMaterial");
        columnasSelect.add("Finalizado");
                
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = boolean
        tipos.add(0);
        tipos.add(2);
        tipos.add(2);
        tipos.add(1);
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        
        selectSQL = "SELECT p.IdPrenda, " +
        "p.IdConfeccion, " +
        "CONCAT( cl.Nombre,' ',cl.ApellidoPaterno,' ',cl.ApellidoMaterno,' ',TO_CHAR( c.FechaPedido,'HH12:MI:SS') ) AS InfoConfeccion, " +
        "CostoTrabajo, CostoMaterial, Finalizado " +
        "FROM Taller.Prenda AS p " +  
        "INNER JOIN Taller.Confeccion AS c ON p.IdConfeccion = c.IdConfeccion " +
        "INNER JOIN Taller.Cliente AS cl ON c.IdCliente = cl.IdCliente" ;
    }
}