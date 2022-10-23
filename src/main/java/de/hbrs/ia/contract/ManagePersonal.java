package de.hbrs.ia.contract;
import de.hbrs.ia.model.*;

import de.hbrs.ia.model.SalesMan;

import java.util.List;

public interface ManagePersonal {

    void createSalesMan( SalesMan record );

    void addPerformanceRecord( EvaluationRecord record , int sid );

    SalesMan readSalesMan( int sid ) throws Exception;

    List<SalesMan> querySalesMan(String attribute , String key );

    EvaluationRecord readEvaluationRecords( int sid ) throws Exception;

    void updateSalesMan(int sid, String key, String value);

    void updateEvaluationRecord(int sid, String key, String value);

    SalesMan deleteSalesman(int sid) throws Exception;

    EvaluationRecord deleteEvaluationRecord(int sid) throws Exception;
}
