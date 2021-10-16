/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import mysql_database.Database;
import mysql_database.DBEntity;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Date;

/**
 *
 * @author Parcasio
 */
public class Subject extends DBEntity {
    public String id = String.valueOf(Math.abs(Database.random.nextInt()));
    public String name;
    public int units;
    public String description;  
    public long professorId;

    public Subject(String name, int units, long professorId, String description) {
        this.name = name;
        this.units = units;
        this.description = description;
        this.professorId = professorId;
    }
    
    public Subject(String id, String name, int units, String description, long professorId) {
        this.id = id;
        this.name = name;
        this.units = units;
        this.description = description;
        this.professorId = professorId;
    }
    
    @Override
    public Object getId() {
        return this.id;
    }

    public static Subject[] search(String idOrName) throws Exception {
        JSONArray data = Database.get("subjects").getBy("id = ? OR name LIKE ?", new Object[] {idOrName, "%" + idOrName + "%"});
        if (data.isEmpty()) {
            throw new Exception("No results found.");
        }

        Subject[] foundSubjects = new Subject[data.size()];
        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            Subject sub = Subject.fromJSON(obj);
            foundSubjects[i] = sub;
        }

        return foundSubjects;
    }

    public static Subject[] getAll() throws Exception {
        JSONArray data = Database.get("subjects").all();
        Subject[] subjects = new Subject[data.size()];

        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            Subject sub = Subject.fromJSON(obj);
            subjects[i] = sub;
        }

        return subjects;
    }

    public static Subject getById(String id) throws Exception {
        JSONArray data = Database.get("subjects").getBy("id = ?", new Object[] {id});
        if (data.isEmpty()) {
            throw new Exception("Subject not found!");
        }
        
        return Subject.fromJSON((JSONObject) data.get(0));
    }

    public static Subject fromJSON(JSONObject obj) throws Exception {
        String id = (String) obj.get("id");
        String name = (String) obj.get("name");
        int units = (int) (long) obj.get("units");
        String description = (String) obj.get("description");
        long professorId = (long) obj.get("professor_id");
        Subject subs = new Subject(id, name, units, description, professorId);
        return subs;
    }

    public Professor getProfessor() throws Exception {
        return Professor.getById(this.professorId);
    }

    public Student[] getStudents() throws Exception {
        JSONArray results = Database.get("student_subjects").getBy("subject_id = ?", new Object[] {this.id});
        Student[] students = new Student[results.size()];
        for (int i = 0; i < students.length; i++) {
            JSONObject data = (JSONObject) results.get(i);
            students[i] = Student.getById((long) data.get("student_id"));
        }
        return students;
    }

    public Note[] getNotes() throws Exception {
        JSONArray results = Database.get("notes").getBy("subject_id = ?", new Object[] {this.id});
        Note[] notes = new Note[results.size()];
        for (int i = 0; i < notes.length; i++) {
            JSONObject data = (JSONObject) results.get(i);
            notes[i] = Note.fromJSON(data);
        }
        return notes;
    }

    public Session[] getSessions() throws Exception {
        JSONArray results = Database.get("sessions").getBy("subject_id = ?", new Object[] {this.id});
        Session[] sessions = new Session[results.size()];
        for (int i = 0; i < sessions.length; i++) {
            JSONObject data = (JSONObject) results.get(i);
            sessions[i] = Session.fromJSON(data);
        }
        return sessions;
    }

    public Note getNoteById(long id) throws Exception {
        JSONArray data = Database.get("notes").getBy("id = ?", new Object[] {id});
        if (data.isEmpty()) {
            throw new Exception("Note not found!");
        }
        
        return Note.fromJSON((JSONObject) data.get(0));
    }

    public Session getSessionByDate(Date date) throws Exception {
        Session[] sessions = this.getSessions();
        for (int i = 0; i < sessions.length; i++) {
            Session session = sessions[i];
            int diff = session.heldAt.get().compareTo(date);
            if (diff == 0) {
                return session;
            } else if (diff > 0) {
                break;
            }
        }

        throw new Exception("Session not found!");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("units", (long) units);
        obj.put("description", description);
        obj.put("professor_id", professorId);
        return obj;
    }

    @Override
    public String getTableName() {
        return "subjects";
    }
    
    @Override
    public void remove() throws Exception {
        Professor prof = this.getProfessor();
        Student[] enrolledStudents = this.getStudents();
        for (int i = 0; i < enrolledStudents.length; i++) {
            enrolledStudents[i].dropSubject(this);
        }
        
        prof.dropSubject(this);
        super.remove();
    }

    @Override
    public void reload() throws Exception {
        Subject newSub = Subject.getById(id);
        this.description = newSub.description;
        this.name = newSub.name;
        this.professorId = newSub.professorId;
        this.units = newSub.units;
    }
}
