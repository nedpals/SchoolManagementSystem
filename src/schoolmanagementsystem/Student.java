/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.sql.PreparedStatement;
import mysql_database.Database;
import mysql_database.Table;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author japz3
 */
public class Student extends User {

    public String name;
    public String section;

    public Student(String username, String password, String name, String section) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.section = section;
    }

    public Student(long id, String username, String password, String name, String section) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.section = section;
    }

    public static Student fromJSON(JSONObject obj) throws Exception {
        long studId = (long) obj.get("id");
        String studUsername = (String) obj.get("username");
        String studPassword = (String) obj.get("password");
        String studName = (String) obj.get("name");
        String studSection = (String) obj.get("section");

        return new Student(studId, studUsername, studPassword, studName, studSection);
    }

    public static Student getByUsername(String username) throws Exception {
        JSONArray data = Database.get("students").getBy("username = ?", new Object[] {username});
        if (data.isEmpty()) {
            throw new Exception("Student not found!");
        }
        
        return Student.fromJSON((JSONObject) data.get(0));
    }

    public static Student getByName(String name) throws Exception {
        JSONArray data = Database.get("students").getBy("name = ?", new Object[] {name});
        if (data.isEmpty()) {
            throw new Exception("Student not found!");
        }
        
        return Student.fromJSON((JSONObject) data.get(0));
    }

    public static Student login(String username, String password) throws Exception {
        Student foundStudent = Student.getByUsername(username);
        if (foundStudent.password.equals(password)) {
            return foundStudent;
        } else {
            throw new Exception("Password inputted is incorrect");
        }
    }

    public void logout() {
    }

    public static Student[] getAll() throws Exception {
        JSONArray data = Database.get("students").all();
        Student[] students = new Student[data.size()];

        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = (JSONObject) data.get(i);
            Student student = Student.fromJSON(obj);
            students[i] = student;
        }

        return students;
    }

    public Subject[] getSubjects() throws Exception {
        JSONArray results = Database.get("student_subjects").getBy("student_id = ?", new Object[] {this.id});
        Subject[] subjects = new Subject[results.size()];
        for (int i = 0; i < subjects.length; i++) {
            JSONObject data = (JSONObject) results.get(i);
            subjects[i] = Subject.getById((String) data.get("subject_id"));
        }
        return subjects;
    }

    public static Student getById(long id) throws Exception {
        JSONArray data = Database.get("students").getBy("id = ?", new Object[] {id});
        if (data.isEmpty()) {
            throw new Exception("Student not found!");
        }
        
        return Student.fromJSON((JSONObject) data.get(0));
    }

    public void addSubject(Subject subject) throws Exception {
        JSONObject ssObj = new JSONObject();
        ssObj.put("student_id", this.id);
        ssObj.put("subject_id", subject.id);
        
        Table table = Database.get("student_subjects");
        table.insert(ssObj);
        this.save();
    }

    public void dropSubject(Subject subject) throws Exception {
        boolean foundSubject = false;
        Subject[] handledSubjects = this.getSubjects();
        for (int i = 0; i < handledSubjects.length; i++) {
            if (handledSubjects[i].id.equals(subject.id)) {
                foundSubject = true;
                break;
            }
        }
        
        if (!foundSubject) {
            throw new Exception("No subject that matches the list.");
        }

        Table table = Database.get("student_subjects");
        table.removeBy("student_id = ? AND subject_id = ?", new Object[] {this.id, subject.id});
        this.save();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("username", username);
        obj.put("password", password);
        obj.put("name", name);
        obj.put("section", section);
        return obj;
    }

    @Override
    public String getTableName() {
        return "students";
    }

    @Override
    public void reload() throws Exception {
        Student newStud = Student.getById(id);
        this.username = newStud.username;
        this.password = newStud.password;
        this.section = newStud.section;
        this.name = newStud.name;
    }
    
    public void present(Session session) throws Exception {
        Attendance att = new Attendance(id, session.id, true, "");
        session.recordAttendance(att);
    }
    
    public void notPresent(Session session, String reason) throws Exception {
        Attendance att = new Attendance(id, session.id, false, reason);
        session.recordAttendance(att);
    }
    
    public static Student[] search(String name) throws Exception {
        JSONArray results = Database.get("students").getBy("name LIKE ?", new Object[] {"%" + name + "%"});
        if (results.isEmpty()) {
            throw new Exception("No results found.");
        }
        
        Student[] foundStudents = new Student[results.size()];
        for (int i = 0; i < foundStudents.length; i++) {
            foundStudents[i] = Student.fromJSON((JSONObject) results.get(i));
        }

        return foundStudents;
    }
}
