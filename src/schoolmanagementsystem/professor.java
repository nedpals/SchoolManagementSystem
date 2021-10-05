/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class professor {
    public int id;
    public String username;
    private String password;
    public String name;
    public String handledSubjects;
    public String department;
    private int arrayIndex = -1;
    
    
    professor(int id, String username, String password, String name, String handledSubjects, String department){
    
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.handledSubjects = handledSubjects;
        this.department = department;
        
}   
    public static professor fromJSON(JSONObject obj){
        int profID = (int)(long) obj.get("id");
        String profUsername = (String) obj.get("username");
        String profPassword = (String) obj.get("password");
        String profName = (String) obj.get("name");
        String profSubjects = (String) obj.get("handledSubjects");
        String profDepartment = (String) obj.get("department");
        return new professor(profID, profUsername, profPassword, profName, profSubjects, profDepartment);   
    }
    
    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("username", this.username);
        obj.put("password", this.password);
        obj.put("name", this.name);
        obj.put("handledSubjects", this.handledSubjects);
        obj.put("department", this.department);
        return obj;
    }
    //getAll()
    public static professor[] getAll(){
       try{
           JSONArray data = Database.get("professors").all();
           professor[] professors = new professor[(data.size())];
           
           for (int i = 0; i<data.size(); i++){
               JSONObject obj = (JSONObject) data.get(i);
               professor prof = professor.fromJSON(obj);
               professors[i] = prof;
               
           }
           return professors;
       }catch(Exception ex){
           System.out.println(ex);
           return null;
       }
        
        
    }
  
}
