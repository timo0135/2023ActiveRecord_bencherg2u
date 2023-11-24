package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

	@BeforeEach
	public void creation() {
		DBConnection.setDbName("testpersonne");
		Film.createTable();
		Film f1 = new Film("The Artist", 1);
		Film f2 = new Film("The Artist2", 1);
		Film f3 = new Film("The Artist3", 1);
		f1.save();
		f2.save();
		f3.save();
	}

	@Test
	public void testFindAll() {
		assertEquals(Film.findAll().size(), 3);
	}

	@Test
	public void testFindById() {
		assertEquals(Film.findById(1).getTitre(), "The Artist");
		assertEquals(Film.findById(2).getTitre(), "The Artist2");
		assertEquals(Film.findById(3).getTitre(), "The Artist3");
	}

	@Test
	void testFindByRealisateur() {
		assertEquals(Film.findByRealisateur(1).size(), 3);
	}

	@Test
	void testDelete() {
		Film f1 = new Film("The Artist", 1);
		f1.save();
		assertEquals(Film.findAll().size(), 2);
		f1.delete();
		assertEquals(Film.findAll().size(), 1);
	}

	@AfterEach
	public void destruction() {
		Film.deleteTable();
	}

}