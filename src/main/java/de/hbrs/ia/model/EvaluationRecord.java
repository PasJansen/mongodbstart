package de.hbrs.ia.model;

import org.bson.Document;

public class EvaluationRecord {
    private int goalId;
    private String goalDescription;
    private double targetValue;
    private double actualValue;
    private int year;
    private int salesmanId;

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

    public int getSalesmenId() {
        return this.salesmanId;
    }

    public void setSalesmanId(int id) {
        this.salesmanId = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public EvaluationRecord(int g_id, int sid, String g_desc, double target_val, double actual_val, int year) {
        this.goalId = g_id;
        this.salesmanId = sid;
        this.goalDescription = g_desc;
        this.targetValue = target_val;
        this.actualValue = actual_val;
        this.year = year;
    }
    public EvaluationRecord (Document evaluationRecord){
        this.goalId = evaluationRecord.getInteger("goal_id");
        this.salesmanId = evaluationRecord.getInteger("salesman_id");
        this.goalDescription = evaluationRecord.getString("");
        this.targetValue = evaluationRecord.getDouble("target_value");
        this.actualValue = evaluationRecord.getDouble("actual_value");
        this.year = evaluationRecord.getInteger("year");
    }
    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("goal_id", this.goalId);
        document.append("salesman_id", this.salesmanId);
        document.append("goal_description", this.goalDescription);
        document.append("target_value", this.targetValue);
        document.append("actual_value", this.actualValue);
        document.append("year", this.year);
        return document;
    }
}


