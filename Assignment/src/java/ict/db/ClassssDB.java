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
public class ClassssDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public ClassssDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");

        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean insertEmptySchedule(String classId, int day, int time) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO classschedule (classID,day,time) VALUES (?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, classId);
            pStmnt.setInt(2, day);
            pStmnt.setInt(3, time);
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

    public boolean editSchedule(String classssID, int subjectID, int day, int time) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        //LocalDate date = java.time.LocalDate.now();
        //String dateString = date.toString();
        
        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE classschedule SET subjectID = ? WHERE classID = ? AND day = ? AND time = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, subjectID);
            pStmnt.setString(2, classssID);
            pStmnt.setInt(3, day);
            pStmnt.setInt(4, time);
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
}
