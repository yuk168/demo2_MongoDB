package com.demo;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class demo2 {

	@Test
	public void findAll() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		// collection,需要遍历才能查询出来
		FindIterable<Document> find = collection.find();
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	@Test
	public void find2() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		FindIterable<Document> find = collection.find(Filters.eq("id", 1100));
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

	@Test
	public void find3() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		long count = collection.count();
		System.out.println(count);

	}

	// 查询collection总id
	@Test
	public void find5() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		DBObject keys = new BasicDBObject();
		keys.put("id", 1);
		long count = collection.count((Bson) keys);
		System.out.println(count);

	}

	// 查询collection中的第一个数据
	@Test
	public void find6() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		Document first = collection.find().first();
		System.out.println(first);
		System.out.println("=====================");
		System.out.println(first.toJson());

	}

	// 根据条件来查询
	// 查询id是1100的数据
	@Test
	public void find7() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		FindIterable<Document> find = collection.find(new Document("id", 1100));
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// 查询id大于1002的数据
	@Test
	public void find8() {

		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("runoob");
		// 下面这里有2种查询方法
		// FindIterable<Document> find = collection.find(new Document("id",new
		// Document("$gt", 1002)));
		FindIterable<Document> find = collection.find(Filters.eq("id", 10001));
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().toJson());
		}
	}

	// 根据id倒序排序
	@Test
	public void find9() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("yourCollection");
		FindIterable<Document> sort = collection.find().sort(Sorts.orderBy(Sorts.descending("id")));
		MongoCursor<Document> iterator = sort.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// mongodb 返回指定字段
	@Test
	public void find10() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("runoob");
		DBObject keys = new BasicDBObject();
		// 这里定义的是，要返回的指定的字段
		// keys.put("_id", 1);
		keys.put("id", 1);
		// 查询条件，用projection来写。
		FindIterable<Document> projection = collection.find().sort(Sorts.orderBy(Sorts.descending("id")))
				.projection((Bson) keys);
		MongoCursor<Document> iterator = projection.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().toJson());
		}
	}

	@Test
	public void find11() {
		MongoCollection<Document> collection = MongdbUtils.getDB().getCollection("runoob");

		AggregateIterable<Document> aggregate = collection.aggregate(Arrays
				.asList(Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("id")))));
		MongoCursor<Document> iterator = aggregate.iterator();

		while (iterator.hasNext()) {
			System.out.println(iterator.next().toJson());
		}

		/*
		 * FindIterable<Document> find = collection.find(Filters.eq("id",
		 * 10001)); MongoCursor<Document> iterator = find.iterator(); while
		 * (iterator.hasNext()) { System.out.println(iterator.next()); }
		 */

	}

	/*
	 * BasicDBObject query = new BasicDBObject("vipid", 3); DBObject findOne =
	 * collection.findOne(query); System.out.println(findOne);
	 */

}