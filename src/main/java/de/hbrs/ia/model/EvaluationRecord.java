package de.hbrs.ia.model;

public class EvaluationRecord{
    private int goalId;
    private String goalDescription;
    private double targetValue;
    private double actualValue;
    private int year;

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

}
