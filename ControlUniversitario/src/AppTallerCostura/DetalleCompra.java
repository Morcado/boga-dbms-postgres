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
public class DetalleCompra extends Tabla {
    public DetalleCompra()  {
        super("DetalleCompra");
        columnasInsert.add("IdCompra");
        columnasInsert.add("IdMaterial");
        columnasInsert.add("CostoUnitario");
        columnasInsert.add("Cantidad");
        columnasInsert.add("Subtotal");
        
        //0 = int, 1 = string, 2 = double o float, 3 = date, 4 = bool, 5 = fecha actual
        tipos.add(0);
        tipos.add(0);
        tipos.add(2);
        tipos.add(0);
        tipos.add(2);
        
        columnasSelect.add("IdDetalleCompra");
        columnasSelect.add("IdCompra");
        columnasSelect.add("FechaCompra");
        columnasSelect.add("IdMaterial");
        columnasSelect.add("Descripcion");
        columnasSelect.add("CostoUnitario");
        columnasSelect.add("Cantidad");
        columnasSelect.add("Subtotal");
        
        selectSQL = "SELECT IdDetalleCompra, d.IdCompra, c.FechaCompra, d.IdMaterial, m.Descripcion, CostoUnitario, Cantidad, Subtotal FROM Taller.DetalleCompra AS d " +
"INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial " +
"INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra";
    }
}
