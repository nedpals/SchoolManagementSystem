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
    public Date2 updatedAt;
    private int arrayIndex = -1;
    
    Attendance(int id, int studentId, int sessionId, boolean isPresent, String reason, Date2 updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
        this.reason = reason;
        this.updatedAt = updatedAt;
    }
    
//    public Student getStudent() {
//        
//    }
    
    public Session getSession() throws Exception {
        return Session.getById(this.sessionId);
    }
    
    public static Session fromJSON(JSONObject obj) throws Exception {
        return Session.fromJSON(obj, -1);
    }
    
    public static Attendance fromJSON(JSONObject obj, int arrayIndex) throws Exception {
        int id = (int) (long) obj.get("id");
        int studentId = (int) (long) obj.get("studentId");
        int sessionId = (int) (long) obj.get("sessionId");
        boolean isPresent = (boolean) obj.get("isPresent");
        String reason = (String) obj.get("reason");
        Date2 updatedAt = Date2.fromJSON((String) obj.get("updatedAt"));
        
        Attendance att = new Attendance(id, studentId, sessionId, isPresent, reason, updatedAt);
        att.setArrayIndex(arrayIndex);
        return att;
    }
    
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
    
    public void setArrayIndex(int newIndex) {
        this.arrayIndex = newIndex;
    }
    
    public void save() throws Exception {
        Table table = Database.get("attendances");
        
        if (this.arrayIndex == -1) {
            table.insert(this.toJSON());
        } else {
            table.update(this.arrayIndex, this.toJSON());
        }
    }
}
