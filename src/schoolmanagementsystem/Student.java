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
 * @author japz3
 */
public class Student extends User {

    public String name;
    public String section;
    public String[] subjectIds;

    public Student(String username, String password, String name, String section) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.section = section;
        this.subjectIds = new String[0];
    }

    public Student(long id, String username, String password, String name, String section, String[] subjectIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.section = section;
        this.subjectIds = subjectIds;
    }

    public static Student fromJSON(JSONObject obj) throws Exception {
        long studId = (long) obj.get("id");
        String studUsername = (String) obj.get("username");
        String studPassword = (String) obj.get("password");
        String studName = (String) obj.get("name");
        String studSection = (String) obj.get("section");
        JSONArray rawsubjectIds = (JSONArray) obj.get("subjectIds");

        String[] subjectIds = new String[rawsubjectIds.size()];
        for (int i = 0; i < subjectIds.length; i++) {
            subjectIds[i] = (String) rawsubjectIds.get(i);
        }

        return new Student(studId, studUsername, studPassword, studName, studSection, subjectIds);
    }

    public static Student getByUsername(String username) throws Exception {
        Iterator sessionsIt = Database.get("students").all().iterator();
        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("username") && ((String) data.get("username")).equals(username)) {
                return Student.fromJSON(data);
            }
        }
        throw new Exception("Student not found!");
    }

    public static Student getByName(String name) throws Exception {
        Iterator sessionsIt = Database.get("students").all().iterator();

        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("name") && ((String) data.get("name")).equals(name)) {
                return Student.fromJSON(data);
            }
        }
        throw new Exception("Student not found!");
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
        Subject[] subjects = new Subject[subjectIds.length];
        for (int i = 0; i < subjectIds.length; i++) {
            subjects[i] = Subject.getById(subjectIds[i]);
        }
        return subjects;
    }

    public static Student getById(long id) throws Exception {
        Iterator studentIt = Database.get("students").all().iterator();

        while (studentIt.hasNext()) {
            JSONObject data = (JSONObject) studentIt.next();
            if (data.containsKey("id") && ((long) data.get("id")) == id) {
                return Student.fromJSON(data);
            }
        }

        throw new Exception("Session not found!");
    }

    public void addSubject(Subject subject) throws Exception {
        String[] newSubjectIds = new String[this.subjectIds.length + 1];
        for (int i = 0; i < this.subjectIds.length; i++) {
            newSubjectIds[i] = this.subjectIds[i];
        }
        newSubjectIds[newSubjectIds.length - 1] = subject.id;
        this.subjectIds = newSubjectIds;
        this.save();

        long[] newstudentIds = new long[subject.studentIds.length + 1];
        for (int i = 0; i < subject.studentIds.length; i++) {
            newstudentIds[i] = subject.studentIds[i];
        }
        newstudentIds[newstudentIds.length - 1] = this.id;
        subject.studentIds = newstudentIds;
        subject.save();
    }

    public void dropSubject(Subject subject) throws Exception {
        String[] newSubjectIds = new String[this.subjectIds.length - 1];
        boolean foundSubject = false;
        for (int i = 0; i < this.subjectIds.length; i++) {
            if (this.subjectIds[i].equals(subject.id)) {
                foundSubject = true;
                continue;
            }
            newSubjectIds[i] = this.subjectIds[i];
        }

        if (!foundSubject) {
            throw new Exception("No subject that matches the list.");
        }

        this.subjectIds = newSubjectIds;
        this.save();

        long[] newstudentIds = new long[subject.studentIds.length - 1];
        for (int i = 0; i < subject.studentIds.length; i++) {
            if (subject.studentIds[i] == this.id) {
                continue;
            }
            newstudentIds[i] = subject.studentIds[i];
        }
        subject.studentIds = newstudentIds;
        subject.save();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("username", username);
        obj.put("password", password);
        obj.put("name", name);
        obj.put("section", section);

        JSONArray jsonsubjectIds = new JSONArray();
        for (int i = 0; i < subjectIds.length; i++) {
            jsonsubjectIds.add(i, subjectIds[i]);
        }

        obj.put("subjectIds", jsonsubjectIds);
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
        this.subjectIds = newStud.subjectIds;
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
        Iterator subjectIt = Database.get("students").all().iterator();
        Student[] foundStudents = new Student[20];
        int i = 0;
        
        while (subjectIt.hasNext()) {
            JSONObject data = (JSONObject) subjectIt.next();
            if (data.containsKey("name") && ((String) data.get("name")).toLowerCase().contains(name)) {
                foundStudents[i] = Student.fromJSON(data);
                i++;
            }
        }

        if (i == 0) {
            throw new Exception("No results found.");
        }
        return foundStudents;
    }
}
