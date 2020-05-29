/*
 * Esta clase controla la conexión a la base de datos.
 * Aqui se define la cadena de conexión, el usuario y la contraseña.
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
    private String url = "jdbc:postgresql://localhost:5432/BD_BOGA";    //Cadena de conexión de la base de datos.

    /* La base de datos tiene tres usuarios:
    *  postgres: Es el dueño de la base de datos, tiene todos los permisos.
    *  admin: Puede hacer SELECT, INSERT, y UPDATE, pero no puede hacer DELETE.
    *  consultor: Solamente puede hacer SELECT.
    */
    private String usuario = "postgres";

    private String contraseña = "postgres"; //Son las mismas que el nombre de usuario, ej. la de admin es admin.
    private Connection tallerCostura ;  //Instancia de una conexión a la base de datos.
    
    /* Intenta conectarse, si no puede marca una excepción, pero no truena la aplicación. */
    public ConexionPostgre()  {
        try {
            this.tallerCostura = DriverManager.getConnection( url, usuario, contraseña );
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
    }
    
    /* Crea un modelo de tabla para la vista, basado en las columnas de la tabla.
    *  Las columnasSelect abarcan también columnas que no existen en la tabla, sino
    *  que son creadas por un inner join con la sentencia SELECT de cada tabla. 
    */
    public DefaultTableModel CreaModeloTabla( Tabla tabla ) {
        DefaultTableModel modelo = new DefaultTableModel();
        
        tabla.ColumnasSelect().forEach((c) -> {
            modelo.addColumn(c);
        });
        
        return modelo ;
    }
    
    /* Crea un modelo para los inputs de información. */
    public DefaultTableModel CreaModeloQuery( Tabla tabla ) {
        DefaultTableModel modelo = new DefaultTableModel();
        
        tabla.Columnas().forEach((c) -> {
            modelo.addColumn(c);
        });
        
        return modelo ;
    }
    
    /* Regresa las filas de la tabla. */
    public List<String[]> RegistrosId( Tabla tabla, int id ) {
        List<String[]> registros = new ArrayList<>();
        List<String> columnas = tabla.ColumnasSelect();
        int numCol = columnas.size();
        
        try {
            Statement statement = tallerCostura.createStatement();
            ResultSet resultSet = statement.executeQuery( "SELECT IdDetalleCompra, d.IdCompra, c.FechaCompra, d.IdMaterial, m.Descripcion, CostoUnitario, Cantidad, Subtotal FROM Taller.DetalleCompra AS d " +
"INNER JOIN Taller.Material AS m ON d.IdMaterial = m.IdMaterial " +
"INNER JOIN Taller.Compra AS c ON d.IdCompra = c.IdCompra" + " WHERE d." + tabla.columnasSelect.get(1) +"="+ id );
            
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
            return registros;
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    /* Regresa las filas de la tabla */
    public List<String[]> Registros( Tabla tabla ) {
        List<String[]> registros = new ArrayList<>();
        List<String> columnas = tabla.ColumnasSelect();
        int numCol = columnas.size();
        
        try {   //Intenta crear un query determinado por la tabla y ejecutarlo.
            Statement statement = tallerCostura.createStatement();
            ResultSet resultSet = statement.executeQuery( tabla.selectSQL );
            
            //Como los resultados están en un enumerador diferido, se cicla mientras
            //devuelva alguno.
            while( resultSet.next() ) {
                String[] registro = new String[numCol];
                for( int i = 0 ; i < columnas.size() ; i++ )  {
                    String valor = resultSet.getString( columnas.get( i ) );
                    
                    if( valor == null ) {
                        registro[i] = " ";
                        continue ;
                    }
                        
                    //Cambia los valores booleanos 't' y 'f' a "SI" y "NO" para hacerlos más legibles
                    //en la vista.
                    registro[i] = valor.equals("f") ? "NO" : valor.equals("t") ? "SI" : valor ;
                }
                
                registros.add( registro );
            }
            
            //Se cierra la conexión.
            resultSet.close();
            statement.close();
            return registros ;
        }
        catch( SQLException e ) {
            System.out.println( e.getMessage() );
        }
        
        return null;
    }
    
    /* Controla la inserción a la base de datos. */
    public boolean InsertDataTo( String[] registro, Tabla tabla )  {
        try {   //Insertar un registro en la tabla.
            PreparedStatement statement = tallerCostura.prepareStatement( tabla.insertSQL );

            if (tabla.nombre == "MaterialParaTrabajo") {
                Statement st = tallerCostura.createStatement();
                String sent = "SELECT SUM(cantidad) FROM Taller.DetalleCompra WHERE IdMaterial = " + registro[1] ;
                ResultSet resultSet = st.executeQuery(sent);
                while (resultSet.next())
                if (resultSet.getInt(1) < Integer.parseInt(registro[2])) {
                    return false;
                }
            }
            
            // Checar si tiene llave primaria para insertar el primer elemento de registro registro[0]
            //El primer valor del registro es el id, como es autonúmerico ignorar.
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
