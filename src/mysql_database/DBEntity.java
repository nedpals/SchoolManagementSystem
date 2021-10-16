/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql_database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.simple.JSONObject;

/**
 *
 * @author Admin
 */
public abstract class DBEntity {
    public long id = Math.abs(Database.random.nextLong());
    public abstract JSONObject toJSON();
    public abstract String getTableName();
    public abstract void reload() throws Exception;
    
    public Object getId() {
        return this.id;
    }
    
    public void remove() throws Exception {
        Table table = Database.get(this.getTableName());        
        table.remove(id);
    }
    
    public boolean isExists() throws Exception {
//        SELECT EXISTS(SELECT * from ExistsRowDemo WHERE ExistId=104);
        String str = String.format("SELECT EXISTS(SELECT id from %s WHERE %s = ?)", Database.tableName(this.getTableName()), "id");
        PreparedStatement stmt = Database.conn.prepareStatement(str);
        Table.injectValue(stmt, 1, this.getId());
        ResultSet result = stmt.executeQuery();
        if (!result.next()) {
            return false;
        }
        
        boolean existValue = result.getBoolean(1);
        return existValue;
    }
    
    public void save() throws Exception {
        Table table = Database.get(this.getTableName());
        if (!this.isExists()) {
            table.insert(this);
        } else {
            table.update(this);
        }
        this.reload();
    }
}
