/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql_database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import schoolmanagementsystem.Date2;

/**
 *
 * @author nedpals
 */
public class Table {
    public final String name;
    public final String[] columns;
    
    
    public Table(String name, String[] columns) {
        this.name = Database.tableName(name);
        this.columns = columns;
    }
    
    public JSONArray all() throws Exception {
        String str = "SELECT ";
        for (int i = 0; i < columns.length; i++) {
            String col = columns[i];
            str += col;
            if (i < columns.length - 1) {
                str += ", ";
            } else {
                str += " ";
            }
        }
        
        if (columns.length == 0) {
            str += "* ";
        }
        
        str += "FROM " + name;
        
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        ResultSet query = stmt.executeQuery();
        ResultSetMetaData meta = query.getMetaData();
        JSONArray data = new JSONArray();
        
        while (query.next()) {
            data.add(this.toJSON(query, meta));
        }
        
        return data;
    }
    
    public JSONArray getBy(String cond, Object[] values) throws Exception {
        String str = "SELECT ";
        for (int i = 0; i < columns.length; i++) {
            String col = columns[i];
            str += col;
            if (i < columns.length - 1) {
                str += ", ";
            } else {
                str += " ";
            }
        }
        
        if (columns.length == 0) {
            str += "* ";
        }
        
        str += String.format("FROM %s WHERE %s", name, cond);
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        for (int i = 0; i < values.length; i++) {
            Table.injectValue(stmt, i + 1, values[i]);
        }
        
        
        System.out.println(stmt.toString());
        ResultSet query = stmt.executeQuery();
        ResultSetMetaData meta = query.getMetaData();
        
        JSONArray data = new JSONArray();
        while (query.next()) {
            data.add(this.toJSON(query, meta));
        }
        
        return data;
    }
    
    public void remove(Object index) throws Exception {
        this.removeBy("id = ?", new Object[] {index});
    }
    
    public void removeBy(String cond, Object[] values) throws Exception {
        String str = String.format("DELETE FROM %s WHERE %s", cond);
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        for (int i = 0; i < values.length; i++) {
            Table.injectValue(stmt, i + 1, values[i]);
        }
        stmt.execute();
        this.save();
    }
    
    public void insert(DBEntity obj) throws Exception {
        this.insert(obj.toJSON());
    }
    
    public void insert(JSONObject newData) throws Exception {
        String str = "INSERT INTO " + name + " (";
        for (int i = 0; i < columns.length; i++) {
            str += columns[i];
            if (i < columns.length - 1) {
                str += ", ";
            }
        }
        str += ") VALUES (";
        for (int i = 0; i < columns.length; i++) {
            str += "?";
            if (i < columns.length - 1) {
                str += ", ";
            }
        }
        str += ")";
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        System.out.println(stmt.toString());
        for (int i = 0; i < columns.length; i++) {
            Object val = newData.get(columns[i]);
            Table.injectValue(stmt, i + 1, val);
        }
        
        stmt.execute();
        this.save();
    }
    
    public static void injectValue(PreparedStatement stmt, int i, Object val) throws SQLException {
        if (val instanceof Long) {
            stmt.setLong(i, (long) val);
        } else if (val instanceof Integer) {
            stmt.setInt(i, (int) val);
        } else if (val instanceof String) {
            // try if date
            try {
               Date2 jsonDate = Date2.fromJSON((String) val);
               stmt.setTimestamp(i, (Timestamp) jsonDate.get());
            } catch (Exception e) {  
                stmt.setString(i, (String) val);
            }
        } else if (val instanceof Boolean) {
            stmt.setBoolean(i, (boolean) val);
        }
    }
    
    public void update(DBEntity obj) throws Exception {
        this.update(obj.toJSON());
    }
    
    public void update(JSONObject updatedData) throws Exception {
        String str = "UPDATE " + name;
        // first column is skipped as it is assumed that it is a primary key
        str += " SET ";
        for (int i = 1; i < columns.length; i++) {
            str += columns[i] + " = ?";
            if (i < columns.length - 1) {
                str += ", ";
            }
        }
 
        str += " WHERE ";
        str += columns[0] + " = ?";
        System.out.println(str);
        
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        for (int i = 1; i < columns.length; i++) {
            Object val = updatedData.get(columns[i]);
            Table.injectValue(stmt, i, val);
        }
        
        Table.injectValue(stmt, columns.length, updatedData.get(columns[0]));
        stmt.execute();
        this.save();
    }
    
    public boolean save() throws IOException {
        return true;
    }
    
    private JSONObject toJSON(ResultSet query, ResultSetMetaData meta) throws Exception {
        JSONObject obj = new JSONObject();
        
        for (int i = 0; i < columns.length; i++) {
            int columnType = meta.getColumnType(i + 1);
            String col = columns[i];

            switch (columnType) {
                case Types.BIGINT:
                case Types.INTEGER:    
                    obj.put(col, query.getLong(col));
                    break;
                case Types.VARCHAR:
                    obj.put(col, query.getString(col));
                    break;
                case Types.BOOLEAN:
                    obj.put(col, query.getBoolean(col));
                    break;
                case Types.DATE:
                case Types.TIMESTAMP:
                    obj.put(col, new Date2((java.util.Date) query.getTimestamp(col)).toJSON());
                    break;
            }
        }
        
        return obj;
    }
}