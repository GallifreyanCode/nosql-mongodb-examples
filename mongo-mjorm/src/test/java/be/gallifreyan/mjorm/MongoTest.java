package be.gallifreyan.mjorm;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.googlecode.mjorm.MongoDao;
import com.googlecode.mjorm.MongoDaoImpl;
import com.googlecode.mjorm.XmlDescriptorObjectMapper;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class MongoTest {

	@Test
	public void testConnection() throws MongoException, XPathExpressionException, ClassNotFoundException, IOException, ParserConfigurationException, SAXException {
		// connect to mongo
		Mongo mongo = new Mongo(new MongoURI("mongodb://localhost:27017")); // 10gen driver

		// create object mapper and add classes
		// create object mapper and add mapping files
		XmlDescriptorObjectMapper objectMapper = new XmlDescriptorObjectMapper();
		objectMapper .addXmlObjectDescriptor(new File("/mappings.xml"));

		// create MongoDao
		MongoDao dao = new MongoDaoImpl(mongo.getDB("dbName"), objectMapper);
	}
}
