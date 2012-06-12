package be.gallifreyan.morphia;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Project {
	private String name;
	
	public Project() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
