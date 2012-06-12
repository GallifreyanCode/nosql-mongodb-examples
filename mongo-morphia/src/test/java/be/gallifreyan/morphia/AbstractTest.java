package be.gallifreyan.morphia;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public abstract class AbstractTest {
	protected Mongo mongo;
	protected Morphia morphia;
	protected Datastore dataStore;
	
	@Before
	public void setUp() throws MongoException, UnknownHostException {
		mongo = new Mongo(new MongoURI("mongodb://localhost:27017"));
		Assert.assertNotNull(mongo);
		morphia = new Morphia();
		Assert.assertNotNull(morphia);
		morphia.map(Developer.class).map(Project.class);
		dataStore = morphia.createDatastore(mongo, "my_database");
		Assert.assertNotNull(dataStore);
	}
	
	protected void checkDataIntegrity(Developer developer) {
		Assert.assertEquals("testFirstName", developer.getFirstName());
		Assert.assertEquals("testLastName", developer.getLastName());
		Assert.assertNotNull(developer.getProject());
		Assert.assertEquals("testProjectName", developer.getProject().getName());
	}
	
	@After
	public void tearDown() {
		/* Delete all */
		dataStore.delete(dataStore.createQuery(Developer.class));
		mongo.close();
	}
}
