/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooad.proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author uk
 */
public class donorsignup extends javax.swing.JFrame {

    public donorsignup() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        phonenum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        goback = new javax.swing.JButton();
        userid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        address = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        creditcardnum = new javax.swing.JTextField();
        pin = new javax.swing.JPasswordField();
        submit = new javax.swing.JButton();
        passwordtf = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);
        jPanel1.add(phonenum);
        phonenum.setBounds(260, 210, 200, 20);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 255, 51));
        jLabel11.setText("Phone No.");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(70, 200, 110, 30);

        goback.setText("GO BACK");
        goback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gobackActionPerformed(evt);
            }
        });
        jPanel1.add(goback);
        goback.setBounds(80, 480, 130, 40);

        userid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        userid.setForeground(new java.awt.Color(153, 255, 153));
        userid.setText("USER ID SET BY DEFAULT");
        jPanel1.add(userid);
        userid.setBounds(260, 110, 210, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 102));
        jLabel2.setText("Name");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 50, 80, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 102));
        jLabel3.setText("Adress");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(70, 400, 80, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 102));
        jLabel4.setText("Email");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(70, 220, 80, 30);

        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(680, 120, 34, 14);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 102));
        jLabel6.setText("User_id");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(70, 96, 80, 40);

        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(660, 210, 34, 14);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 255, 102));
        jLabel8.setText("Credit Card #");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(70, 260, 130, 40);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 255, 102));
        jLabel9.setText("Pin (4 digits)");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(80, 320, 120, 30);
        jPanel1.add(name);
        name.setBounds(260, 50, 210, 30);
        jPanel1.add(address);
        address.setBounds(260, 400, 210, 30);

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });
        jPanel1.add(email);
        email.setBounds(260, 230, 210, 30);

        creditcardnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditcardnumActionPerformed(evt);
            }
        });
        jPanel1.add(creditcardnum);
        creditcardnum.setBounds(260, 270, 210, 30);
        jPanel1.add(pin);
        pin.setBounds(260, 330, 210, 30);

        submit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        submit.setForeground(new java.awt.Color(0, 0, 204));
        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit);
        submit.setBounds(420, 460, 120, 50);

        passwordtf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        passwordtf.setForeground(new java.awt.Color(0, 255, 102));
        passwordtf.setText("Password");
        jPanel1.add(passwordtf);
        passwordtf.setBounds(70, 156, 120, 40);
        jPanel1.add(password);
        password.setBounds(260, 160, 210, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ooad/proj/donationslogin.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 1080, 590);

        jLabel10.setText("jLabel10");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(80, 450, 40, 14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void creditcardnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditcardnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditcardnumActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        donor d = new donor();
        d.Signup(phonenum.getText(),address.getText(), creditcardnum.getText(), pin.getText(), userid.getText(), name.getText(), password.getText(), email.getText());
    }//GEN-LAST:event_submitActionPerformed

    private void gobackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gobackActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_gobackActionPerformed

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
            java.util.logging.Logger.getLogger(donorsignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(donorsignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(donorsignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(donorsignup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new donorsignup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address;
    private javax.swing.JTextField creditcardnum;
    private javax.swing.JTextField email;
    private javax.swing.JButton goback;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField name;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordtf;
    private javax.swing.JTextField phonenum;
    private javax.swing.JPasswordField pin;
    private javax.swing.JButton submit;
    private javax.swing.JLabel userid;
    // End of variables declaration//GEN-END:variables
}
