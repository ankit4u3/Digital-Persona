import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FingerDB {

	private static final String tableName = "users";
	private static final String userColumn = "userID";
	private static final String print1Column = "print1";
	private static final String print2Column = "print2";

	private String URL = "jdbc:mysql://localhost:3306/";
	private String host;
	private String database;
	private String userName;
	private String pwd;
	private java.sql.Connection connection = null;
	private String preppedStmtInsert = null;
	private String preppedStmtUpdate = null;

	public class Record {
		String userID;
		byte[] fmdBinary;

		Record(String ID, byte[] fmd) {
			userID = ID;
			fmdBinary = fmd;
		}
	}

	public FingerDB(String _host, String db, String user, String password) {
		database = db;
		userName = user;
		pwd = password;
		host = _host;

		URL = "jdbc:mysql://" + host + ":3306/";
		preppedStmtInsert = "INSERT INTO " + tableName + "(" + userColumn + ","
				+ print1Column + ") VALUES(?,?)";
	}

	@Override
	public void finalize() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Open() throws SQLException {
		connection = DriverManager.getConnection(URL + database, userName, pwd);
	}

	public void Close() throws SQLException {
		connection.close();
	}

	public boolean UserExists(String userID) throws SQLException {
		String sqlStmt = "Select " + userColumn + " from " + tableName
				+ " WHERE " + userColumn + "='" + userID + "'";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(sqlStmt);
		return rs.next();
	}

	public void Insert(String userID, byte[] print1) throws SQLException {
		java.sql.PreparedStatement pst = connection
				.prepareStatement(preppedStmtInsert);
		pst.setString(1, userID);
		pst.setBytes(2, print1);
		pst.execute();
	}

	public List<Record> GetAllFPData() throws SQLException {
		List<Record> listUsers = new ArrayList<Record>();
		String sqlStmt = "Select * from " + tableName;
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(sqlStmt);
		while (rs.next()) {
			if (rs.getBytes(print1Column) != null)
				listUsers.add(new Record(rs.getString(userColumn), rs
						.getBytes(print1Column)));
		}
		return listUsers;
	}

	public String GetConnectionString() {
		return URL + " User: " + this.userName;
	}

	public String GetExpectedTableSchema() {
		return "Table: " + tableName + " PK(VARCHAR(32)): " + userColumn
				+ "VARBINARY(4000): " + print1Column;
	}
}
