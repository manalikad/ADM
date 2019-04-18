package adm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import adm.data.CityDetails;
import adm.data.GenericConstants;

public class CityDetailsRequest {
	String city="";
	MongoCollection<Document> transport_collection; 
	MongoCollection<Document> population_collection;
	MongoCollection<Document> industry_collection;
	MongoCollection<Document> real_collection;
	MongoCollection<Document> literacy_collection;
	CityDetails cd = new CityDetails();
	MongoCursor<Document>  it;
	
	/*Load collections from the database*/
	public void loadCollections() {
		MongoDBDataLoader db = new MongoDBDataLoader();
		MongoClient client = db.setDatabase();
		MongoDatabase database = client.getDatabase(GenericConstants.dbName);
		
		transport_collection = database.getCollection(GenericConstants.transportCollection);
		population_collection = database.getCollection(GenericConstants.populationCollection);
		industry_collection = database.getCollection(GenericConstants.industryCollection);
		real_collection = database.getCollection(GenericConstants.realEstateCollection);
		literacy_collection = database.getCollection(GenericConstants.literacyCollection);    
	}
	
	/*Enter input for city name from user*/
	public String getCityInput() {
		System.out.println("Enter city name");
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		String city = "";
		try {
			city = br.readLine();
		} catch (IOException e) {
			System.out.println("Enter valid city type");
		}
		return city;
	}
	
	public void getCityTransport() {
		try {
			BasicDBObject searchObject = new BasicDBObject();
			searchObject.put("City", java.util.regex.Pattern.compile(city));
			BasicDBObject projObject = new BasicDBObject();
			projObject.put("Transport_level",1);
			projObject.put("_id",0);
			FindIterable<Document> iterDoc = transport_collection.find(searchObject).projection(projObject); 
			it = iterDoc.iterator(); 
			cd.setTransportation(processIterator(it));
		} catch(Exception e) {System.out.println(e.toString());}	
	}
	
	public void getCityPopulation() {
		try {
			BasicDBObject searchObject = new BasicDBObject();
			searchObject.put("AccentCity", java.util.regex.Pattern.compile(city));
			BasicDBObject projObject = new BasicDBObject();
			projObject.put("Population_level",1);
			projObject.put("_id",0);
			BasicDBObject sortObject = new BasicDBObject();
			sortObject.put("Population_level",1);
			FindIterable<Document> iterDoc = population_collection.find(searchObject).sort(sortObject).projection(projObject).limit(1); 
			it = iterDoc.iterator(); 
			cd.setPopulation(processIterator(it));
		}catch(Exception e) {System.out.println(e.toString());}
	}
	
	public void getCityIndustries() {
		try {				
			BasicDBObject searchObject = new BasicDBObject();
			searchObject.put("City", java.util.regex.Pattern.compile(city));
			BasicDBObject sortObject = new BasicDBObject();
			sortObject.put("Rank", 1);
			BasicDBObject projObject = new BasicDBObject();
			projObject.put("Industry_Name",1);
			projObject.put("_id",0);
			FindIterable<Document> iterDoc = industry_collection.find(searchObject).sort(sortObject).projection(projObject).limit(5); 
			it = iterDoc.iterator(); 
			cd.setIndustryType(processIteratorForList(it));
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void getCityLiteracy() {
		try {	
			BasicDBObject searchObject = new BasicDBObject();
			searchObject.put("AccentCity", java.util.regex.Pattern.compile(city));
			BasicDBObject sortObject = new BasicDBObject();
			sortObject.put("Total", -1);
			BasicDBObject projObject = new BasicDBObject();
			projObject.put("Literacy_Rate",1);
			projObject.put("_id",0);
			FindIterable<Document> iterDoc = literacy_collection.find(searchObject).sort(sortObject).projection(projObject).limit(1); 
			it = iterDoc.iterator(); 	
			cd.setLiteracyRate(processIterator(it));
		} catch(Exception e) {System.out.println(e.toString());}
	}
	
	public void getRealEstate() {
		try {
			BasicDBObject searchObject = new BasicDBObject();
			searchObject.put("AccentCity", java.util.regex.Pattern.compile(city));
			BasicDBObject sortObject = new BasicDBObject();
			sortObject.put("Rent_Rate", 1);
			BasicDBObject projObject = new BasicDBObject();
			projObject.put("Rent_Rate",1);
			projObject.put("_id",0);
			FindIterable<Document> iterDoc = real_collection.find(searchObject).sort(sortObject).projection(projObject).limit(1); 
			it = iterDoc.iterator(); 
			cd.setRealEstate(processIterator(it));
		} catch(Exception e) {System.out.println(e.toString());}

	}
	
	/*Function to get values from json objects*/
	public String processIterator(MongoCursor<Document>  it) {
		String parameter = "";
		if (it == null) {
		    System.out.println("No values");
		}
			
		while (it.hasNext()) {  
			parameter = it.next().toString();
			parameter = parameter.substring(parameter.lastIndexOf("=")+1,parameter.indexOf("}"));
		}
		return parameter;
	}
	
	/*Function to get list of values from json object*/
	public List<String> processIteratorForList(MongoCursor<Document>  it) {
		List<String> listParameter = new ArrayList<String>();
		String parameter;
		if (it == null) {
		    System.out.println("No values");
		}
			
		while (it.hasNext()) {  
			parameter = it.next().toString();
			parameter = parameter.substring(parameter.lastIndexOf("=")+1,parameter.indexOf("}"));
			listParameter.add(parameter);
		}
		return listParameter;
	}
	
	/*Main function to get city details*/
	public void processCityDetails() {
		loadCollections();
		city = getCityInput();
		 if(null != city && !city.equals("")) {
			 cd.setCityName(city);
		 }
		
		getCityTransport();	
		getCityPopulation();
		getCityIndustries();
		getCityLiteracy();
		getRealEstate();
		System.out.println(cd.toString());
	}
}
