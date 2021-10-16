/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import mysql_database.Database;
import mysql_database.DBEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author nedpals
 */
public class Session extends DBEntity {
    public Date2 heldAt;
    public String subjectId;
    public String title;
    public String description;
    
    public Session(String subjectId, String title, String description) {
        this.heldAt = new Date2();
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
    }
    
    Session(long id, Date2 heldAt, String subjectId, String title, String description) {
        this.id = id;
        this.heldAt = heldAt;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
    }
    
    public static Session getById(long id) throws Exception {
        JSONArray data = Database.get("sessions").getBy("id = ?", new Object[] {id});
        if (data.isEmpty()) {
            throw new Exception("Session not found!");
        }
        
        return Session.fromJSON((JSONObject) data.get(0));
    }
    
    public Attendance getAttendanceFromStudent(long studentId) throws Exception {
        JSONArray data = Database.get("attendances").getBy("student_id = ? && session_id = ?", new Object[] {studentId, this.id});
        if (data.isEmpty()) {
            throw new Exception("Session not found!");
        }
        return Attendance.fromJSON((JSONObject) data.get(0));
    }
    
    public static Session fromJSON(JSONObject obj) throws Exception {
        long id = (long) obj.get("id");
        Date2 heldAt = Date2.fromJSON((String) obj.get("held_at"));
        String subjectID = (String) obj.get("subject_id");
        String title = (String) obj.get("title");
        String description = (String) obj.get("description");
        Session sess = new Session(id, heldAt, subjectID, title, description);
        return sess;
    }
    
    public Subject getSubject() throws Exception {
        return Subject.getById(subjectId);
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("held_at", heldAt.toJSON());
        obj.put("subject_id", subjectId);
        obj.put("title", title);
        obj.put("description", description);
        return obj;
    }

    @Override
    public String getTableName() {
        return "sessions";
    }

    @Override
    public void reload() throws Exception {
        Session newSess = Session.getById(id);
        this.description = newSess.description;
        this.heldAt = newSess.heldAt;
        this.subjectId = newSess.subjectId;
        this.title = newSess.title;
    }
    
    public void recordAttendance(Attendance att) throws Exception {
        att.save();
    }
}
