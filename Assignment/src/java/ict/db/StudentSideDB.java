/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import ict.bean.Classss;
import ict.bean.Schedule;
import ict.bean.Student;
import ict.bean.Subject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class StudentSideDB {
    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public StudentSideDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
    
    public ArrayList<Schedule> getSchedule(String classss) {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<Schedule> schedule = new ArrayList<Schedule>();
        Schedule s;
        try {

            //SELECT c.subjectID, c.day, c.time, s.subjectName
//FROM classschedule c
//INNER JOIN subject s ON c.subjectID = s.subjectID WHERE c.classID = "IT114105"
            cnnct = getConnection();
            String preQueryStatement = "SELECT c.subjectID, c.day, c.time, s.subjectName "
                    + "FROM classschedule c "
                    + "INNER JOIN subject s ON c.subjectID = s.subjectID WHERE c.classID = ?";

            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {

                s = new Schedule();
                s.setSubjectID(rs.getInt(1));
                s.setDay(rs.getInt(2));
                s.setTime(rs.getInt(3));
                s.setSubjectName(rs.getString(4));

                schedule.add(s);
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

        return schedule;
    }
    
    public ArrayList<Subject> getSubjectList() {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int count = 0;
        Subject s;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM subject";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                s = new Subject();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                
                subjects.add(s);
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

        return subjects;
    }
    
    public ArrayList<Student> getOneStudentAttendanceRate(String classss, String userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Student> students = new ArrayList<Student>();
        Student s;
        
        double attRate;

        try {

            cnnct = getConnection(); 
            String preQueryStatement = "SELECT DISTINCT a.userID, a.classID, u.userName "
                    + "FROM attendanceperday2 a " 
                    + "INNER JOIN User u ON a.userID = u.userID WHERE classID = ? AND a.userID = ? ";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classss);
            pStmnt.setString(2, userID);
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
