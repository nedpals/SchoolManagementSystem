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
public class Note extends DBEntity {
    public int id;
    public int subjectId;
    public int professorId;
    public String title;
    public String content;
    public Date2 updatedAt;
    private int arrayIndex = -1;
    
    Note(int id, int subjectId, String title, String content, Date updatedAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
        this.updatedAt = new Date2(updatedAt);
    }
    
    Note(int id, int subjectId, String title, String content, Date2 updatedAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public void getSubject() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public void getProfessor() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    public static Note fromJSON(JSONObject obj) throws Exception {
        return Note.fromJSON(obj, -1);
    }
    
    public static Note fromJSON(JSONObject obj, int arrayIndex) throws Exception {
        int id = (int) (long) obj.get("id");
        int subjectId = (int) (long) obj.get("subjectId");
        String title = (String) obj.get("title");
        String content = (String) obj.get("content");
        Date2 updatedAt = Date2.fromJSON((String) obj.get("updatedAt"));
        Note newNote = new Note(id, subjectId, title, content, updatedAt);
        newNote.setArrayIndex(arrayIndex);
        return newNote;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("subjectId", subjectId);
        obj.put("professorId", professorId);
        obj.put("updatedAt", updatedAt.toJSON());
        return obj;
    }

    @Override
    public void setArrayIndex(int newIndex) {
        this.arrayIndex = newIndex;
    }

    @Override
    public void save() throws Exception {
        Table table = Database.get("notes");
        if (this.arrayIndex == -1) {
            table.insert(this);
        } else {
            table.update(arrayIndex, this);
        }
    }
}
