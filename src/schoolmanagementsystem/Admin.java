/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author nedpals
 */
public class Admin {
    public int id;
    public String username;
    private String password;
    private int arrayIndex = -1;
    
    Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public static Admin fromJSON(JSONObject obj) {
        return Admin.fromJSON(obj, -1);
    }
    
    public static Admin fromJSON(JSONObject obj, int arrayIndex) {
        int adminID = (int) (long) obj.get("id");
        String adminUsername = (String) obj.get("username");
        String adminPassword = (String) obj.get("password");
        Admin adm = new Admin(adminID, adminUsername, adminPassword);
        adm.setArrayIndex(arrayIndex);
        return adm;
    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("password", this.password);
        return obj;
    }
    
    public static Admin[] getAll() {
        try {
           JSONArray data = Database.get("admins").all();
           Admin[] admins = new Admin[data.size()];
           
           for (int i = 0; i < data.size(); i++) {
               JSONObject obj = (JSONObject) data.get(i);
               Admin admin = Admin.fromJSON(obj, i);
               admins[i] = admin;
           }
           
           return admins;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    public void setArrayIndex(int newIndex) {
        this.arrayIndex = newIndex;
    }
    
    public void save() throws Exception {
        Table adminTable = Database.get("admins");
        
        if (this.arrayIndex == -1) {
            adminTable.insert(this.toJSON());
        } else {
            adminTable.update(this.arrayIndex, this.toJSON());
        }
    }
}
