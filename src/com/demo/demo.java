package com.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class demo {

/*	@Test
	public void save() {
 
		DBCollection collection= MongdbUtils.getDB().getCollection("yourCollection");   
		DBObject document= new BasicDBObject();  
		document.put("id", 1003);
		document.put("msg","hello world mongoDB in Java222");  
		collection.insert(document);
		DBObject searchQuery= new BasicDBObject(); 
		searchQuery.put("id", 1003);
		DBCursor cursor= collection.find(searchQuery);    
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		System.out.println("Done");       
	}*/
	
	//插入一个
	@Test
	public void save() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");   
		Document document = new Document("id", 1100).append("msg", "hello word!22");
		collection.insertOne(document);
	}
	
	
	//插入多个
	@Test
	public void savemany(){
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		
		Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
		
		collection.insertOne(doc);
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
	}
	
	//插入多个
	@Test
	public void savemany2(){
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < 100; i++) {
		    documents.add(new Document("i", i));
		}
		collection.insertMany(documents);
		//documents不需要遍历，这里直接就可以打印出来。
		System.out.println(documents);
	}
	
	
	
	@Test
	public void update(){
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		
		collection.updateMany(Filters.eq("id",1004), new Document("$set", new Document("id", 1100)));
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	
	@Test
	public void delete(){
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		collection.deleteOne(Filters.eq("id",1100));
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}
