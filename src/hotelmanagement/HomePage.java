package hotelmanagement;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

class booking {

    private Date currentDate;
    private Connection sqlConnection;
    private Statement statement;
    private ResultSet query;
    private String user, password, confirmation, id;
    private ArrayList<String[]> returnBookings;
    private SimpleDateFormat sdf;

    booking() {
        sqlConnection = null;
        statement = null;
        query = null;
        user = "root";
        password = "";
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        returnBookings = new ArrayList<String[]>();
    }

    public void viewTable() {
        currentDate = new Date();
        try {
            returnBookings.clear();
            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement?serverTimezone=UTC", user, password);
            statement = sqlConnection.createStatement();
            query = statement.executeQuery("SELECT * FROM booking where Checkin >= '" + sdf.format(currentDate) + "' OR Checkout >='" + sdf.format(currentDate) + "'");
            while (query.next()) {
                String[] row = {query.getString("id"), query.getString("Name"), query.getString("Checkin"), query.getString("Checkout"), query.getString("Stay"), query.getString("Room_Id"), query.getString("Payment_Status"), query.getString("IC_Passport"), query.getString("Email"), query.getString("Contact_Number")};
                returnBookings.add(row);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public ArrayList<String[]> getList() {
        return (returnBookings);
    }

    public void setDeleteConfirmation(String c) {
        confirmation = c;
    }

    public String getDeleteConfirmation() {
        return confirmation;
    }

    public void setId(String ID) {
        id = ID;
    }

    public void cancelBooking(int result) {
        if (result == 0) {
            //delete
            try {
                sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement?serverTimezone=UTC", user, password);
                PreparedStatement deleteBooking = sqlConnection.prepareStatement("DELETE FROM booking WHERE Id =?");
                deleteBooking.setString(1, id);
                deleteBooking.executeUpdate();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else {
            //do nothing
        }
    }
}

public class HomePage extends javax.swing.JFrame {

    public HomePage() {
        initComponents();
        viewBookings();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        bookingTable.removeColumn(bookingTable.getColumnModel().getColumn(0));
        this.getContentPane().setBackground(new Color(243, 244, 246));
    }

    public HomePage(String name) {
        initComponents();
        viewBookings();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        lblWelcome.setText("Welcome: " + name);
        bookingTable.removeColumn(bookingTable.getColumnModel().getColumn(0));
        this.getContentPane().setBackground(new Color(243, 244, 246));
    }


    
    
    
    booking data = new booking();
    ArrayList<String> returnSelected = new ArrayList<String>();//for edit booking
 
    public void viewBookings() {
        data.viewTable();
        DefaultTableModel table = (DefaultTableModel) bookingTable.getModel();
        table.setRowCount(0);
        for (String[] rowValue : data.getList()) {
            table.addRow(rowValue);
        }
        // disable button no selected row
        btnEdit.setEnabled(false);
        btnPay.setEnabled(false);
        btnCancelBooking.setEnabled(false);
        // get data of selected row
        bookingTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnEdit.setEnabled(true);
                btnPay.setEnabled(true);
                btnCancelBooking.setEnabled(true);
                // i = the index of the selected row
                int rows = bookingTable.getSelectedRow();
                int row = bookingTable.convertRowIndexToModel(rows);
                returnSelected.clear();
                returnSelected.add(table.getValueAt(row, 0).toString());
                returnSelected.add(table.getValueAt(row, 1).toString());
                returnSelected.add(table.getValueAt(row, 2).toString());
                returnSelected.add(table.getValueAt(row, 3).toString());
                returnSelected.add(table.getValueAt(row, 4).toString());
                returnSelected.add(table.getValueAt(row, 5).toString());
                returnSelected.add(table.getValueAt(row, 6).toString());
                returnSelected.add(table.getValueAt(row, 7).toString());
                returnSelected.add(table.getValueAt(row, 8).toString());
                returnSelected.add(table.getValueAt(row, 9).toString());
                lblName.setText(table.getValueAt(row, 1).toString());
                data.setDeleteConfirmation(table.getValueAt(row, 1).toString());
                data.setId(table.getValueAt(row, 0).toString());
            }
        });
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
        bookingTable = new javax.swing.JTable();
        txtSearch = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnPay = new javax.swing.JButton();
        btnCancelBooking = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hotel Management System");
        setBackground(new java.awt.Color(255, 255, 255));

        bookingTable.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        bookingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Check-in", "Check-out", "Stay (days)", "Room", "Payment", "IC/Passport", "Email", "Contact Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bookingTable.setRowHeight(30);
        bookingTable.setSelectionBackground(new java.awt.Color(0, 156, 232));
        bookingTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        bookingTable.setShowGrid(false);
        jScrollPane1.setViewportView(bookingTable);

        txtSearch.setName(""); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Search:");

        btnAdd.setBackground(new java.awt.Color(31, 41, 55));
        btnAdd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(31, 41, 55));
        btnEdit.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Select a Row to");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnPay.setBackground(new java.awt.Color(31, 41, 55));
        btnPay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setText("Make Payment");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        btnCancelBooking.setBackground(new java.awt.Color(31, 41, 55));
        btnCancelBooking.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnCancelBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelBooking.setText("Cancel Booking");
        btnCancelBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelBookingActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Selected Customer:");

        lblName.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btnLogout.setBackground(new java.awt.Color(31, 41, 55));
        btnLogout.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblWelcome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnLogout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnPay)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelBooking))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DefaultTableModel model = (DefaultTableModel) bookingTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        bookingTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        AddBooking add = new AddBooking(this, true);
        add.setVisible(true);
        viewBookings();
        lblName.setText("");

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        new EditBooking(this, true, returnSelected).setVisible(true);
        viewBookings();
        lblName.setText("");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        new MakePayment(this, true, returnSelected).setVisible(true);
        viewBookings();
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnCancelBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelBookingActionPerformed
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking made by " + data.getDeleteConfirmation(),
                "Booking Cancelation",
                JOptionPane.YES_NO_OPTION);
        data.cancelBooking(result);
        viewBookings();
        lblName.setText("");
    }//GEN-LAST:event_btnCancelBookingActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed
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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookingTable;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelBooking;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblWelcome;
    private java.awt.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
