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
 * @author User
 */
public class Professor extends User {
    public int id;
    public String name;
    public String[] handledSubjectIds;
    public String department;
    
    
    
    Professor(int id, String username, String password, String name, String[] handledSubjectIds, String department, int arrayIndex1){
    
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.handledSubjectIds = handledSubjectIds;
        this.department = department;
        
}   
    
    public static Professor fromJSON(JSONObject obj){
        return Professor.fromJSON(obj, -1);
    }

    public static Professor fromJSON(JSONObject obj, int arrayIndex){
        int profID = (int)(long) obj.get("id");
        String profUsername = (String) obj.get("username");
        String profPassword = (String) obj.get("password");
        String profName = (String) obj.get("name");
        JSONArray profHandledSubjectIds = (JSONArray) obj.get("handledSubjectIds");
        String[] handledSubjectIds = new String[profHandledSubjectIds.size()];
        
        for (int i = 0; i < handledSubjectIds.length; i++) {
            handledSubjectIds[i] = (String) profHandledSubjectIds.get(i);
        }
        String profDepartment = (String) obj.get("department");
        return new Professor(profID, profUsername, profPassword, profName, handledSubjectIds, profDepartment, arrayIndex);   
    }
    
    
    public JSONObject toJSON(){
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
    public static Professor[] getAll() throws Exception{
       JSONArray data = Database.get("professors").all();
        Professor[] professors = new Professor[data.size()];

        for (int i = 0; i<data.size(); i++){
            JSONObject obj = (JSONObject) data.get(i);
            Professor prof = Professor.fromJSON(obj, i);
            professors[i] = prof;

        }
        return professors;
    }
    //save()
    public void save()throws Exception {
        Table professorTable = Database.get("professors");
        if(arrayIndex == -1){
            professorTable.insert(this.toJSON());
        }else{
            professorTable.update(this.arrayIndex, this.toJSON());
        }
    }
    public static Professor getByUsername(String username) throws Exception {
        Iterator professorsIt = Database.get("professors").all().iterator();
        
        while (professorsIt.hasNext()) {
            JSONObject data = (JSONObject) professorsIt.next();
            if (data.containsKey("username")) {
                String adminUser = (String) data.get("username");
                if (!adminUser.equals(username)){
                    continue;
                }
                return Professor.fromJSON(data);
            }
        }
        
        throw new Exception("Admin not found!");
    }
    
    public static Professor login(String username, String password) throws Exception{
        Professor foundProfessor = Professor.getByUsername(username);
         if (foundProfessor.password.equals(password)){
             return foundProfessor;
         } else {
             throw new Exception("Password is incorrect");
                     
         }
    }
   
    public void logout(){
        this.logout();
    }
    
    public static Professor getById(int id) throws Exception {
        Iterator professorIt = Database.get("professors").all().iterator();
        
        while (professorIt.hasNext()) {
            JSONObject data = (JSONObject) professorIt.next();
            if (data.containsKey("id")) {
                int professorId = (int)data.get("id");
                if(professorId != id){
                    continue;                  
                }
                return Professor.fromJSON(data);
            }
        }
        
        throw new Exception("Professor not found!");
    }
    
    public Subject[] getHandledSubjects()throws Exception{
        Subject[] subjects = new Subject[handledSubjectIds.length];
        for (int i = 0; i < handledSubjectIds.length; i++) {
            subjects[i] = Subject.getById(handledSubjectIds[i]);
        }
        return subjects;
    }   
    public void createSubject(Subject subject)throws Exception{
        subject.professorId = this.id;
        subject.save();
    }
        
    public void deleteSubject(Subject subject)throws Exception{
        Table table = Database.get("subjects");
     //   table.remove(subject.);
    }
    public void createSession (Subject sub, Session session)throws Exception{
        session.save();
        int[] newSessionIds = new int[sub.sessionIds.length+1];
       for(int i = 0; i<sub.sessionIds.length; i++){
           newSessionIds[i] = sub.sessionIds[i];
       }
       newSessionIds[newSessionIds.length-1] = session.id;
       sub.sessionIds = newSessionIds;
       sub.save();
    }
    
    public void createNote (Subject sub, Note note)throws Exception{
        note.save();
        int[] newNoteIds = new int[sub.noteIds.length+1];
       for(int i = 0; i<sub.noteIds.length; i++){
           newNoteIds[i] = sub.noteIds[i];
       }
       newNoteIds[newNoteIds.length-1] = note.id;
       sub.sessionIds = newNoteIds;
       sub.save();
    }
    
    public void deleteNote(Subject sub, Note note)throws Exception{
        Table table = Database.get("notes");
        
    }
    
    public void dropStudent(Subject subj, Student stud) throws Exception{
    
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
}
    


