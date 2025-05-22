package com.example.diabexpert;

import java.util.*;

public class DecisionTree {
    public DecisionTreeNode root;

    public void train(List<UserData> dataset) {
        System.out.println(" Training tree on " + dataset.size() + " samples");
        root = buildTree(dataset);
    }

    private DecisionTreeNode buildTree(List<UserData> data) {
        if (data.isEmpty()) {
            return new DecisionTreeNode(0);
        }
        if (isPure(data)) {
            return new DecisionTreeNode(data.get(0).label);
        }

        int bestFeature = 0;
        double bestThreshold = data.get(0).features[0];
        double bestGini = Double.MAX_VALUE;
        List<UserData> leftBest = new ArrayList<>();
        List<UserData> rightBest = new ArrayList<>();

        for (int i = 0; i < data.get(0).features.length; i++) {
            double threshold = getMedian(data, i);
            List<UserData> left = new ArrayList<>();
            List<UserData> right = new ArrayList<>();

            for (UserData d : data) {
                if (d.features[i] <= threshold) left.add(d);
                else right.add(d);
            }

            double gini = giniImpurity(left, right);
            if (gini < bestGini) {
                bestGini = gini;
                bestFeature = i;
                bestThreshold = threshold;
                leftBest = left;
                rightBest = right;
            }
        }

        DecisionTreeNode node = new DecisionTreeNode(bestFeature, bestThreshold);
        node.left = buildTree(leftBest);
        node.right = buildTree(rightBest);
        return node;
    }

    private boolean isPure(List<UserData> data) {
        if (data.isEmpty()) return true;
        int label = data.get(0).label;
        for (UserData d : data) {
            if (d.label != label) return false;
        }
        return true;
    }

    private double getMedian(List<UserData> data, int featureIndex) {
        return data.stream()
                   .mapToDouble(d -> d.features[featureIndex])
                   .sorted()
                   .skip(data.size() / 2)
                   .findFirst().orElse(0);
    }

    private double giniImpurity(List<UserData> left, List<UserData> right) {
        int total = left.size() + right.size();
        return (left.size() * gini(left) + right.size() * gini(right)) / total;
    }

    private double gini(List<UserData> data) {
    // Find the maximum label to size the count array properly
    int maxLabel = 0;
    for (UserData d : data) {
        if (d.label > maxLabel) maxLabel = d.label;
    }

    int[] counts = new int[maxLabel + 1]; // dynamic size
    for (UserData d : data) {
        counts[d.label]++;
    }

    double gini = 1.0;
    for (int count : counts) {
        double p = count / (double) data.size();
        gini -= p * p;
    }

    return gini;
}


    public int predict(double[] features) {
            System.out.println(" Predicting for input: " + Arrays.toString(features));
        DecisionTreeNode node = root;
        while (!node.isLeaf()) {
            node = (features[node.featureIndex] <= node.threshold) ? node.left : node.right;
        }
            System.out.println(" Final classification: " + node.classification);
        return node.classification;
    }
}
