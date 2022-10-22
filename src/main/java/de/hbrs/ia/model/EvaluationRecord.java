package de.hbrs.ia.model;

import org.bson.Document;

public class EvaluationRecord{
    private int goalId;
    private String goalDescription;
    private double targetValue;
    private double actualValue;
    private int year;

    private int salesmenId;

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public int getYear() {
        return year;
    }

    public int getSalesmenId(){
        return this.salesmenId;
    }
    public void setSalesmenId(int id){
        this.salesmenId = id;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public EvaluationRecord(int g_id, String g_desc, double target_val, double actual_val, int year){
        this.goalId = g_id;
        this.goalDescription = g_desc;
        this.targetValue = target_val;
        this.actualValue= actual_val;
        this.year = year;
    }
    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("goal_id" , this.goalId );
        document.append("goal_description" , this.goalDescription );
        document.append("target_value" , this.targetValue);
        document.append("actual_value" , this.actualValue);
        document.append("year" , this.year);
        return document;
    }
}


