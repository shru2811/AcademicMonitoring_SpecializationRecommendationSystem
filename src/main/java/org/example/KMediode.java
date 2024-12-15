package org.example;

import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;

class Point {
    int sapId;
    double cgpa;
    int GradeCPL;
    int GradeDSA;
    int GradePython;
    int GradeCOA;
    int GradeAEM;
    int GradePhysics;
    int DataPatternsInterest;
    int MLInterest;
    int MathInterest;
    int CyberSecurityInterest;
    int NetworkingInterest;
    int CloudStorageInterest;
    int UIUXDesignInterest;
    int SoftwareIntegrationInterest;
    int AutomationInterest;
    int OSInterest;
    int SoftwareDeploymentInterest;
    int DataOrganizationInterest;
    int DatabaseManagementInterest;
    int CloudPlatformInterest;
    int DistributedSystemsInterest;
    int InterestAR_VR;
    int GameEngineInterest;

    Point(int sapId, double cgpa,
          int GradeCPL,
          int GradeDSA,
          int GradePython,
          int GradeCOA,
          int GradeAEM,
          int GradePhysics,
          int DataPatternsInterest,
          int MLInterest,
          int MathInterest,
          int CyberSecurityInterest,
          int NetworkingInterest,
          int CloudStorageInterest,
          int UIUXDesignInterest,
          int SoftwareIntegrationInterest,
          int AutomationInterest,
          int OSInterest,
          int SoftwareDeploymentInterest,
          int DataOrganizationInterest,
          int DatabaseManagementInterest,
          int CloudPlatformInterest,
          int DistributedSystemsInterest,
          int InterestAR_VR,
          int GameEngineInterest) {
        this.sapId = sapId;
        this.cgpa = cgpa;
        this.GradeCPL = GradeCPL;
        this.GradeDSA = GradeDSA;
        this.GradePython = GradePython;
        this.GradeCOA = GradeCOA;
        this.GradeAEM = GradeAEM;
        this.GradePhysics = GradePhysics;
        this.DataPatternsInterest = DataPatternsInterest;
        this.MLInterest = MLInterest;
        this.MathInterest = MathInterest;
        this.CyberSecurityInterest = CyberSecurityInterest;
        this.NetworkingInterest = NetworkingInterest;
        this.CloudStorageInterest = CloudStorageInterest;
        this.UIUXDesignInterest = UIUXDesignInterest;
        this.SoftwareIntegrationInterest = SoftwareIntegrationInterest;
        this.AutomationInterest = AutomationInterest;
        this.OSInterest = OSInterest;
        this.SoftwareDeploymentInterest = SoftwareDeploymentInterest;
        this.DataOrganizationInterest = DataOrganizationInterest;
        this.DatabaseManagementInterest = DatabaseManagementInterest;
        this.CloudPlatformInterest = CloudPlatformInterest;
        this.DistributedSystemsInterest = DistributedSystemsInterest;
        this.InterestAR_VR = InterestAR_VR;
        this.GameEngineInterest = GameEngineInterest;
    }
}

class KMediode {
    private List<Point> points;

    public KMediode(String csvFilePath) {
        this.points = new ArrayList<>();
        loadDataFromCSV(csvFilePath);
    }

    private void loadDataFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                // Ensure that the line has at least 25 elements to prevent ArrayIndexOutOfBoundsException
                if (line.length < 25) {
                    System.out.println("Skipping incomplete line: " + Arrays.toString(line));
                    continue;
                }
                Point p = new Point(
                        Integer.parseInt(line[0]),
                        Double.parseDouble(line[1]),
                        Integer.parseInt(line[2]),
                        Integer.parseInt(line[3]),
                        Integer.parseInt(line[4]),
                        Integer.parseInt(line[5]),
                        Integer.parseInt(line[6]),
                        Integer.parseInt(line[7]),
                        Integer.parseInt(line[8]),
                        Integer.parseInt(line[9]),
                        Integer.parseInt(line[10]),
                        Integer.parseInt(line[11]),
                        Integer.parseInt(line[12]),
                        Integer.parseInt(line[13]),
                        Integer.parseInt(line[14]),
                        Integer.parseInt(line[15]),
                        Integer.parseInt(line[16]),
                        Integer.parseInt(line[17]),
                        Integer.parseInt(line[18]),
                        Integer.parseInt(line[19]),
                        Integer.parseInt(line[20]),
                        Integer.parseInt(line[21]),
                        Integer.parseInt(line[22]),
                        Integer.parseInt(line[23]),
                        Integer.parseInt(line[24])
                );
                points.add(p);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    // Unique medoids selection
    private Point[] pickMediode(int k) {
        Point[] centroids = new Point[k];
        Random random = new Random();
        Set<Integer> selectedIndices = new HashSet<>();  // To track selected indices

        for (int i = 0; i < k; i++) {
            int index;
            do {
                index = random.nextInt(points.size()); // Get a random index
            } while (selectedIndices.contains(index)); // Ensure it's not already selected

            selectedIndices.add(index); // Mark this index as selected
            centroids[i] = points.get(index); // Assign the centroid
        }

        return centroids;
    }

    private double euclideanDist(Point a, Point b) {
        return Math.sqrt(
                Math.pow(a.GradeCPL - b.GradeCPL, 2) + Math.pow(a.GradeDSA - b.GradeDSA, 2) +
                        Math.pow(a.GradePython - b.GradePython, 2) +
                        Math.pow(a.GradeCOA - b.GradeCOA, 2) +
                        Math.pow(a.GradeAEM - b.GradeAEM, 2) +
                        Math.pow(a.GradePhysics - b.GradePhysics, 2) +
                        Math.pow(a.DataPatternsInterest - b.DataPatternsInterest, 2) +
                        Math.pow(a.MLInterest - b.MLInterest, 2) +
                        Math.pow(a.MathInterest - b.MathInterest, 2) +
                        Math.pow(a.CyberSecurityInterest - b.CyberSecurityInterest, 2) +
                        Math.pow(a.NetworkingInterest - b.NetworkingInterest, 2) +
                        Math.pow(a.CloudStorageInterest - b.CloudStorageInterest, 2) +
                        Math.pow(a.UIUXDesignInterest - b.UIUXDesignInterest, 2) +
                        Math.pow(a.SoftwareIntegrationInterest - b.SoftwareIntegrationInterest, 2) +
                        Math.pow(a.AutomationInterest - b.AutomationInterest, 2) +
                        Math.pow(a.OSInterest - b.OSInterest, 2) +
                        Math.pow(a.SoftwareDeploymentInterest - b.SoftwareDeploymentInterest, 2) +
                        Math.pow(a.DataOrganizationInterest - b.DataOrganizationInterest, 2) +
                        Math.pow(a.DatabaseManagementInterest - b.DatabaseManagementInterest, 2) +
                        Math.pow(a.CloudPlatformInterest - b.CloudPlatformInterest, 2) +
                        Math.pow(a.DistributedSystemsInterest - b.DistributedSystemsInterest, 2) +
                        Math.pow(a.InterestAR_VR - b.InterestAR_VR, 2) +
                        Math.pow(a.GameEngineInterest - b.GameEngineInterest, 2)
        );
    }

    private double[][] assignPoint(int k) {
        double[][] labels = new double[points.size()][2];
        Point[] centroids = pickMediode(k);
        System.out.print("Random Medoids Selected: ");
        for (Point centroid : centroids) {
            System.out.print(centroid.sapId + " ");
        }
        System.out.println(); // Move to the next line after listing medoids

        for (int i = 0; i < points.size(); i++) {
            double minCluster = Double.MAX_VALUE;
            int clusterNum = -1;
            for (int j = 0; j < centroids.length; j++) {
                double dist = euclideanDist(points.get(i), centroids[j]);
                if (minCluster > dist) {
                    clusterNum = j;
                    minCluster = dist;
                }
            }
            labels[i][0] = clusterNum;
            labels[i][1] = minCluster;
        }

        return labels;
    }

    private int costCalculate(double[][] labels) {
        int cost = 0;
        for (double[] label : labels) {
            cost += label[1];
        }
        return cost;
    }

    // Method to print clusters in table format
    private void printClustersTable(List<List<Integer>> clusters, int iteration, int k, int cost, long timeTaken) {

        System.out.println("\nIteration " + iteration);
        System.out.println("=========================================");
        // Determine the maximum number of students in any cluster
        int maxSize = 0;
        for (List<Integer> cluster : clusters) {
            if (cluster.size() > maxSize) {
                maxSize = cluster.size();
            }
        }

        // Print the header
        for (int j = 0; j < k; j++) {
            System.out.printf("| %-20s ", "Cluster " + (j + 1));
        }
        System.out.println("|");
        // Print separator
        for (int j = 0; j < k; j++) {
            System.out.print("|----------------------");
        }
        System.out.println("|");

        // Print rows
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < k; j++) {
                if (i < clusters.get(j).size()) {
                    System.out.printf("| %-20s ", clusters.get(j).get(i));
                } else {
                    System.out.printf("| %-20s ", "");
                }
            }
            System.out.println("|");
        }
        // Print footer
        for (int j = 0; j < k; j++) {
            System.out.print("|----------------------");
        }
        System.out.println("|");

        // Print cost and time taken
        System.out.println("The cost for this clustering is: " + cost);
        System.out.println("Time taken for iteration " + iteration + ": " + timeTaken + " milliseconds\n");
    }

    // Method to print final clusters in table format
    private void printFinalClustersTable(List<List<Integer>> clusters, int k, int minCost) {

        System.out.println("\nFinal Clusters After 5 Iterations");
        System.out.println("=========================================");
        // Determine the maximum number of students in any cluster
        int maxSize = 0;
        for (List<Integer> cluster : clusters) {
            if (cluster.size() > maxSize) {
                maxSize = cluster.size();
            }
        }

        // Print the header
        for (int j = 0; j < k; j++) {
            System.out.printf("| %-20s ", "Cluster " + (j + 1));
        }
        System.out.println("|");
        // Print separator
        for (int j = 0; j < k; j++) {
            System.out.print("|----------------------");
        }
        System.out.println("|");

        // Print rows
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < k; j++) {
                if (i < clusters.get(j).size()) {
                    System.out.printf("| %-20s ", clusters.get(j).get(i));
                } else {
                    System.out.printf("| %-20s ", "");
                }
            }
            System.out.println("|");
        }
        // Print footer
        for (int j = 0; j < k; j++) {
            System.out.print("|----------------------");
        }
        System.out.println("|");

        // Print minimum cost achieved
        System.out.println("\n"+Main.line);
        System.out.println(Main.s + "The minimum cost achieved in clustering is: " + minCost);
        System.out.println(Main.line+"\n");

    }

    // Method to print clusters during each iteration in table format
    private void printClusters(List<List<Integer>> clusters, int iteration, int k, int cost, long timeTaken) {
        printClustersTable(clusters, iteration, k, cost, timeTaken);
    }

    public void clustering(int k) {
        int minCost = Integer.MAX_VALUE;
        double[][] bestClusters = null;
        List<List<Integer>> finalClusters = null;

        // Perform clustering 5 times and select the one with the minimum cost
        for (int i = 1; i <= 5; i++) {
            long startTime = System.currentTimeMillis();
            double[][] labels = this.assignPoint(k);
            int cost = this.costCalculate(labels);
            List<List<Integer>> clusters = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                clusters.add(new ArrayList<>());
            }

            // Group the SAP IDs into the respective clusters
            for (int j = 0; j < labels.length; j++) {
                int clusterIndex = (int) labels[j][0];
                clusters.get(clusterIndex).add(this.points.get(j).sapId);
            }
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            printClusters(clusters, i, k, cost, timeTaken);

            // Update the best clustering based on cost
            if (cost < minCost) {
                minCost = cost;
                bestClusters = labels;
                finalClusters = clusters;
            }
        }

        // Print the final best clusters in table format
        if (finalClusters != null) {
            printFinalClustersTable(finalClusters, k, minCost);
        }
    }
}

