/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.Database;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author nedpals
 */
public class Admin extends User {
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static Admin fromJSON(JSONObject obj) {
        long adminID = (long) obj.get("id");
        String adminUsername = (String) obj.get("username");
        String adminPassword = (String) obj.get("password");
        Admin adm = new Admin(adminID, adminUsername, adminPassword);
        return adm;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("password", this.password);
        return obj;
    }

    public static Admin[] getAll() throws Exception {
        JSONArray data = Database.get("admins").all();
        Admin[] admins = new Admin[data.size()];

        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            Admin admin = Admin.fromJSON(obj);
            admins[i] = admin;
        }

        return admins;
    }

    public static Admin getByUsername(String username) throws Exception {
        Iterator sessionsIt = Database.get("admins").all().iterator();

        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("username") && ((String) data.get("username")).equals(username)) {
                return Admin.fromJSON(data);
            }
        }

        throw new Exception("Admin not found!");
    }

    public static Admin login(String username, String password) throws Exception {
        Admin foundAdmin = Admin.getByUsername(username);
        if (foundAdmin.password.equals(password)) {
            return foundAdmin;
        } else {
            throw new Exception("Password inputted is incorrect");
        }
    }

    public void logout() {
        this.logout();
    }

    @Override
    public String getTableName() {
        return "admins";
    }

    @Override
    public void reload() throws Exception {}
}
