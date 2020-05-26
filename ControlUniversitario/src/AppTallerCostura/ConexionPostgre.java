/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppTallerCostura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.table.DefaultTableModel;
import java.util.*; 
import static jdk.nashorn.internal.objects.NativeMath.round;

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
    
    public DefaultTableModel CreaModeloQuery( Tabla tabla ) {
        DefaultTableModel modelo = new DefaultTableModel();
        
        tabla.Columnas().forEach((c) -> {
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
                    String valor = resultSet.getString( columnas.get( i ) );
                    
                    if( valor == null ) {
                        registro[i] = " ";
                        continue ;
                    }
                        
                    registro[i] = valor.equals("f") ? "NO" : valor.equals("t") ? "SI" : valor ;
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
            PreparedStatement statement = tallerCostura.prepareStatement( tabla.insertSQL );

            // Checar si tiene llave primaria para insertar el primer elemento de registro registro[0]
            for (int i = 0; i < tabla.Columnas().size(); i++) {
                if (tabla.tipos.get(i) == 0) { // es entero
                    statement.setInt(i + 1, Integer.parseInt(registro[i]));
                }
                else if (tabla.tipos.get(i) == 1){ // es cadena 
                    statement.setString(i + 1, registro[i]);
                } 
                else if( tabla.tipos.get(i) == 2 ){  //flotante
;                   float num = (float) (Math.round(Float.parseFloat(registro[i]) * Math.pow(10, 2)) / Math.pow(10, 2));
                    statement.setDouble(i + 1, num);
                }
                else if( tabla.tipos.get(i) == 3 )  { //fecha
                    statement.setDate( i + 1, java.sql.Date.valueOf( registro[i] ) );
                }
                else if( tabla.tipos.get( i ) == 4 )    {   //booleano
                        statement.setBoolean( i + 1, registro[i].equals("SI") ? true : registro[i].equals("NO") ? false : null );
                }
                else if( tabla.tipos.get(i) == 5 )  { //fecha actual
                    statement.setTimestamp( i + 1, java.sql.Timestamp.valueOf( java.time.LocalDateTime.now() ) );
                }
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
            statement.execute();
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
            int i = 0;
            for (; i < tabla.Columnas().size(); i++) {
                if (tabla.tipos.get(i) == 0) { // es entero
                    statement.setInt(i + 1, Integer.parseInt(registroNuevo[i]));
                }
                else if (tabla.tipos.get(i) == 1){ // es cadena 
                    statement.setString(i + 1, registroNuevo[i]);
                } 
                else if( tabla.tipos.get(i) == 2 )    { //double
                    statement.setDouble(i + 1, Double.parseDouble(registroNuevo[i]));
                }
                else if( tabla.tipos.get(i) == 3 )  { //fecha
                    statement.setDate( i + 1, java.sql.Date.valueOf( registroNuevo[i] ) );
                }
                else if( tabla.tipos.get( i ) == 4 )    {   //booleano
                        statement.setBoolean( i + 1, registroNuevo[i].equals("SI") ? true : registroNuevo[i].equals("NO") ? false : null );
                }
                else if( tabla.tipos.get(i) == 5 )  { //fecha actual
                    statement.setDate( i + 1, java.sql.Date.valueOf( java.time.LocalDate.now() ) );
                }
                // setString siempre empieza en 1, si se modifica el ciclo para i = 0, adaptar setstring;
            }
            statement.setInt(i + 1, idRegistro);

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
