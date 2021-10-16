/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_database;

import java.util.Iterator;
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
    
    private int getIndex() throws Exception {
        Table table = Database.get(this.getTableName());
        int index = -1;
        Object receivedId = this.getId();
        
        System.out.printf("receivedId type : %s\n", receivedId.getClass());
        
        Iterator iter = table.all().iterator();
        while (iter.hasNext()) {
            index++;
            JSONObject obj = (JSONObject) iter.next();
            Object gotId = obj.get("id");
            
            System.out.printf("gotId type : %s\n", gotId.getClass());
            
            boolean isInt = gotId instanceof Long && receivedId instanceof Integer && ((int) (long) gotId) == (int) receivedId;
            boolean isLong = gotId instanceof Long && receivedId instanceof Long && (long) gotId == (long) receivedId;
            boolean isString = gotId instanceof String && receivedId instanceof String && ((String) gotId).equals((String) receivedId);
            
            if (isInt || isLong || isString) {
                return index;
            }
        }
        
        return -1;
    }
    
    public void remove() throws Exception {
        Table table = Database.get(this.getTableName());
        int index = this.getIndex();
        if (index == -1) {
            throw new Exception("Unable to delete data.");
        }
        
        table.remove(index);
    }
    
    public void save() throws Exception {
        Table table = Database.get(this.getTableName());
        int index = this.getIndex();
        System.out.printf("index is %d", index);
        
        if (index == -1) {
            table.insert(this);
        } else {
            table.update(index, this);
        }
        
        this.reload();
    }
}
