package com.demo;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoMain {

	static DB db = null;
	static DBCollection coll = null;

	static {
		Mongo m = null;
		try {
			// m = new Mongo();//默认本地
			// m = new Mongo("127.0.0.1");//默认端口
			m = new Mongo("127.0.0.1", 27017);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取名为 test 的数据库，不存在的情况下创建
		db = m.getDB("test");

		// 登录数据库（用户名:test，密码:test）
		// boolean auth = db.authenticate("test", "test".toCharArray());
		/*
		 * if(auth){ //只有admin中的用户才有此权限(show dbs) //获取所有数据库，不显示无collection的db
		 * //System.out.println("所有数据库名："+m.getDatabaseNames());
		 * 
		 * //获取名为 user 的collection(相当于表)，不存在的情况下创建 coll =
		 * db.getCollection("user"); }else{ System.out.println("登录失败!"); }
		 */
	}

	public static void main(String[] args) {
		MongoMain test = new MongoMain();
		if (coll != null) {
			test.saveData();
			test.searchData();
			test.updateData();
			test.deleteData();
		}
	}

	/**
	 * 保存数据
	 */
	@Test
	public void saveData() {
		// 录入学生1的信息
		DBObject stu1 = new BasicDBObject();

		stu1.put("name", "jack");
		stu1.put("age", "25");
		BasicDBObject sight1 = new BasicDBObject();
		sight1.put("left", 1.5);
		sight1.put("right", 1.2);
		stu1.put("sight", sight1);

		// 录入学生2的信息
		BasicDBObject stu2 = new BasicDBObject();
		stu2.put("name", "lucy");
		stu2.put("age", "22");
		BasicDBObject sight2 = new BasicDBObject();
		sight2.put("left", 1.0);
		sight2.put("right", 1.3);
		stu2.put("sight", sight2);

		// 注意：不能直接对sight赋值{left:1.0,right:1.3}

		coll.insert(stu1);
		coll.insert(stu2);
	}

	/**
	 * 查询数据
	 */
	public void searchData() {
		System.out.println("=======================");
		// show collections
		// 获取数据库下所有的collection，不显示无数据的collection
		Set<String> colls = db.getCollectionNames();
		showData(colls);

		System.out.println("=======================");

		// 查询coll中全部记录
		DBCursor ite = coll.find();
		showData(ite);

		System.out.println("=======================");

		// 获取第一条记录
		DBObject o = coll.findOne();
		System.out.println(o);

		System.out.println("=======================");

		// 统计colletion的数据条数
		System.out.println(coll.getCount());

		System.out.println("=======================");

		// 查询 name为jack的对象
		BasicDBObject query = new BasicDBObject();
		query.put("name", "jack");
		DBCursor it = coll.find(query);
		showData(it);

		System.out.println("=======================");

		// 查询age小于30，age不等于20的对象
		BasicDBObject query2 = new BasicDBObject();
		query2.put("age", new BasicDBObject("$lt", 30));
		query2.put("age", new BasicDBObject("$ne", 20));
		DBCursor it2 = coll.find(query2);
		showData(it2);
	}

	/**
	 * 修改数据
	 */
	public void updateData() {
		BasicDBObject query = new BasicDBObject();
		query.put("name", "lucy");
		// 这里的new_info对象一定要是find出的而不是new的，否则多字段的情况下就会丢失其它字段信息
		DBObject new_info = coll.findOne(query);

		// 方法一（缺点，必须把2个值都put进去）
		BasicDBObject sight = new BasicDBObject();
		sight.put("left", 1.3);
		sight.put("right", 1.3);
		new_info.put("sight", sight);

		// 方法二（优点，只需设置要修改的字段的值）
		DBObject obj = (DBObject) new_info.get("sight");
		obj.put("right", 1.5);

		coll.update(query, new_info);
	}

	/**
	 * 删除数据
	 */
	public void deleteData() {
		BasicDBObject data = new BasicDBObject();
		// 删除名称为lucy的记录
		data.put("name", "lucy");
		// 传入[空实例]删除所有
		coll.remove(data);
	}

	/**
	 * 遍历显示结果
	 * 
	 * @param result
	 */
	@SuppressWarnings("rawtypes")
	public void showData(Iterable result) {
		Iterator it = result.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}