/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.Database;
import database.DBEntity;
import java.util.Arrays;
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
    public long[] sessionIds = new long[0];
    public long[] noteIds = new long[0];
    public long[] studentIds = new long[0];

    public Subject(String name, int units, long professorId, String description) {
        this.name = name;
        this.units = units;
        this.description = description;
        this.professorId = professorId;
        this.noteIds = new long[0];
        this.sessionIds = new long[0];
        this.studentIds = new long[0];
    }
    
    public Subject(String id, String name, int units, String description, long professorId, long[] sessionIds, long[] noteIds, long[] studentIds) {
        this.id = id;
        this.name = name;
        this.units = units;
        this.description = description;
        this.professorId = professorId;
        this.noteIds = noteIds;
        this.sessionIds = sessionIds;
        this.studentIds = studentIds;
    }
    
    public Object getId() {
        return this.id;
    }

    public static Subject[] search(String idOrName) throws Exception {
        Iterator subjectIt = Database.get("subjects").all().iterator();
        Subject[] foundSubjects = new Subject[20];
        int i = 0;
        while (subjectIt.hasNext()) {
            JSONObject data = (JSONObject) subjectIt.next();
            boolean idMatched = data.containsKey("id") && ((String) data.get("id")).equals(idOrName);
            boolean nameMatched = data.containsKey("name") && ((String) data.get("name")).toLowerCase().contains(idOrName);
            if (idMatched || nameMatched) {
                foundSubjects[i] = Subject.fromJSON(data);
                i++;
            }
        }

        if (i == 0) {
            throw new Exception("No results found.");
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
        Iterator subjectIt = Database.get("subjects").all().iterator();

        while (subjectIt.hasNext()) {
            JSONObject data = (JSONObject) subjectIt.next();
            if (data.containsKey("id") && ((String) data.get("id")).equals(id)) {
                return Subject.fromJSON(data);
            }
        }

        throw new Exception("Subject not found!");
    }

    public static Subject fromJSON(JSONObject obj) throws Exception {
        String id = (String) obj.get("id");
        String name = (String) obj.get("name");
        System.out.println(obj.get("units").getClass());
        int units = (int) (long) obj.get("units");
        String description = (String) obj.get("description");
        long professorId = (long) obj.get("professorId");
        JSONArray rawSessionIds = (JSONArray) obj.get("sessionIds");
        JSONArray rawNoteIds = (JSONArray) obj.get("noteIds");
        JSONArray rawStudentIds = (JSONArray) obj.get("studentIds");

        long[] sessionIds = new long[rawSessionIds.size()];
        long[] noteIds = new long[rawNoteIds.size()];
        long[] studentIds = new long[rawStudentIds.size()];

        for (int i = 0; i < sessionIds.length; i++) {
            sessionIds[i] = (long) rawSessionIds.get(i);
        }
        for (int i = 0; i < noteIds.length; i++) {
            noteIds[i] = (long) rawNoteIds.get(i);
        }
        for (int i = 0; i < studentIds.length; i++) {
            studentIds[i] = (long) rawStudentIds.get(i);
        }

        System.out.println(Arrays.toString(sessionIds));
        Subject subs = new Subject(id, name, units, description, professorId, sessionIds, noteIds, studentIds);
        return subs;
    }

    public Professor getProfessor() throws Exception {
        if (professorId == -1) {
            throw new Exception("Professor not found.");
        }
        
        return Professor.getById(this.professorId);
    }

    public Student[] getStudents() throws Exception {
        Student[] students = new Student[studentIds.length];
        for (int i = 0; i < studentIds.length; i++) {
            students[i] = Student.getById(studentIds[i]);
        }
        return students; 
    }

    public Note[] getNotes() throws Exception {
        Note[] notes = new Note[noteIds.length];
        for (int i = 0; i < noteIds.length; i++) {
            notes[i] = this.getNoteById(noteIds[i]);
        }
        return notes;
    }

    public Session[] getSessions() throws Exception {
        Session[] sessions = new Session[sessionIds.length];
        System.out.println("sessionIds: " + Arrays.toString(sessionIds));
        for (int i = 0; i < sessionIds.length; i++) {
            System.out.printf("id: %d\n", sessionIds[i]);
            sessions[i] = Session.getById(sessionIds[i]);
        }
        return sessions;
    }

    public Note getNoteById(long id) throws Exception {
        Iterator notesIt = Database.get("notes").all().iterator();

        while (notesIt.hasNext()) {
            JSONObject data = (JSONObject) notesIt.next();
            if (data.containsKey("id") && ((long) data.get("id")) == id) {
                return Note.fromJSON(data);
            }
        }

        throw new Exception("Note not found!");
    }

    public Session getSessionByDate(Date date) throws Exception {
        for (int i = 0; i < sessionIds.length; i++) {
            Session session = Session.getById(sessionIds[i]);
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
        this.noteIds = newSub.noteIds;
        this.professorId = newSub.professorId;
        this.sessionIds = newSub.sessionIds;
        this.studentIds = newSub.studentIds;
        this.units = newSub.units;
    }
}
