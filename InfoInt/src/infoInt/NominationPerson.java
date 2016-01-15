package infoInt;

public class NominationPerson {

	private String name;
	private String film;
	private String role;
	private String won;
	private String category;
	private String ceremony;
	private String year;
	private Integer id;
	
	public NominationPerson() {
		this.name = null;
		this.film = null;
		this.role = null;
		this.won = null;
		this.category = null;
		this.ceremony = null;
		this.year = null;
		this.id = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilm() {
		return film;
	}

	public void setFilm(String film) {
		this.film = film;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getWon() {
		return won;
	}

	public void setWon(String won) {
		this.won = won;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCeremony() {
		return ceremony;
	}

	public void setCeremony(String ceremony) {
		this.ceremony = ceremony;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
