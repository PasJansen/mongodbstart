package test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.contract.ManagePersonalController;
import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagePersonalControllerTest {
    private ManagePersonalController dbController;

    @BeforeEach
    void setUp() {
        // Setting up the connection to a local MongoDB with standard port 27017
        // must be started within a terminal with command 'mongod'.

        //Disable unnecessary logging
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        dbController = new ManagePersonalController("localhost", 27017, "hooverdb");
        dbController.getCollection("evaluation_records").deleteMany(new Document());
        dbController.getCollection("salesmen").deleteMany(new Document());
    }

    @Test
    @DisplayName("Test CREATE salesman")
    void testCreateSalesman() {
        SalesMan testSalesman = new SalesMan("Max", "Mustermann", 1);
        dbController.createSalesMan(testSalesman);

        Document insertedSalesman = dbController.getCollection("salesmen").find(eq("id", 1)).first();

        dbController.getCollection("salesmen").deleteOne(eq("id", 1));

        assert insertedSalesman != null;
        assertEquals(testSalesman.getFirstname(), insertedSalesman.getString("firstname"));
        assertEquals(testSalesman.getLastname(), insertedSalesman.getString("lastname"));
        assertEquals(testSalesman.getId(), insertedSalesman.getInteger("id"));

    }

    @Test
    @DisplayName("Test CREATE evaluation record")
    void testCreateEvaluationRecord() {


        EvaluationRecord testRecord = new EvaluationRecord(1, 1, "Desc", 2400, 4200, 2017);
        dbController.addPerformanceRecord(testRecord, 1);

        Document insertedRecord = dbController.getCollection("evaluation_records").find(eq("salesman_id", 1)).first();

        assert insertedRecord != null;

        dbController.getCollection("salesman").deleteOne(eq("id", 1));

        assertEquals(testRecord.getSalesmenId(), insertedRecord.getInteger("salesman_id"));
        assertEquals(testRecord.getGoalId(), insertedRecord.getInteger("goal_id"));
        assertEquals(testRecord.getTargetValue(), insertedRecord.getInteger("target_value"));
        assertEquals(testRecord.getActualValue(), insertedRecord.getInteger("actual_value"));


    }

    @Test
    @DisplayName("Test READ salesman")
    void testReadSalesMan() throws Exception {
        SalesMan testSalesman = new SalesMan("Max", "Mustermann", 1);
        dbController.createSalesMan(testSalesman);

        SalesMan insertedSalesman = dbController.readSalesMan(1);
        dbController.getCollection("salesmen").deleteOne(eq("id", 1));

        assertEquals(testSalesman.getId(), insertedSalesman.getId());
        assertEquals(testSalesman.getFirstname(), insertedSalesman.getFirstname());
        assertEquals(testSalesman.getLastname(), insertedSalesman.getLastname());
    }

    @Test
    @DisplayName("Test READ query of salesman ")
    void testQuerySalesman() throws Exception {
        //CREATING 10 Salesman
        SalesMan test;
        for (int i = 1; i <= 10; i++) {
            if (i > 5) {
                test = new SalesMan("Max", "Mustermann", i);
            } else {
                test = new SalesMan("Sascha", "Alda", i);
            }
            dbController.createSalesMan(test);

        }

        List<SalesMan> salesmen = dbController.querySalesMan("firstname", "Sascha");
        for (int i = 1; i <= 10; i++) {
            dbController.deleteSalesman(i);
        }
        assertEquals(salesmen.size(), 5);
        assertEquals(salesmen.get(0).getId(), 1);
    }

    @Test
    @DisplayName("Test READ evaluation record")
    void testReadEvaluationRecord() throws Exception {
        EvaluationRecord testRecord = new EvaluationRecord(1, 1, "Desc", 2400, 4200, 2017);
        dbController.addPerformanceRecord(testRecord, 1);
        EvaluationRecord readRecord = dbController.readEvaluationRecord(1);
        dbController.getCollection("evaluation_records").deleteMany(eq("salesman_id", 1));

        assertEquals(testRecord.getSalesmenId(), readRecord.getSalesmenId());
        assertEquals(testRecord.getYear(), readRecord.getYear());
        assertEquals(testRecord.getGoalDescription(), readRecord.getGoalDescription());
        assertEquals(testRecord.getActualValue(), readRecord.getActualValue());
        assertEquals(testRecord.getTargetValue(), readRecord.getTargetValue());
    }

    @Test
    @DisplayName("Test UPDATE salesman")
    void testUpdateSalesMan() throws Exception {
        SalesMan testSalesman = new SalesMan("Max", "Mustermann", 1);
        dbController.createSalesMan(testSalesman);
        dbController.updateSalesMan(1, "firstname", "Uwe");
        SalesMan updatedSalesman = dbController.readSalesMan(1);

        dbController.getCollection("salesmen").deleteOne(eq("id", 1));

        assertEquals(1, updatedSalesman.getId());
        assertEquals("Uwe", updatedSalesman.getFirstname());
        assertEquals(testSalesman.getLastname(), updatedSalesman.getLastname());

    }

    @Test
    @DisplayName("Test UPDATE evaluation record")
    void testUpdateEvaluationRecord() throws Exception {
        EvaluationRecord testRecord = new EvaluationRecord(1, 1, "Desc", 2400, 4200, 2017);
        dbController.addPerformanceRecord(testRecord, 1);
        dbController.updateEvaluationRecord(1, "target_value", 2900);

        EvaluationRecord updatedRecord = dbController.readEvaluationRecord(1);
        dbController.getCollection("evaluation_records").deleteOne(eq("salesman_id", 1));

        assertEquals(2900, updatedRecord.getTargetValue());

    }

    @Test
    @DisplayName("Test DELETE salesman")
    void testDeleteSalesman() throws Exception {
        SalesMan testSalesman = new SalesMan("Max", "Mustermann", 1);
        dbController.createSalesMan(testSalesman);
        SalesMan deletedSalesman = dbController.deleteSalesman(1);

        assertEquals("Max", deletedSalesman.getFirstname());
        assertEquals("Mustermann", deletedSalesman.getLastname());
        assertEquals(1, deletedSalesman.getId());
    }
    @Test
    @DisplayName("Test DELETE evaluation record")
    void testDeleteEvaluationRecord() throws Exception {
        EvaluationRecord testRecord = new EvaluationRecord(1, 1, "Desc", 2400, 4200, 2017);
        dbController.addPerformanceRecord(testRecord, 1);
        EvaluationRecord deletedRecord = dbController.deleteEvaluationRecord(1);

        assertEquals(1, deletedRecord.getGoalId());
        assertEquals(1, deletedRecord.getSalesmenId());
        assertEquals(2400, deletedRecord.getTargetValue());
        assertEquals(4200, deletedRecord.getActualValue());
        assertEquals(2017, deletedRecord.getYear());
    }
}
