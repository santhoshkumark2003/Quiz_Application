
package quiz.application;

import java.sql.*;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn
{
    Connection c;
    Statement s;
    public conn(){  
        try{  
           
            c =DriverManager.getConnection("jdbc:mysql:///qapp","root","root");    
            s =c.createStatement();     
        }catch(Exception e)
        { 
            System.out.println(e);
        }  
}  
}