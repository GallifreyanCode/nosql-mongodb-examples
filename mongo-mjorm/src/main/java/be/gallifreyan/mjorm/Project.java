package be.gallifreyan.mjorm;

import com.googlecode.mjorm.annotations.Entity;
import com.googlecode.mjorm.annotations.Property;

@Entity
public class Project {
	private String name;
	
	public Project() {
		
	}
	
	@Property
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
