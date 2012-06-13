package be.gallifreyan.mjorm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.googlecode.mjorm.MongoDao;
import com.googlecode.mjorm.MongoDaoImpl;
import com.googlecode.mjorm.XmlDescriptorObjectMapper;
import com.googlecode.mjorm.query.DaoQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoTest {
	private static final String URI = "mongodb://localhost:27017";
	private static final String DBNAME = "dbName";
	private Mongo mongo;
	private XmlDescriptorObjectMapper objectMapper;
	protected DBCollection collection;
	protected DB db;

	@Before
	public void setUp() throws MongoException, URISyntaxException,
			XPathExpressionException, ClassNotFoundException, IOException,
			ParserConfigurationException, SAXException {
		mongo = new Mongo(new MongoURI(URI));
		Assert.assertNotNull(mongo);
		objectMapper = new XmlDescriptorObjectMapper();
		Assert.assertNotNull(objectMapper);
		Path path = Paths.get(this.getClass().getResource("/mappings.xml")
				.toURI());
		Assert.assertTrue(path.toFile().exists());
		objectMapper.addXmlObjectDescriptor(path.toFile());
		
		/* MANUAL */
		db = mongo.getDB(DBNAME);
		for (String c : db.getCollectionNames()) {
			db.getCollection(c).drop();
		}

		db.createCollection("test", new BasicDBObject());
		collection = db.getCollection("test");
	}

	@Test
	public void testConnection() {
		MongoDao dao = new MongoDaoImpl(mongo.getDB(DBNAME), objectMapper);
		Assert.assertEquals(DBNAME, dao.getDB().getName());
	}

	@Test
	public void testCreate() {
		MongoDao dao = new MongoDaoImpl(mongo.getDB(DBNAME), objectMapper);

		DBObject object = BasicDBObjectBuilder.start()
				.add("firstName", "firstName").add("lastName", "lastName")
				.push("project").add("name", "name").pop().get();
		collection.insert(object);

		DaoQuery query = dao.createQuery();
		// query.all(property, values)
		// query.eq("firstName", "firstNameTest");
		// query.eq("lastName", "Banner");
		// query.in("aliases", "Hulk", "The Hulk", "His Hulkness");
		//
		// ObjectIterator<Developer> people =
		// query.findObjects(Developer.class); // expecting multiple
		Developer person = query.findObject(Developer.class); // expecting
		// one result
		// Assert.assertNotNull(people);
	}

	@After
	public void tearDown() {
		if (mongo != null) {
			mongo.dropDatabase(DBNAME);
			mongo.close();
			mongo = null;
		}
	}
}
