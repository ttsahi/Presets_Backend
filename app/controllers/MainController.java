package controllers;

import com.mongodb.MongoClient;
import data.entities.Person;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.mvc.*;

import java.net.UnknownHostException;

/**
 * Created by tzachit on 16/11/14.
 */
public class MainController extends Controller {

    public static Result index(){

        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            Datastore ds = new Morphia().createDatastore(mongoClient, "SimpleDb");

            Person p = new Person();
            p.setName("Tsahi");
            p.setAge(24);

            ds.save(p);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ok("Finish");
    }
}
