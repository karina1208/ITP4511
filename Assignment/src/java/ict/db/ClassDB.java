/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.Classss;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author yusin
 */
public class ClassDB {
    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";
    
  
    
    public ClassDB(String dburl, String dbUser, String dbPassword){
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
     public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
    
     public ArrayList<Classss> showClass(){   
          
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        
        ArrayList<Classss> Class = new ArrayList<Classss>();  
        Class c;
       
        
         try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM class";
            pStmnt = cnnct.prepareStatement(preQueryStatement);               
            
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                Class.add(new Classss(rs.getString(1), rs.getString(2)));
            }
            pStmnt.close();
            cnnct.close();
           
         }catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         return Class;
     }
     
     public boolean addRecord(String classID, String className ,int schoolday) {
        Connection cnnct = null;       
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
       
        try {
            cnnct = getConnection();
            
            String preQueryStatement = "INSERT INTO class VALUES(?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classID);
            pStmnt.setString(2, className);
            pStmnt.setInt(3, schoolday);                    

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(classID + " is added");
            }

            pStmnt.close();
            cnnct.close();
            
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
}
