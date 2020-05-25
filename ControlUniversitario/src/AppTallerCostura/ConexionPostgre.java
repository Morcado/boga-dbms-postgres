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
        
        tabla.ColumnasSelect().forEach((c) -> {
            modelo.addColumn(c);
        });
        
        
        return modelo ;
    }
    
    public List<String[]> Registros( Tabla tabla ) {
        List<String[]> registros = new ArrayList<>();
        List<String> columnas = tabla.ColumnasSelect();
        int numCol = columnas.size();
        
        try {
            Statement statement = tallerCostura.createStatement();
            ResultSet resultSet = statement.executeQuery( tabla.selectSQL() );
            
            while( resultSet.next() ) {
                String[] registro = new String[numCol];
                for( int i = 0 ; i < columnas.size() ; i++ )  {
                    registro[i] = resultSet.getString( columnas.get( i ) );
                }
                
                registros.add( registro );
            }
            
            resultSet.close();
            statement.close();
            return registros ;
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
        
        return null;
    }
    
    public boolean InsertDataTo( String[] registro, Tabla tabla )  {
        //Insertar un registro en la tabla.
        try {
            PreparedStatement statement = tallerCostura.prepareStatement(tabla.insertSQL);

            // Checar si tiene llave primaria para insertar el primer elemento de registro registro[0]
            for (int i = 1; i < tabla.Columnas().size()+1; i++) {
                statement.setString(i, registro[i]);
                // setString siempre empieza en 1, si se modifica el ciclo para i = 0, adaptar setstring;
            }
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
    
    public boolean DeleteDataFrom( int idRegistro, Tabla tabla )    {
        //Eliminar un registro que tiene el id de la tabla.
        try {
            PreparedStatement statement = tallerCostura.prepareStatement(tabla.deleteSQL());
            // Checar si tiene llave primaria para insertar el primer elemento de registro registro[0]
            statement.setInt(1, idRegistro);

            //java.sql.Statement sqlConnect = tallerCostura.createStatement();
            statement.executeUpdate();
            statement.close();
            
            return true;

        } catch (Exception e) {
            //Esxception: handle exception
            System.out.println("Errorrrrrr: " + e.getMessage());
            return false;
        }
    }
    
    public boolean ModifyRow( int idRegistro, String[] registroNuevo, Tabla tabla )    {
        try {
            PreparedStatement statement = tallerCostura.prepareStatement(tabla.updateSQL());
            // Checar si tiene llave primaria para insertar el primer elemento de registro registro[0]
            int i = 1;
            for (; i < tabla.Columnas().size() + 1; i++) {
                statement.setString(i, registroNuevo[i]);
                // setString siempre empieza en 1, si se modifica el ciclo para i = 0, adaptar setstring;
            }
            statement.setInt(i, idRegistro);

            //java.sql.Statement sqlConnect = tallerCostura.createStatement();
            statement.executeUpdate();
            statement.close();
            
            return true;

        } catch (Exception e) {
            //Esxception: handle exception
            System.out.println("Errorrrrrr: " + e.getMessage());
            return false;
        }
    }
}
