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
    private String url = "jdbc:postgresql://localhost:5432/BD_BOGA";
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
            
            while( resultSet.next() ) {
                String[] registro = new String[numCol];
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
    
    public boolean InsertDataTo( String[] registro, Tabla tabla )  {
        //Insertar un registro en la tabla.
        try {

            String insert = "INSERT INTO Tabla.Cliente (Nombre, ApellidoPaterno, ApellidoMaterno, Telefono) VALUES (?, ?, ?, ?)";
            //String statement = "INSERT INTO Datos.Carrera(nombre) VALUES ('" + nombre + "')";
            PreparedStatement statement = tallerCostura.prepareStatement(insert);
            //java.sql.Statement sqlConnect = tallerCostura.createStatement();
            statement.executeUpdate();
            statement.close();
            
            return true;

        } catch (Exception e) {
            //Esxception: handle exception
            System.out.println("Errorrrrrr: " + e.getMessage());
            return false;
        }
        //El primer valor del registro es el id, como es autonúmerico ignorar.
    }
    
    public void DeleteDataFrom( int idRegistro, Tabla tabla )    {
        //Eliminar un registro que tiene el id de la tabla.
    }
    
    public void ModifyRow( int idRegistro, String[] newInfo, Tabla tabla )    {
        //Modificar el registro con el id con los valores nuevos de la tabla.
    }
}