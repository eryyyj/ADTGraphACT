/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pisalbon.pisalbon_adtgraph_activity;

import java.util.Scanner;

/**
 *
 * @author ERY JAY
 */
public class PISALBON_ADTGraph_Activity {
    public static void main(String[] args) {
        System.out.println("main");
        // variable assignment
        Scanner scanner = new Scanner(System.in);
        AttendanceSys lso = new AttendanceSys();
        System.out.println("Checking connection");
        lso.checkConnection();
        boolean Interface = true;
        
        // initiate the interface
        while (Interface) {
            System.out.println("\n1 Insert Student");
            System.out.println("2 Update Student");
            System.out.println("3 ADTGraph");
            System.out.println("0 Exit");
            System.out.print("Enter your choice:");

            int choice = scanner.nextInt();
            
            // switch case for the choice of the user
            switch (choice) {
                // option for inserting new account
                case 1 -> {
                    System.out.print("Enter First name: ");
                    String FName = scanner.next();
                    System.out.print("Enter Middle Name: ");
                    String MName = scanner.next();
                    System.out.print("Enter Last Name: ");
                    String LName = scanner.next();
                    lso.AddAccount(FName,MName,LName);
                }
                // option for updating some registered account
                case 2 -> {
                    System.out.print("Enter the studentID: ");
                    int studID = scanner.nextInt();
                    lso.UpdateAccount(studID);
                }
                // option for connecting the ids of registered students
                case 3 -> {
                    boolean adt = true;
                    while(adt){
                    System.out.print("Enter the # of ids to connect: ");
                    int numIDs = scanner.nextInt();
                    if (numIDs == 0){
                        System.out.println("invalid number");
                        continue;
                    }
                        lso.ADTGraph(numIDs);
                        adt = false;
                    }
                }
                case 0 -> Interface = false;
                default -> System.out.println("Invalid choice.");
            }  
        }
    }
}
