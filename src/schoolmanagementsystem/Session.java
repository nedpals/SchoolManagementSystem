/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author nedpals
 */
public class Session {
    public int id;
    public Date2 heldAt;
    public int subjectId;
    public String title;
    public String description;
    public int[] attendanceIds;
    private int arrayIndex = -1;
    
    Session(int id, Date2 heldAt, int subjectId, String title, String description, int[] attendanceIds) {
        this.id = id;
        this.heldAt = heldAt;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.attendanceIds = attendanceIds;
    }
    
    public static Session getById(int id) throws Exception {
        Iterator sessionsIt = Database.get("sessions").all().iterator();
        
        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("id") && (int) data.get("id") == id) {
                return Session.fromJSON(data);
            }
        }
        
        throw new Exception("Session not found!");
    }
    
    public static Session fromJSON(JSONObject obj) throws Exception {
        return Session.fromJSON(obj, -1);
    }
    
    public static Session fromJSON(JSONObject obj, int arrayIndex) throws Exception {
        int id = (int) (long) obj.get("id");
        Date2 heldAt = Date2.fromJSON((String) obj.get("heldAt"));
        int subjectID = (int) (long) obj.get("subjectId");
        String title = (String) obj.get("title");
        String description = (String) obj.get("description");
        JSONArray rawAttendanceIds = (JSONArray) obj.get("attendanceIds");
        
        int[] attendanceIds = new int[rawAttendanceIds.size()];
        for (int i = 0; i < attendanceIds.length; i++) {
            attendanceIds[i] = (int) (long) rawAttendanceIds.get(i);
        }
        
        Session sess = new Session(id, heldAt, subjectID, title, description, attendanceIds);
        sess.setArrayIndex(arrayIndex);
        return sess;
    }
    
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
    
    public void setArrayIndex(int newIndex) {
        this.arrayIndex = newIndex;
    }
    
    public void save() throws Exception {
        Table table = Database.get("sessions");
        
        if (this.arrayIndex == -1) {
            table.insert(this.toJSON());
        } else {
            table.update(this.arrayIndex, this.toJSON());
        }
    }
}
