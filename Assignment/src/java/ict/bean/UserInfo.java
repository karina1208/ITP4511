/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class UserInfo implements Serializable {

    private String username;
    private String password;
    private String userRole;
    private String userRoleFull;
    private String userID;

    public String getUserRoleFull() {
        return userRoleFull;
    }

    public void setUserRoleFull() {
        switch (userRole) {
            case "I":
                userRoleFull = "adminstrator";
                break;
            case "T":
                userRoleFull = "teacher";
                break;
            case "S":
                userRoleFull = "student";
                break;
        }
    }
    private String userClass;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        setUserRoleFull();
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public UserInfo(String userID, String password, String username, String userRole, String userClass) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.userID = userID;
        this.userClass = userClass;
    }

    public UserInfo() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
