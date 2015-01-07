package db.parking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManage				//connection to data base 
{
	private static ConnectionManage instance = null;
	private String DBNAME = "hani";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private String host = "localhost"; // default 
	private String port = "3306";      // default
	private static final String H_CONN_STRING =
			"jdbc:hsqldb:data/explorecalifornia";
	private static final String M_CONN_STRING =
			"jdbc:mysql://localhost/hani";
	
	private static String driver = "com.mysql.jdbc.Driver"; // default
	
	/*
	
	 * */


	
	private DBType dbType = DBType.MYSQL;

	private Connection conn = null;

	private ConnectionManage() {
		
	}

	public static ConnectionManage getInstance() {
		if (instance == null) {
			 try {		           
		            Class.forName(driver).newInstance();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
			instance = new ConnectionManage();
		}
		return instance;
		
		/*		if (instance == null) {			//pld code
			instance = new ConnectionManage();
		}
		return instance;*/ 
	}

	public void setDBType(DBType dbType) {
		this.dbType = dbType;
	}

	private boolean openConnection()
	{
		try {
			System.out.print("hani here");
			switch (dbType) {
			
			case MYSQL:
				conn = DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
				return true;

			case HSQLDB:
				conn = DriverManager.getConnection(H_CONN_STRING, USERNAME, PASSWORD);
				return true;

			default: 
				return false;
			}
		}
		catch (SQLException e) {
			System.err.println(e);
			return false;
		}

	}

	public Connection getConnection()
	{
		if (conn == null) {
			if (openConnection()) {
				System.out.println("Connection opened");
				return conn;
			} else {
				return null;
			}
		}
		return conn;
	}

	public void close() {
		System.out.println("Closing connection");
		try {
			conn.close();
			conn = null;
		} catch (Exception e) {
		}
	}

}