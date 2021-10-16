/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author nedpals
 */
public class Database {
    private static String[] tableNames;
    private static Table[] tables;
    public static Random random = new Random();
  
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
