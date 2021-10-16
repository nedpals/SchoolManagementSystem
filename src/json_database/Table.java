/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_database;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author nedpals
 */
public class Table {
    public final String name;
    private final String jsonFilePath;
    public JSONArray data = new JSONArray();
    
    public Table(String name, JSONArray data, String jsonFilePath) {
        this.name = name;
        this.data = data;
        this.jsonFilePath = jsonFilePath;
    }
    
    public JSONArray all() {
        return this.data;
    }
    
    public void remove(int index) throws Exception {
        this.data.remove(index);
        this.save();
    }
    
    public void insert(DBEntity obj) throws Exception {
        this.data.add(obj.toJSON());
        this.save();
    }
    
    public void insert(JSONObject newData) throws Exception {
        this.data.add(newData);
        this.save();
    }
    
    public void update(int index, DBEntity obj) throws Exception {
        this.data.set(index, obj.toJSON());
        this.save();
    }
    
    public void update(int index, JSONObject updatedData) throws Exception {
        this.data.set(index, updatedData);
        this.save();
    }
    
    public boolean save() throws IOException {
        FileWriter fileWriter = new FileWriter(this.jsonFilePath);
        fileWriter.write(this.data.toJSONString());
        fileWriter.close();
        return true;
    }
}