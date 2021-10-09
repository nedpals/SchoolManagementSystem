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
public class Note extends DBEntity {
    public String subjectId;
    public String title;
    public String content;
    public Date2 updatedAt;

    Note(long id, String subjectId, String title, String content, Date2 updatedAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public Note(String subjectId, String title, String content) {
        this.subjectId = subjectId;
        this.title = title;
        this.content = content;
        this.updatedAt = new Date2();
    }

    public Subject getSubject() throws Exception {
        return Subject.getById(subjectId);
    }

    public void getProfessor() throws Exception {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static Note fromJSON(JSONObject obj) throws Exception {
        long id = (long) obj.get("id");
        String subjectId = (String) obj.get("subjectId");
        String title = (String) obj.get("title");
        String content = (String) obj.get("content");
        Date2 updatedAt = Date2.fromJSON((String) obj.get("updatedAt"));
        Note newNote = new Note(id, subjectId, title, content, updatedAt);
        return newNote;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("title", title);
        obj.put("content", content);
        obj.put("subjectId", subjectId);
        obj.put("updatedAt", updatedAt.toJSON());
        return obj;
    }

    @Override
    public String getTableName() {
        return "notes";
    }

    @Override
    public void reload() throws Exception {}
}
