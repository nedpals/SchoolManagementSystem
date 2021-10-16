/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql_database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import sql_script_runner.ScriptRunner;

/**
 *
 * @author nedpals
 */
public class Database {
    private static String[] tableNames;
    private static Table[] tables;
    public static Random random = new Random();  
    public static String connURL = "jdbc:mysql://localhost:3306/?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static String databaseName = "schoolmanagementsystem";
    public static Connection conn;
    public static int currTableIdx = -1;
    
    public static Table get(String name) throws Exception {
        for (int i = 0; i < tableNames.length; i++) {
            if (tableNames[i].equals(name)) {
                return tables[i];
            }
        }
        
        throw new Exception(String.format("Table '%s' not found!", name));
    }
    
    private static void addTable(String tableName, String[] columns) throws Exception {
        if (currTableIdx + 1 >= tables.length) {
            throw new Exception("Reached out of bounds for creating tables.");
        }
        
        currTableIdx++;
        tableNames[currTableIdx] = tableName;
        tables[currTableIdx] = new Table(tableName, columns);
    }
    
    private static void createDB() throws Exception {
        if (conn == null) {
            throw new Exception("DB not connected");
        }
        
        FileReader reader = new FileReader("src/schoolmanagementsystem/database_tables/SQLSchema.sql");
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(reader));
        reader.close();
    }
    
    public static String tableName(String initTableName) {
        return Database.databaseName + "." + initTableName;
    }
    
    public static void connect(String user, String pass) throws Exception {
        conn = DriverManager.getConnection(connURL, user, pass);
        createDB();
        
        Database.tableNames = new String[9];
        Database.tables = new Table[9];
        
        Database.addTable("admins", new String[] {"id", "username", "password"});
        Database.addTable("attendances", new String[] {"id", "student_id", "session_id", "is_present", "reason", "updated_at"});
        Database.addTable("notes", new String[] {"id", "subject_id", "title", "content", "updated_at"});
        Database.addTable("professors", new String[] {"id", "username", "password", "name", "department"});
        Database.addTable("sessions", new String[] {"id", "held_at", "subject_id", "title", "description"});
        Database.addTable("students", new String[] {"id", "username", "password", "name", "section"});
        Database.addTable("subjects", new String[] {"id", "name", "units", "description", "professor_id"});
        Database.addTable("student_subjects", new String[] {"student_id", "subject_id"});
        Database.addTable("handled_subjects", new String[] {"professor_id", "subject_id"});
        System.out.println("Connected");
    }
}
