package com.example.diabexpert.dto;

public class PredictionResponse {
    private int severity;
    private double score; // Optional, can be confidence %
    private double bloodGlucoseLevel;
    private double bmi;
    private double hbA1cLevel;

    public PredictionResponse(int severity, double score, double bloodGlucoseLevel, double bmi, double hbA1cLevel) {
        this.severity = severity;
        this.score = score;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.bmi = bmi;
        this.hbA1cLevel = hbA1cLevel;
    }

    public int getseverity() { return severity; }
    public double getScore() { return score; }
    public double getbloodGlucoseLevel() { return bloodGlucoseLevel; }
    public double getBmi() { return bmi; }
    public double gethbA1cLevel() { return hbA1cLevel; }
}
