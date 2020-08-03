/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class UserDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean isValidUser(String user, String pwd) {
        boolean isValid = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE userID = ? and userPW = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public String checkUserRole(String user) {
        String role = "";

        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT userRole FROM user WHERE userID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                role = rs.getString(1);
            }
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return role;
    }

    public String checkUserClass(String user) {
        String classss = "";

        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT userClass FROM user WHERE userID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, user);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                classss = rs.getString(1);
            }
        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classss;
    }

//    public void CreateUserInfoTable() {
//        Statement stmnt = null;
//        Connection cnnct = null;
//
//        try {
//            cnnct = getConnection();
//            stmnt = cnnct.createStatement();
//            String sql
//                    = "CREATE TABLE IF NOT EXISTS userInfo ("
//                    + "Id varchar(5) NOT NULL, "
//                    + "username varchar(25) NOT NULL, "
//                    + "password varchar(25) NOT NULL, "
//                    + "PRIMARY KEY (Id)"
//                    + ")";
//            stmnt.execute(sql);
//            stmnt.close();
//            cnnct.close();
//        } catch (SQLException e) {
//            while (e != null) {
//                e.printStackTrace();
//                e = e.getNextException();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean addUserInfo(String id, String user, String pwd) {
//        Connection cnnct = null;
//        PreparedStatement pStmnt = null;
//        boolean isSuccess = false;
//
//        try {
//            cnnct = getConnection();
//            String preQueryStatement = "INSERT INTO USERINFO VALUES (?, ?, ?)";
//            pStmnt = cnnct.prepareStatement(preQueryStatement);
//            pStmnt.setString(1, "1");
//            pStmnt.setString(2, "abc");
//            pStmnt.setString(3, "123");
//            int rowCount = pStmnt.executeUpdate();
//            if (rowCount >= 1) {
//                isSuccess = true;
//            }
//            pStmnt.close();
//            cnnct.close();
//        } catch (SQLException e) {
//            while (e != null) {
//                e.printStackTrace();
//                e = e.getNextException();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return isSuccess;
//    }
    public boolean addRecord(String userID, String userPW, String userName, String userRole, String userClass) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();

            String preQueryStatement = "INSERT INTO user VALUES(?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userID);
            pStmnt.setString(2, userPW);
            pStmnt.setString(3, userName);
            pStmnt.setString(4, userRole);
            pStmnt.setString(5, userClass);

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(userID + " is added");
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

    public ArrayList<UserInfo> showClass() {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<UserInfo> Uinfo = new ArrayList<UserInfo>();

        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM user";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {
                Uinfo.add(new UserInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException e) {
            while (e != null) {
                e.printStackTrace();
                e = e.getNextException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uinfo;
    }

    public UserInfo queryInfoByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserInfo UI = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE userID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                UI = new UserInfo();
                UI.setUserID(rs.getString(1));
                UI.setPassword(rs.getString(2));
                UI.setUsername(rs.getString(3));
                UI.setUserRole(rs.getString(4));
                UI.setUserClass(rs.getString(5));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return UI;
    }

    public boolean editRecord(UserInfo userinfo) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE user SET userPW=?, userName=?, userRole=?,userClass=? WHERE userID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userinfo.getPassword());
            pStmnt.setString(2, userinfo.getUsername());
            pStmnt.setString(3, userinfo.getUserRole());
            pStmnt.setString(4, userinfo.getUserClass());
            pStmnt.setString(5, userinfo.getUserID());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("Updated");
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

    public boolean delRecord(String userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM user WHERE userID=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userID);

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("userID:" + userID + " Deleted");
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
