/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.Database;
import database.Table;
import database.DBEntity;
import java.util.Iterator;
import java.util.Date;
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
    public long[] attendanceIds;
    
    public Session(String subjectId, String title, String description) {
        this.heldAt = new Date2();
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.attendanceIds = new long[0];
    }
    
    Session(long id, Date2 heldAt, String subjectId, String title, String description, long[] attendanceIds) {
        this.id = id;
        this.heldAt = heldAt;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.attendanceIds = attendanceIds;
    }
    
    public static Session getById(long id) throws Exception {
        Iterator sessionsIt = Database.get("sessions").all().iterator();
        
        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("id") && ((long) data.get("id")) == id) {
                return Session.fromJSON(data);
            }
        }
        
        throw new Exception("Session not found!");
    }
    
    public static Session fromJSON(JSONObject obj) throws Exception {
        long id = (long) obj.get("id");
        Date2 heldAt = Date2.fromJSON((String) obj.get("heldAt"));
        String subjectID = (String) obj.get("subjectId");
        String title = (String) obj.get("title");
        String description = (String) obj.get("description");
        JSONArray rawAttendanceIds = (JSONArray) obj.get("attendanceIds");
        
        long[] attendanceIds = new long[rawAttendanceIds.size()];
        for (int i = 0; i < attendanceIds.length; i++) {
            attendanceIds[i] = (long) rawAttendanceIds.get(i);
        }
        
        Session sess = new Session(id, heldAt, subjectID, title, description, attendanceIds);
        return sess;
    }
    
    public Subject getSubject() throws Exception {
        return Subject.getById(subjectId);
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("heldAt", heldAt.toJSON());
        obj.put("subjectId", subjectId);
        obj.put("title", title);
        obj.put("description", description);
        
        JSONArray jsonAttendanceIds = new JSONArray();
        for (int i = 0; i < attendanceIds.length; i++) {
            jsonAttendanceIds.add(i, attendanceIds[i]);
        }
        
        obj.put("attendanceIds", jsonAttendanceIds);
        return obj;
    }

    @Override
    public String getTableName() {
        return "sessions";
    }

    @Override
    public void reload() throws Exception {
        Session newSess = Session.getById(id);
        this.attendanceIds = newSess.attendanceIds;
        this.description = newSess.description;
        this.heldAt = newSess.heldAt;
        this.subjectId = newSess.subjectId;
        this.title = newSess.title;
    }
    
    public void recordAttendance(Attendance att) throws Exception {
        att.save();
        long[] newAttendanceIds = new long[this.attendanceIds.length + 1];
        for (int i = 0; i < this.attendanceIds.length; i++) {
            newAttendanceIds[i] = this.attendanceIds[i];
        }
        newAttendanceIds[newAttendanceIds.length - 1] = att.id;
        this.attendanceIds = newAttendanceIds;
        this.save();
    }
}
