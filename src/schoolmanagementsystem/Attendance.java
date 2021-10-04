/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author nedpals
 */
public class Attendance {
    public int id;
    public int studentId;
    public int sessionId;
    public boolean isPresent;
    public String reason;
    public Date updatedAt;
    
//    public Student getStudent() {
//        
//    }
    
//    public Session getSession() {
//        
//    }
    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("studentId", studentId);
        obj.put("sessionId", sessionId);
        obj.put("isPresent", isPresent);
        obj.put("reason", reason);
        obj.put("updatedAt", updatedAt);
        return obj;
    }
}
