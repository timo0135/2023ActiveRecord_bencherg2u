package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static Connection instance = null;

	private static String dbName = "testpersonne";

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			// variables a modifier en fonction de la base
			String userName = "root";
			String password = "root";
			String serverName = "localhost";
			//Attention, sous MAMP, le port est 8889
			String portNumber = "8889";
			String tableName = "personne";

			// iL faut une base nommee testPersonne !
			String dbName = DBConnection.dbName;

			// creation de la connection
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			String urlDB = "jdbc:mysql://" + serverName + ":";
			urlDB += portNumber + "/" + dbName;
			instance = DriverManager.getConnection(urlDB, connectionProps);

		}
		return instance;
	}

	public static void setDbName(String dbName) {
		DBConnection.instance = null;
		DBConnection.dbName = dbName;
	}

	public static boolean tableExists(String tableName) throws SQLException {
		Connection connect = DBConnection.getConnection();
		String SQLprep = "SELECT * FROM ?;";
		try {
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setString(1, tableName);
			prep.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
