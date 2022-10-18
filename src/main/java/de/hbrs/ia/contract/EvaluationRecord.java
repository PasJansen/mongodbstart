package de.hbrs.ia.contract;

public class EvaluationRecord{
    private int goal_id;
    private String goal_description;
    private double target_value;
    private double actual_value;
    private int year;

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public double getTarget_value() {
        return target_value;
    }

    public void setTarget_value(double target_value) {
        this.target_value = target_value;
    }

    public double getActual_value() {
        return actual_value;
    }

    public void setActual_value(double actual_value) {
        this.actual_value = actual_value;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public EvaluationRecord(int g_id, String g_desc, double target_val, double actual_val, int year){
        this.goal_id = g_id;
        this.goal_description = g_desc;
        this.target_value = target_val;
        this.actual_value= actual_val;
        this.year = year;
    }

}
