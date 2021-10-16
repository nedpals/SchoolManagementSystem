/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import mysql_database.Database;
import mysql_database.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class Professor extends User {
    public String name;
    public String department;

    public Professor(String username, String password, String name, String department) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.department = department;
    }
    
    Professor(long id, String username, String password, String name, String department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.department = department;
    }

    public static Professor fromJSON(JSONObject obj) {
        long profID = (long) obj.get("id");
        String profUsername = (String) obj.get("username");
        String profPassword = (String) obj.get("password");
        String profName = (String) obj.get("name");
        String profDepartment = (String) obj.get("department");
        Professor prof = new Professor(profID, profUsername, profPassword, profName, profDepartment);
        return prof;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("password", this.password);
        obj.put("name", this.name);
        obj.put("department", this.department);
        return obj;
    }

    //getAll()
    public static Professor[] getAll() throws Exception {
        JSONArray data = Database.get("professors").all();
        Professor[] professors = new Professor[data.size()];

        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            Professor prof = Professor.fromJSON(obj);
            professors[i] = prof;

        }
        return professors;
    }

    public static Professor getByUsername(String username) throws Exception {
        JSONArray data = Database.get("professors").getBy("username = ?", new Object[] {username});
        if (data.isEmpty()) {
            throw new Exception("Professor not found!");
        }
        
        return Professor.fromJSON((JSONObject) data.get(0));
    }

    public static Professor login(String username, String password) throws Exception {
        Professor foundProfessor = Professor.getByUsername(username);
        if (foundProfessor.password.equals(password)) {
            return foundProfessor;
        } else {
            throw new Exception("Password is incorrect");
        }
    }

    public void logout() {
        this.logout();
    }

    public static Professor getById(long id) throws Exception {
        JSONArray results = Database.get("professors").getBy("id = ?", new Object[] {id});
        if (results.isEmpty()) {
            throw new Exception("Professor not found!");
        }

        return Professor.fromJSON((JSONObject) results.get(0));
    }

    public Subject[] getHandledSubjects() throws Exception {
        JSONArray results = Database.get("handled_subjects").getBy("professor_id = ?", new Object[] {this.id});
        Subject[] subjects = new Subject[results.size()];
        for (int i = 0; i < subjects.length; i++) {
            JSONObject data = (JSONObject) results.get(i);
            subjects[i] = Subject.getById((String) data.get("subject_id"));
        }
        return subjects;
    }

    public void createSubject(Subject subject) throws Exception {
        boolean foundSubject = false;
        Subject[] handledSubjects = this.getHandledSubjects();

        for (int i = 0; i < handledSubjects.length; i++) {
            if (handledSubjects[i].id.equals(subject.id)) {
                foundSubject = true;
                break;
            }
        }
        
        if (foundSubject) {
            throw new Exception("Subject already exists.");
        }

        subject.professorId = this.id;
        subject.save();
        
        JSONObject ssObj = new JSONObject();
        ssObj.put("professor_id", this.id);
        ssObj.put("subject_id", subject.id);
        
        Table table = Database.get("handled_subjects");
        table.insert(ssObj);
    }

    public void dropSubject(Subject subject) throws Exception {
        boolean foundSubject = false;
        Subject[] handledSubjects = this.getHandledSubjects();

        for (int i = 0; i < handledSubjects.length; i++) {
            if (handledSubjects[i].id.equals(subject.id)) {
                foundSubject = true;
                break;
            }
        }
        
        if (!foundSubject) {
            throw new Exception("No subject that matches the list.");
        }
        
        Table table = Database.get("handled_subjects");
        table.removeBy("professor_id = ? AND subject_id = ?", new Object[] {this.id, subject.id});
        // manually invoke the subject remove method
    }

    public void createSession(Subject sub, Session session) throws Exception {
        session.save();
    }

    public void createNote(Subject sub, Note note) throws Exception {
        note.save();
    }

    public void deleteNote(Subject sub, Note note) throws Exception {
        note.remove();
    }

    public void dropStudent(Subject subj, Student stud) throws Exception {
        stud.dropSubject(subj);
    }

    @Override
    public String getTableName() {
        return "professors";
    }

    @Override
    public void reload() throws Exception {
        Professor newProf = Professor.getById(this.id);
        this.username = newProf.username;
        this.password = newProf.password;
        this.name = newProf.name;
        this.department = newProf.department;
    }
}
