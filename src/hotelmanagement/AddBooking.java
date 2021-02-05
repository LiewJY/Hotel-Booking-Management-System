package hotelmanagement;

import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

class Customer extends People {

    private Connection sqlConnection;
    private Statement statement;
    private ResultSet roomQuery, occcupiedRoomQuery;
    private PreparedStatement insertBooking;
    private String user, password;
    private ArrayList<String> returnRooms, occupiedRooms, availableRooms;
    private String ic, email, contactNumber;
    private Date checkIn, checkOut;
    private int room;
    private float day;
    private SimpleDateFormat sdf;

    Customer() {
        super();
        sqlConnection = null;
        statement = null;
        roomQuery = null;
        occcupiedRoomQuery = null;
        user = "root";
        password = "";
        returnRooms = new ArrayList<String>();
        occupiedRooms = new ArrayList<String>();
        availableRooms = new ArrayList<String>();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setName(String n) {
        name = n;
    }

    public void setIc(String IC) {
        ic = IC;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public void setContactNumber(String CN) {
        contactNumber = CN;
    }

    public void setCheckIn(Date chkin) {
        checkIn = chkin;
    }

    public void setCheckOut(Date chkout) {
        checkOut = chkout;
    }

    public void setRoom(int booked) {
        room = booked;
    }

    public void calculateDay() {
        long difference = checkOut.getTime() - checkIn.getTime();
        day = (difference / (1000 * 60 * 60 * 24));
    }

    public void avaliableRoom() {
        try {
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
            //format date for sql 
            occcupiedRoomQuery = statement.executeQuery("SELECT Room_Id FROM `booking` WHERE Checkin BETWEEN '" + sdf.format(checkIn) + "' AND '" + sdf.format(checkOut) + "' OR Checkout BETWEEN '" + sdf.format(checkIn) + "' AND '" + sdf.format(checkOut) + "' ");
            while (occcupiedRoomQuery.next()) {
                String row = occcupiedRoomQuery.getString("Room_Id");
                occupiedRooms.add(row);
            }
            returnRooms.removeAll(occupiedRooms);
            availableRooms = returnRooms;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public ArrayList<String> getRooms() {
        return (availableRooms);
    }

    public void insertBooking() {
        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmanagement?serverTimezone=UTC", user, password);
            insertBooking = sqlConnection.prepareStatement("INSERT INTO booking (Name,IC_Passport,Email,Contact_Number,Checkin,Checkout,Stay,Room_Id,Payment_Status) VALUES (?,?,?,?,?,?,?,?,?)");
            insertBooking.setString(1, name);
            insertBooking.setString(2, ic);
            insertBooking.setString(3, email);
            insertBooking.setString(4, contactNumber);
            insertBooking.setString(5, sdf.format(checkIn));
            insertBooking.setString(6, sdf.format(checkOut));
            insertBooking.setFloat(7, day);
            insertBooking.setInt(8, room);
            insertBooking.setString(9, "Pending");
            insertBooking.executeUpdate();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

public class AddBooking extends javax.swing.JDialog {
    
    Customer booking = new Customer();
    public AddBooking(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //CHANGE DATE FORMAT
        dcCheckout.setDateFormatString("yyyy/MM/dd");
        dcCheckin.setDateFormatString("yyyy/MM/dd");
        txtPayment.setEditable(false);
        //style
        this.getContentPane().setBackground(new Color(243, 244, 246));
    }

    private void updateAvailableRoom() {
        booking.avaliableRoom();
        DefaultComboBoxModel cmb = new DefaultComboBoxModel();
        for (String rowValue : booking.getRooms()) {
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

        cmbRoom = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtIC = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dcCheckin = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dcCheckout = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
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
        setTitle("Add Booking");

        cmbRoom.setBackground(new java.awt.Color(243, 244, 246));
        cmbRoom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Check-out Date:");

        txtIC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtIC.setPreferredSize(new java.awt.Dimension(8, 23));
        txtIC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtICKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setText("Booking Info");

        dcCheckin.setBackground(new java.awt.Color(243, 244, 246));
        dcCheckin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcCheckinPropertyChange(evt);
            }
        });
        dcCheckin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dcCheckinKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Check-in Date:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Email:");

        dcCheckout.setBackground(new java.awt.Color(243, 244, 246));
        dcCheckout.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcCheckoutPropertyChange(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Room:");

        txtContact.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtContact.setPreferredSize(new java.awt.Dimension(8, 23));
        txtContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContactKeyReleased(evt);
            }
        });

        txtName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtName.setPreferredSize(new java.awt.Dimension(8, 23));
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(31, 41, 55));
        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Payment:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Contact Number:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Name:");

        btnCancel.setBackground(new java.awt.Color(31, 41, 55));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.setPreferredSize(new java.awt.Dimension(8, 23));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("IC/Passport: ");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel9.setText("Customer Information");

        txtPayment.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPayment.setText("Pending");
        txtPayment.setPreferredSize(new java.awt.Dimension(56, 25));

        lblName.setForeground(new java.awt.Color(255, 0, 0));

        lblCheckIn.setForeground(new java.awt.Color(255, 0, 0));

        lblIC.setForeground(new java.awt.Color(255, 0, 0));

        lblCheckOut.setForeground(new java.awt.Color(255, 0, 0));

        lblEmail.setForeground(new java.awt.Color(255, 0, 0));

        lblRoom.setForeground(new java.awt.Color(255, 0, 0));

        lblContact.setForeground(new java.awt.Color(255, 0, 0));
        lblContact.setToolTipText("");
        lblContact.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblPayment.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4))
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtContact, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                            .addComponent(lblIC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtIC, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addComponent(lblContact, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(15, 15, 15)
                                            .addComponent(cmbRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(15, 15, 15)
                                            .addComponent(txtPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(dcCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(105, 105, 105)
                                        .addComponent(lblCheckIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dcCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel10)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(58, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnCancel)
                        .addGap(54, 54, 54))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dcCheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtIC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dcCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                                    .addComponent(lblCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCheckOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRoomActionPerformed

    private void dcCheckinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcCheckinPropertyChange
        if (dcCheckin.getDate() == null) {
            dcCheckout.setEnabled(false);
            cmbRoom.setEnabled(false);
        } else {
            booking.setCheckIn(dcCheckin.getDate());
            dcCheckout.setEnabled(true);
            lblCheckIn.setText("");
        }
    }//GEN-LAST:event_dcCheckinPropertyChange

    private void dcCheckinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dcCheckinKeyReleased

    }//GEN-LAST:event_dcCheckinKeyReleased

    private void dcCheckoutPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcCheckoutPropertyChange
        if (dcCheckout.getDate() == null) {
            cmbRoom.setEnabled(false);
        } else {
            booking.setCheckOut(dcCheckout.getDate());
            updateAvailableRoom();
            cmbRoom.setEnabled(true);
            lblCheckOut.setText("");
        }
    }//GEN-LAST:event_dcCheckoutPropertyChange

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (txtName.getText().trim().isEmpty()) {
            lblName.setText("Name cannot be empty.");
            JOptionPane.showMessageDialog(this,
                    "Name cannot be empty.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            lblName.setText("");
            if (txtIC.getText().trim().isEmpty()) {
                lblIC.setText("IC cannot be empty.");
                JOptionPane.showMessageDialog(this,
                        "IC/passport cannot be empty.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                lblIC.setText("");
                if (!Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", txtEmail.getText())) {
                    lblEmail.setText("Invalid email address.");
                    JOptionPane.showMessageDialog(this,
                            "Invalid email address.",
                            "Validation Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    lblEmail.setText("");
                    if (!Pattern.matches("^[0-9]+$", txtContact.getText())) {
                        lblContact.setText("Contact can contain numbers only.");
                        JOptionPane.showMessageDialog(this,
                                "Contact can contain numbers only.",
                                "Validation Error",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        lblContact.setText("");
                        if (dcCheckin.getDate() == null) {
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
                                    booking.setName(txtName.getText());
                                    booking.setIc(txtIC.getText());
                                    booking.setContactNumber(txtContact.getText());
                                    booking.setEmail(txtEmail.getText());
                                    booking.setRoom(parseInt(cmbRoom.getSelectedItem().toString()));
                                    booking.calculateDay();
                                    booking.insertBooking();
                                    this.dispose();
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed

    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        if (txtName.getText().trim().isEmpty()) {
            lblName.setText("Name cannot be empty.");
        } else {
            lblName.setText("");
        }
    }//GEN-LAST:event_txtNameKeyReleased

    private void txtICKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtICKeyReleased
        if (txtIC.getText().trim().isEmpty()) {
            lblIC.setText("IC cannot be empty.");
        } else {
            lblIC.setText("");
        }
    }//GEN-LAST:event_txtICKeyReleased

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        if (!Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", txtEmail.getText())) {
            lblEmail.setText("Invalid email format.");
        } else {
            lblEmail.setText("");
        }
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtContactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactKeyReleased
        if (!Pattern.matches("^[0-9]+$", txtContact.getText())) {
            lblContact.setText("Contact can contain numbers only.");
        } else {
            lblContact.setText("");
        }
    }//GEN-LAST:event_txtContactKeyReleased

    private void cmbRoomPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbRoomPropertyChange
        if (cmbRoom.getSelectedIndex() == -1) {
            lblRoom.setText("Room cannot be empty.");
        } else {
            lblRoom.setText("");
        }
    }//GEN-LAST:event_cmbRoomPropertyChange

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
            java.util.logging.Logger.getLogger(AddBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddBooking dialog = new AddBooking(new javax.swing.JFrame(), true);
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
    private com.toedter.calendar.JDateChooser dcCheckin;
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
