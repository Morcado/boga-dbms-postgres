/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.*; 

/**
 *
 * @author LuisN
 */
public class ConexionPostgre {
    private String url = "jdbc:postgresql://localhost:5432/ControlUniversitario";
    private String usuario = "postgres";
    private String contraseña = "postgres";
    private Connection tallerCostura ;
    
    public ConexionPostgre()  {
        try {
            this.tallerCostura = DriverManager.getConnection( url, usuario, contraseña );
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
    }
    
    public DefaultTableModel CreaModeloTabla( Tabla tabla ) {
        DefaultTableModel modelo = new DefaultTableModel();
        
        tabla.Columnas().forEach((c) -> {
            modelo.addColumn( c );
        });
        
        return modelo ;
    }
    
    public List<String[]> Registros( Tabla tabla ) {
        List<String[]> registros = new ArrayList<>();
        List<String> columnas = tabla.Columnas();
        int numCol = columnas.size();
        
        try {
            Statement statement = tallerCostura.createStatement();
            ResultSet resultSet = statement.executeQuery( tabla.selectSQL() );
            
            String[] registro = new String[numCol];
            while( resultSet.next() ) {
                for( int i = 0 ; i < columnas.size() ; ++i )  {
                    registro[i] = resultSet.getString( columnas.get( i ) );
                }
                
                registros.add( registro );
            }
            
            resultSet.close();
            statement.close();
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
        
        return registros ;
    }
    
    public void InsertDataTo( String[] registro, Tabla tabla )  {
        
    }
    
    public void DeleteDataFrom( String[] registro, Tabla tabla )    {
        
    }
    
    public void ModifyRow( String[] regOriginal, String[] regNuevo, Tabla tabla )    {
        
    }
}
