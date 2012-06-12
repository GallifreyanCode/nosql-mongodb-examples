package be.gallifreyan.morphia.dao;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import be.gallifreyan.morphia.Developer;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

public class DeveloperDao extends BasicDAO<Developer, ObjectId> {

	public DeveloperDao(Morphia morphia, Mongo mongo ) {
		super(mongo, morphia, "my_database");
	}
	
	public List<Developer> findByLastName(String lastName) {
	    Pattern regExp = Pattern.compile(lastName + ".*", Pattern.CASE_INSENSITIVE);
	    return ds.find(entityClazz).filter("lastName", regExp).order("lastName").asList();
	}
}
