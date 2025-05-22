package com.example.diabexpert;

public class SeverityScale {
    public static String classify(double score, double glucose) {
        if (glucose >= 300) return "High Risk (Glucose Override)";
        if (score < 0.25) return "Low Risk";
        if (score < 0.5 && score > 0.25) return "Moderate Risk";
        if (score < 0.75 && score > 0.5)  return "High Risk";
        return "High Risk";
    }
}
