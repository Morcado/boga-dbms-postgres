/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controluniversitario;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLEditorKit.Parser;

import java.io.Console;
import java.sql.*;

/**
 *
 * @author Morcado
 */
public class Carrera extends javax.swing.JFrame {

    /**
     * Creates new form Alumno
     */
    
    String url = "jdbc:postgresql://localhost:5432/ControlUniversitario";
    String usuario = "postgres";
    String contraseña = "postgres";
    String selectedPK = "-1";
    DefaultTableModel md;
    // datos de la base de datos
    String data[][] = {};
    String cabeza[] = {"IdCarrera", "nombre"};
    Connection connection;

    public Carrera() {
        initComponents();
        md = new DefaultTableModel(data, cabeza);
        jTable1.setModel(md);
        Conexion();
        
        
        /*jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected setRowSorter(sorter);
                jTextField1.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                selectedPK = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                System.out.println(selectedPK);
                System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
            }
        });*/

        ShowData("Carrera");
        
    }

    public void Conexion() {
        try {
            System.out.println("HOAL");
            Class.forName("org.postgresql.Driver"); 
            connection = DriverManager.getConnection(url, usuario, contraseña);
        }
        catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
    
    public void ShowData(String tabla) {
        try {
            md = new DefaultTableModel(data, cabeza);
            jTable1.setModel(md);
            
            java.sql.Statement statement = connection.createStatement();
            String sql = "SELECT IdCarrera, Nombre FROM Datos." + tabla;            
            ResultSet resultSet = statement.executeQuery(sql);
                
           
            while (resultSet.next()) {
                String idCarrera = resultSet.getString("idcarrera");
                String nombre = resultSet.getString("nombre");
                String datos[] = {idCarrera, nombre};

                md.addRow(datos);

            }
            
            resultSet.close();
            statement.close();
            
            /*jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event) {
                    // do some actions here, for example
                    // print first column value from selected setRowSorter(sorter);
                    jTextField1.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                    selectedPK = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                      
                System.out.println(selectedPK);
                System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
                }
            });*/

            System.out.println("Mostrar terminado");
        } catch (Exception e) {
            System.out.println("Error de mostrar: " + e.getMessage());
        }
    }

    
    public boolean InsertData() {
        String nombre = jTextField1.getText();
        String statement = new String();

        try {
            statement = "INSERT INTO Datos.Carrera(nombre) VALUES ('" + nombre + "')";

            java.sql.Statement sqlConnect = connection.createStatement();
            sqlConnect.executeUpdate(statement);
            sqlConnect.close();
            
            return true;

        } catch (Exception e) {
            //Esxception: handle exception
            System.out.println("Errorrrrrr: " + e.getMessage());
            return false;
        }
    }

    public void DeleteData() {
        String statement = new String();
        if (selectedPK != "-1") {
            try {
                statement = "DELETE FROM Datos.Carrera WHERE IdCarrera=" + selectedPK;

                java.sql.Statement sqlConnect = connection.createStatement();
                sqlConnect.executeUpdate(statement);
                sqlConnect.close();

            } catch (Exception e) {
                //Esxception: handle exception
                System.out.println("Errorrrrrr: " + e.getMessage());
            }
        }
    }

    public boolean ModifyData() {
        String statement = new String();
        if (selectedPK != "-1") {
            try {
                java.sql.Statement sqlConnect = connection.createStatement();
    
                statement = "UPDATE Datos.Carrera SET Nombre = '" + jTextField1.getText() + "' WHERE IdCarrera = " + selectedPK;
                sqlConnect.executeUpdate(statement);
                sqlConnect.close();
                
                return true;
    
            } catch (Exception e) {
                //Esxception: handle exception
                System.out.println("Errorrrrrr: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Nombre Carrera");

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Carrera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        InsertData();
        ShowData("Carrera");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ModifyData();
        ShowData("Carrera");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DeleteData();
        ShowData("Carrera");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jTextField1.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        selectedPK = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Carrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Carrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Carrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Carrera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Carrera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
