/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import json_database.Database;
import mysql_database.DBEntity;

/**
 *
 * @author nedpals
 */
public abstract class User extends DBEntity {
    public String username;
    protected String password;
    
    public Object getId() {
        return this.id;
    }
    
//    public abstract void login(String username, String password) throws Exception;
//    public abstract void logout();
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public String getPassword() {
        return this.password;
    }
}
