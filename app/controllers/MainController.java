package controllers;

import play.mvc.*;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by tzachit on 16/11/14.
 */
public class MainController extends Controller {

    public static Result index(){

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient( "localhost" , 27017 );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB db = mongoClient.getDB( "mydb" );
        DBCollection collection =  db.getCollection("users");
        collection.insert(new BasicDBObject("name", "Tsahi"));
        return ok("Finish");
    }
}
