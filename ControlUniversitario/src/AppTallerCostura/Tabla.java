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
    protected String updateSQL ;
    protected String deleteSQL ;
    
    public Tabla( String nombre )   {
        this.nombre = nombre ;
    }
    
    public void InitializeQueryStrings()    {
        //Inicializar las cadenas de update y delete para cada tabla.
        //Este método se llama en el constructor de la tabla especifica.
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
    
    public String updateSQL()   {
        return updateSQL ;
    }
    
    public String deleteSQL()   {
        return deleteSQL ;
    }
}
