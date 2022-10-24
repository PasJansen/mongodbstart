package de.hbrs.ia.view;

import de.hbrs.ia.contract.ManagePersonalController;
import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagePersonalView {
    public static void main(String[] args) {
        // Setting up variables
        Scanner sc = new Scanner(System.in);
        ManagePersonalController dbController = null;

        // Starting conversation
        System.out.println("Connecting to DB...");

        // Creating DB Connection
        try {
            //Disable unnecessary logging
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.SEVERE);

            dbController = new ManagePersonalController("localhost", 27017, "hooverdb");

            // Connection established
            System.out.println("\nConnection to the database has been established");
        } catch (Exception e) {
            // Failed to Connect
            System.out.println("\nConnection to the database could not be established");
        }

        label:
        while (true) {
            // Ask what operation the user wants to perform
            System.out.println("\n\n------------------------------------------------------");
            System.out.println("\nWhat operation do you want to perform?..");
            System.out.println("Type Create, Read, Update or Delete");

            // Reading input
            String inputOperation = sc.next().toLowerCase();

            // Calling operations
            switch (inputOperation) {
                case "create":

                    // Write Salesman or EvaluationRecord?
                    System.out.println("\nWhat do you want to write into the Database?");
                    System.out.println("Type 'sale' to create a salesman and 'record' to write an Evaluation Record" +
                            " into the Database");
                    String writeOperation = sc.next().toLowerCase();

                    if (writeOperation.equals("sale")) {         // Creating Salesman
                        System.out.print("\nYou want to add a salesman...\n\nFirstname: ");
                        String firstname = sc.next();
                        System.out.print("\nLastname: ");
                        String lastname = sc.next();
                        System.out.print("\nID: ");
                        int id = sc.nextInt();


                        try {
                            SalesMan inputSalesman = new SalesMan(firstname, lastname, id);

                            assert dbController != null;
                            dbController.createSalesMan(inputSalesman);

                            System.out.println("\nThank you... Your salesman is now added...");
                        } catch (Exception e) {
                            System.out.println("Error, could not add salesman!");
                            System.exit(-1);
                        }
                    } else if (writeOperation.equals("record")) {        // Creating Evaluation Record
                        System.out.print("You want to add an Evaluation Record...\n\nGoal_ID: ");
                        int goalId = sc.nextInt();
                        System.out.print("\nSalesman ID: ");
                        int salesmanId = sc.nextInt();
                        System.out.print("\nGoal Description: ");
                        String goalDescription = sc.next();
                        System.out.print("\nTarget Value: ");
                        int targetValue = sc.nextInt();
                        System.out.print("\nActual Value: ");
                        int actualValue = sc.nextInt();
                        System.out.print("\nYear: ");
                        int year = sc.nextInt();

                        try {
                            EvaluationRecord newRecord = new EvaluationRecord(goalId, salesmanId,
                                    goalDescription, targetValue,
                                    actualValue, year);

                            assert dbController != null;
                            dbController.addPerformanceRecord(newRecord, salesmanId);

                            System.out.println("\nThank you... Your Evaluation Record is now added...");
                        } catch (Exception e) {
                            System.out.println("Error, could not add Evaluation Record!");
                            System.exit(-1);
                        }
                    }
                    break;
                case "read":
                    // Read Salesman or EvaluationRecord?
                    System.out.println("What do you want to read from the Database?");
                    System.out.println("Type 'sale' to read a salesman and 'record' to read an Evaluation Record");
                    String readOperation = sc.next().toLowerCase();

                    if (readOperation.equals("sale")) {  // Reading Salesman

                        System.out.println("You want to read a salesman...");
                        System.out.println("How you want to find the salesman? Type Firstname, Lastname or ID");
                        String readSalesManQuery = sc.next().toLowerCase();

                        switch (readSalesManQuery) {
                            case "id":
                                System.out.print("Please enter the salesman ID: ");

                                int findSalesmanByID = sc.nextInt();
                                try {
                                    assert dbController != null;
                                    SalesMan foundSalesman = dbController.readSalesMan(findSalesmanByID);

                                    System.out.println("Your salesman is found:\n\n");

                                    System.out.println(foundSalesman.toString());
                                } catch (Exception e) {
                                    System.out.println("Error, could not read the Database");
                                    break;
                                }
                                break;
                            case "firstname":
                                System.out.print("Pleaser enter the firstname: ");

                                String searchSalesManByFirstname = sc.next();
                                try {
                                    assert dbController != null;
                                    List<SalesMan> foundSalesman = dbController.querySalesMan("firstname",
                                            searchSalesManByFirstname);

                                    System.out.println("\nYour query result:\n");
                                    for (SalesMan salesMan : foundSalesman) {
                                        System.out.println(salesMan);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Unknown Error");
                                    break;
                                }
                                break;
                            case "lastname":
                                System.out.print("Pleaser enter the lastname: ");

                                String searchSalesManByLastname = sc.next();
                                try {
                                    assert dbController != null;
                                    List<SalesMan> foundSalesman = dbController.querySalesMan("lastname",
                                            searchSalesManByLastname);

                                    System.out.println("\nYour query result:\n");
                                    for (SalesMan salesMan : foundSalesman) {
                                        System.out.println(salesMan);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Unknown Error");
                                    break;
                                }
                                break;
                        }
                    } else if (readOperation.equals("record")) {  // Reading EvaluationRecord
                        System.out.println("You want to read an Evaluation Record...");
                        System.out.print("\nPlease enter the salesman ID: ");

                        int findEvaluationRecordByID = sc.nextInt();
                        try {
                            assert dbController != null;
                            EvaluationRecord foundRecord = dbController.readEvaluationRecord(findEvaluationRecordByID);

                            System.out.println("Your Evaluation Record is found:\n\n");
                            System.out.println(foundRecord.toString());
                        } catch (Exception e) {
                            System.out.println("Error, could not read the Database");
                        }
                    } else {
                        System.out.println("Error");
                        break label;
                    }
                    break;
                case "update":
                    // Update Salesman or EvaluationRecord?
                    System.out.println("What do you want to update in the Database?");
                    System.out.println("Type 'sale' to update a salesman and 'record' to update an Evaluation Record");
                    String updateOperation = sc.next().toLowerCase();

                    if (updateOperation.equals("sale")) {
                        System.out.println("You want to update a salesman...");

                        // Operate on which Salesman
                        System.out.print("Enter Salesman ID: ");
                        int salesmanID = sc.nextInt();

                        // What to Update
                        System.out.println("\nWhat do you want to Update?   Type: Firstname, Lastname or ID");
                        String updateScopeOperation = sc.next().toLowerCase();

                        // Update Operations
                        switch (updateScopeOperation) {
                            case "firstname":
                                System.out.print("Enter new Firstname: ");
                                String newFirstname = sc.next();
                                assert dbController != null;
                                dbController.updateSalesMan(salesmanID, "firstname", newFirstname);
                                System.out.println("Thank you.. Firstname is now changed");
                                break;
                            case "lastname":
                                System.out.print("Enter new Lastname: ");
                                String newLastname = sc.next();
                                assert dbController != null;
                                dbController.updateSalesMan(salesmanID, "lastname", newLastname);
                                System.out.println("Thank you.. Lastname is now changed");
                                break;
                            case "id":
                                System.out.print("Enter new ID: ");
                                int newId = sc.nextInt();
                                assert dbController != null;
                                dbController.updateSalesMan(salesmanID, "id", newId);
                                System.out.println("Thank you... ID is now changed");
                                break;
                            default:
                                System.out.println("Unknown Error");
                                break;
                        }
                    } else if (updateOperation.equals("record")) {
                        System.out.println("You want to update a evaluation record...");

                        // Operate on which Salesman
                        System.out.print("Enter Salesman ID: ");
                        int salesmanID = sc.nextInt();

                        // What to Update
                        System.out.println("\nWhat do you want to Update?   Type: GoalID, GoalDescription, " +
                                "SalesmanID, TargetValue, ActualValue or Year");
                        String updateScopeOperation = sc.next().toLowerCase();

                        // Update Operations
                        switch (updateScopeOperation) {
                            case "goalid":
                                System.out.print("Enter new Goal ID: ");
                                int newGoalId = sc.nextInt();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "goal_id", newGoalId);
                                System.out.println("Thank you... GoalID is now changed");
                                break;
                            case "goaldescription":
                                System.out.print("Enter new Goal Description: ");
                                String newGoalDescription = sc.next();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "goal_description", newGoalDescription);
                                System.out.println("Thank you... Goal Description is now changed");
                                break;
                            case "salesmanid":
                                System.out.print("Enter new ID: ");
                                int newId = sc.nextInt();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "salesman_id", newId);
                                System.out.println("Thank you... Salesman ID is now changed");
                                break;
                            case "targetvalue":
                                System.out.print("Enter new target value: ");
                                int newTargetValue = sc.nextInt();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "target_value", newTargetValue);
                                System.out.println("Thank you... Target Value is now changed");
                                break;
                            case "actualvalue":
                                System.out.print("Enter new actual value: ");
                                int newActualValue = sc.nextInt();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "actual_value", newActualValue);
                                System.out.println("Thank you... Actual Value is now changed");
                                break;
                            case "year":
                                System.out.print("Enter new year: ");
                                int newYear = sc.nextInt();

                                assert dbController != null;
                                dbController.updateEvaluationRecord(salesmanID, "year", newYear);
                                System.out.println("Thank you... Year is now changed");
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    } else {
                        System.out.println("Error");
                        break label;
                    }


                    break;
                case "delete":
                    // Delete Salesman or EvaluationRecord?
                    System.out.println("What do you want to delete in the Database?");
                    System.out.println("Type 'sale' to delete a salesman and 'record' to delete an Evaluation Record");
                    String deleteOperation = sc.next().toLowerCase();

                    if (deleteOperation.equals("sale")) {
                        // Read SID to delete the Salesman
                        System.out.print("Enter ID: ");
                        int deleteId = sc.nextInt();

                        try {
                            System.out.println("Deleting Salesman with the ID: " + deleteId + "\n");

                            assert dbController != null;
                            SalesMan deletedSalesman = dbController.deleteSalesman(deleteId);

                            //Print out deleted Salesman
                            System.out.println("Following Salesman has been deleted\n");
                            System.out.println(deletedSalesman.toString());
                        } catch (Exception e) {
                            System.out.println("Error!");
                            System.exit(-1);
                        }
                    } else if (deleteOperation.equals("record")) {
                        // Read SID to delete the evaluation record
                        System.out.print("Enter ID: ");
                        int deleteId = sc.nextInt();

                        try {
                            System.out.println("Deleting Evaluation Record of Salesman with ID: " + deleteId + "\n");

                            assert dbController != null;
                            EvaluationRecord deletedRecord = dbController.deleteEvaluationRecord(deleteId);

                            //Print out deleted Record
                            System.out.println("Following Evaluation Record has been deleted\n");
                            System.out.println(deletedRecord.toString());
                        } catch (Exception e) {
                            System.out.println("Error!");
                            System.exit(-1);
                        }
                    }
                    break;
                default:
                    System.out.println("Error, wrong operation!");
                    break;
            }

        }
    }

}
