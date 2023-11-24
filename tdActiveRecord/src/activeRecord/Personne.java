package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {
	private int id = -1;
	private String nom;
	private String prenom;


	public Personne(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	private Personne(int id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	public void save() throws SQLException {
		if (this.id == -1) {
			this.saveNew();
		} else {
			// mise a jour
			this.update();
		}
	}

	public int getId() {
		return this.id;
	}

	public void delete(){
		String SQLprepar = "DELETE FROM Film WHERE id_real=?;";
		String SQLprep = "DELETE FROM Personne WHERE id=?;";
		try {
			Connection connect = DBConnection.getConnection();
			if(DBConnection.tableExists("Film")){
				PreparedStatement prepar = connect.prepareStatement(SQLprepar);
				prepar.setInt(1, this.id);
				prepar.execute();
			}
			PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setInt(1, this.id);
			prep.execute();
			this.id = -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveNew() {

		// insertion
		String SQLprep = "insert into Personne (nom, prenom) values (?,?);";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setString(1, this.nom);
			prep.setString(2, this.prenom);
			prep.execute();
			// recuperation de l'id
			String SQLPrep = "SELECT * FROM Personne WHERE nom=? AND prenom=?;";
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setString(1, this.nom);
			prep1.setString(2, this.prenom);
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

	private void update(){
		// mise a jour
		String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.setString(1, this.nom);
			prep.setString(2, this.prenom);
			prep.setInt(3, this.id);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Personne findById(int id){
		String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setInt(1, id);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			if (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int identification = rs.getInt("id");
				return new Personne(identification,nom, prenom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Personne> findByName(String name){
		String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
		ArrayList<Personne> personnes = new ArrayList<>();
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.setString(1, name);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int id = rs.getInt("id");
				personnes.add( new Personne(id,nom, prenom));
			}
			return personnes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Personne> findAll(){
		ArrayList<Personne> personnes = new ArrayList<>();
		String SQLPrep = "SELECT * FROM Personne;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
			prep1.execute();
			ResultSet rs = prep1.getResultSet();
			// s'il y a un resultat
			while (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int id = rs.getInt("id");
				personnes.add(new Personne(id, nom, prenom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personnes;
	}

	public static void createTable(){
		String SQLprep = "CREATE TABLE IF NOT EXISTS Personne (id INTEGER PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(255), prenom VARCHAR(255));";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteTable(){
		String SQLprep = "DROP TABLE IF EXISTS Personne;";
		try {
			java.sql.Connection connect = DBConnection.getConnection();
			java.sql.PreparedStatement prep = connect.prepareStatement(SQLprep);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "(" + this.id + ") " + this.nom + ", " + this.prenom;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
