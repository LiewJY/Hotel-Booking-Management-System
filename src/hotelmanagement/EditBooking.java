package hotelmanagement;

import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import java.util.Date;
import javax.swing.JOptionPane;

class edit {

    private Connection sqlConnection;
    private Statement statement;
    private ResultSet roomQuery, occcupiedRoomQuery;
    private String user, password, id;
    private ArrayList<String> returnRooms, occupiedRooms, availableRooms;
    private Date checkIn, checkOut;
    private int room;
    private float day;
    private SimpleDateFormat sdf;

    edit() {
        sqlConnection = null;
        statement = null;
        occcupiedRoomQuery = null;
        user = "root";
        password = "";
        returnRooms = new ArrayList<String>();
        occupiedRooms = new ArrayList<String>();
        availableRooms = new ArrayList<String>();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setRoom(int booked) {
        room = booked;
    }

    public void setCheckIn(Date chkin) {
        checkIn = chkin;
    }

    public void setCheckOut(Date chkout) {
        checkOut = chkout;
    }

    public void setId(String ID) {
        id = ID;
    }

    public void calculateDay() {
        long difference = checkOut.getTime() - checkIn.getTime();
        day = (difference / (1000 * 60 * 60 * 24));
    }

    public void avaliableRoom() {

        try {
            //get all room
            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement?serverTimezone=UTC", user, password);
            statement = sqlConnection.createStatement();
            roomQuery = statement.executeQuery("SELECT * FROM room");
            availableRooms.clear();
            returnRooms.clear();
            occupiedRooms.clear();
            while (roomQuery.next()) {
                String row = roomQuery.getString("id");
                returnRooms.add(row);
            }
            //get occupied room
            occcupiedRoomQuery = statement.executeQuery("SELECT Room_Id FROM `booking` WHERE Checkin BETWEEN '" + sdf.format(checkIn) + "' AND '" + sdf.format(checkOut) + "' OR Checkout BETWEEN '" + sdf.format(checkIn) + "' AND '" + sdf.format(checkOut) + "' ");
            while (occcupiedRoomQuery.next()) {
                String row = occcupiedRoomQuery.getString("Room_Id");
                occupiedRooms.add(row);
            }
            // get all avaliable room
            returnRooms.removeAll(occupiedRooms);
            availableRooms = returnRooms;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public ArrayList<String> getRooms() {
        return (availableRooms);
    }

    public void editBooking() {
        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement?serverTimezone=UTC", user, password);
            PreparedStatement editBooking = sqlConnection.prepareStatement("UPDATE booking SET Checkin =?, Checkout =?,Stay =?, Room_Id =? WHERE Id = '" + id + "'");
            editBooking.setString(1, sdf.format(checkIn));
            editBooking.setString(2, sdf.format(checkOut));
            editBooking.setFloat(3, day);
            editBooking.setInt(4, room);
            editBooking.executeUpdate();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

public class EditBooking extends javax.swing.JDialog {

    edit edit = new edit();
    DefaultComboBoxModel cmb = new DefaultComboBoxModel();

    public EditBooking(java.awt.Frame parent, boolean modal, ArrayList<String> returnSelected) {
        super(parent, modal);
        initComponents();
        //CHANGE DATE FORMAT
        dcCheckout.setDateFormatString("yyyy-MM-dd");
        dcCheckIn.setDateFormatString("yyyy-MM-dd");
        // diable field                 
        txtName.setEditable(false);
        txtIC.setEditable(false);
        txtEmail.setEditable(false);
        txtContact.setEditable(false);
        txtPayment.setEditable(false);

        txtName.setText(returnSelected.get(1));
        txtIC.setText(returnSelected.get(7));
        txtEmail.setText(returnSelected.get(8));
        txtContact.setText(returnSelected.get(9));
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(returnSelected.get(2));
            dcCheckIn.setDate(date);

        } catch (ParseException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(returnSelected.get(3));
            dcCheckout.setDate(date);

        } catch (ParseException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmb.addElement(returnSelected.get(5));
        cmbRoom.setSelectedItem(returnSelected.get(5));
        txtPayment.setText(returnSelected.get(6));
        edit.setId(returnSelected.get(0));
        this.getContentPane().setBackground(new Color(243, 244, 246));
    }

    private void updateAvailableRoom() {
        edit.avaliableRoom();
        cmbRoom.removeAllItems();
        for (String rowValue : edit.getRooms()) {
            cmb.addElement(rowValue);
        }
        cmbRoom.setModel(cmb);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        cmbRoom = new javax.swing.JComboBox<>();
        txtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        txtIC = new javax.swing.JTextField();
        dcCheckIn = new com.toedter.calendar.JDateChooser();
        dcCheckout = new com.toedter.calendar.JDateChooser();
        txtPayment = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblCheckIn = new javax.swing.JLabel();
        lblIC = new javax.swing.JLabel();
        lblCheckOut = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblRoom = new javax.swing.JLabel();
        lblContact = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Booking");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Payment:");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setText("Booking Info");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Contact Number:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Name:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Check-in Date:");

        btnCancel.setBackground(new java.awt.Color(31, 41, 55));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Email:");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("IC/Passport: ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Room:");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel9.setText("Customer Information");

        cmbRoom.setBackground(new java.awt.Color(243, 244, 246));
        cmbRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRoomActionPerformed(evt);
            }
        });
        cmbRoom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbRoomPropertyChange(evt);
            }
        });

        txtName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Check-out Date:");

        btnSave.setBackground(new java.awt.Color(31, 41, 55));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Changes");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtIC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtICActionPerformed(evt);
            }
        });

        dcCheckIn.setBackground(new java.awt.Color(243, 244, 246));
        dcCheckIn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcCheckInPropertyChange(evt);
            }
        });

        dcCheckout.setBackground(new java.awt.Color(243, 244, 246));
        dcCheckout.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcCheckoutPropertyChange(evt);
            }
        });

        txtPayment.setText("Pending");

        lblCheckIn.setForeground(new java.awt.Color(255, 0, 0));

        lblCheckOut.setForeground(new java.awt.Color(255, 0, 0));

        lblRoom.setForeground(new java.awt.Color(255, 0, 0));

        lblContact.setToolTipText("");
        lblContact.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblPayment.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(58, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(34, 34, 34)
                                        .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblIC, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtIC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(lblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblCheckOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbRoom, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcCheckIn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dcCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void cmbRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRoomActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (dcCheckIn.getDate() == null) {
            lblCheckIn.setText("Checkin cannot be empty.");
            JOptionPane.showMessageDialog(this,
                    "Checkin date cannot be empty.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            lblCheckIn.setText("");
            if (dcCheckout.getDate() == null) {
                lblCheckOut.setText("Checkout cannot be empty.");
                JOptionPane.showMessageDialog(this,
                        "Checkout date cannot be empty.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                lblCheckOut.setText("");
                if (cmbRoom.getSelectedIndex() == -1) {
                    lblRoom.setText("Room cannot be empty.");
                    JOptionPane.showMessageDialog(this,
                            "Room cannot be empty.",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    lblRoom.setText("");
                    edit.setRoom(parseInt(cmbRoom.getSelectedItem().toString()));
                    edit.calculateDay();
                    edit.editBooking();
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void dcCheckInPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcCheckInPropertyChange
        if (dcCheckIn.getDate() == null) {
            dcCheckout.setEnabled(false);
            cmbRoom.setEnabled(false);
        } else {
            edit.setCheckIn(dcCheckIn.getDate());
            dcCheckout.setEnabled(true);
            cmbRoom.setEnabled(false);
            lblCheckIn.setText("");
        }
    }//GEN-LAST:event_dcCheckInPropertyChange
    private void dcCheckoutPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcCheckoutPropertyChange
        if (dcCheckout.getDate() == null) {
            cmbRoom.setEnabled(false);
        } else {
            edit.setCheckOut(dcCheckout.getDate());
            updateAvailableRoom();
            cmbRoom.setEnabled(true);
            lblCheckOut.setText("");
        }
    }//GEN-LAST:event_dcCheckoutPropertyChange

    private void cmbRoomPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbRoomPropertyChange
        if (cmbRoom.getSelectedIndex() == -1) {
            lblRoom.setText("Room cannot be empty.");
        } else {
            lblRoom.setText("");
        }
    }//GEN-LAST:event_cmbRoomPropertyChange

    private void txtICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtICActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtICActionPerformed

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
            java.util.logging.Logger.getLogger(EditBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditBooking dialog = new EditBooking(new javax.swing.JFrame(), true, new ArrayList<String>());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbRoom;
    private com.toedter.calendar.JDateChooser dcCheckIn;
    private com.toedter.calendar.JDateChooser dcCheckout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblCheckIn;
    private javax.swing.JLabel lblCheckOut;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblIC;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblRoom;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIC;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPayment;
    // End of variables declaration//GEN-END:variables
}
