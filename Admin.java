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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static ooad.proj.UpdateDProfile.u;

/**
 *
 * @author uk
 */
public class Admin extends person {

    int admin_id;
    String pass;
    String email;
    String phonenum;
    DataBaseLayer d; // used for getting data from DB and also for sending data

    Admin() {
        super();
        admin_id = -1;
        pass = null;
        email = null;
        phonenum = null;
    }

    void Login(String userid, String password) {

        if (userid.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            boolean bool = false;

            try {

                //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "select * from app.admin";
                PreparedStatement check = d.getPrepareStatement(Statement);
                ResultSet rs = d.ExecuteQuery(check);

                while (rs.next()) {
                    if (rs.getString("userid").equals(userid) && rs.getString("password").equals(password)) {
                        bool = true;
                        adminscreen a = new adminscreen();
                        //String[] b = {userid,"ignoreit"};
                        a.Myid = Integer.parseInt(userid);
                        a.main(null);

                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!bool) {
                JOptionPane.showMessageDialog(null, "Oops....\nUsername or Password not matches.\nPlease write correct username and password. ");
            }
        }

    }

    int addAdmin(String pass, String name, String email, String address, String phonenum) {

        int id = 0;
        try {
            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.admin order by userid desc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);
            rs.next();
            id = rs.getInt("userid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = true;
        if (phonenum == null) {
            bool = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                Double.parseDouble(phonenum);
            } catch (Exception e) {
                bool = false;
                JOptionPane.showMessageDialog(null, "Check phone number again.\n It will be accepted in numeric only. ");
            }
        }
        if ((bool == true) && (pass == null || name == null || email == null || address == null || phonenum == null)) {
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else if (bool) {
            try {

                //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "insert into app.admin values(?,?,?,?,?,?)";
                PreparedStatement insert = d.getPrepareStatement(Statement);

                insert.setString(1, name);
                insert.setInt(2, id + 1);
                insert.setString(3, pass);
                insert.setDouble(4, Integer.parseInt(phonenum));
                insert.setString(5, address);
                insert.setString(6, email);
                insert.executeUpdate();
                JOptionPane.showMessageDialog(null, "New admin id is " + (id + 1) + "\nAdmin updates");

                //assinging to instance variables
                this.admin_id = id + 1;
                this.pass = pass;
                this.email = email;
                this.address = address;
                this.phonenum = phonenum;

                return 1;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    void donor_donated(int id,int amount) {
        donor don = new donor();
        if (!don.check_donor(id)) {
            return;
        } else {
            try {
                String str=new String();
                int q;
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "select * from app.donor where userid="+id;
                PreparedStatement insert = d.getPrepareStatement(Statement);
                ResultSet rs = d.ExecuteQuery(insert);
                while(rs.next())
                {
                    str=rs.getString("donated");
                }
                q=Integer.parseInt(str)+amount;
                str=q+"";
                //getting data from DB
                d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                Statement = "update app.donor set donated = '"+str+"' where userid="+id;
                insert = d.getPrepareStatement(Statement);
               d.ExecuteUpdate(insert);

                JOptionPane.showMessageDialog(null, "Donations successfull.\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void donated_by_unregistered(String name, int amount) {

        try {
            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "insert into app.unregistered_donor values(?,?)";
            PreparedStatement insert = d.getPrepareStatement(Statement);

            insert.setString(1, name);
            insert.setInt(2, amount);
            d.Execute(insert);
            JOptionPane.showMessageDialog(null, "Donations successfull.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void removeAdmin(String id, String reason) {

        boolean bool = false;
        try {
            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.admin where userid=" + id;
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            if (rs.next()) {
                if (Integer.parseInt(id) == rs.getInt("userid")) {
                    bool = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool == true) {
            try {

                //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "delete from app.admin where userid=" + id;
                PreparedStatement check = d.getPrepareStatement(Statement);
                d.Execute(check);

                check = d.getPrepareStatement("insert into app.removeadmin values(?,?)");
                check.setInt(1, Integer.parseInt(id));
                if (!reason.isEmpty()) {
                    check.setString(2, reason);
                } else {
                    check.setString(2, reason);
                }
                d.Execute(check);
                JOptionPane.showMessageDialog(null, "SUCCESSFULLY DELETED\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void viewDonations(JList l1, JList l2, JList l3, JList l4) {
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();

        try {

            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.donor d join app.project p on d.p_id=p.id";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("userid"));
                model2.add(i, rs.getString("name"));
                model3.add(i, rs.getString("donated"));
                model4.add(i, rs.getString("id"));

                i++;
            }
            l1.setModel(model1);
            l2.setModel(model2);
            l3.setModel(model3);
            l4.setModel(model4);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void viewAdmins(JList l1, JList l2, JList l3, JList l4, JList l5) {
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();

        try {

            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.admin order by userid asc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("userid"));
                model2.add(i, rs.getString("name"));
                model3.add(i, rs.getString("email"));
                model4.add(i, rs.getString("phonenum"));
                model5.add(i, rs.getString("address"));

                i++;
            }
            l1.setModel(model1);
            l2.setModel(model2);
            l3.setModel(model3);
            l4.setModel(model4);
            l5.setModel(model5);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void sendEmails(String email) {
        try {

            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "update app.donor set msg = '" + email + "'";
            PreparedStatement check = d.getPrepareStatement(Statement);
            d.ExecuteUpdate(check);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "update app.volunteer set msg = '" + email + "'";
            PreparedStatement check = d.getPrepareStatement(Statement);
            d.ExecuteUpdate(check);

            JOptionPane.showMessageDialog(null, "MESSAGE SENT SUCCESSFULLY!!!");
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void UpdateAdminProfile(String userid, String name, String password, String phnnum, String address, String email) {
        try {

            int id = Integer.parseInt(userid);
            try {

                //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);

                String Statement = "update app.admin set name='" + name + "'  where userid=" + id;
                PreparedStatement check = d.getPrepareStatement(Statement);
                d.ExecuteUpdate(check);

                Statement = "update app.admin set password='" + password + "'  where userid=" + id;
                check = d.getPrepareStatement(Statement);
                d.ExecuteUpdate(check);

                Statement = "update app.admin set phonenum=" + Double.parseDouble(phnnum) + "  where userid=" + id;
                check = d.getPrepareStatement(Statement);
                d.ExecuteUpdate(check);

                Statement = "update app.admin set address='" + address + "'  where userid=" + id;
                check = d.getPrepareStatement(Statement);
                d.ExecuteUpdate(check);

                Statement = "update app.admin set email='" + email + "'  where userid=" + id;
                check = d.getPrepareStatement(Statement);
                d.ExecuteUpdate(check);

                JOptionPane.showMessageDialog(null, "ALL ARE UPDATED... ");

            } catch (SQLException ex) {
                Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception : " + e);
        }
    }

    void loadAdminProfile(String userid, JTextField name, JTextField password, JTextField email, JTextField address, JTextField phonenum) {
        if (userid != null) {
            try {
                int i = Integer.parseInt(userid);
                try {
                    // TODO add your handling code here:
                    int id = Integer.parseInt(userid);

                    //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    String Statement = "select * from app.admin order by userid asc";
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    ResultSet rs = d.ExecuteQuery(check);

                    while (rs.next()) {
                        name.setText(rs.getString("name"));
                        password.setText(rs.getString("password"));
                        phonenum.setText(rs.getString("phonenum"));
                        address.setText(rs.getString("address"));
                        email.setText(rs.getString("email"));
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid intput");
        }
    }

    void loadAdminProfile(String userid, JLabel name, JLabel password, JLabel email, JLabel address, JLabel phonenum) {
        if (userid != null) {
            try {
                int i = Integer.parseInt(userid);
                try {
                    // TODO add your handling code here:
                    int id = Integer.parseInt(userid);

                    //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    String Statement = "select * from app.admin order by userid asc";
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    ResultSet rs = d.ExecuteQuery(check);

                    while (rs.next()) {
                        name.setText(rs.getString("name"));
                        password.setText(rs.getString("password"));
                        phonenum.setText(rs.getString("phonenum"));
                        address.setText(rs.getString("address"));
                        email.setText(rs.getString("email"));
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    void loadfunds(JTextField t1, JTextField t2, JTextField t3) {
        try {

            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.organization order by id asc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            t1.setText("1000000");
            t2.setText("500000");
            t3.setText("500000");

            int i = 0;
            while (rs.next()) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    int addPledge(String name, String D_ID, String pledge, String amount) {
        donor dr = new donor();
        if (!dr.check_donor(Integer.parseInt(D_ID))) {
            return 0;
        }
        int id = 0;
        try {

            //getting data from DB LAYER
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.pledge order by pid desc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            if (rs.next()) {
                id = rs.getInt("pid");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = true;
        if (name == null || D_ID == null || amount == null) {
            bool = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                Double.parseDouble(amount);
                Double.parseDouble(D_ID);
            } catch (Exception e) {
                bool = false;
                JOptionPane.showMessageDialog(null, "Check amount again.\n It will be accepted in numeric only. ");
            }
        }

        if (pledge.isEmpty()) {
            pledge = "No statement...";
        }

        if (bool == true) {
            try {

                //DB layer interaction
                Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "insert into app.pledge values(?,?,?,?,?)";
                PreparedStatement insert = d.prepareStatement(Statement);

                insert.setInt(1, id + 1);
                insert.setString(2, name);
                insert.setInt(3, Integer.parseInt(D_ID));
                insert.setString(4, pledge);
                insert.setInt(5, Integer.parseInt(amount));

                insert.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pledges Updated :) \n");

                return (id + 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    int removepledge(String jListValue) {
        if (jListValue == null) {
            JOptionPane.showMessageDialog(null, "YOU NEED TO SELECT Sr.No TO DELETE!!!!");
            return -1;
        } else {
            int a = Integer.parseInt(jListValue);
            try {

                //getting data from DB LAYER
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "delete from app.pledge where pid=" + a;
                PreparedStatement check = d.getPrepareStatement(Statement);
                d.Execute(check);

                JOptionPane.showMessageDialog(null, "SELECTED Pledge REMOVED :) \n");
                return 1;

            } catch (SQLException ex) {
                Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    void loadpledges(JList l1, JList l2, JList l3, JList l4, JList l5) {
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();

        try {
            // TODO add your handling code here:

            //getting data from DB LAYER
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
            String Statement = "select * from app.pledge order by pid asc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("pid"));
                model2.add(i, rs.getString("name"));
                model3.add(i, rs.getString("D_ID"));
                model4.add(i, rs.getString("statement"));
                model5.add(i, rs.getString("amount"));

                i++;
            }

            l1.setModel(model1);
            l2.setModel(model2);
            l3.setModel(model3);
            l4.setModel(model4);
            l5.setModel(model5);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
