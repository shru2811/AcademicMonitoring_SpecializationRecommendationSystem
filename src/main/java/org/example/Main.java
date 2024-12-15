package org.example;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String s = "\t";
    static String line = "============================================================================================================================";

    static String us;
    static String name1;
    static Scanner scanner = new Scanner(System.in);

    // MongoDB client and database setup
    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static final MongoDatabase database = mongoClient.getDatabase("userDatabase"); // Database name
    private static final MongoCollection<Document> userCollection = database.getCollection("users"); // Collection name

    // Admin credentials (stored for simplicity, can be extended to store in DB)
    private static final String ADMIN_USER_ID = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        System.out.println("\n"+line);
        System.out.println("** Welcome to Academic Monitoring and Specialization Selection System **");
        System.out.println(line+"\n");

        while (true) {
            try {
                System.out.println(s+"1. Login");
                System.out.println(s+"2. Create new account");
                System.out.println(s+"3. Exit");
                System.out.print(s+"Enter your choice: ");

                int ch = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                switch (ch) {
                    case 1:
                        System.out.println("\n"+s+"Login: ");
                        login();  // Call the login function
                        break;
                    case 2:
                        System.out.println("\n"+s+"Create new account: ");
                        createAccount();  // Call the create account function
                        break;
                    case 3:
                        System.out.println("\n"+s+"Exiting system...");
                        mongoClient.close(); // Close MongoDB connection
                        System.exit(0);  // Exit the program
                        break;
                    default:
                        System.out.println("\n"+s+"Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(s+"Invalid input. Please enter a number.");
                scanner.nextLine();  // Consume the invalid input to avoid infinite loop
            } catch (Exception e) {
                System.out.println(s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            }
        }
    }
    public static void cluster() {
        KMediode obj = new KMediode("D:\\Fifth sem\\Minor project\\end sem\\Student_Monitoring_and_Specialization_Selection_system\\Student_Monitoring_and_Specialization_Selection_system\\src\\resources\\Modified_data1.csv");
        Scanner sc = new Scanner(System.in);
        System.out.println("\n"+line);
        System.out.println(s + "** Welcome to Student Monitoring System **");
        System.out.println(line+"\n");
        int k = 0;
        while (true) {
            try {
                System.out.print(s+"Enter the number of clusters (must be between 1 and 10): ");
                k = sc.nextInt();
                if (k > 0 && k <= 10) {
                    break; // Exit loop if a valid number is entered
                } else {
                    System.out.println(s+"Invalid input. The number of clusters must be a positive integer less than 11.");
                }
            } catch (InputMismatchException e) {
                System.out.println(s+"Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Clear invalid input
            }
        }

        obj.clustering(k);
    }



    public static void classify(){
        Scanner scanner = new Scanner(System.in);
        Database_Handler dbHandler = new Database_Handler();
        List<Database_Handler.Student> students = dbHandler.getStudents();

        DecisionTree dt = new DecisionTree();
        List<String> attributes = List.of(
                "CGPA_1YR", "G_CPL", "G_DSA", "G_Python", "G_COA",
                "G_AEM", "G_Physics", "I_DataPatterns", "I_ML",
                "I_Math", "I_CyberSecurity", "I_Networking",
                "I_CloudStorage", "I_UIUXDesign", "I_SoftwareIntegration",
                "I_Automation", "I_OS", "I_SoftwareDeployment",
                "I_DataOrganization", "I_DatabaseManagement",
                "I_CloudPlatform", "I_DistributedSystems",
                "I_AR_VR", "I_GameEngine", "Specialization_selected"
        );

        DecisionTree.TreeNode root = dt.buildTree(students, attributes);

        // Welcome Header
        System.out.println("\n"+line);
        System.out.println(s + "** Welcome to Specialization Predictor **");
        System.out.println(line+"\n");

        // Take name input from the user

        String name;
        while (true) {
            try {
                name = name1;
                if (name.isEmpty()) {
                    throw new IllegalArgumentException(s+"Name cannot be empty.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(s + "Error: " + e.getMessage() + " Please enter a valid name.");
            } catch (Exception e) {
                System.out.println(s + "Unexpected error. Please try again.");
            }
        }

        // Take SAP ID input from the user
        String sapId;
        while (true) {
            try {
                System.out.print(s + "Enter SAP ID: ");
                sapId = scanner.nextLine().trim();
                if (sapId.isEmpty()) {
                    throw new IllegalArgumentException("SAP ID cannot be empty.");
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(s + "Error: " + e.getMessage() + " Please enter a valid SAP ID.");
            } catch (Exception e) {
                System.out.println(s+ "Unexpected error. Please try again.");
            }
        }

        // Take CGPA input from the user
        double cgpa = 0.0;
        while (true) {
            try {
                System.out.print("\n" + s + "Enter your overall 1st year CGPA: ");
                String cgpaInput = scanner.nextLine().trim();
                cgpa = Double.parseDouble(cgpaInput);
                if (cgpa < 0.0 || cgpa > 10.0) {
                    throw new IllegalArgumentException("CGPA must be between 0.0 and 10.0.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(s + "Error: Invalid CGPA input. Please enter a numeric value.");
            } catch (IllegalArgumentException e) {
                System.out.println(s + "Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(s + "Unexpected error. Please try again.");
            }
        }

        // Grades input (one by one)
        int[] convertedGrades = new int[6];
        String[] gradeCodes = {"G_CPL", "G_DSA", "G_Python", "G_COA", "G_AEM", "G_Physics"};
        String[] gradePrompts = {
                "C Programming Language (CPL)",
                "Data Structures and Algorithms (DSA)",
                "Python Programming",
                "Computer Organization and Architecture (COA)",
                "Advanced Engineering Mathematics (AEM)",
                "Physics"
        };
        ArrayList<String> validGrades = new ArrayList<>(Arrays.asList("O", "A+", "A", "B+", "B", "C+", "C", "D", "F"));

        for (int i = 0; i < gradeCodes.length; i++) {
            while (true) {
                try {
                    System.out.print("\n" + s + "Enter Grade for " + gradePrompts[i] + " (O, A+, A, B+, B, C+, C, D, F): ");
                    String grade = scanner.nextLine().trim().toUpperCase();

                    if (!validGrades.contains(grade)) {
                        throw new IllegalArgumentException("Invalid grade input '" + grade + "'.");
                    }

                    int gradeValue = dt.convertGrade(grade);
                    convertedGrades[i] = gradeValue;
                    break;

                } catch (IllegalArgumentException e) {
                    System.out.println(s + "Error: " + e.getMessage() + " Please enter a valid grade.");
                } catch (Exception e) {
                    System.out.println(s + "Unexpected error. Please try again.");
                }
            }
        }

        // Initialize inputs for interest areas (one by one)
        double[] interests = new double[17];
        String[] interestCodes = {
                "I_DataPatterns", "I_ML", "I_Math", "I_CyberSecurity", "I_Networking",
                "I_CloudStorage", "I_UIUXDesign", "I_SoftwareIntegration", "I_Automation",
                "I_OS", "I_SoftwareDeployment", "I_DataOrganization", "I_DatabaseManagement",
                "I_CloudPlatform", "I_DistributedSystems", "I_AR_VR", "I_GameEngine"
        };
        String[] interestPrompts = {
                "Do you enjoy working with data to uncover patterns or trends? (1-5)",
                "Are you interested in learning how machines can be trained to recognize objects or predict outcomes? (1-5)",
                "Do you find solving complex mathematical problems and using algorithms exciting? (1-5)",
                "Are you curious about how to protect data and systems from cyber threats? (1-5)",
                "Do you enjoy understanding how computer networks work and ensuring their reliability? (1-5)",
                "Are you interested in learning how data is stored and accessed securely in the cloud? (1-5)",
                "Do you enjoy designing and building user interfaces for websites or applications? (1-5)",
                "Are you interested in learning how different parts of a software system (front-end and back-end) communicate? (1-5)",
                "Are you interested in automating tasks and making processes more efficient? (1-5)",
                "Do you enjoy working with operating systems and exploring how software runs on them? (1-5)",
                "Are you curious about integrating and deploying software systems in real-time environments? (1-5)",
                "Do you enjoy working with large sets of data and organizing them efficiently? (1-5)",
                "Are you interested in understanding how data is stored, queried, and managed in databases? (1-5)",
                "Do you like exploring cloud platforms and understanding how they store and process data? (1-5)",
                "Are you interested in learning how distributed systems process big data across multiple machines? (1-5)",
                "Are you interested in creating visual designs, animations, AR, VR or working with game mechanics? (1-5)",
                "Have you ever been interested in how game engines, like Unity or Unreal, work behind the scenes? (1-5)"
        };

        for (int i = 0; i < interestCodes.length; i++) {
            while (true) {
                try {
                    System.out.print("\n" + s + interestPrompts[i] + " : ");
                    String interestInput = scanner.nextLine().trim();
                    double interestLevel = Double.parseDouble(interestInput);
                    if (interestLevel < 1 || interestLevel > 5) {
                        throw new IllegalArgumentException("Interest level must be between 1 and 5.");
                    }
                    interests[i] = interestLevel;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(s + "Error: Invalid number input. Please enter a number between 1 and 5.");
                } catch (IllegalArgumentException e) {
                    System.out.println(s + "Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println(s + "Unexpected error. Please try again.");
                }
            }
        }

        // Create the input student based on user input
        Database_Handler.Student inputStudent = new Database_Handler.Student(
                name, sapId, cgpa,
                convertedGrades[0], convertedGrades[1], convertedGrades[2],
                convertedGrades[3], convertedGrades[4], convertedGrades[5],
                (int) interests[0], (int) interests[1], (int) interests[2],
                (int) interests[3], (int) interests[4], (int) interests[5],
                (int) interests[6], (int) interests[7], (int) interests[8],
                (int) interests[9], (int) interests[10], (int) interests[11],
                (int) interests[12], (int) interests[13], (int) interests[14],
                (int) interests[15], (int) interests[16],
                ""
        );

        // Suggest specialization
        String specialization = dt.suggestSpecialization(root, inputStudent);

        // Display result
        System.out.println("\n"+line);
        System.out.println(s + "Suggested Specialization: " + specialization);
        System.out.println(line);

        Document updateQuery = new Document("userId", us);
        Document updateFields = new Document("$set", new Document("previousSpecialization", specialization));

        // Update the record in the MongoDB collection
        userCollection.updateOne(updateQuery, updateFields);

//        System.out.println("Specialization suggestion: " + specialization);
//        System.out.println("The specialization has been saved as the previous specialization suggestion.");

        // Close resources

        dbHandler.close();

    }

    // Helper method to find index in an array
    private static int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static void login() {
        try {
            System.out.print(s+"Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print(s+"Enter Password: ");
            String password = scanner.nextLine();

            // If it's the admin login, check against the hardcoded admin credentials
            if (userId.equals(ADMIN_USER_ID) && password.equals(ADMIN_PASSWORD)) {
                System.out.println("\n"+s+"Admin login successful.");
                adminMenu();
            } else {
                // Otherwise, check if the user is a student or educator
                Document user = userCollection.find(Filters.eq("userId", userId)).first();
                if (user != null) {
                    String storedPassword = user.getString("password");
                    if (storedPassword.equals(password)) {
                        String role = user.getString("role");
                        if (role.equals("student")) {
                            System.out.println("\n"+s+"Login successful for student: " + userId);
                            us = userId;
                            Document usname = userCollection.find(Filters.eq("userId", userId)).first();
                            name1 = usname.getString("name");

                            // Get previous specialization suggestion
                            String previousSpecialization = user.getString("previousSpecialization");
                            if (previousSpecialization != null && !previousSpecialization.isEmpty()) {
                                System.out.println("\n"+s+"Your previous specialization suggestion was: " + previousSpecialization);
                            } else {
                                System.out.println("\n"+s+"No previous specialization suggestion found.");
                            }
                            studentMenu();
                        } else if (role.equals("educator")) {
                            System.out.println("\n"+s+"Login successful for educator: " + userId);
                            educatorMenu();
                        } else {
                            System.out.println("\n"+s+"Unknown role. Access denied.");
                        }
                    } else {
                        System.out.println("\n"+s+"Incorrect password. Please try again.");
                    }
                } else {
                    System.out.println("\n"+s+"User ID not found. Please try again.");
                }
            }
        } catch (NullPointerException e) {
            System.out.println(s+"An error occurred while retrieving user information: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        } catch (MongoException e) {
            System.out.println(s+"MongoDB connection error: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        } catch (Exception e) {
            System.out.println(s+"An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        }
    }

    public static void adminMenu() {
        while (true) {
            try {
                System.out.println("\n"+s+"Admin Menu:");
                System.out.println(s+"1. Create educator account");
                System.out.println(s+"2. See/Edit student details");
                System.out.println(s+"3. See/Edit educator details");
                System.out.println(s+"4. Delete educator or student");
                System.out.println(s+"5. Classify (Student specialization suggestion)");
                System.out.println(s+"6. Cluster (Group students based on performance)");
                System.out.println(s+"7. Exit");
                System.out.print(s+"Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        createEducatorAccount();
                        break;
                    case 2:
                        manageUserDetails("student");
                        break;
                    case 3:
                        manageUserDetails("educator");
                        break;
                    case 4:
                        deleteUser();
                        break;
                    case 5:
                        name1 = "Admin";
                        us = "admin";
                        classify();
                        break;
                    case 6:
                        cluster();
                        break;
                    case 7:
                        System.out.println("\n"+s+"Exiting admin menu.");
                        return;  // Exit the admin menu loop
                    default:
                        System.out.println("\n"+s+"Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n"+s+"Invalid input. Please enter a number between 1 and 7.");
                scanner.nextLine();  // Consume the invalid input to prevent infinite loop
            }catch (Exception e) {
                System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            }
        }
    }


    // Admin option to create an educator account
    public static void createEducatorAccount() {
        try {
            System.out.print("\n"+s+"Enter Name: ");
            String name = scanner.nextLine();

            System.out.print(s+"Enter Email: ");
            String email = scanner.nextLine();

            System.out.print(s+"Enter University Name: ");
            String university = scanner.nextLine();

            System.out.print(s+"Create User ID: ");
            String newUserId = scanner.nextLine();

            System.out.print(s+"Create Password: ");
            String newPassword = scanner.nextLine();

            System.out.print(s+"Confirm Password: ");
            String confirmPassword = scanner.nextLine();

            // Validate password match
            if (!newPassword.equals(confirmPassword)) {
                System.out.println(s+"Passwords do not match. Please try again.");
                return;  // Exit if passwords don't match
            }

            // Check if the user ID already exists
            Document existingUser = userCollection.find(Filters.eq("userId", newUserId)).first();
            if (existingUser != null) {
                System.out.println("\n"+s+"User ID already exists. Please choose a different User ID.");
                return;  // Exit if the user ID already exists
            }

            // Create an educator account and assign the role as 'educator'
            Document newEducator = new Document("name", name)
                    .append("email", email)
                    .append("university", university)
                    .append("userId", newUserId)
                    .append("password", newPassword)
                    .append("role", "educator");

            // Store the new educator data in MongoDB
            userCollection.insertOne(newEducator);
            System.out.println("\n"+s+"Educator account created successfully for user: " + newUserId);

        } catch (MongoException e) {
            System.out.println("\n"+s+"Error occurred while interacting with the database: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        } catch (Exception e) {
            System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();  // Print stack trace for debugging
        }
    }

    public static void manageUserDetails(String role) {
        while (true) {
            try {
                System.out.println("\n"+s+"Search for " + role + " by:");
                System.out.println(s+"1. User ID");
                System.out.println(s+"2. Name");
                System.out.println(s+"3. Exit");
                System.out.print(s+"Choose an option: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("\n"+s+"Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the invalid input
                    continue;
                }
                scanner.nextLine(); // Consume newline character

                if (choice == 3) {
                    return;  // Exit the loop
                }

                Document user = null;
                if (choice == 1) {
                    // Search by User ID
                    System.out.print("\n"+s+"Enter " + role + " User ID: ");
                    String userId = scanner.nextLine();
                    user = userCollection.find(Filters.and(Filters.eq("userId", userId), Filters.eq("role", role))).first();
                } else if (choice == 2) {
                    // Search by Name
                    System.out.print("\n"+s+"Enter " + role + " Name: ");
                    String name = scanner.nextLine();
                    user = userCollection.find(Filters.and(Filters.eq("name", name), Filters.eq("role", role))).first();
                } else {
                    System.out.println("\n"+s+"Invalid choice. Please try again.");
                    continue;  // Re-prompt if the choice is invalid
                }

                if (user != null) {
                    System.out.println("\n"+s+ role.substring(0, 1).toUpperCase() + role.substring(1) + " details: " + user.toJson());
                    // Further editing functionality can be added here, such as updating or deleting details
                } else {
                    System.out.println("\n"+s+ role.substring(0, 1).toUpperCase() + role.substring(1) + " not found.");
                }

            } catch (MongoException e) {
                System.out.println("\n"+s+"Error occurred while interacting with the database: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            } catch (Exception e) {
                System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            }
        }
    }

    // Admin option to delete user (either student or educator)
    public static void deleteUser() {
        while (true) {
            try {
                System.out.println("\n"+s+"Delete user by:");
                System.out.println(s+"1. User ID");
                System.out.println(s+"2. Name");
                System.out.println(s+"3. Exit");
                System.out.print(s+"Choose an option: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("\n"+s+"Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the invalid input
                    continue;
                }
                scanner.nextLine(); // Consume newline character

                Document user = null;
                if (choice == 1) {
                    // Search by User ID
                    System.out.print("\n"+s+"Enter User ID to delete: ");
                    String userId = scanner.nextLine();
                    user = userCollection.find(Filters.eq("userId", userId)).first();
                } else if (choice == 2) {
                    // Search by Name
                    System.out.print("\n"+s+"Enter Name to delete: ");
                    String name = scanner.nextLine();
                    user = userCollection.find(Filters.eq("name", name)).first();
                } else if (choice == 3) {
                    return; // Exit the loop
                } else {
                    System.out.println("\n"+s+"Invalid choice. Please try again.");
                    continue; // Re-prompt the user if an invalid choice is made
                }

                if (user != null) {
                    // Deleting the user
                    userCollection.deleteOne(Filters.eq("userId", user.getString("userId")));
                    System.out.println("\n"+s+"User with ID " + user.getString("userId") + " deleted successfully.");
                } else {
                    System.out.println("\n"+s+"User not found.");
                }

            } catch (MongoException e) {
                System.out.println("\n"+s+"Error occurred while interacting with the database: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            } catch (Exception e) {
                System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            }
        }
    }


    // Student menu function
    public static void studentMenu() {
        while (true) {
            try {
                System.out.println("\n"+s+"Student Menu:");
                System.out.println(s+"1. Classify (Get specialization suggestion)");
                System.out.println(s+"2. Exit");
                System.out.print(s+"Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        classify(); // Call classification logic for students
                        break;
                    case 2:
                        System.out.println("\n"+s+"Exiting student menu.");
                        return;
                    default:
                        System.out.println("\n"+s+"Invalid choice. Please enter a number from the menu.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n"+s+"Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            } catch (Exception e) {
                System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); // Optional: For debugging purposes
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            }
        }
    }

    public static void educatorMenu() {
        while (true) {
            try {
                System.out.println("\n"+s+"Educator Menu:");
                System.out.println(s+"1. Cluster (Group students based on performance)");
                System.out.println(s+"2. Exit");
                System.out.print(s+"Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        cluster(); // Call clustering logic for educators
                        break;
                    case 2:
                        System.out.println("\n"+s+"Exiting educator menu.");
                        return;
                    default:
                        System.out.println("\n"+s+"Invalid choice. Please enter a number from the menu.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n"+s+"Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            } catch (Exception e) {
                System.out.println("\n"+s+"An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); // Optional: For debugging purposes
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            }
        }
    }

    // Function to create a new user account (used by admin)
    public static void createAccount() {
        try {
            System.out.print("\n"+s+"Enter Name: ");
            String name = scanner.nextLine();

            System.out.print(s+"Enter Email: ");
            String email = scanner.nextLine();
            // Validate email format
            if (!isValidEmail(email)) {
                System.out.println("\n"+s+"Invalid email format. Please enter a valid email address.");
                return;
            }

            System.out.print(s+"Enter University Name: ");
            String university = scanner.nextLine();

            String role = "student";

            System.out.print(s+"Create User ID: ");
            String userId = scanner.nextLine();

            // Check if userId already exists
            if (userCollection.find(Filters.eq("userId", userId)).first() != null) {
                System.out.println("\n"+s+"User ID already exists. Please choose a different User ID.");
                return;
            }

            System.out.print(s+"Create Password: ");
            String password = scanner.nextLine();

            System.out.print(s+"Confirm Password: ");
            String confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                // Create a new account with the specified role
                Document newUser = new Document("name", name)
                        .append("email", email)
                        .append("role", role)
                        .append("userId", userId)
                        .append("university", university)
                        .append("password", password);

                // Store the new user in MongoDB
                userCollection.insertOne(newUser);
                System.out.println("\n"+s+role.substring(0, 1).toUpperCase() + role.substring(1) + " account created successfully for user: " + userId);
            } else {
                System.out.println("\n"+s+"Passwords do not match. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("\n"+s+"An error occurred while creating the account: " + e.getMessage());
            e.printStackTrace(); // Optional: For debugging purposes
        }
    }

    // Helper method to validate email format
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}


/*
    Sample Data (for reference):
        9     O O O O A+ O     4 2 4 2 2 5 5 5 5 1 5 3 4 3 4 1 1
        7.8   A A B O A A      4 3 1 3 1 2 4 4 5 5 4 5 3 5 5 4 33
        7.77	B+ B A+ B+ B+ C 	4 3 1 3 1 2 4 4 5 5 4 5 3 5 5 4 3
        8.01	O A+ O A+ A A+	4 4 4 5 5 5 5 5 5 5 5 5 5 5 3 4 4
*/
