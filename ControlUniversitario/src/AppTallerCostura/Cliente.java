/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;
import java.util.*;  
/**
 *
 * @author LuisN
 */
public class Cliente extends Tabla {
    public Cliente() {
        super("Cliente");
        columnasInsert.add("Nombre");
        columnasInsert.add("ApellidoPaterno");
        columnasInsert.add("ApellidoMaterno");
        columnasInsert.add("Telefono");
        
        columnasSelect.add("idCliente");
        columnasSelect.add("Nombre");
        columnasSelect.add("ApellidoPaterno");
        columnasSelect.add("ApellidoMaterno");
        columnasSelect.add("Telefono");
        
        InitializeDeleteQuery();
        InitializeInsertQuery();
        InitializeUpdateQuery();
        InitializeSelectQuery();
    }
}
