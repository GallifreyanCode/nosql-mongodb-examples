package be.gallifreyan.morphia.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import be.gallifreyan.morphia.AbstractTest;
import be.gallifreyan.morphia.Developer;
import be.gallifreyan.morphia.Project;

public class DeveloperDaoTest extends AbstractTest {

	@Test
	public void testDao() {
		DeveloperDao dao = new DeveloperDao(morphia, mongo);
	
		Developer developer = new Developer();
		developer.setFirstName("testFirstName");
		developer.setLastName("testLastName");
		developer.setAge(30);
		Project project = new Project();
		project.setName("testProjectName");
		developer.setProject(project);
		
		Assert.assertNotNull(developer.getProject());
		dao.save(developer);
		Developer persistedDeveloper = dao.get(developer.getId());
		Assert.assertNotNull(persistedDeveloper);
		Assert.assertEquals(developer.getId(), persistedDeveloper.getId());
		
		checkDataIntegrity(persistedDeveloper);
	}
	
	@Test
	public void testFindByLastName() {
		DeveloperDao dao = new DeveloperDao(morphia, mongo);
		
		Developer developer = new Developer();
		developer.setFirstName("testFirstName");
		developer.setLastName("testLastName");
		developer.setAge(30);
		Project project = new Project();
		project.setName("testProjectName");
		developer.setProject(project);
		
		Assert.assertNotNull(developer.getProject());
		dao.save(developer);
		List<Developer> foundDevelopers = dao.findByLastName("testLastName");
		Assert.assertNotNull(foundDevelopers);
		Assert.assertEquals(1, foundDevelopers.size());
		
		checkDataIntegrity(foundDevelopers.get(0));
	}
}
