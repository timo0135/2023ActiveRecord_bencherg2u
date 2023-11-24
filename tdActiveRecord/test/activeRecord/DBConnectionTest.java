package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

	@Test
	void getConnection() throws SQLException {
		Connection connect = DBConnection.getConnection();
		Connection connect2 = DBConnection.getConnection();
		assertEquals(connect, connect2);
	}

	@Test
	void setDbName() throws SQLException {
		Connection connect = DBConnection.getConnection();
		DBConnection.setDbName("test");
		Connection connect2 = DBConnection.getConnection();
		assertNotEquals(connect, connect2);
	}
}