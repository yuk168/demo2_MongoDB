package com.demo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pojo.Address;
import com.pojo.Person;

public class demo3 {

	// 插入一条数据
	@Test
	public void test1() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		MongoDatabase database = mongoClient.getDatabase("yourdb");
		database = database.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> collection = MongdbUtils.getDB().getCollection("people", Person.class);
		collection = collection.withCodecRegistry(pojoCodecRegistry);
		Person ada = new Person("Ada Byron2", 202, new Address("St James Square2", "London2", "W12"));
		collection.insertOne(ada);
	}

	// 查询所有数据
	@Test
	public void test2() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClient mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		MongoDatabase database = mongoClient.getDatabase("yourdb");
		database = database.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> collection = MongdbUtils.getDB().getCollection("people", Person.class);
		collection = collection.withCodecRegistry(pojoCodecRegistry);
		Block<Person> printBlock = new Block<Person>() {
			@Override
			public void apply(final Person person) {
				System.out.println(person);
			}
		};

		collection.find().forEach(printBlock);
	}

	// 插入多条数据
	@Test
	public void test3() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClient mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		MongoDatabase database = mongoClient.getDatabase("yourdb");
		database = database.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> collection = MongdbUtils.getDB().getCollection("people", Person.class);
		collection = collection.withCodecRegistry(pojoCodecRegistry);

		List<Person> people = asList(
				new Person("Charles Babbage", 45, new Address("5 Devonshire Street", "London", "W11")),
				new Person("Alan Turing", 28, new Address("Bletchley Hall", "Bletchley Park", "MK12")),
				new Person("Timothy Berners-Lee", 61, new Address("Colehill", "Wimborne", null)));
		collection.insertMany(people);

	}

	// Get A Single Person That Matches a Filter
	@Test
	public void test4() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClient mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		MongoDatabase database = mongoClient.getDatabase("yourdb");
		database = database.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> collection = MongdbUtils.getDB().getCollection("people", Person.class);
		collection = collection.withCodecRegistry(pojoCodecRegistry);

		Person somebody = collection.find(eq("address.city", "Wimborne")).first();
		System.out.println(somebody);
	}

	// Get All Person Instances That Match a Filter
	@Test
	public void test5() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClient mongoClient = new MongoClient("localhost",
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		MongoDatabase database = mongoClient.getDatabase("yourdb");
		database = database.withCodecRegistry(pojoCodecRegistry);
		MongoCollection<Person> collection = MongdbUtils.getDB().getCollection("people", Person.class);
		collection = collection.withCodecRegistry(pojoCodecRegistry);
		Block<Person> printBlock = new Block<Person>() {
			@Override
			public void apply(final Person person) {
				System.out.println(person);
			}
		};
		collection.find(gt("age", 30)).forEach(printBlock);
	}

}
