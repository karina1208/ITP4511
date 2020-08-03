/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.Classss;
import ict.bean.Student;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class StudentDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    private ArrayList<Classss> classss = null;

    public StudentDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Student> getStudentByClass(String classss) {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<Student> students = new ArrayList<Student>();
        Student s;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE userRole = 'S' AND userClass = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {

                s = new Student();
                s.setStudentId(rs.getString(1));
                s.setStudentName(rs.getString(3));
                s.setClassId(rs.getString(5));
                students.add(s);
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

        return students;
    }

    public String getStudentByClassColumnNo(String classss) {
        ArrayList<Student> students = new ArrayList<Student>();

        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int count = 0;
        String test = "nothing";
        Student s;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM user WHERE userRole = 'S' AND userClass = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                test += "hahagahaha";
                s = new Student();
                s.setStudentId(rs.getString(1));
                s.setStudentName(rs.getString(3));
                s.setClassId(rs.getString(5));
                students.add(s);
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

        return test;
    }

    public boolean addDayAttendRecord(String userId, String classId, int status, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO attendanceperday2 (userID,classID,dateAttend,status) VALUES (?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userId);
            pStmnt.setString(2, classId);
            pStmnt.setString(3, date);
            pStmnt.setInt(4, status);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
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

        return isSuccess;
    }

    public ArrayList<Student> getStudentRecordByClassAndDate(String classss, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        PreparedStatement pStmnt2 = null;

        ArrayList<Student> students = new ArrayList<Student>();
        Student s;

//        String sDate1="31/12/1998";  
//    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
//        LocalDate date2 = java.time.LocalDate.now();
//        String dateString = date2.toString();
        try {


            cnnct = getConnection(); 
            String preQueryStatement = "SELECT a.userID, a.classID, a.dateAttend, a.status, u.userName "
                    + "FROM attendanceperday2 a " 
                    + "INNER JOIN User u ON a.userID = u.userID WHERE classID = ? AND dateAttend = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            pStmnt.setString(2, date);
            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {

                s = new Student();
                s.setStudentId(rs.getString(1));
                s.setClassId(rs.getString(2));
                s.setDate(date);
                s.setStudentName(rs.getString(5));
                s.setStatus(Integer.parseInt(rs.getString(4)));
                students.add(s);

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

        return students;
    }

    public boolean editAttendanceByDay(String userId, String classId, int status, String date) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        //LocalDate date = java.time.LocalDate.now();
        //String dateString = date.toString();
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE attendanceperday2 SET status = ? WHERE userId = ? AND dateAttend = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setInt(1, status);
            pStmnt.setString(2, userId);
            pStmnt.setString(3, date);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
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

        return isSuccess;
    }
    
    public ArrayList<Student> getAllStudentAttendanceRate(String classss) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Student> students = new ArrayList<Student>();
        Student s;
        
        double attRate;

        try {

            cnnct = getConnection(); 
            String preQueryStatement = "SELECT DISTINCT a.userID, a.classID, u.userName "
                    + "FROM attendanceperday2 a " 
                    + "INNER JOIN User u ON a.userID = u.userID WHERE classID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {

                s = new Student();
                s.setStudentId(rs.getString(1));
                s.setClassId(rs.getString(2));
                s.setStudentName(rs.getString(3));
                
                attRate = (double) findStudentAttendedDay(rs.getString(1))/dayRecorded();
                attRate = Double.parseDouble(String.format("%.4f", attRate));
                
                s.setAttendanceRate(attRate);

                students.add(s);

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

        return students;
    }
    
     public ArrayList<Student> getAdminAllStudentAttendanceRate() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Student> students = new ArrayList<Student>();
        Student s;
        
        double attRate;

        try {

            cnnct = getConnection(); 
            String preQueryStatement = "SELECT DISTINCT a.userID, a.classID, u.userName "
                    + "FROM attendanceperday2 a " 
                    + "INNER JOIN User u ON a.userID = u.userID";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = pStmnt.executeQuery();

            while (rs.next()) {

                s = new Student();
                s.setStudentId(rs.getString(1));
                s.setClassId(rs.getString(2));
                s.setStudentName(rs.getString(3));
                
                attRate = (double) findStudentAttendedDay(rs.getString(1))/dayRecorded();
                attRate = Double.parseDouble(String.format("%.4f", attRate));
                
                s.setAttendanceRate(attRate);

                students.add(s);

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

        return students;
    }
    
    public int dayRecorded() {
        int day = 1;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        
        try {

            cnnct = getConnection(); 
            String preQueryStatement = "SELECT COUNT( DISTINCT dateAttend ) FROM attendanceperday2";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
            day = rs.getInt(1);
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


        return day;
    }
    
    public int findStudentAttendedDay(String userID) {
        
        int att = 1;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        
        try {

            cnnct = getConnection(); 
            String preQueryStatement = "SELECT COUNT(status) FROM attendanceperday2 WHERE userID = ? AND status = 1;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userID);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
            att = rs.getInt(1);
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


        return att;
        
        
       
    }
    
    
}
