package com.demo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongdbUtils {

	@SuppressWarnings("deprecation")
	public static DB getDBtwo() {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("yourdb");
		return db;
	}

	@SuppressWarnings("resource")
	public static MongoDatabase getDB() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase db = mongoClient.getDatabase("runoob");
		return db;
	}

}
