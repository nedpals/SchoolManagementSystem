/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

interface JSONNable {
    JSONObject toJSON();
    void setArrayIndex(int newIndex);
}

class Table {
    public String name;
    private JSONArray data = new JSONArray();
    private String jsonFilePath;
    
    Table(String name, JSONArray data, String jsonFilePath) {
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
    
    public void insert(JSONNable obj) throws Exception {
        this.data.add(obj.toJSON());
        this.save();
    }
    
    public void insert(JSONObject newData) throws Exception {
        this.data.add(newData);
        this.save();
    }
    
    public void update(int index, JSONNable obj) throws Exception {
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

/**
 *
 * @author nedpals
 */
public class Database {
    private static String[] tableNames;
    private static Table[] tables;

    private static String checkAndGetTable(String dbPath) throws IOException {
        File dbFile = new File(dbPath);

        if (!dbFile.exists()) {
            throw new IOException("FAILED. File does not exist.");
        }

        if (dbFile.length() == 0) {
            return null;
        }
        
        String fname = dbFile.getName();
        int pos = fname.lastIndexOf(".");
        
        if (pos <= 0) {
            return null;
        }
        
        return fname.substring(0, pos);
    }
    
    public static Table get(String name) throws Exception {
        for (int i = 0; i < tableNames.length; i++) {
            if (tableNames[i].equals(name)) {
                return tables[i];
            }
        }
        
        throw new Exception(String.format("Table '%s' not found!", name));
    }
    
    public static void connect() throws Exception {
        JSONParser jsonParser = new JSONParser();
        String[] dbTablePaths = {
            "src/schoolmanagementsystem/database_tables/admins.json",
            "src/schoolmanagementsystem/database_tables/attendances.json",
            "src/schoolmanagementsystem/database_tables/notes.json",
            "src/schoolmanagementsystem/database_tables/professors.json",
            "src/schoolmanagementsystem/database_tables/sessions.json",
            "src/schoolmanagementsystem/database_tables/students.json",
            "src/schoolmanagementsystem/database_tables/subjects.json"
        };
        
        tableNames = new String[dbTablePaths.length];
        tables = new Table[dbTablePaths.length];
        
        for (int i = 0; i < dbTablePaths.length; i++) {
            String tableName = checkAndGetTable(dbTablePaths[i]);
            if (tableName == null) {
                throw new Exception(String.format("File '%s' was not found.", dbTablePaths[i]));
            }
            
            System.out.printf("Table %s loaded.\n", tableName);
            FileReader reader = new FileReader(dbTablePaths[i]);
            JSONArray data = (JSONArray) jsonParser.parse(reader);
            reader.close();
            
            tableNames[i] = tableName;
            tables[i] = new Table(tableName, data, dbTablePaths[i]);
        }
    }
}
