package activeRecord;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Film {
	private int id = -1;
	private String titre;
	private int id_real;

	public Film(String titre, int id_real) {
		this.titre = titre;
		this.id_real = id_real;
	}

	private Film(int id, String titre, int id_real) {
		this.id = id;
		this.titre = titre;
		this.id_real = id_real;
	}

	public Film(String titre, Personne realisateur) throws RealisateurAbsentException {
		this.titre = titre;
		if (realisateur == null) {
			throw new RealisateurAbsentException("Le realisateur n'existe pas");
		} else {
			this.id_real = realisateur.getId();
		}
	}

	//patron active record
	public void save() {
		if (this.id == -1) {
			try {
				this.saveNew();
			} catch (RealisateurAbsentException e) {
				e.getMessage();
			}
		} else {
			// mise a jour
			this.update();
		}
	}

	public void delete(){
		String SQLprep = "DELETE FROM Film WHERE id=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setInt(1, this.id);
			prep.execute();
			this.id = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveNew() throws RealisateurAbsentException {
		// insertion
		String SQLprep = "insert into Film (titre, id_real) values (?,?);";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setString(1, this.titre);
			prep.setInt(2, this.id_real);
			prep.execute();
			// recuperation de l'id
			String SQLPrep = "SELECT * FROM Film WHERE titre=? AND id_real=?;";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setString(1, this.titre);
			prep1.setInt(2, this.id_real);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			if (rs.next()) {
				this.id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		String SQLprep = "update Film set titre=?, id_real=? where id=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setString(1, this.titre);
			prep.setInt(2, this.id_real);
			prep.setInt(3, this.id);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Film findById(int id) {
		String SQLPrep = "SELECT * FROM Film WHERE id=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setInt(1, id);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			if (rs.next()) {
				String titre = rs.getString("titre");
				int id_real = rs.getInt("id_real");
				int identification = rs.getInt("id");
				return new Film(identification, titre, id_real);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Film> findAll(){
		ArrayList<Film> films = new ArrayList<Film>();
		String SQLPrep = "SELECT * FROM Film;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String titre = rs.getString("titre");
				int id_real = rs.getInt("id_real");
				int id = rs.getInt("id");
				films.add(new Film(id, titre, id_real));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	public static ArrayList<Film> findByRealisateur(int id_real){
		ArrayList<Film> films = new ArrayList<Film>();
		String SQLPrep = "SELECT * FROM Film WHERE id_real=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setInt(1, id_real);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String titre = rs.getString("titre");
				int id = rs.getInt("id");
				films.add(new Film(id, titre, id_real));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	public static void createTable(){
		String SQLprep = "CREATE TABLE IF NOT EXISTS Film (id INTEGER PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(255), id_real INTEGER);";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteTable(){
		String SQLprep = "DROP TABLE Film;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "Film [id=" + id + ", titre=" + titre + ", id_real=" + id_real + "]";
	}

	public int getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public int getId_real() {
		return id_real;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setId_real(int id_real) {
		this.id_real = id_real;
	}

	public Personne getRealisateur() {
		return Personne.findById(this.id_real);
	}


}
