/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import org.json.simple.JSONObject;

/**
 *
 * @author Admin
 */
abstract class DBEntity {
    protected int arrayIndex = -1;
    
    public abstract JSONObject toJSON();
    public abstract void save() throws Exception;
    
    public void setArrayIndex(int newIndex) {
        this.arrayIndex = newIndex;
    }
}
