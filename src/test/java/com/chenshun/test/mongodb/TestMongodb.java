package com.chenshun.test.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/7/31 10:01  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 * <pre>
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::  FUCK YOU
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 * </pre>
 */
public class TestMongodb {

    @Test
    public void testMongoDB() {
        MongoClient mongoClient = null;
        try {
            // 连接到MongoDB服务 如果是远程连接可以替换“hadoop-senior01”为服务器所在IP地址
            // ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("hadoop-senior01", 27017);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);
            // MongoCredential.createScramSha1Credential() 三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.
                    createScramSha1Credential("test", "test_db", "123456".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            // 通过连接认证获取MongoDB连接
            mongoClient = new MongoClient(addrs, credentials);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test_db");
            System.out.println("Connect to database successfully");

            // 创建集合
            mongoDatabase.createCollection("test_mongo");
            System.out.println("集合创建成功");

            // 获取集合
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println("集合 test 选择成功");

            // 插入文档
            /**
             * 1. 创建文档 org.bson.Document 参数为key-value的格式
             * 2. 创建文档集合List<Document>
             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
             *    mongoCollection.insertOne(Document)
             */
            Document document = new Document("title", "MongoDB").append("description", "database").
                    append("likes", 100).append("by", "Fly");
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");

            // 检索所有文档
            /**
             * 1. 获取迭代器FindIterable<Document>
             * 2. 获取游标MongoCursor<Document>
             * 3. 通过游标遍历检索出的文档集合
             */
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            // 更新文档   将文档中likes=100的文档修改为likes=200
            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
            // 检索查看结果
            findIterable = collection.find();
            mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            // 删除符合条件的第一个文档
            collection.deleteOne(Filters.eq("likes", 200));
            // 删除所有符合条件的文档
            collection.deleteMany(Filters.eq("likes", 200));
            // 检索查看结果
            findIterable = collection.find();
            mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next());
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    @Test
    public void testGetContent() {
        MongoClient mongoClient = null;
        try {
            ServerAddress serverAddress = new ServerAddress("hadoop-senior01", 27017);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.
                    createScramSha1Credential("test", "test_db", "123456".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            mongoClient = new MongoClient(addrs, credentials);

            MongoDatabase mongoDatabase = mongoClient.getDatabase("test_db");
            System.out.println("Connect to database successfully");

            System.out.println("集合 test 中的内容:");
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            for (Document document : collection.find()) {
                System.out.println(document);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    @Test
    public void testAddContent() {
        MongoClient mongoClient = null;
        try {
            ServerAddress serverAddress = new ServerAddress("hadoop-senior01", 27017);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);
            MongoCredential credential = MongoCredential.
                    createScramSha1Credential("test", "test_db", "123456".toCharArray());
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);
            mongoClient = new MongoClient(addrs, credentials);

            MongoDatabase mongoDatabase = mongoClient.getDatabase("test_db");
            System.out.println("Connect to database successfully");

            MongoCollection<Document> collection = mongoDatabase.getCollection("test");

            Document document = new Document("title", "MongoDB").append("description", "database").
                    append("likes", 100).append("by", "Fly").append("content", "测试内容");
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");

            System.out.println("集合 test 中的内容:");
            for (Document doc : collection.find()) {
                System.out.println(doc);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

}
