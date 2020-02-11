package ooad.proj;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ooad.proj.DataBaseLayer;
import static ooad.proj.UpdateDProfile.u;
import ooad.proj.person;


public class donor extends person {
    int D_ID;
    String creditCardNo;
    int amount_donated;
    int pin;        //must be between 999 to 9999
    String email;
    int membershipCheck;
    int phonenum;
    
    donor()
    {
       super();
       D_ID = -1;
       creditCardNo = null;
       amount_donated = 0;
       pin = -1;
       email = null;
       membershipCheck = 0; //not member by default
       phonenum=-1;
    }
    
    
    
    
    void Login(String userid , String password){
         
        if(userid.isEmpty() ||  password.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        }
        else
        {
            boolean bool=false;

            try{
                
                //getting data from database layer
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                String Statement = "select * from app.donor";
                PreparedStatement check = d.getPrepareStatement(Statement);
                ResultSet rs = d.ExecuteQuery(check);

                
               
                while(rs.next())
                {
                    if(rs.getString("userid").equals(userid) && rs.getString("password").equals(password))
                    {
                        bool=true;
                        //System.out.println(rs.getString("userid"));
                        DonorScreen a  = new DonorScreen();
                        a.u=userid;
                        a.n=rs.getString("name");
                        a.main(null);
                        break;
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            if(!bool)
            {
                JOptionPane.showMessageDialog(null,"Oops....\nUsername or Password not matches.\nPlease write correct username and password. ");
            }
        }
    }
    
    void Signup(String phonenum,String address,String creditcardnum, String pin,String userid, String name, String password,String email){
        int id = 0;
        try {
            
            //getting data from database layer
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.donor order by userid desc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);

            
            
            if(rs.next())
            {
                id = rs.getInt("userid");
            }
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = true;
        boolean check1 = true;
        boolean check2 = true;
        if (phonenum.isEmpty()) {
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

        if (check1 == true && bool == true && creditcardnum.isEmpty()) {
            check2 = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else if (check1 == true && bool == true) {
            try {
                Double.parseDouble(creditcardnum);
            } catch (Exception e) {
                check2 = false;
                JOptionPane.showMessageDialog(null, "Check credit card number again.\n It will be accepted in numeric only. ");
            }
        }

        if (check1 == true && check2 == true && bool == true && pin.isEmpty()) {
            check2 = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else if (check1 == true && check2 == true && bool == true) {
            try {
                int p = Integer.parseInt(pin);
                if (p > 9999 || p < 1000) {
                    check2 = false;
                    JOptionPane.showMessageDialog(null, "Check pin number again.\n It will be b/w 10000 and 999 ");
                }
            } catch (Exception e) {
                check2 = false;
                JOptionPane.showMessageDialog(null, "Check pin number again.\n It will be accepted in numeric only. ");
            }
        }
        if (check1 == true && check2 == true && bool == true) {
            if (userid.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty() || phonenum.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
            } else {
                try {
                    
                      //getting data from database layer
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "insert into app.donor values(?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement insert = d.getPrepareStatement(Statement);
                    

                    
                
                    insert.setString(1, name);
                    insert.setString(2, password);
                    insert.setString(3, email);
                    insert.setDouble(4, Double.parseDouble(creditcardnum));
                    insert.setDouble(5, Double.parseDouble(phonenum));
                    insert.setInt(6, id + 1);
                    insert.setBoolean(7, false);
                    insert.setString(8, pin);
                    insert.setString(9, "no message");
                    insert.setString(10, null);
                    insert.setString(11, null);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Your user-id is " + (id + 1) + "\nYour request is send to admin for approval.\n Please wait..... ");
                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    
    
    int addDonor(String phnNbr,String ccn,String n,String pin,String email,String pass,String adrs,String don,String message){
        
        int id = 0;
        try {
            //getting data from database layer
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.donor order by userid desc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);
            
            
            if(rs.next())
            {
                id = rs.getInt("userid");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = true;
        boolean check1 = true;
        boolean check2 = true;
        if (phnNbr== null) {
            bool = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                Double.parseDouble(phnNbr);
            } catch (Exception e) {
                bool = false;
                JOptionPane.showMessageDialog(null, "Check phone number again.\n It will be accepted in numeric only. ");
            }
        }

        if (check1 == true && bool == true && ccn==null) {
            check2 = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else if (check1 == true && bool == true) {
            try {
                Double.parseDouble(ccn);
            } catch (Exception e) {
                check2 = false;
                JOptionPane.showMessageDialog(null, "Check credit card number again.\n It will be accepted in numeric only. ");
            }
        }

        if (check1 == true && check2 == true && bool == true && pin==null) {
            check2 = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else if (check1 == true && check2 == true && bool == true) {
            try {
                int p = Integer.parseInt(pin);
                if (p > 9999 || p < 1000) {
                    check2 = false;
                    JOptionPane.showMessageDialog(null, "Check pin number again.\n It will be b/w 10000 and 999 ");
                }
            } catch (Exception e) {
                check2 = false;
                JOptionPane.showMessageDialog(null, "Check pin number again.\n It will be accepted in numeric only. ");
            }
        }
        if (check1 == true && check2 == true && bool == true) {
            if ( pass==null || n==null || email==null || adrs==null || phnNbr==null) {
                JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
                 //-1 hogi
            } else {
                try {
                    
                    
                    //DB layer interaction
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "insert into app.donor values(?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement insert = d.getPrepareStatement(Statement);
                    
                    
                    insert.setString(1, n);
                    insert.setString(2, pass);
                    insert.setString(3, email);
                    insert.setDouble(4, Double.parseDouble(ccn));
                    insert.setDouble(5, Double.parseDouble(phnNbr));
                    insert.setInt(6, id + 1);
                    insert.setBoolean(7, true);
                    insert.setString(8, pin);
                    insert.setString(9, message);
                    insert.setString(10, don);
                    insert.setString(11, null);
                    insert.executeUpdate();
                    return (id+1);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
    
    
    int remove_donor(String jListValue){
        if (jListValue==null){
            JOptionPane.showMessageDialog(null,"YOU NEED TO SELECT Sr.No TO DELETE!!!!");
            return -1;
        }
        else{
        int a=Integer.parseInt(jListValue);
        try {
            //databaselayer
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "delete from app.donor where userid="+a;
            PreparedStatement check = d.getPrepareStatement(Statement);
            
            check.execute();
            JOptionPane.showMessageDialog(null,"SELECTED DONOR REMOVED :) \n");
            return 1;
         
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return -1;
    }
    
    
    void view_donors(JList l1,JList l2,JList l3,JList l4,JList l5,JList l6,JList l7){
        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();
        DefaultListModel<String> model5 = new DefaultListModel<>();
        DefaultListModel<String> model6 = new DefaultListModel<>();
        DefaultListModel<String> model7 = new DefaultListModel<>();

        try {
            // TODO add your handling code here:
            
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.donor order by userid asc";
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);
            
            int i = 0;
            while (rs.next()) 
            {
                model1.add(i, rs.getString("userid"));
                model2.add(i, rs.getString("name"));
                model3.add(i,rs.getString("email"));
                model4.add(i,rs.getString("creditcard"));
                model5.add(i,rs.getString("donated"));
                model6.add(i,rs.getString("phonenum"));
                model7.add(i,rs.getString("msg"));
                
                i++;
            }
            
            l1.setModel(model1);
            l2.setModel(model2);
            l3.setModel(model3);
            l4.setModel(model6);
            l5.setModel(model4);
            l6.setModel(model5);
            l7.setModel(model7);
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void load_donor(String userid,JTextField name,JTextField password,JTextField creditcardnum,JTextField email,JTextField pin,JTextField phonenum,JTextField donated ){
        
        if (userid!=null) {
            try {
                int i = Integer.parseInt(userid);
                try {
                    // TODO add your handling code here:
                    int id = Integer.parseInt(userid);
                    
                    //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "select * from app.donor order by userid asc";
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    ResultSet rs = d.ExecuteQuery(check);

                    while (rs.next()) {
                        name.setText(rs.getString("name"));
                        password.setText(rs.getString("password"));
                        creditcardnum.setText(rs.getString("creditcard"));
                        email.setText(rs.getString("email"));
                        pin.setText(rs.getString("pin"));
                        phonenum.setText(rs.getString("phonenum"));
                        donated.setText(rs.getString("donated"));
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
    
    
    boolean check_donor(int id)
    {
        try {
            
            
            //getting data from DB
            DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.donor where userid=" + id;
            PreparedStatement check = d.getPrepareStatement(Statement);
            ResultSet rs = d.ExecuteQuery(check);


            if (rs.next()) {
                if (id == (rs.getInt("userid"))) {
                    return true;
                }
            }
            //id=rs.getInt("userid");
            //System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }    
        JOptionPane.showMessageDialog(null, "Donor not present in database. check donor id...");
            
        return false;
    }
    
    void updateDonorName(String userid,String name){
        
         if (!userid.isEmpty()) {
            try {

                int id = Integer.parseInt(userid);
                try {
                    
                    //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "update app.donor set name='" + name + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.ExecuteUpdate(check);

                    JOptionPane.showMessageDialog(null, "Updated!!! ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }

    void updateDonorEmail(String userid,String email){
        if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                try {
                    
                     //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement =  "update app.donor set email='" + email + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.ExecuteUpdate(check);
                    JOptionPane.showMessageDialog(null, "Updated!!!");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    
    void updateDonorPhn(String userid,String phonenum){
        if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                double phone=Integer.parseInt(phonenum);
                try {
                     //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement =  "update app.donor set phonenum=" + phone + "  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                     d.ExecuteUpdate(check);
                    
                    
                    JOptionPane.showMessageDialog(null, "Updated!!! ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    
    void updateDonorCriditCard(String userid,String creditcardnum){
          if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                double credit=Integer.parseInt(creditcardnum);
                try {
                   
                      //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement =  "update app.donor set creditcard=" + credit + "  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                     d.ExecuteUpdate(check);
                    
                    JOptionPane.showMessageDialog(null, "Updated!!! ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    
    void updateDonorPass(String userid,String password){
           if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                try {
                    
                    //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "update app.donor set password='" + password + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                     d.ExecuteUpdate(check);
                    JOptionPane.showMessageDialog(null, "Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
     
    void updateDonorAmount(String userid,String donated){
        if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                try {
                    //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "update app.donor set donated='" + donated + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                   d.ExecuteUpdate(check);
                  
                    JOptionPane.showMessageDialog(null, "Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    void donor_donated(int id,int amount) {
        donor don = new donor();
        if (!don.check_donor(id)) {
            return;
        } else {
            try {
                String str=new String();
                int q=0;
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad", "ooad", null);
                String Statement = "select * from app.donor where userid="+id;
                PreparedStatement insert = d.getPrepareStatement(Statement);
                ResultSet rs = d.ExecuteQuery(insert);
                while(rs.next())
                {
                    str=rs.getString("donated");
                }
                if(!str.isEmpty())
                {
                    q=Integer.parseInt(str)+amount;
                }
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
    void updateDonorPin(String userid,String pin){
         if (userid!=null) {
            try {

                int id = Integer.parseInt(userid);
                int p=Integer.parseInt(pin);
                try {
                    // TODO add your handling code here:
                    
                      //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "update app.donor set pin='" + p + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                     d.ExecuteUpdate(check);
                    
                    JOptionPane.showMessageDialog(null, "Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
    }
    
    
     
     int addPledge(String pledge,String d_id,String name,String amount){
        
        int id = 0;
        try {
            //getting data from database layer
            Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.pledge order by pid desc";
            PreparedStatement check = d.prepareStatement(Statement);
            ResultSet rs = check.executeQuery();
            
            
            if(rs.next())
            {
                id = rs.getInt("pid");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean bool = true;
        if (amount== null) {
            bool = false;
            JOptionPane.showMessageDialog(null, "ALL FIELDS ARE REQUIRED!!! YOU MISSED SOME :( ");
        } else {
            try {
                Double.parseDouble(amount);
            } catch (Exception e) {
                bool = false;
                JOptionPane.showMessageDialog(null, "Check amount again.\n It will be accepted in numeric only. ");
            }
        }

        if(pledge.isEmpty())
        {
            pledge = "No statement...";
        }
        
      
        if (bool == true) {
                try {
                    int d_i=Integer.parseInt(d_id);
                    //DB layer interaction
                    Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "insert into app.pledge values(?,?,?,?,?)";
                    PreparedStatement insert = d.prepareStatement(Statement);
                    
                    insert.setInt(1, id + 1);
                    insert.setString(2, name);
                    insert.setInt(3, d_i);
                    insert.setString(4, pledge);
                    insert.setDouble(5, Double.parseDouble(amount));
                    
                    insert.executeUpdate();
                    return (id+1);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return -1;
    }
     int removepledge(String jListValue){
        if (jListValue==null){
            JOptionPane.showMessageDialog(null,"YOU NEED TO SELECT Sr.No TO DELETE!!!!");
            return -1;
        }
        else{
        int a=Integer.parseInt(jListValue);
        try {
            //databaselayer
            Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "delete from app.pledge where userid="+a;
            PreparedStatement check = d.prepareStatement(Statement);
            
            check.execute();
            JOptionPane.showMessageDialog(null,"SELECTED Pledge REMOVED :) \n");
            return 1;
         
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return -1;
    }
     void loadpledges(JList l1,String d_i){
        DefaultListModel<String> model1 = new DefaultListModel<>();
       

        try {
            int d_id=Integer.parseInt(d_i);
            // TODO add your handling code here:
            
            Connection d = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad","ooad",null);
            String Statement = "select * from app.pledge where d_id="+d_id ;
            PreparedStatement check = d.prepareStatement(Statement);
            ResultSet rs = check.executeQuery();
            
            int i = 0;
            while (rs.next()) 
            {
                model1.add(i, rs.getString("statement"));
                
                i++;
            }
            
            l1.setModel(model1);
            
        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
        
     void verifyDonorMembership(){
        try {
              

        //getting data from DB
           DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
           String Statement = "update app.donor set register=true where register=false";
           PreparedStatement check = d.getPrepareStatement(Statement);
           d.Execute(check);

        } catch (SQLException ex) {
            Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     void verifyDonorMembershipSelected(JList jList2){
          // TODO add your handling code here:
        if(jList2.getSelectedValue()!=null)
        {
            String str=jList2.getSelectedValue().toString();
            int a=Integer.parseInt(str);
            //System.out.println(a);
            try {
                // TODO add your handling code here:
                
                  //getting data from DB
                DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                String Statement = "update app.donor set register=true where userid="+a;
                PreparedStatement check = d.getPrepareStatement(Statement);
                d.Execute(check);

                
         

            } catch (SQLException ex) {
                Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
     
    String checkEmails(String userid) throws SQLException{
        
         //getting data from DB LAYER
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement = "select * from app.donor order by userid asc";
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    ResultSet rs = d.ExecuteQuery(check);
                    rs.next();
                    return rs.getString("msg");
                   
    }
     
   
   
    void loadMydata(String userid,JTextField name,JTextField password,JTextField creditcardnum,JTextField email,JTextField pin,JTextField phonenum){
         
        if (!userid.isEmpty()) {
            try {
                int i = Integer.parseInt(userid);
                try {
                    // TODO add your handling code here:
                    int id = Integer.parseInt(userid);
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ooad", "ooad", null);
                    PreparedStatement check = con.prepareStatement("select * from app.donor where userid=" + i);
                    ResultSet rs = check.executeQuery();
                    while (rs.next()) {
                        name.setText(rs.getString("name"));
                        password.setText(rs.getString("password"));
                        creditcardnum.setText(rs.getString("creditcard"));
                        email.setText(rs.getString("email"));
                        pin.setText(rs.getString("pin"));
                        phonenum.setText(rs.getString("phonenum"));
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
   
    void UpdateAll(String userid,JTextField name,JTextField email,JTextField password,JTextField creditcardnum,JTextField pin){
          if (!userid.isEmpty()) {
            try {

                int id = Integer.parseInt(u);
                try {
                    //getting data from DB
                    DataBaseLayer d = new DataBaseLayer("jdbc:derby://localhost:1527/ooad","ooad",null);
                    String Statement ="update app.donor set name='" + name.getText() + "'  where userid=" + id;
                    PreparedStatement check = d.getPrepareStatement(Statement);
                    d.ExecuteUpdate(check);

                    check = d.getPrepareStatement("update app.donor set email='" + email.getText() + "'  where userid=" + id);
                    d.ExecuteUpdate(check);

                    double credit=Integer.parseInt(creditcardnum.getText());
                    check = d.getPrepareStatement("update app.donor set creditcard=" + credit + "  where userid=" + id);
                    d.ExecuteUpdate(check);

                    check = d.getPrepareStatement( "update app.donor set password='" + password.getText() + "'  where userid=" + id);
                    d.ExecuteUpdate(check);

                    int p=Integer.parseInt(pin.getText());
                    check = d.getPrepareStatement("update app.donor set pin=" +p+ "  where userid=" + id);
                    d.ExecuteUpdate(check);


                    JOptionPane.showMessageDialog(null, "All are Updated... ");

                } catch (SQLException ex) {
                    Logger.getLogger(DonorMVerifications.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception : " + e);
            }
        }
     }
     
}

