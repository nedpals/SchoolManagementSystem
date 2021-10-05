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
public class SchoolManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Database.connect();
        
        professor[] professors = professor.getAll();
        for(int i = 0; i<professors.length; i++){
            System.out.printf("%s %s %s %s %s \n", professors[i].id, professors[i].username, professors[i].name, professors[i].department, professors[i].handledSubjects);
        }
        
    }
    
}
