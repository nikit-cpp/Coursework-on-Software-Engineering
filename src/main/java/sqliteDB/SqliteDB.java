package sqliteDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDB {
	Connection connection = null;
	Statement stmt = null;
	
	public SqliteDB(String dbFileName) throws ClassNotFoundException, SQLException {
		connect(dbFileName);
	}

	public void executeUpdate(String sql) throws SQLException {
		if(stmt!=null /*&& !stmt.isClosed()*/) stmt.close();
		stmt = connection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public ResultSet executeQuery(String sql) throws SQLException{
		if(stmt!=null /*&& !stmt.isClosed()*/) stmt.close();
		stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		return rs;
	}
	
	public void closeConnection() throws SQLException{
		stmt.close();
		connection.close();
	}
	
	private void connect(String dbFileName) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		
		String url = "jdbc:sqlite:" + dbFileName;
		connection = DriverManager.getConnection(url);
		System.out.println("Opened database "+dbFileName+" successfully");
	}
}
