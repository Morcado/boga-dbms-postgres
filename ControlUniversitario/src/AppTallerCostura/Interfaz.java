/*
 * Controlador de la vista.
 * Aqui se definen eventos varios, como click en elemento.
 */
package AppTallerCostura;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.Console;
import java.sql.*;
import java.util.*;  
import java.awt.event.* ;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Morcado
 */
public class Interfaz extends javax.swing.JFrame {
    
    private ConexionPostgre tallerCostura ; //Conexión a la base de datos.
    private List<Tabla> tablas ;    //Todas las tablas que modelan la base de datos.
    private Tabla tablaSeleccionada ;   //Tabla selecccionada en el combobox.
    private int idRegistroSeleccionado ;    //Registro seleccionado en la vista.
    private List<String[]> registros;   //Los registros de cada tabla.

    public Interfaz() {
        initComponents();
        
        try {   //Se intenta crear una conexión.
            tallerCostura = new ConexionPostgre();
        }
        catch( Exception e )    {
            JOptionPane.showMessageDialog(null, e.getMessage() );
        }

        tablas = new ArrayList<>();
        /*Agregar las tablas del modelo de la base de datos.*/
        tablas.add( new Cliente() );
        tablas.add( new TipoTrabajo() );
        tablas.add( new Empleado() );
        tablas.add( new Confeccion() );
        tablas.add( new Prenda() );
        tablas.add( new Trabajo () );
        tablas.add( new Material() );
        tablas.add( new MaterialParaTrabajo() );
        tablas.add( new Proveedor() );
        tablas.add( new DetalleCompra() );
        tablas.add( new Compra() ); //10
        tablas.forEach( c ->  choice1.addItem( c.Nombre() ) );
        
        jButton4.setVisible(false);
    }
    
    public void ShowData( Tabla tabla ) {
        try {
            //Vista de filas de abajo.
            DefaultTableModel modeloTabla = tallerCostura.CreaModeloTabla( tabla );
            jTable2.setModel( modeloTabla );
            
            //Vista de fila única de arriba.
            DefaultTableModel modeloRegistro = tallerCostura.CreaModeloQuery( tabla );
            jTable1.setModel( modeloRegistro );
            
            //Se agregan las filas de la tabla a la vista de abajo.
            registros = tallerCostura.Registros( tabla );
            registros.forEach( r -> modeloTabla.addRow( r ) );
            
            //Se agrega una fila vacia para los inputs.
            String[] registro = new String[tabla.Columnas().size()];
            modeloRegistro.addRow( registro );
            
            System.out.println("Mostrar terminado");
        } catch (Exception e) {
            System.out.println("Error de mostrar: " + e.getMessage());
        }
    }

    public void InsertRegister() {
        //Se obtiene la cantidad de columnas para crear un contenedor
        //del mismo tamaño para la información.
        int tam = tablaSeleccionada.Columnas().size();
        String[] registro = new String[tam];

        // Saltar el primero si tiene llave primaria
        for (int i = 0; i < tam; i++) {
            if( jTable1.getColumnName(i).equals("FechaPedido") || jTable1.getColumnName(i).equals("FechaCompra"))
                continue ;
            
            registro[i] = jTable1.getModel().getValueAt(0, i).toString();
        }

        //Tomar los valores del grid de arriba y los mete en registro.
        //Aunque toma en cuenta el id, dentro de insertar se ignora.
        boolean result = tallerCostura.InsertDataTo( registro, tablaSeleccionada );
        if (result) {   //Si fue exitosa la operación...
            //Actualizamos la vista.
            ShowData( tablaSeleccionada );
        }
        else {
            if (tablaSeleccionada.nombre == "MaterialParaTrabajo") {
                JOptionPane.showMessageDialog(null, "Error al insertar: no hay suficiente material");
            }
            else {
                JOptionPane.showMessageDialog(null, "Error al insertar");
            }
        }
    }

    public void DeleteRegister() {     
        //Se borra un registro, del cual tenemos su id, y la tabla a la que pertenece.
        boolean result = tallerCostura.DeleteDataFrom( idRegistroSeleccionado, tablaSeleccionada );
        if (result) {   //Si fue exitosa la operación...
            //Actualizamos la vista.
            ShowData( tablaSeleccionada );
        }
        else {
            JOptionPane.showMessageDialog(null, "Error al borrar.");
        }
    }

    public void ModifyRegister() {    
        //Tomar los valores de los inputs de arriba y los mete en newInfo.
        int tam = tablaSeleccionada.Columnas().size();
        String[] registro = new String[tam];

        // Saltar el primero si tiene llave primaria
        for (int i = 0; i < tam; i++) {
            if( jTable1.getColumnName(i).equals("FechaPedido") )
                continue ;
            
            registro[i] = jTable1.getModel().getValueAt(0, i).toString();
        }
        
        boolean result = tallerCostura.ModifyRow( idRegistroSeleccionado, registro, tablaSeleccionada );
        if (result) {   //Si fue exitosa la operación...
            //Se actualizan los datos
            ShowData( tablaSeleccionada );
        }
        else {
            JOptionPane.showMessageDialog(null, "Error al modificar.");
        }
    }
    
    public void ShowRegister(int fila)  {
        List<String> registro = new ArrayList<>();

        for( int i = 0; i < jTable2.getModel().getColumnCount() ; i++ ) {
            String nombreCol = jTable2.getColumnName( i );
            
            if( tablaSeleccionada.Columnas().contains( nombreCol ) )
                registro.add( jTable2.getModel().getValueAt(fila, i).toString() );
        }
        
        for (int i = 0; i < jTable1.getModel().getColumnCount() ; i++) {
            jTable1.getModel().setValueAt(registro.get(i), 0, i);
        }
        
        //Muestra la información del registro seleccionado en el grid de arriba.

    }
    /* ================Esta parte es creada automáticamente por NETBeans.=================== */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        label1 = new java.awt.Label();
        choice1 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Taller de Costura");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getAccessibleContext().setAccessibleName("atributosRegistro");

        jButton1.setText("Insertar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        label1.setText("Tabla:");

        choice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice1ActionPerformed(evt);
            }
        });

        jButton4.setText("Ver detalle compra");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(229, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(153, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        InsertRegister();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ModifyRegister();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DeleteRegister();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if( tablaSeleccionada != null ) {
            idRegistroSeleccionado = Integer.parseInt(jTable2.getModel().getValueAt(jTable2.getSelectedRow(), 0).toString());
            ShowRegister(jTable2.getSelectedRow());
               if (tablaSeleccionada.nombre == "Compra") {
                   registros = tallerCostura.RegistrosId(tablas.get(9), idRegistroSeleccionado);
                   if (registros.isEmpty())
                       jButton3.setEnabled(true); 
                   else 
                       jButton3.setEnabled(false);
               }
               else {
                   jButton3.setEnabled(true); 
               }
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void choice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice1ActionPerformed
        if( choice1.getSelectedIndex() != -1 )  {
            tablaSeleccionada = tablas.get( choice1.getSelectedIndex() );
            ShowData( tablaSeleccionada );
            if (tablaSeleccionada.nombre == "Compra") {
                jButton4.setVisible(true);
                jButton3.setEnabled(false);
            }else{
                jButton4.setVisible(false);
            }
        }
    }//GEN-LAST:event_choice1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JDialog frame = new JDialog();
        JTable jTable3 = new JTable();

        DefaultTableModel modelo = tallerCostura.CreaModeloTabla( tablas.get(9) );
        jTable3.setModel( modelo );

        jTable3.setModel( modelo );
        registros = tallerCostura.RegistrosId(tablas.get(9), idRegistroSeleccionado);
        registros.forEach( r -> modelo.addRow( r ) );
        
        JScrollPane jScrollPane3 = new JScrollPane(jTable3);
        
        frame.getContentPane().add(jScrollPane3);
        frame.pack();
        frame.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
    /* ==========================Fin de la parte creada por NETBeans=================== */
}
