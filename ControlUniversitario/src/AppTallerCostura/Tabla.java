/*
 * Esta clase es la clase padre a todas las tablas.
 * Aquí se crean las sentencias de INSERT, UPDATE, y DELETE con 
 * las columnas de las tablas.
 * Si la tabla no necesita de una sentencia SELECT especial, también
 * se crea aquí.
 */
package AppTallerCostura;
import java.util.*;  

/**
 *
 * @author LuisN
 */
public class Tabla {
    protected String nombre ;
    //Cada tabla tiene dos listas de columnas:
    //columnasSelect contiene las columnas que se leen con el select,
    //como en algunas tablas se pide información que solo se consigue con 
    //un join, entonces esas columnas agregadas están en columnSelect.
    protected ArrayList<String> columnasSelect ;
    //columnInsert contiene las columnas de la tabla menos el id, para poder
    //insertar los datos y que el id se lo ponga la base de datos.
    protected ArrayList<String> columnasInsert ;
    protected ArrayList<Integer> tipos; //0 = int, 1 = string, 2 = double o float, 3 = date
    protected String selectSQL ;
    protected String insertSQL ;
    protected String updateSQL ;
    protected String deleteSQL ;
    
    /* Se inicializa el nombre y las listas. */
    public Tabla( String nombre )   {
        this.nombre = nombre ;
        tipos = new ArrayList<>();
        columnasSelect = new ArrayList<>();
        columnasInsert = new ArrayList<>();
    }
    
    /* Se crea la sentencia SELECT. */
    /* Bastante gracioso, se puede llamar un método de la clase padre */
    /* en el constructor de la clase hijo por que ya fue inicializada */
    /* con super(). */
    public void InitializeSelectQuery() {
        selectSQL = "SELECT * FROM Taller." + nombre;
    }
    
    /* Se crea la sentencia DELETE */
    public void InitializeDeleteQuery() {
        String delete = "DELETE FROM Taller." + nombre ;
        
        delete += " WHERE ";
        delete += columnasSelect.get( 0 );
        delete += "=?" ;
        
        deleteSQL = delete ;
    }
    
    /* Se crea la sentencia UPDATE */
    public void InitializeUpdateQuery()    {
        //Inicializar las cadenas de update y delete para cada tabla.
        //Este método se llama en el constructor de la tabla especifica.
        String update = "UPDATE Taller." + nombre + " SET ";
        
        //columnasInsert contiene las columnas utilizadas en la inserción,
        //también se pueden utilizar para update.
        for( String c : columnasInsert )    {
            update += c + "= ?," ;
        }
        
        update = update.substring(0, update.length() - 1 );
        
        //columnasSelect siempre tiene el id al inicio.
        update += " WHERE " + columnasSelect.get(0) + "= ?" ;
        
        updateSQL = update ;
    }
    
    /* Se crea la sentencia INSERT. */
    public void InitializeInsertQuery()    {
        //Inicializar las cadenas de update y delete para cada tabla.
        //Este método se llama en el constructor de la tabla especifica.
        String insert = "INSERT INTO Taller." + nombre + "(";
        
        //columnasInsert contiene las columnas utilizadas en la inserción,
        //en general son todas las columnas menos el id.
        for( String c : columnasInsert )    {
            insert += c + "," ;
        }
        
        insert = insert.substring(0, insert.length() - 1 );
        insert += ") VALUES(" ;
        
        for( int i = 0 ; i < columnasInsert.size() ; ++i )
            insert += "?," ;
        
        insert = insert.substring(0, insert.length() - 1 );
        
        insert += ")" ;
        insertSQL = insert ;
    }
    
    /* ===== GETTERS ===== */
    public String Nombre()  {
        return nombre ;
    }
    
    public List<String> ColumnasSelect()    {
        return columnasSelect ;
    }
    
    public List<String> Columnas()  {
        return columnasInsert ;
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
