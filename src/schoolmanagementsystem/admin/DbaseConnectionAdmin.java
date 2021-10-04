
package schoolmanagementsystem.admin;

import java.sql.*;


public class DbaseConnectionAdmin {
    /* jdbc:mysql://localhost:3306/admins?zeroDateTimeBehavior=CONVERT_TO_NULL */ 
    public static Connection connect;
    public static Statement jquery;
    public static String user = "root";
    public static String password = "";
    public static String strcon = "jdbc:mysql://localhost:3306/admins?zeroDateTimeBehavior=CONVERT_TO_NULL";

     public static void connection(){
        try{
            connect = DriverManager.getConnection(strcon,user,password);
            jquery = connect.createStatement();
            System.out.println("Connected\n");
            
            
        }catch (Exception e) {
            System.out.println("Connection Error");
        }
    }

}