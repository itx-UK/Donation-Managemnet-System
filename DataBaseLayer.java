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

/**
 *
 * @author uk
 */
public class DataBaseLayer {
    
    String address;
    String username;
    String password;
    
    
    DataBaseLayer(String add,String un,String pass) throws SQLException{
        address = add;
        username =un;
        password = pass;
        
    }

    public DataBaseLayer(String jdbcderbylocalhost1527ooad, String ooad, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public PreparedStatement getPrepareStatement(String statement) throws SQLException{
        Connection con = DriverManager.getConnection(address,username,password);
        PreparedStatement check = con.prepareStatement(statement);
        return check;
    }
    public ResultSet ExecuteQuery(PreparedStatement check) throws SQLException{
       
        ResultSet rs = check.executeQuery();
        return rs;
    }
     public void Execute(PreparedStatement check) throws SQLException{
         check.execute();
    }
    
     public int ExecuteUpdate(PreparedStatement check) throws SQLException{
        return check.executeUpdate();
    }
    
   
    DataBaseLayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
           
}
