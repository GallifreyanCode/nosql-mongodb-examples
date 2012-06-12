package be.gallifreyan.morphia;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MongoTest extends AbstractTest {
	
	@Test
	public void testDeveloper() {
		Developer developer = new Developer();
		developer.setFirstName("testFirstName");
		developer.setLastName("testLastName");
		
		Assert.assertNotNull(developer);
		Assert.assertEquals("testFirstName", developer.getFirstName());
		Assert.assertEquals("testLastName", developer.getLastName());
	}
	
	@Test
	public void testProject() {
		Project project = new Project();
		project.setName("testProjectName");
		
		Assert.assertNotNull(project);
		Assert.assertEquals("testProjectName", project.getName());
	}
	
	@Test
	public void testCreate() {
		Developer developer = new Developer();
		developer.setFirstName("testFirstName");
		developer.setLastName("testLastName");
		developer.setAge(30);
		Project project = new Project();
		project.setName("testProjectName");
		developer.setProject(project);
		
		Assert.assertNotNull(developer.getProject());
		dataStore.save(developer);
		Developer persistedDeveloper = dataStore.get(Developer.class, developer.getId());
		Assert.assertNotNull(persistedDeveloper);
		Assert.assertEquals(developer.getId(), persistedDeveloper.getId());
		
		checkDataIntegrity(persistedDeveloper);
	}
	
	@Test
	public void testQuery() {
		testCreate();
		List<Developer> seniorDevelopers = dataStore.find(Developer.class, "age >=", 30).asList();
		Assert.assertNotNull(seniorDevelopers);
		Assert.assertEquals(1, seniorDevelopers.size());
		Developer foundDeveloper = seniorDevelopers.get(0);
		Assert.assertNotNull(foundDeveloper);
		checkDataIntegrity(foundDeveloper);
	}
	
	@Test
	public void testQueryAlternative() {
		testCreate();
		List<Developer> seniorDevelopers = dataStore.find(Developer.class).field("age").greaterThanOrEq(30).asList();
		Assert.assertNotNull(seniorDevelopers);
		Assert.assertEquals(1, seniorDevelopers.size());
		Developer foundDeveloper = seniorDevelopers.get(0);
		Assert.assertNotNull(foundDeveloper);
		checkDataIntegrity(foundDeveloper);
	}
	
	@Test
	public void testQueryEmpty() {
		List<Developer> seniorDevelopers = dataStore.find(Developer.class, "age >=", 30).asList();
		Assert.assertNotNull(seniorDevelopers);
		Assert.assertEquals(0, seniorDevelopers.size());
		seniorDevelopers = null;
		
	}
	
	@Test
	public void testQueryEmptyAlternative() {
		List<Developer> seniorDevelopers = dataStore.find(Developer.class).field("age").greaterThanOrEq(30).asList();
		Assert.assertNotNull(seniorDevelopers);
		Assert.assertEquals(0, seniorDevelopers.size());
	}
	
	
}
