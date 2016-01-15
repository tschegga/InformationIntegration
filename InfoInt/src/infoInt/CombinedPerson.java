package infoInt;

import java.util.ArrayList;

public class CombinedPerson {

	private String name;
	private Integer id;
	private ArrayList<StarPerson> starPersonList;
	private ArrayList<NominationPerson> nominationPersonList;
	
	public CombinedPerson(String name, Integer id) {
		this.name = name;
		this.id = id;
		this.starPersonList = new ArrayList<StarPerson>();
		this.nominationPersonList = new ArrayList<NominationPerson>();
	}
	
	public void addNominationPerson(NominationPerson person) {
		nominationPersonList.add(person);
	}
	
	public void addStarPerson(StarPerson person) {
		starPersonList.add(person);
	}
	
	public Integer getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<StarPerson> getStarPersonList() {
		return starPersonList;
	}

	public ArrayList<NominationPerson> getNominationPersonList() {
		return nominationPersonList;
	}
	
	
}
