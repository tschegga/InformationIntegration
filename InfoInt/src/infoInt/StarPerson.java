package infoInt;

public class StarPerson {

	private String name;
	private String category;
	private String address;
	private Integer id;
	
	public StarPerson() {
		this.name = null;
		this.category = null;
		this.address = null;
		this.id = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
