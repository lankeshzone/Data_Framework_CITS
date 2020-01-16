package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtility {

	public void DB_Connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		String url = "jdbc:mysql://localhost/product"; 
   		String userName = "root";
   		String password = "";
   		String driversql = "com.mysql.jdbc.Driver";
   		Class.forName(driversql).newInstance();
		Connection conn = DriverManager.getConnection(url,userName,password);
				
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * from category");
		
		while (rs.next()) {
		    String s = rs.getString("catname");
		    System.out.println(s);
		    String category = "Test";// = Ecel
		    
		    if(s.equals(category)){
		    	System.out.println("pass");
		    	break;
		    }else{
		    	System.out.println("Fail");
		    }
		    
		}
	}


    public static String myDriver = "com.mysql.jdbc.Driver";
    public static String myUrl = "jdbc:mysql://localhost:3306/vl_results";


    public static Connection GetConnect(String aUser, String aPassword) {
        Connection conn = null;
        try {
            // create our mysql database connection
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, aUser, aPassword);

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return conn;
    }

    public static Connection GetConnect() {
        return GetConnect("root", "");
    }

	
}
