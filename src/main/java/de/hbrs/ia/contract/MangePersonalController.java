package de.hbrs.ia.contract;

import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;

import java.util.List;

public class MangePersonalController implements ManagePersonal {

    @Override
    public void createSalesMan(SalesMan record) {
        //TODO create new DB entry
    }

    @Override
    public void addPerformanceRecord(EvaluationRecord record, int sid) {
        //TODO Add evalRecord to existing SalesMan (exception if Salesman not found)
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        //TODO read DB entry
        return null;
    }

    @Override
    public List<SalesMan> querySalesMan(String attribute, String key) {
        //TODO read DB entry
        return null;
    }

    @Override
    public EvaluationRecord readEvaluationRecords(int sid) {
        //TODO read evalRecord from existing SalesMan (exception if Salesman not found)
        return null;
    }

    //TODO CRUD -> Create Read Update Delete -> Create and Read represented, implement Update and Delete functions
}
