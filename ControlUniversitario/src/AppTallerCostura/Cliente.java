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
    public Cliente()    {
        super("Cliente");
        this.columnas = new ArrayList<String>();
        
        this.selectSQL = "SELECT * FROM Taller.cliente";
    }
}
