package de.hbrs.ia.contract;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class ManagePersonalController implements ManagePersonal {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> salesmen;
    private MongoCollection<Document> evaluationRecords;

    public ManagePersonalController() {
        this.client = new MongoClient("localhost", 27017);
        this.database = client.getDatabase("hooverdb");
    }

    public ManagePersonalController(String host, int port, String db) {
        this.client = new MongoClient(host, port);
        this.database = client.getDatabase(db);
        this.salesmen = database.getCollection("salesmen");
        this.evaluationRecords = database.getCollection("evaluation_records");

    }

    //For testing
    public MongoCollection<Document> getCollection(String collection){
        return database.getCollection(collection);
    }

    @Override
    public void createSalesMan(SalesMan record) {
        //TODO create new DB entry
        salesmen.insertOne(record.toDocument());
    }

    @Override
    public void addPerformanceRecord(EvaluationRecord record, int sid) {
        //TODO Add evalRecord to existing SalesMan (exception if Salesman not found)
        record.setSalesmanId(sid);
        evaluationRecords.insertOne(record.toDocument());
    }

    @Override
    public SalesMan readSalesMan(int sid) throws Exception {
        //TODO read DB entry
        Document salesmenDoc = this.salesmen.find(eq("id", sid)).first();
        if(salesmenDoc == null){
            throw new Exception("Salesman with the ID " +  sid + " not found.");
        }
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

        for (Document result : results) {
            salesmenList.add(new SalesMan(result.getString("firstname"),
                    result.getString("lastname"), result.getInteger("id")));
        }

        return salesmenList;
    }

    @Override
    public EvaluationRecord readEvaluationRecord(int sid) throws Exception {
        //TODO read evalRecord from existing SalesMan (exception if Salesman not found)

        Document evalRecord = evaluationRecords.find(eq("salesman_id", sid)).first();

        if (evalRecord == null) {
            throw new Exception(("Evaluation Record for Salesman with the ID " + sid + " not found."));
        }


        return new EvaluationRecord(evalRecord);
    }
    //TODO CRUD -> Create Read Update Delete -> Create and Read represented, implement Update and Delete functions
    @Override
    public boolean updateSalesMan(int sid, String key, Object value) {
       UpdateResult res = salesmen.updateOne(eq("id", sid), set(key, value));
       return res.wasAcknowledged();
    }

    @Override
    public boolean updateEvaluationRecord(int sid, String key, Object value) {
        UpdateResult res = evaluationRecords.updateOne(eq("salesman_id", sid), set(key, value));
        return res.wasAcknowledged();
    }

    @Override
    public SalesMan deleteSalesman(int sid) throws Exception {
        Document salesman = salesmen.find(eq("id", sid)).first();
        // Checks if Salesman exists
        if (salesman == null){
            throw new Exception("Salesman with the ID " + sid + " not found.");
        }

        DeleteResult result = salesmen.deleteOne(eq("id", sid));
        //Check if deletion of Salesman was successful
        if(!result.wasAcknowledged()){
            throw new Exception("Deletion of Salesman was not successful");
        }

        return new SalesMan( salesman.getString("firstname"), salesman.getString("lastname"), salesman.getInteger("id"));
    }
    @Override
    public EvaluationRecord deleteEvaluationRecord(int sid) throws Exception {
        Document evalRecord = evaluationRecords.find(eq("salesman_id", sid)).first();
        // Checks if Evaluation Record exists
        if(evalRecord == null){
            throw new Exception(("Evaluation Record for Salesman with the ID " + sid + " not found."));
        }
        DeleteResult result = evaluationRecords.deleteOne(eq("salesman_id", sid));
        //Check if deletion of Evaluation Record was successful
        if(!result.wasAcknowledged()){
            throw new Exception("Deletion of EvaluationRecord was not successful");
        }

        return new EvaluationRecord(evalRecord);
    }


}
