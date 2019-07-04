package in.vamsoft.empservice.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
private static Connection conn;  
   private static String url = "jdbc:mysql://localhost:3306/employee";  
   private static String user = "root";//Username of database  
   private static String pass = "root";//Password of database  
   public static Connection connect() throws SQLException{  
     try{  
       Class.forName("com.mysql.jdbc.Driver");  
     }catch(ClassNotFoundException cnfe){  
       System.err.println("Error: "+cnfe.getMessage());
     }  
     conn = DriverManager.getConnection(url,user,pass);  
     return conn;  
   }  
   public static Connection getConnection() throws SQLException, ClassNotFoundException{  
     if(conn !=null && !conn.isClosed())  
       return conn;  
     connect();  
     return conn;  
   }  
}

