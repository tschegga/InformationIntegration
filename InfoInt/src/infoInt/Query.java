package infoInt;

public class Query {

	private Object result;
	
	public Query(String query) {
		
		DatabaseConnector connection = new DatabaseConnector(transformQuery(query));
		
	}
	
	/**
	 * Transforms the user input into a valid SQL-statement.
	 * 
	 * @param query
	 * @return SQL statement as String
	 */
	private String transformQuery(String query) {
		
		//TODO: Transform query here.
		
		return null;
	}

	/**
	 * Returns the result of the query as a Java object.
	 * 
	 * @return the object
	 */
	public Object getResult() {

		//TODO: Implement
		
		return result;
	}
	
}
