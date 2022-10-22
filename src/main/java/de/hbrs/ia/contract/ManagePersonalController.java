package de.hbrs.ia.contract;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
public class ManagePersonalController implements ManagePersonal {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> salesmen;
    private MongoCollection<Document> performanceRecords;

    public ManagePersonalController() {
        this.client = new MongoClient("localhost", 27017);
        this.database = client.getDatabase("hooverdb");
    }

    public ManagePersonalController(String host, int port, String db) {
        this.client = new MongoClient(host, port);
        this.database = client.getDatabase(db);

    }

    @Override
    public void createSalesMan(SalesMan record) {
        //TODO create new DB entry
        salesmen.insertOne(record.toDocument());
    }

    @Override
    public void addPerformanceRecord(EvaluationRecord record, int sid) {
        //TODO Add evalRecord to existing SalesMan (exception if Salesman not found)
        performanceRecords.insertOne(record.toDocument());
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        //TODO read DB entry
        Document salesmenDoc = this.salesmen.find(eq("id", sid)).first();
        String firstname = salesmenDoc.getString("firstname");
        String lastname = salesmenDoc.getString("lastname");
        int id = salesmenDoc.getInteger("id");

        return new SalesMan(firstname, lastname, id);
    }

    @Override
    public List<SalesMan> querySalesMan(String attribute, String key) {
        //TODO read DB entry
        List<SalesMan> salesmenList = new ArrayList<>();
        List<Document> results = new ArrayList<>();

        FindIterable<Document> cursor = salesmen.find(eq(attribute, key));

        for (Document document : cursor) {
            results.add(document);
        }

        for(Document result : results){
            salesmenList.add(new SalesMan(result.getString("firstname"),
                                        result.getString("lastname"), result.getInteger("id")));
        }

        return salesmenList;
    }

    @Override
    public EvaluationRecord readEvaluationRecords(int sid) {
        //TODO read evalRecord from existing SalesMan (exception if Salesman not found)
        

        return null;
    }

    //TODO CRUD -> Create Read Update Delete -> Create and Read represented, implement Update and Delete functions
}
