package de.hbrs.ia.contract;
import de.hbrs.ia.model.*;

import de.hbrs.ia.model.SalesMan;

import java.util.List;

public interface ManagePersonal {

    void createSalesMan( SalesMan record );

    void addPerformanceRecord( EvaluationRecord record , int sid );

    SalesMan readSalesMan( int sid );

    List<SalesMan> querySalesMan(String attribute , String key );

    EvaluationRecord readEvaluationRecords( int sid );
}
