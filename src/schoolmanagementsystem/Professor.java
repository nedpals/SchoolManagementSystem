/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.Database;
import database.Table;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class Professor extends User {
    public String name;
    public String[] handledSubjectIds;
    public String department;

    public Professor(String username, String password, String name, String department) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.handledSubjectIds = new String[0];
        this.department = department;
    }
    
    Professor(long id, String username, String password, String name, String[] handledSubjectIds, String department) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.handledSubjectIds = handledSubjectIds;
        this.department = department;
    }

    public static Professor fromJSON(JSONObject obj) {
        long profID = (long) obj.get("id");
        String profUsername = (String) obj.get("username");
        String profPassword = (String) obj.get("password");
        String profName = (String) obj.get("name");
        JSONArray profHandledSubjectIds = (JSONArray) obj.get("handledSubjectIds");
        String[] handledSubjectIds = new String[profHandledSubjectIds.size()];
        for (int i = 0; i < handledSubjectIds.length; i++) {
            handledSubjectIds[i] = (String) profHandledSubjectIds.get(i);
        }
        String profDepartment = (String) obj.get("department");
        Professor prof = new Professor(profID, profUsername, profPassword, profName, handledSubjectIds, profDepartment);
        return prof;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("password", this.password);
        obj.put("name", this.name);

        JSONArray jsonHandledSubjectIds = new JSONArray();
        for (int i = 0; i < this.handledSubjectIds.length; i++) {
            jsonHandledSubjectIds.add(this.handledSubjectIds[i]);
        }

        obj.put("handledSubjectIds", jsonHandledSubjectIds);
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
        Iterator professorsIt = Database.get("professors").all().iterator();

        while (professorsIt.hasNext()) {
            JSONObject data = (JSONObject) professorsIt.next();
            if (data.containsKey("username") && ((String) data.get("username")).equals(username)) {
                return Professor.fromJSON(data);
            }
        }

        throw new Exception("Professor not found!");
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
        Iterator professorIt = Database.get("professors").all().iterator();

        while (professorIt.hasNext()) {
            JSONObject data = (JSONObject) professorIt.next();
            if (data.containsKey("id") && ((long) data.get("id")) == id) {
                return Professor.fromJSON(data);
            }
        }

        throw new Exception("Professor not found!");
    }

    public Subject[] getHandledSubjects() throws Exception {
        Subject[] subjects = new Subject[handledSubjectIds.length];
        for (int i = 0; i < handledSubjectIds.length; i++) {
            subjects[i] = Subject.getById(handledSubjectIds[i]);
        }
        return subjects;
    }

    public void createSubject(Subject subject) throws Exception {
        String[] newSubjectIds = new String[this.handledSubjectIds.length + 1];
        boolean foundSubject = false;
        
        for (int i = 0; i < this.handledSubjectIds.length; i++) {
            if (this.handledSubjectIds[i].equals(subject.id)) {
                foundSubject = true;
            }
            
            newSubjectIds[i] = this.handledSubjectIds[i];
        }
        
        if (foundSubject) {
            throw new Exception("Subject already exists.");
        }
        
        newSubjectIds[newSubjectIds.length - 1] = subject.id;
        this.handledSubjectIds = newSubjectIds;
        subject.professorId = this.id;
        subject.save();
        this.save();
    }

    public void dropSubject(Subject subject) throws Exception {
        String[] newSubjectIds = new String[this.handledSubjectIds.length - 1];
        boolean foundSubject = false;
        
        for (int i = 0; i < this.handledSubjectIds.length; i++) {
            if (this.handledSubjectIds[i].equals(subject.id)) {
                foundSubject = true;
                continue;
            }
            newSubjectIds[i] = this.handledSubjectIds[i];
        }
        
        if (!foundSubject) {
            throw new Exception("No subject that matches the list.");
        }
        
        this.handledSubjectIds = newSubjectIds;
        this.save();
        
        // manually invoke the subject remove method
    }

    public void createSession(Subject sub, Session session) throws Exception {
        session.save();
        long[] newSessionIds = new long[sub.sessionIds.length + 1];
        for (int i = 0; i < sub.sessionIds.length; i++) {
            newSessionIds[i] = sub.sessionIds[i];
        }
        newSessionIds[newSessionIds.length - 1] = session.id;
        sub.sessionIds = newSessionIds;
        sub.save();
    }

    public void createNote(Subject sub, Note note) throws Exception {
        note.save();
        long[] newNoteIds = new long[sub.noteIds.length + 1];
        for (int i = 0; i < sub.noteIds.length; i++) {
            newNoteIds[i] = sub.noteIds[i];
        }
        newNoteIds[newNoteIds.length - 1] = note.id;
        sub.noteIds = newNoteIds;
        sub.save();
    }

    public void deleteNote(Subject sub, Note note) throws Exception {
        Table table = Database.get("notes");

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
        this.handledSubjectIds = newProf.handledSubjectIds;
    }
}
