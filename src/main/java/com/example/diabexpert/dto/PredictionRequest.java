package com.example.diabexpert.dto;

public class PredictionRequest {
    private String gender;
    private double age;
    private String hypertension;
    private String heartDisease;
    private String smokingHistory;
    private double bmi;
    private double hbA1cLevel;
    private double bloodGlucoseLevel;

    // Getters and setters
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getAge() { return age; }
    public void setAge(double age) { this.age = age; }

    public String getHypertension() { return hypertension; }
    public void setHypertension(String hypertension) { this.hypertension = hypertension; }

    public String getHeartDisease() { return heartDisease; }
    public void setHeartDisease(String heartDisease) { this.heartDisease = heartDisease; }

    public String getSmokingHistory() { return smokingHistory; }
    public void setSmokingHistory(String smokingHistory) { this.smokingHistory = smokingHistory; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public double getHbA1cLevel() { return hbA1cLevel; }
    public void setHbA1cLevel(double hbA1cLevel) { this.hbA1cLevel = hbA1cLevel; }

    public double getBloodGlucoseLevel() { return bloodGlucoseLevel; }
    public void setBloodGlucoseLevel(double bloodGlucoseLevel) { this.bloodGlucoseLevel = bloodGlucoseLevel; }
}
