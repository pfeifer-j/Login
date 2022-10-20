package de.uni_passau.fim.Login;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcTest implements Serializable {

	private static final long serialVersionUID = 1L;

	static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_HOST = "bueno.fim.uni-passau.de";

	// customization start
	private static final String DB_NAME = "pfeifer";
	private static final String DB_USER = "pfeifer";
	private static final String DB_PASSWORD = "Uxeiy6aiDoot";
	// customization end

	public static void main(String[] args) {
		Connection conn = null;

		try {
			System.out.println("Loading JDBC Driver");
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found");
			return;
		}

		Properties props = new Properties();
		props.setProperty("user", DB_USER);
		props.setProperty("password", DB_PASSWORD);
		props.setProperty("ssl", "true"); // necessary!

		// since jdbc driver 42.2.5 necessary to find ca chain in java keystore,
		// whereas older versions do this automatically and this setting leads
		// to an error
		props.setProperty("sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory");

		// insecure: omitting server cert validation, e.g., if root ca is not
		// contained in jvm cert store (OracleJDK and OpenJDK do contain
		// it automatically)
		// props.setProperty("sslfactory",
		// "org.postgresql.ssl.NonValidatingFactory");

		try {
			System.out.println("Opening Database Connection");
			conn = DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + "/" + DB_NAME, props);

			// do some sample work
			/*
			 * createTables(conn); insertData(conn); showContents(conn); deleteData(conn);
			 * showContents(conn); dropTables(conn);
			 */
			// addDemoData(conn);
			System.out.println("Demodata added.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close DB connection even if we have an exception
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("Exception while closing Database Connection:");
				e.printStackTrace();
			}
		}
	}

	private static Connection establishConn() {
		Connection conn = null;

		try {
			System.out.println("Loading JDBC Driver");
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found");
			return null;
		}

		Properties props = new Properties();
		props.setProperty("user", DB_USER);
		props.setProperty("password", DB_PASSWORD);
		props.setProperty("ssl", "true"); // necessary!

		// since jdbc driver 42.2.5 necessary to find ca chain in java keystore,
		// whereas older versions do this automatically and this setting leads
		// to an error
		props.setProperty("sslfactory", "org.postgresql.ssl.DefaultJavaSSLFactory");

		// insecure: omitting server cert validation, e.g., if root ca is not
		// contained in jvm cert store (OracleJDK and OpenJDK do contain
		// it automatically)
		// props.setProperty("sslfactory",
		// "org.postgresql.ssl.NonValidatingFactory");

		try {
			System.out.println("Opening Database Connection");
			conn = DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + "/" + DB_NAME, props);
			System.out.println("Connection established.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("An error occured while closing the connection.");
			e.printStackTrace();
		}
	}

	/*
	private static void addDemoData(Connection conn) throws SQLException {

		Statement stmt = conn.createStatement();

		stmt.executeUpdate("INSERT INTO AccountStore(username, password, name, birthday, address) "
				+ "VALUES ('henry01', 'password', 'Henry Miller', '8.8.8.8', 'Uganda'), "
				+ " ('alex_the_great', 'better:the_greatest', 'Alexander The Great', '1.2.3.4', 'Alexandria')");

		stmt.close();
	}
	*/

	/*
	 * private static void createTables(Connection conn) throws SQLException {
	 * 
	 * System.out.println("Creating Table");
	 * 
	 * Statement stmt = conn.createStatement();
	 * 
	 * stmt.executeUpdate( "CREATE TABLE test ( " +
	 * "  id      INTEGER not null primary key, " + "  value VARCHAR(30) " + ")" );
	 * 
	 * stmt.close(); }
	 * 
	 * private static void insertData(Connection conn) throws SQLException {
	 * System.out.println("Inserting Data");
	 * 
	 * Statement stmt = conn.createStatement();
	 * 
	 * stmt.executeUpdate( "INSERT INTO test (id, value) " + "VALUES (1, 'Apfel')"
	 * );
	 * 
	 * stmt.close(); }
	 * 
	 * private static void showContents(Connection conn) throws SQLException {
	 * System.out.println("Contents:");
	 * 
	 * Statement stmt = conn.createStatement(); ResultSet rst =
	 * stmt.executeQuery("SELECT * FROM test");
	 * 
	 * while(rst.next()) System.out.println("  " + rst.getInt("id") + "  " +
	 * rst.getString("value"));
	 * 
	 * stmt.close(); }
	 * 
	 * private static void deleteData(Connection conn) throws SQLException {
	 * System.out.println("Deleting Data");
	 * 
	 * Statement stmt = conn.createStatement();
	 * 
	 * stmt.executeUpdate("DELETE FROM test");
	 * 
	 * stmt.close(); }
	 * 
	 * private static void dropTables(Connection conn) throws SQLException {
	 * System.out.println("Dropping Table");
	 * 
	 * Statement stmt = conn.createStatement();
	 * stmt.executeUpdate("DROP TABLE test"); stmt.close(); }
	 */

	public static boolean checkLogin(String userName, String password) {
		Connection conn = establishConn();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(
					"SELECT * FROM login WHERE username = '" + userName + "' AND password = '" + password + "'");
			boolean entryExists = rst.next();
			stmt.close();
			closeConnection(conn);
			return entryExists;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static User getInfo(Login login) {
		Connection connection = establishConn();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT * FROM login WHERE username = '" + login.getUsername()
					+ "' AND password = '" + login.getPassword() + "'");
			rst.next();
			User user = new User(rst.getString("username"), rst.getString("password"), rst.getString("name"),
					rst.getString("address"), rst.getString("birthday"));

			stmt.close();
			closeConnection(connection);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateInfo(User user) {
		Connection connection = establishConn();

		try {
			Statement stmt = connection.createStatement();
			String execStmt = "UPDATE login SET username = '"+user.getUsername()+"', password = '"+user.getPassword()+"', name = '"+user.getName()+"', address = '"+user.getAddress()+"', birthday = '"+user.getBirthday()+"' WHERE username = '"+user.getUsername()+"';";
										
			stmt.executeUpdate(execStmt);
			stmt.close();

			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeConnection(connection);
	}

}