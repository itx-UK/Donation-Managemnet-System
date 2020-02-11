/*d
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
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author uk
 */
public class Organization {
    String name;
    String RegistrationNo;
    int AdminAssingnedID;
    DataBaseLayer d;
    SuperAdmin sa;
    Organization() {
    }

    void view_organization(JList l1, JList l2, JList l3, JList l4, JList l5) {
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();

        //jList1 = new javax.swing.JList(model);
        try {
            // TODO add your handling code here:
            
              //getting data from DB using DAta Layer
             DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
             String Statement ="select * from app.organization order by id asc";
             PreparedStatement check = d.getPrepareStatement(Statement);
             ResultSet rs =  d.ExecuteQuery(check);

            
            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("id"));
                model2.add(i, rs.getString("organization_name"));
                model3.add(i, rs.getString("registration_num"));
                model4.add(i, rs.getString("address"));
                model5.add(i, rs.getString("admin_assign"));
                //System.out.println(i);
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
    boolean check_org(int id)
    {
        try {
            
               //getting data from DB using DAta Layer
             DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
             String Statement ="select * from app.organization where id=" + id;
             PreparedStatement check = d.getPrepareStatement(Statement);
             ResultSet rs =  d.ExecuteQuery(check);

            
            if (rs.next()) {
                if (id == (rs.getInt("id"))) {
                    return true;
                }
            }
            //id=rs.getInt("userid");
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    void remove_organization(int id) {
        boolean bool = false;
        try {
            
            //getting data from DB using DAta Layer
             DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
             String Statement ="select * from app.organization where id=" + id;
             PreparedStatement check = d.getPrepareStatement(Statement);
             ResultSet rs =  d.ExecuteQuery(check);

            if (rs.next()) {
                if (id == (rs.getInt("id"))) {
                    bool = true;
                }
            }
            //id=rs.getInt("userid");
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bool == true) {
            try {
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                PreparedStatement check = con.prepareStatement("delete from app.organization where id=" + id);
                check.execute();
                check = con.prepareStatement("insert into app.remove_organization values(?)");
                check.setInt(1, id);
                check.execute();
                JOptionPane.showMessageDialog(null, "SUCCESSFULLY DELETED\n");

                //id=rs.getInt("userid");
                //System.out.println(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    boolean add_organization(JLabel i, JTextField n, JTextField reg_num, JTextField add, JTextField adm_assign) {
        int id = 0;
        try {
            
            
             //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement ="select * from app.organization order by id desc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs =  d.ExecuteQuery(check);
            
          
            
            rs.next();
            id = rs.getInt("id");
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i.getText().isEmpty() || n.getText().isEmpty() || reg_num.getText().isEmpty() || add.getText().isEmpty() || adm_assign.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                int a=Integer.parseInt(adm_assign.getText());
                
                //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                String Statement ="insert into app.organization values(?,?,?,?,?)";
                PreparedStatement insert = d.getPrepareStatement(Statement);

                insert.setInt(1, id + 1);
                insert.setString(2, n.getText());
                insert.setString(3, reg_num.getText());
                insert.setString(4, (add.getText()));
                insert.setInt(5, Integer.parseInt(adm_assign.getText()));
                d.ExecuteUpdate(insert);
                JOptionPane.showMessageDialog(null, "New created organization id is " + (id + 1) + "\nOrganizations update");
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return false;
    }

    void load_organization(JTextField id, JTextField name, JTextField reg_num, JTextField addr, JTextField adm_assign) {
        if (!id.getText().isEmpty()) {
            try {
                int i = Integer.parseInt(id.getText());
                
                 //getting data from DB using DAta Layer
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                String Statement ="select * from app.organization where id=" + i;
                PreparedStatement check = d.getPrepareStatement(Statement);
                ResultSet rs =  d.ExecuteQuery(check);

                
            
                while (rs.next()) {
                    name.setText(rs.getString("organization_name"));
                    reg_num.setText(rs.getString("registration_num"));
                    addr.setText(rs.getString("address"));
                    adm_assign.setText(rs.getString("admin_assign"));
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid intput");
        }
    }
    void update_org_name(JTextField id,JTextField name)
    {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    
                        //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement ="update app.organization set organization_name='" + name.getText() + "'  where id=" + i;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.Execute(check);

                    JOptionPane.showMessageDialog(null, "Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    void update_org_reg_num(JTextField id,JTextField reg_num)
    {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    
                         //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement ="update app.organization set registration_num='" + reg_num.getText() + "'  where id=" + i;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.Execute(check);
                    
                  
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    void update_org_addr(JTextField id,JTextField add)
    {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    
                         //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement ="update app.organization set address='" + add.getText() + "'  where id=" + i;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.Execute(check);
                 
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    void update_org_adm_assign(JTextField id,JTextField adm_assign)
    {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                int a=Integer.parseInt(adm_assign.getText());
                try {
                     
                    //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement ="update app.organization set admin_assign=" + a + "  where id=" + i;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.Execute(check);
                    
                  
                    JOptionPane.showMessageDialog(null, "Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
}
