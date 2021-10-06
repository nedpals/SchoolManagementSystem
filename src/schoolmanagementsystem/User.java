/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

/**
 *
 * @author nedpals
 */
public abstract class User extends DBEntity {
    public String username;
    protected String password;
    
//    public abstract void login(String username, String password) throws Exception;
//    public abstract void logout();
    public void setPassword(String newPassword) {
        this.password = newPassword;
    };
}
