/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author japz3
 */
public class Student extends User{
    public int id;
    public String username;
    private String password;
    public String name;
    public String section;
    public String[] subjectIds;
    
    Student(int id, String username, String password, String name, String section, String[] subjectIds){
     this.id = id;
     this.username = username;
     this.password = password;
     this.name = name;
     this.section = section;
     this.subjectIds = subjectIds;
    }
    
    public static Student fromJSON(JSONObject obj) throws Exception {
        return Student.fromJSON(obj, -1);
    }
    
     public static Student fromJSON(JSONObject obj, int arrayIndex) throws Exception {
        int studId = (int) obj.get("id");
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
            if (data.containsKey("username")) {
                String studentUser = (String) data.get("username");
                if (!studentUser.equals(username)){
                    continue;
                }
                return Student.fromJSON(data);
            }
        }
        throw new Exception("Student not found!");
    }
     
     public static Student getByName (String name) throws Exception {
        Iterator sessionsIt = Database.get("students").all().iterator();
        
        while (sessionsIt.hasNext()) {
            JSONObject data = (JSONObject) sessionsIt.next();
            if (data.containsKey("name")) {
                String studentUser = (String) data.get("name");
                if (!studentUser.equals(name)){
                    continue;
                }
                return Student.fromJSON(data);
            }
        }
        throw new Exception("Student not found!");
    }
    
     public static Student login(String username, String password) throws Exception {
         Student foundStudent = Student.getByUsername(username);
         if (foundStudent.password.equals(password)){
             return foundStudent;
         } else {
             throw new Exception("Password inputted is incorrect");
                     
         }
    }

public void logout(){
}


@Override
    public void save() throws Exception {
        Table table = Database.get("students");
        
        if (this.arrayIndex == -1) {
            table.insert(this.toJSON());
        } else {
            table.update(this.arrayIndex, this.toJSON());
        }
    }

public static Student[] getAll() {
        try {
           JSONArray data = Database.get("students").all();
           Student[] students = new Student[data.size()];
           
           for (int i = 0; i < data.size(); i++) {
               JSONObject obj = (JSONObject) data.get(i);
              Student student = Student.fromJSON(obj, i);
               students[i] = student;
           }
           
           return students;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

public Subject[] getSubjects()throws Exception{
        Subject[] subjects = new Subject[subjectIds.length];
        for (int i = 0; i < subjectIds.length; i++) {
            subjects[i] = Subject.getById(subjectIds[i]);
        }
        return subjects;
    }

public static Student getById(int id) throws Exception {
        Iterator studentIt = Database.get("subjects").all().iterator();
        
        while (studentIt.hasNext()) {
            JSONObject data = (JSONObject) studentIt.next();
            if (data.containsKey("id")) {
                int studentId = (int)data.get("id");
                if(studentId!=id){
                    continue;                  
                }
                return Student.fromJSON(data);
            }
        }
        
        throw new Exception("Session not found!");
    }

public void addSubject(Subject subject) throws Exception{
    
        String[] newSubjectIds = new String[this.subjectIds.length+1];
       for(int i = 0; i<this.subjectIds.length; i++){
           newSubjectIds[i] = this.subjectIds[i];
       }
       newSubjectIds[newSubjectIds.length-1] = subject.id;
       this.subjectIds = newSubjectIds;
       this.save();
    
       int[] newstudentIds = new int[subject.studentIds.length+1];
       for(int i = 0; i<subject.studentIds.length; i++){
           newstudentIds[i] = subject.studentIds[i];
       }
       newstudentIds[newstudentIds.length-1] = this.id;
       subject.studentIds = newstudentIds;
       subject.save();
}

public void dropSubject(Subject subject) throws Exception{
    
        String[] newSubjectIds = new String[this.subjectIds.length-1];
       for(int i = 0; i<this.subjectIds.length; i++){
           if(this.subjectIds[i]==subject.id){
               continue;
           }
           newSubjectIds[i] = this.subjectIds[i];
       }
       this.subjectIds = newSubjectIds;
       this.save();
    
       int[] newstudentIds = new int[subject.studentIds.length-1];
       for(int i = 0; i<subject.studentIds.length; i++){
           if(subject.studentIds[i]==this.id){
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
        
        obj.put("subjectIds",  jsonsubjectIds);
        return obj;
    }


}
