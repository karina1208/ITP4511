/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Classss {

    private String classID;
    private String className;
    ArrayList<Schedule> schedules = new ArrayList<Schedule>();

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(int time, int day, int subjectID) {
        Schedule schedule = new Schedule();
        schedules.add(schedule);
    }

    public Classss() {

    }

    public Classss(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
