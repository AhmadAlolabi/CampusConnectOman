package com.mycompany.utilitytask;
import java.util.*;
import java.util.Scanner;

public class Utilitytask {

public static void main(String[] args) {
   ArrayList<String> serviceRequests = new ArrayList<>();
   Scanner input = new Scanner(System.in);
   int choice = 0;

    while (choice != 5) {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Add Service Request");
        System.out.println("2. Remove Service Request");
        System.out.println("3. Display All Requests");
        System.out.println("4. Iterate Through Requests");
        System.out.println("5. Exit");
        System.out.print("Enter your option: ");

        choice = input.nextInt();
        input.nextLine();

        // Add
        if (choice == 1) {

            System.out.print("Enter Student ID: ");
            int id = input.nextInt();
            input.nextLine();

            System.out.print("Enter Service Code: ");
            String serviceCode = input.nextLine();

            System.out.print("Enter Priority Type: ");
            String priority = input.nextLine();

            String request = "ID: " + id + " | Service Code: " + serviceCode + " | Priority: " + priority;

            serviceRequests.add(request);

            System.out.println("Request Added");
        }
        // Remove
        else if (choice == 2) {

            System.out.print("Enter Full Request To Remove: ");
            String request = input.nextLine();

            serviceRequests.remove(request);

            System.out.println("Request Removed");
        }
        // Display
        else if (choice == 3) {

            System.out.println("\n===== ALL REQUESTS =====");

            System.out.println(serviceRequests);
        }
        // Iterate
        else if (choice == 4) {

            System.out.println("\n===== ITERATING REQUESTS =====");

            Iterator<String> it = serviceRequests.iterator();

            while (it.hasNext()) {

                System.out.println(it.next());
            }
        }
        // Exit
        else if (choice == 5) {

            System.out.println("Program Ended");
        }
        else {

            System.out.println("Invalid Choice");
        }
    }
}}
