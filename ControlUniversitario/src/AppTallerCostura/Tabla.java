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
public class Tabla {
    protected String nombre ;
    protected List<String> columnas ;
    protected String selectSQL ;
    
    public Tabla( String nombre )   {
        this.nombre = nombre ;
    }
    
    public String Nombre()  {
        return nombre ;
    }
    
    public List<String> Columnas()  {
        return columnas ;
    }
    
    public String selectSQL() {
        return selectSQL ;
    }
}
