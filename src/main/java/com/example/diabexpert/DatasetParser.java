package com.example.diabexpert;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.core.io.ClassPathResource;

public class DatasetParser {
    public static List<UserData> parseCSV(String filepath) throws IOException {
        List<UserData> dataset = new ArrayList<>();
        
        // Use ClassPathResource to load the file from classpath
        ClassPathResource resource = new ClassPathResource(filepath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            // Skip header
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                double[] features = new double[tokens.length - 1];
                for (int i = 0; i < tokens.length - 1; i++) {
                    try {
                        features[i] = Double.parseDouble(tokens[i]);
                    } catch (NumberFormatException e) {
                        features[i] = -1; // Default for missing/invalid
                    }
                }
                int label = Integer.parseInt(tokens[tokens.length - 1]);
                dataset.add(new UserData(features, label));
            }
        }

        return dataset;
    }
}
