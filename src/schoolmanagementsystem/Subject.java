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
 * @author Parcasio
 */
public class Subject extends DBEntity{
    public String id;
    public String name;
    public int units;
    public String description;
    public int professorId;
    public int[] sessionIds;
    public int[] noteIds;
    public int[] studentIds;
    
    Subject(String id, String name, int units, String description, int professsorId, int[] sessionIds, int[] noteIds, int[] studentIds){
     this.id = id;
     this.name = name;
     this.units = units;
     this.description = description;
     this.professorId = professorId;
     this.noteIds = noteIds;
     this.studentIds = studentIds;
    }
    public static Subject[] search(String idOrName)throws Exception{
     Iterator subjectIt = Database.get("subjects").all().iterator();
        Subject[] foundSubjects = new Subject[20];
        int i = 0;
        while (subjectIt.hasNext()) {
            JSONObject data = (JSONObject) subjectIt.next();
            if (data.containsKey("id")) {
                String subjectId = (String)data.get("id");
                if(!subjectId.equals(idOrName)){
                    continue;                  
                }
                
                foundSubjects[i] = Subject.fromJSON(data);
                i++;
                continue;
            }
              if (data.containsKey("name")) {
                String subjectId = (String)data.get("name");
                if(!subjectId.equals(idOrName)){
                    continue;                  
                }
                foundSubjects[i] = Subject.fromJSON(data);
                i++;
                continue;           
            }
        }
        return foundSubjects;
}
    public static Subject getById(String id) throws Exception {
        Iterator subjectIt = Database.get("subjects").all().iterator();
        
        while (subjectIt.hasNext()) {
            JSONObject data = (JSONObject) subjectIt.next();
            if (data.containsKey("id")) {
                String subjectId = (String)data.get("id");
                if(!subjectId.equals(id)){
                    continue;                  
                }
                return Subject.fromJSON(data);
            }
        }
        
        throw new Exception("Session not found!");
    }
      public static Subject fromJSON(JSONObject obj) throws Exception {
        return Subject.fromJSON(obj, -1);
    }
      
    
    public static Subject fromJSON(JSONObject obj, int arrayIndex) throws Exception {
        String id = (String) obj.get("id");
        String name = (String) obj.get("name");
        int units = (int) (long) obj.get("units");
        String description = (String) obj.get("description");
        int professorId = (int) (long) obj.get("professorId");      
        JSONArray rawSessionIds = (JSONArray) obj.get("sessionIds");
        JSONArray rawNoteIds = (JSONArray) obj.get("noteIds");
        JSONArray rawStudentIds = (JSONArray) obj.get("studentIds");
        
        int[] sessionIds = new int[rawSessionIds.size()];
        int[] noteIds = new int[rawNoteIds.size()];
        int[] studentIds = new int[rawStudentIds.size()];
        
        for (int i = 0; i < sessionIds.length; i++) {
            sessionIds[i] = (int) (long) rawSessionIds.get(i);
        }
        for (int i = 0; i < noteIds.length; i++) {
            noteIds[i] = (int) (long) rawNoteIds.get(i);
        }
        for (int i = 0; i < studentIds.length; i++) {
            studentIds[i] = (int) (long) rawStudentIds.get(i);
        }
        
        Subject subs = new Subject(id, name, units, description, professorId, sessionIds, noteIds, studentIds);
        subs.setArrayIndex(arrayIndex);
        return subs;
    }


    public void getProfessor() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    public void getStudents() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    public void getNotes() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    public void getSessions() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    public void getNoteById() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    public void getSessionByDate() throws Exception {
        throw new Exception("Not implemented yet!");
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("units", units);
        obj.put("description", description);
        obj.put("professorId", professorId);
        
        JSONArray jsonsessionIds = new JSONArray();
        for (int i = 0; i < sessionIds.length; i++) {
            jsonsessionIds.add(i, sessionIds[i]);
        }
        JSONArray jsonnoteIds = new JSONArray();
        for (int i = 0; i < noteIds.length; i++) {
            jsonnoteIds.add(i, noteIds[i]);
        }
        JSONArray jsonstudentIds = new JSONArray();
        for (int i = 0; i < studentIds.length; i++) {
            jsonstudentIds.add(i, studentIds[i]);
        }
        
        obj.put("sessionIds", jsonsessionIds);
        obj.put("noteIds", jsonnoteIds);
        obj.put("studentIds", jsonstudentIds);
        return obj;
    }
    
    @Override
    public void save() throws Exception {
        Table table = Database.get("subjects");
        
        if (this.arrayIndex == -1) {
            table.insert(this.toJSON());
        } else {
            table.update(this.arrayIndex, this.toJSON());
        }
    }
    
}
