/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagementsystem;

import database.Database;
import schoolmanagementsystem.common_gui.Login;

/**
 *
 * @author nedpals
 */
public class SchoolManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Database.connect();
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }
}
