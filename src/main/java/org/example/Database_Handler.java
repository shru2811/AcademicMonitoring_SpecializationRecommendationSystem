package org.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

class Database_Handler {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Database_Handler() {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("decision_database");
            collection = database.getCollection("student_data");
        } catch (Exception e) {
            System.out.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        try {
            for (Document doc : collection.find()) {
                Integer sapId = doc.getInteger("_id");
                String name = doc.getString("Name");
                double cgpa = doc.getDouble("CGPA_1YR");
                int gCPL = doc.getInteger("G_CPL");
                int gDSA = doc.getInteger("G_DSA");
                int gPython = doc.getInteger("G_Python");
                int gCOA = doc.getInteger("G_COA");
                int gAEM = doc.getInteger("G_AEM");
                int gPhysics = doc.getInteger("G_Physics");

                // New interest areas as integers
                int iDataPatterns = doc.getInteger("I_DataPatterns");
                int iML = doc.getInteger("I_ML");
                int iMath = doc.getInteger("I_Math");
                int iCyberSecurity = doc.getInteger("I_CyberSecurity");
                int iNetworking = doc.getInteger("I_Networking");
                int iCloudStorage = doc.getInteger("I_CloudStorage");
                int iUIUXDesign = doc.getInteger("I_UIUXDesign");
                int iSoftwareIntegration = doc.getInteger("I_SoftwareIntegration");
                int iAutomation = doc.getInteger("I_Automation");
                int iOS = doc.getInteger("I_OS");
                int iSoftwareDeployment = doc.getInteger("I_SoftwareDeployment");
                int iDataOrganization = doc.getInteger("I_DataOrganization");
                int iDatabaseManagement = doc.getInteger("I_DatabaseManagement");
                int iCloudPlatform = doc.getInteger("I_CloudPlatform");
                int iDistributedSystems = doc.getInteger("I_DistributedSystems");
                int iAR_VR = doc.getInteger("I_AR_VR");
                int iGameEngine = doc.getInteger("I_GameEngine");
                String specialization = doc.getString("Specialization_selected");

                Student student = new Student(
                        sapId.toString(), name, cgpa, gCPL, gDSA, gPython, gCOA, gAEM, gPhysics,
                        iDataPatterns, iML, iMath, iCyberSecurity, iNetworking,
                        iCloudStorage, iUIUXDesign, iSoftwareIntegration, iAutomation, iOS,
                        iSoftwareDeployment, iDataOrganization, iDatabaseManagement, iCloudPlatform,
                        iDistributedSystems, iAR_VR, iGameEngine, specialization
                );
                students.add(student);
            }
        } catch (Exception e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return students;
    }

    public void close() {
        mongoClient.close();
    }

    public static class Student {
        private String name;
        private String sapId;
        private double cgpa;
        private int gradeCPL;
        private int gradeDSA;
        private int gradePython;
        private int gradeCOA;
        private int gradeAEM;
        private int gradePhysics;

        // New attributes for interest areas as integers
        private int iDataPatterns;
        private int iML;
        private int iMath;
        private int iCyberSecurity;
        private int iNetworking;
        private int iCloudStorage;
        private int iUIUXDesign;
        private int iSoftwareIntegration;
        private int iAutomation;
        private int iOS;
        private int iSoftwareDeployment;
        private int iDataOrganization;
        private int iDatabaseManagement;
        private int iCloudPlatform;
        private int iDistributedSystems;
        private int iAR_VR;
        private int iGameEngine;

        private String specialization;

        public Student(String sapId, String name, double cgpa, int gradeCPL, int gradeDSA, int gradePython,
                       int gradeCOA, int gradeAEM, int gradePhysics,
                       int iDataPatterns, int iML, int iMath, int iCyberSecurity,
                       int iNetworking, int iCloudStorage, int iUIUXDesign,
                       int iSoftwareIntegration, int iAutomation, int iOS,
                       int iSoftwareDeployment, int iDataOrganization,
                       int iDatabaseManagement, int iCloudPlatform,
                       int iDistributedSystems, int iAR_VR, int iGameEngine,
                       String specialization) {
            this.sapId = sapId;
            this.name = name;
            this.cgpa = cgpa;
            this.gradeCPL = gradeCPL;
            this.gradeDSA = gradeDSA;
            this.gradePython = gradePython;
            this.gradeCOA = gradeCOA;
            this.gradeAEM = gradeAEM;
            this.gradePhysics = gradePhysics;
            this.iDataPatterns = iDataPatterns;
            this.iML = iML;
            this.iMath = iMath;
            this.iCyberSecurity = iCyberSecurity;
            this.iNetworking = iNetworking;
            this.iCloudStorage = iCloudStorage;
            this.iUIUXDesign = iUIUXDesign;
            this.iSoftwareIntegration = iSoftwareIntegration;
            this.iAutomation = iAutomation;
            this.iOS = iOS;
            this.iSoftwareDeployment = iSoftwareDeployment;
            this.iDataOrganization = iDataOrganization;
            this.iDatabaseManagement = iDatabaseManagement;
            this.iCloudPlatform = iCloudPlatform;
            this.iDistributedSystems = iDistributedSystems;
            this.iAR_VR = iAR_VR;
            this.iGameEngine = iGameEngine;
            this.specialization = specialization;
        }

        // Getters
        public double getCgpa() { return cgpa; }
        public int getGradeCPL() { return gradeCPL; }
        public int getGradeDSA() { return gradeDSA; }
        public int getGradePython() { return gradePython; }
        public int getGradeCOA() { return gradeCOA; }
        public int getGradeAEM() { return gradeAEM; }
        public int getGradePhysics() { return gradePhysics; }
        public int getIDataPatterns() { return iDataPatterns; }
        public int getIML() { return iML; }
        public int getIMath() { return iMath; }
        public int getICyberSecurity() { return iCyberSecurity; }
        public int getINetworking() { return iNetworking; }
        public int getICloudStorage() { return iCloudStorage; }
        public int getIUIUXDesign() { return iUIUXDesign; }
        public int getISoftwareIntegration() { return iSoftwareIntegration; }
        public int getIAutomation() { return iAutomation; }
        public int getIOS() { return iOS; }
        public int getISoftwareDeployment() { return iSoftwareDeployment; }
        public int getIDataOrganization() { return iDataOrganization; }
        public int getIDatabaseManagement() { return iDatabaseManagement; }
        public int getICloudPlatform() { return iCloudPlatform; }
        public int getIDistributedSystems() { return iDistributedSystems; }
        public int getIAR_VR() { return iAR_VR; }
        public int getIGameEngine() { return iGameEngine; }
        public String getSpecialization() { return specialization; }
    }
}
