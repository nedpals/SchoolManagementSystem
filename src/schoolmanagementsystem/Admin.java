/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author nedpals
 */
public class Admin extends User {
    public int id;
    
    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    public static Admin fromJSON(JSONObject obj) {
        return Admin.fromJSON(obj, -1);
    }
    
    public static Admin fromJSON (JSONObject obj, int arrayIndex) {
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
    
    public void save() throws Exception {
        Table adminTable = Database.get("admins");
        
        if (this.arrayIndex == -1) {
            adminTable.insert(this);
        } else {
            adminTable.update(this.arrayIndex, this);
        }
    }

    
    public static Admin getByUsername(String username) throws Exception {
        Iterator sessionsIt = Database.get("admins").all().iterator();
        
        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("username")) {
                String adminUser = (String) data.get("username");
                if (!adminUser.equals(username)){
                    continue;
                }
                return Admin.fromJSON(data);
            }
        }
        
        throw new Exception("Admin not found!");
    }
    
    
    
    public static Admin login(String username, String password) throws Exception {
         Admin foundAdmin = Admin.getByUsername(username);
         if (foundAdmin.password.equals(password)){
             return foundAdmin;
         } else {
             throw new Exception("Password inputted is incorrect");
                     
         }
    }
    
    @Override
    public void logout() {
       this.logout();
    }
    /*    
    @Override
    public void login(String username, String password) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
>>>>>>> 5896a80fbb679303fe4a5663fef61b8f68e8dfb2
    }
*/
 }

