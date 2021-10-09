/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.DBEntity;
import org.json.simple.JSONObject;

/**
 *
 * @author nedpals
 */
public class Attendance extends DBEntity {
    public long studentId;
    public long sessionId;
    public boolean isPresent;
    public String reason;
    public Date2 updatedAt;
    
    Attendance(long studentId, long sessionId, boolean isPresent, String reason) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
        this.reason = reason;
        this.updatedAt = new Date2();
    }
    
    Attendance(long id, long studentId, long sessionId, boolean isPresent, String reason, Date2 updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
        this.reason = reason;
        this.updatedAt = updatedAt;
    }
    
    public void getStudent() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    
    public Session getSession() throws Exception {
        return Session.getById(this.sessionId);
    }
    
    public static Attendance fromJSON(JSONObject obj) throws Exception {
        long id = (long) obj.get("id");
        long studentId = (long) obj.get("studentId");
        long sessionId = (long) obj.get("sessionId");
        boolean isPresent = (boolean) obj.get("isPresent");
        String reason = (String) obj.get("reason");
        Date2 updatedAt = Date2.fromJSON((String) obj.get("updatedAt"));
        
        Attendance att = new Attendance(id, studentId, sessionId, isPresent, reason, updatedAt);
        return att;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("studentId", studentId);
        obj.put("sessionId", sessionId);
        obj.put("isPresent", isPresent);
        obj.put("reason", reason);
        obj.put("updatedAt", updatedAt.toJSON());
        return obj;
    }

    @Override
    public String getTableName() {
        return "attendances";
    }

    @Override
    public void reload() throws Exception {}
}
