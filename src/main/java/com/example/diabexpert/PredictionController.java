package com.example.diabexpert;

import com.example.diabexpert.dto.PredictionRequest;
import com.example.diabexpert.dto.PredictionResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PredictionController {

    @PostMapping("/predict")
    public PredictionResponse predictRisk(@RequestBody PredictionRequest request) {
        try {
            // Prepare input features
            double[] features = new double[]{
                mapGender(request.getGender()),
                request.getAge(),
                mapYesNo(request.getHypertension()),
                mapYesNo(request.getHeartDisease()),
                mapSmoking(request.getSmokingHistory()),
                request.getBmi(),
                request.getHbA1cLevel(),
                request.getBloodGlucoseLevel()
            };
            System.out.println(" Input Features: " + Arrays.toString(features));

            // Load and train custom Decision Tree model
            List<UserData> trainingData = DatasetParser.parseCSV("trained-DS.csv");
            DecisionTree tree = new DecisionTree();
            tree.train(trainingData);

            // Predict label
            int label = tree.predict(features);
            System.out.println(" Input Features: " + Arrays.toString(features));
            System.out.println(" Predicted Severity (before override): " + label);

            // Override prediction if all key metrics are healthy
            if (request.getBloodGlucoseLevel() < 100 &&
                request.getBmi() < 25 &&
                request.getHbA1cLevel() < 5.7 &&
                mapYesNo(request.getHypertension()) == 0 &&
                mapYesNo(request.getHeartDisease()) == 0) {
                label = 0;
                System.out.println(" Overriding prediction: Low Risk based on healthy metrics.");
            }

            System.out.println(" Input Features: " + Arrays.toString(features));
            System.out.println(" Predicted Severity: " + label);

            // No confidence from custom tree — set to placeholder or estimate manually if needed
            double confidence = 1.0; // Placeholder, or implement manual confidence logic

            // Create response
            return new PredictionResponse(label, confidence, request.getBloodGlucoseLevel(),request.getBmi(),request.getHbA1cLevel());
        } catch (Exception e) {
            e.printStackTrace();
            
            return new PredictionResponse(-1, 0.0, 0.0, 0.0, 0.0); // fallback on error
        }
        
    }

    private double mapGender(String gender) {
        if (gender == null) return 3;
        return switch (gender.trim().toLowerCase()) {
            case "male" -> 0;
            case "female" -> 1;
            case "other" -> 2;
            default -> 3;
        };
    }

    private double mapSmoking(String value) {
        if (value == null) return 5;
        return switch (value.trim().toLowerCase()) {
            case "never" -> 0;
            case "former" -> 1;
            case "current" -> 2;
            case "not current" -> 3;
            case "ever" -> 4;
            default -> 5;
        };
    }
    private double mapYesNo(Object value) {
    if (value instanceof String s) {
        return s.trim().equalsIgnoreCase("yes") ? 1.0 : 0.0;
    } else if (value instanceof Number n) {
        return n.intValue();
    }
    return 0.0;
}

}

