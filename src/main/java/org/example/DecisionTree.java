package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DecisionTree {

    public int convertGrade(String grade) {
        switch (grade) {
            case "O":
                return 10;
            case "A+":
                return 9;
            case "A":
                return 8;
            case "B+":
                return 7;
            case "B":
                return 6;
            case "C":
                return 5;
            case "C+":
                return 4;
            case "D":
                return 3;
            default:
                return 0; // Indicate invalid grade
        }
    }

    static class TreeNode {
        String attribute;
        String specialization; // For leaf nodes
        Map<String, TreeNode> children = new HashMap<>();

        TreeNode(String attribute) {
            this.attribute = attribute;
        }

        TreeNode(String specialization, boolean isLeaf) {
            this.specialization = specialization;
        }
    }

    private double calculateEntropy(List<Database_Handler.Student> students) {
        Map<String, Integer> specializationCounts = new HashMap<>();
        for (Database_Handler.Student student : students) {
            specializationCounts.put(student.getSpecialization(),
                    specializationCounts.getOrDefault(student.getSpecialization(), 0) + 1);
        }

        double entropy = 0.0;
        int totalStudents = students.size();

        for (int count : specializationCounts.values()) {
            double probability = (double) count / totalStudents;
            entropy -= probability * Math.log(probability) / Math.log(2);
        }

        return entropy;
    }

    private double calculateInformationGain(List<Database_Handler.Student> students, String attribute) {
        double totalEntropy = calculateEntropy(students);
        Map<String, List<Database_Handler.Student>> attributeGroups = new HashMap<>();

        for (Database_Handler.Student student : students) {
            String attributeValue = getAttributeValue(student, attribute);
            attributeGroups.putIfAbsent(attributeValue, new ArrayList<>());
            attributeGroups.get(attributeValue).add(student);
        }

        double weightedEntropy = 0.0;
        int totalStudents = students.size();

        for (List<Database_Handler.Student> group : attributeGroups.values()) {
            double groupSize = group.size();
            double groupEntropy = calculateEntropy(group);
            weightedEntropy += (groupSize / totalStudents) * groupEntropy;
        }

        return totalEntropy - weightedEntropy;
    }

    private String getAttributeValue(Database_Handler.Student student, String attribute) {
        switch (attribute) {
            case "CGPA": return String.valueOf((int) student.getCgpa()); // Convert CGPA to int
            case "G_CPL": return String.valueOf(student.getGradeCPL());
            case "G_DSA": return String.valueOf(student.getGradeDSA());
            case "I_ML": return String.valueOf(student.getIML()); // Ensure this returns the correct type
            case "G_Python": return String.valueOf(student.getGradePython());
            case "G_COA": return String.valueOf(student.getGradeCOA());
            case "G_AEM": return String.valueOf(student.getGradeAEM());
            case "G_Physics": return String.valueOf(student.getGradePhysics());
            case "I_DataPatterns": return String.valueOf(student.getIDataPatterns());
            case "I_CyberSecurity": return String.valueOf(student.getICyberSecurity());
            case "I_Networking": return String.valueOf(student.getINetworking());
            case "I_CloudStorage": return String.valueOf(student.getICloudStorage());
            case "I_UIUXDesign": return String.valueOf(student.getIUIUXDesign());
            case "I_SoftwareIntegration": return String.valueOf(student.getISoftwareIntegration());
            case "I_Automation": return String.valueOf(student.getIAutomation());
            case "I_OS": return String.valueOf(student.getIOS());
            case "I_SoftwareDeployment": return String.valueOf(student.getISoftwareDeployment());
            case "I_DataOrganization": return String.valueOf(student.getIDataOrganization());
            case "I_DatabaseManagement": return String.valueOf(student.getIDatabaseManagement());
            case "I_CloudPlatform": return String.valueOf(student.getICloudPlatform());
            case "I_DistributedSystems": return String.valueOf(student.getIDistributedSystems());
            case "I_AR_VR": return String.valueOf(student.getIAR_VR());
            case "I_GameEngine": return String.valueOf(student.getIGameEngine());
            default: return "Unknown";
        }
    }

    private String getBestAttribute(List<Database_Handler.Student> students, List<String> attributes) {
        String bestAttribute = null;
        double bestGain = -1.0;

        for (String attribute : attributes) {
            double infoGain = calculateInformationGain(students, attribute);
            if (infoGain > bestGain) {
                bestGain = infoGain;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }

    public TreeNode buildTree(List<Database_Handler.Student> students, List<String> attributes) {
        if (students.isEmpty()) {
            return null;
        }

        if (allSameSpecialization(students)) {
            return new TreeNode(students.get(0).getSpecialization(), true);
        }

        if (attributes.isEmpty()) {
            return new TreeNode(getMostCommonSpecialization(students), true);
        }

        String bestAttribute = getBestAttribute(students, attributes);
        TreeNode node = new TreeNode(bestAttribute);
        Map<String, List<Database_Handler.Student>> subsets = new HashMap<>();

        for (Database_Handler.Student student : students) {
            String attributeValue = getAttributeValue(student, bestAttribute);
            subsets.putIfAbsent(attributeValue, new ArrayList<>());
            subsets.get(attributeValue).add(student);
        }

        List<String> remainingAttributes = new ArrayList<>(attributes);
        remainingAttributes.remove(bestAttribute);

        for (Map.Entry<String, List<Database_Handler.Student>> entry : subsets.entrySet()) {
            String attributeValue = entry.getKey();
            List<Database_Handler.Student> subset = entry.getValue();
            TreeNode childNode = buildTree(subset, remainingAttributes);
            node.children.put(attributeValue, childNode);
        }

        return node;
    }

    private boolean allSameSpecialization(List<Database_Handler.Student> students) {
        String firstSpecialization = students.get(0).getSpecialization();
        for (Database_Handler.Student student : students) {
            if (!student.getSpecialization().equals(firstSpecialization)) {
                return false;
            }
        }
        return true;
    }

    private String getMostCommonSpecialization(List<Database_Handler.Student> students) {
        Map<String, Integer> specializationCounts = new HashMap<>();
        for (Database_Handler.Student student : students) {
            specializationCounts.put(student.getSpecialization(),
                    specializationCounts.getOrDefault(student.getSpecialization(), 0) + 1);
        }
        return specializationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow().getKey();
    }

    public String suggestSpecialization(TreeNode root, Database_Handler.Student student) {
        TreeNode currentNode = root;
        String mostCommonSpecialization = getMostCommonSpecializationFromNode(root); // Fallback to most common specialization

        while (currentNode.specialization == null) {
            String attributeValue = getAttributeValue(student, currentNode.attribute);
            if (currentNode.children.containsKey(attributeValue)) {
                currentNode = currentNode.children.get(attributeValue);
            } else {
                // If the attribute value doesn't exist, return the most common specialization up to this point
                return mostCommonSpecialization;
            }
        }

        // Return the leaf specialization if found
        return currentNode.specialization;
    }

    private String getMostCommonSpecializationFromNode(TreeNode node) {
        // Traverses the tree from this node and gets the most common specialization in the subtree
        Map<String, Integer> specializationCounts = new HashMap<>();
        countSpecializations(node, specializationCounts);

        return specializationCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow().getKey();  // Fallback to most common specialization
    }

    private void countSpecializations(TreeNode node, Map<String, Integer> specializationCounts) {
        if (node.specialization != null) {
            specializationCounts.put(node.specialization,
                    specializationCounts.getOrDefault(node.specialization, 0) + 1);
        } else {
            for (TreeNode child : node.children.values()) {
                countSpecializations(child, specializationCounts);
            }
        }
    }
}
