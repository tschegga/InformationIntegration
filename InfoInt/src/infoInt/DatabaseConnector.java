package infoInt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseConnector {
	
	private ArrayList<StarPerson> starPersonList;
	private ArrayList<NominationPerson> nominationPersonList;
	private ArrayList<CombinedPerson> combinedPersonList;
	
	// Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://eu-cdbr-azure-west-c.cloudapp.net/infointdb";

	// Database credentials
	static final String USER = "b1816e541cbcfb";
	static final String PASS = "f7e60334";
	
	// Database connection
	private Connection conn;
	
	public DatabaseConnector(String query) {
		
		this.starPersonList = new ArrayList<StarPerson>();
		this.nominationPersonList = new ArrayList<NominationPerson>();
		this.combinedPersonList = new ArrayList<CombinedPerson>();
		
		this.conn = null;
		
		try {
			connect();
			
			queryStarDatabase(query);
			
			queryNominationDatabase(query);
			
		} catch (SQLException | ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				
				conn.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
		
		mergeResults();
	}

	/**
	 * Sends a star query to the database;
	 * 
	 * @throws SQLException 
	 */
	private void queryStarDatabase(String query) throws SQLException {
		
		Statement stmt = null;
		stmt = conn.createStatement();
		
		query = "select tbl_person.name, tbl_category.name, tbl_star.address, tbl_person.idPerson " +
				"from tbl_category, tbl_star, tbl_person " +
				"where tbl_person.name = '" +
				query +
				"' and idCategory = tbl_star.category " +
				"and tbl_star.actor = tbl_person.idPerson;";
		
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			//TODO: Map result to Java objects
			StarPerson person = new StarPerson();
			person.setName(rs.getString(1));
			person.setCategory(rs.getString(2));
			person.setAddress(rs.getString(3));
			person.setId(rs.getInt(4));
			starPersonList.add(person);
		}
//		
		rs.close();
		stmt.close();
	}
	
	/**
	 * Sends a nomination query to the database;
	 * 
	 * @throws SQLException 
	 */
	private void queryNominationDatabase(String query) throws SQLException {
		
		Statement stmt = null;
		stmt = conn.createStatement();
		
		query = "select tbl_person.name, tbl_nominationfilm.role, tbl_nomination.won, tbl_film.name, tbl_category.name, tbl_ceremony.name, tbl_ceremony.year, tbl_person.idPerson" +
				" from tbl_person, tbl_nominationperson, tbl_nomination, tbl_nominationfilm, tbl_film, tbl_category, tbl_ceremony" +
				" where tbl_person.name = '" +
				query +
				"' and tbl_nominationperson.personid = tbl_person.idPerson" +
				" and tbl_nomination.idNomination = tbl_nominationperson.nominationid" +
				" and tbl_nominationfilm.nominationpersonid = tbl_nominationperson.id" +
				" and tbl_film.idFilm = tbl_nominationfilm.filmid" +
				" and tbl_nomination.category = tbl_category.idCategory" +
				" and tbl_ceremony.id = tbl_nomination.ceremony;";
		
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()) {
			//TODO: Map result to Java objects
			NominationPerson person = new NominationPerson();
			person.setName(rs.getString(1));
			person.setRole(rs.getString(2));
			person.setWon(rs.getString(3));
			person.setFilm(rs.getString(4));
			person.setCategory(rs.getString(5));
			person.setCeremony(rs.getString(6));
			person.setYear(rs.getString(7));
			person.setId(rs.getInt(8));
			nominationPersonList.add(person);
		}

		rs.close();
		stmt.close();
	}

	/**
	 * Connects to the MYSQL Database.
	 * 
	 * @throws ClassNotFoundException 
	 */
	private void connect() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}
	
	/**
	 * Checks wheter there are the same persons in the starPersonList
	 * and in the nominationPersonList and if so it merges them in a
	 * new CombinedPerson Object
	 */
	private void mergeResults() {
		if(!nominationPersonList.isEmpty()) {
			Iterator<NominationPerson> nIterator = nominationPersonList.iterator();
			while(nIterator.hasNext()) {
				NominationPerson nPerson = nIterator.next();
				
				// If combinedPersonList is empty or the person is not contained in it.
				if(combinedPersonList.isEmpty() | !contains(nPerson)) {
					CombinedPerson cP = new CombinedPerson(nPerson.getName(), nPerson.getId());
					cP.addNominationPerson(nPerson);
					combinedPersonList.add(cP);
				}
				else {
					find(nPerson);
				}
			}
		}
			
		if(!starPersonList.isEmpty()) {
			Iterator<StarPerson> sIterator = starPersonList.iterator();
			while(sIterator.hasNext()) {
				StarPerson sPerson = sIterator.next();
				
				if(combinedPersonList.isEmpty() | !contains(sPerson)) {
					CombinedPerson cP = new CombinedPerson(sPerson.getName(), sPerson.getId());
					cP.addStarPerson(sPerson);
					combinedPersonList.add(cP);
				}
				else {
					find(sPerson);
				}
			}
		}
	}
	
	/**
	 * Checks if the given Person is already in the combinedPersonList.
	 * 
	 * @param sourceList
	 * @return
	 */
	private boolean contains(StarPerson person) {
		if(combinedPersonList.isEmpty()) {
			return false;
		}
		else {
			Iterator<CombinedPerson> iterator = combinedPersonList.iterator();
			while(iterator.hasNext()) {
				CombinedPerson cPerson = iterator.next();
				if (cPerson.getID().equals(person.getId())){
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Checks if the given Person is already in the combinedPersonList.
	 * 
	 * @param sourceList
	 * @return
	 */
	private boolean contains(NominationPerson person) {
		if(combinedPersonList.isEmpty()) {
			return false;
		}
		else {
			Iterator<CombinedPerson> iterator = combinedPersonList.iterator();
			while(iterator.hasNext()) {
				CombinedPerson cPerson = iterator.next();
				if (cPerson.getID().equals(person.getId())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Finds an already existing person and adds additional
	 * info to it.
	 * 
	 * @param person
	 */
	private void find(NominationPerson person) {
		Iterator<CombinedPerson> iterator = combinedPersonList.iterator();
		while(iterator.hasNext()) {
			CombinedPerson cP = iterator.next();
			if(cP.getID().equals(person.getId())) {
				cP.addNominationPerson(person);
			}
		}
	}
	
	/**
	 * Finds an already existing person and adds additional
	 * info to it.
	 * 
	 * @param person
	 */
	private void find(StarPerson person) {
		Iterator<CombinedPerson> iterator = combinedPersonList.iterator();
		while(iterator.hasNext()) {
			CombinedPerson cP = iterator.next();
			if(cP.getID().equals(person.getId())) {
				cP.addStarPerson(person);
			}
		}
	}
	
	/**
	 * Returns the result of the query as Java object
	 * 
	 * @return the query result
	 */
	public ArrayList<CombinedPerson> getResult() {
		return combinedPersonList;
	}
}
