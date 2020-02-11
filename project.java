/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooad.proj;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class project {

    String ProjectName;
    String ProjectDescription;
    int[] V_ID;
    String LaunchingDate;
    String Status;      //complete or in process or not launched

    project() {
        ProjectName = null;
        ProjectDescription = null;
        V_ID = new int[1];
        Status = null;
    }

    void load(String u, JList l1, JList l2, JList l3, JList l4, JList l5) {
        //System.out.println(u);
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();
        int o_i = Integer.parseInt(u);
        //jList1 = new javax.swing.JList(model);
        try {
            // TODO add your handling code here:
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = con.prepareStatement("select * from app.project where o_id=" + o_i + " order by id asc");
            ResultSet rs = check.executeQuery();
            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("id"));
                model2.add(i, rs.getString("name"));
                model3.add(i, rs.getString("detail"));
                model4.add(i, rs.getString("status"));
                model5.add(i, rs.getString("launch"));
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

    void load_volunteers(JList l1, JList l2, JList l3) {
        //System.out.println(u);
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
//        int o_i = Integer.parseInt(u);
        //jList1 = new javax.swing.JList(model);
        try {
            // TODO add your handling code here:
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = con.prepareStatement("select * from app.volunteer order by userid asc");
            ResultSet rs = check.executeQuery();
            int i = 0;
            while (rs.next()) {
                model1.add(i, rs.getString("userid"));
                model2.add(i, rs.getString("name"));
                model3.add(i, rs.getString("task_list"));
                //System.out.println(i);
                i++;
            }
            l1.setModel(model1);
            l2.setModel(model2);
            l3.setModel(model3);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void load_proj(JList l1, JList l2, JList l3, JList l4, JList l5) {
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();
        //jList1 = new javax.swing.JList(model);
        try {
            // TODO add your handling code here:
            Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = d.prepareStatement("select * from app.project p join app.volunteer v on p.id=v.p_id");
            ResultSet rs = check.executeQuery();
            int i = 0;
            //System.out.println(rs.getString("id"));
            while (rs.next()) {
                //System.out.println("waleed");
                if (rs.getBoolean("status")) {
                    model1.add(i, rs.getString("id"));
                    model2.add(i, rs.getString("name"));
                    model3.add(i, rs.getString("detail"));
                    model4.add(i, rs.getString("userid"));
                    model5.add(i, rs.getString("budget"));
                    i++;
                }
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

    void remove_project(int id) {
        boolean bool = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = con.prepareStatement("select * from app.project where id=" + id);
            ResultSet rs = check.executeQuery();
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
                PreparedStatement check = con.prepareStatement("delete from app.project where id=" + id);
                check.execute();
                check = con.prepareStatement("insert into app.remove_project values(?)");
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

    boolean add_project(JTextField n, JTextField details, JTextField o_id) {
        int id = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = con.prepareStatement("select * from app.project order by id desc");
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n.getText().isEmpty() || details.getText().isEmpty() || o_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                int o_i = Integer.parseInt(o_id.getText());
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                PreparedStatement insert = con.prepareStatement("insert into app.project values(?,?,?,?,?,?,?,?)");
                //ResultSet rs = stat.executeQuery();

                java.util.Date utilDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(9999 - 12 - 31);

                //Date date = new Date();
                insert.setInt(1, id + 1);
                insert.setString(2, n.getText());
                insert.setString(3, details.getText());
                insert.setBoolean(4, false);
                insert.setDate(5, sqlDate);
                insert.setInt(6, o_i);
                insert.setInt(7, 0);
                insert.setInt(8, 0);
                insert.executeUpdate();
                JOptionPane.showMessageDialog(null, "New created project id is " + (id + 1) + "\nProjects update");
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
        }
        return false;
    }

    void assign(String userid,String p_i,String task) {
        try {

            int id = Integer.parseInt(userid);
            int p_id = Integer.parseInt(p_i);
            try {
                System.out.println(p_id);
                // TODO add your handling code here:
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                PreparedStatement check = con.prepareStatement("update app.volunteer set p_id =" + p_id + "  where userid=" + id);
                check.execute();
                check = con.prepareStatement("update app.volunteer set task_list = '" + task + "'  where userid=" + id);
                check.execute();
                //java.sql.Date sDate = convertUtilToSql(date1);
                JOptionPane.showMessageDialog(null, "Assigned... ");

            } catch (SQLException ex) {
                Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception : " + e);
        }
    }

    void launch(String userid) {
        try {

            int id = Integer.parseInt(userid);
            try {
                // TODO add your handling code here:
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                PreparedStatement check = con.prepareStatement("update app.project set status=" + true + "  where id=" + id);
                check.execute();
                String sDate1 = JOptionPane.showInputDialog("Please enter a valid date in this format YYYY-MM-DD...");
                //Date date1=(Date) new SimpleDateFormat("yyyy-mm-dd").parse(sDate1);  
                //java.sql.Date sDate = convertUtilToSql(date1);
                check = con.prepareStatement("update app.project set launch= CAST('" + sDate1 + "' AS DATE)  where id=" + id);
                check.execute();
                JOptionPane.showMessageDialog(null, "Launch... ");

            } catch (SQLException ex) {
                Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception : " + e);
        }
    }

    void load_project(JTextField id, JTextField name, JTextField detail, JTextField status, JTextField launch, JTextField o_id) {
        if (!id.getText().isEmpty()) {
            try {
                int i = Integer.parseInt(id.getText());
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                PreparedStatement check = con.prepareStatement("select * from app.project where id=" + i);
                ResultSet rs = check.executeQuery();
                while (rs.next()) {
                    name.setText(rs.getString("name"));
                    detail.setText(rs.getString("detail"));
                    status.setText(rs.getString("status"));
                    launch.setText(rs.getString("launch"));
                    o_id.setText(rs.getString("o_id"));
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid intput");
        }
    }

    void update_proj_name(JTextField id, JTextField name) {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("update app.project set name='" + name.getText() + "'  where id=" + i);
                    check.execute();
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    void update_proj_detail(JTextField id, JTextField detail) {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("update app.project set detail='" + detail.getText() + "'  where id=" + i);
                    check.execute();
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    void update_proj_status(JTextField id, JTextField status) {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("update app.project set status=" + status.getText() + "  where id=" + i);
                    check.execute();
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    boolean check_pro(int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
            PreparedStatement check = con.prepareStatement("select * from app.project where id=" + id);
            ResultSet rs = check.executeQuery();
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

    void update_proj_launch(JTextField id, JTextField launch) {
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                try {
                    // TODO add your handling code here:
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("update app.project set launch= CAST('" + launch.getText() + "' AS DATE) where id=" + i);
                    check.execute();
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    void update_proj_org_id(JTextField id, JTextField o_id) {
        Organization o = new Organization();
        if (o.check_org(Integer.parseInt(o_id.getText()))) {
        } else {
            JOptionPane.showMessageDialog(null, "Enter valid organization id.\n");
            return;
        }
        if (!id.getText().isEmpty()) {
            try {

                int i = Integer.parseInt(id.getText());
                int o_i = Integer.parseInt(o_id.getText());
                try {
                    // TODO add your handling code here:
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("update app.project set o_id=" + o_i + "  where id=" + i);
                    check.execute();
                    JOptionPane.showMessageDialog(null, "Update... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

}
