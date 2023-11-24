package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

	@BeforeEach
	void creation() throws SQLException {
		DBConnection.getConnection();
		Personne.createTable();
		Personne p1 = new Personne("Dujardin", "Jean");
		Personne p2 = new Personne("RD", "J");
		Personne p3 = new Personne("Scarlette", "Jonhson");
		Personne p4 = new Personne("Scarlette", "Jonhson");
		p1.save();
		p2.save();
		p3.save();
		p4.save();

	}

	@Test
	void testFindAll() throws SQLException {

		assertEquals(Personne.findAll().size(), 4);
	}

	@Test
	void testFindById() throws SQLException {
		assertEquals(Personne.findById(1).getNom(), "Dujardin");
		assertEquals(Personne.findById(2).getNom(), "RD");
		assertEquals(Personne.findById(3).getNom(), "Scarlette");
	}

	@Test
	void testFindByName() throws SQLException {
		assertEquals(Personne.findByName("Dujardin").size(), 1);
		assertEquals(Personne.findByName("RD").size(), 1);
		assertEquals(Personne.findByName("Scarlette").size(), 2);
	}

	@Test
	void testDelete() throws SQLException {
		Personne p1 = new Personne("Dujardin", "Jean");
		p1.save();
		assertEquals(Personne.findAll().size(), 5);
		Film.createTable();
		Film f1 = new Film("The Artist", 2);
		f1.save();
		p1.delete();
		assertEquals(Personne.findAll().size(), 4);
	}

	@AfterEach
	void destruction() throws SQLException {
		Personne.deleteTable();
	}

}