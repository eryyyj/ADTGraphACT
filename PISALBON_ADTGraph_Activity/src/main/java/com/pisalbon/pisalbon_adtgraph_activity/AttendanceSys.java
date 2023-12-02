/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pisalbon.pisalbon_adtgraph_activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ERY JAY
 */
public class AttendanceSys {
    // variable assignment
    private Map<Integer, List<Integer>> adjacencyList;
    public Connection conn;
    Scanner sc = new Scanner(System.in);
    
    // constructor class
    public AttendanceSys(){
       this.adjacencyList = new HashMap<>();
    }
    public void checkConnection(){
        String url = "jdbc:mysql://localhost:3306/attendancedb";
        String user = "root";
        String password="1630mysql";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connections Successful! " + url);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(" " + e);
        }
    }

    public void AddAccount(String FName, String MName, String LName) {
        try{
            String query = "insert into attendance" 
                    + "(First_Name,Middle_Name,Last_Name)"
                    + "values('"+FName+"','"+MName+"','"+LName+"')";
            Statement statement = conn.createStatement();
            statement.execute(query);
            System.out.println("Account has been added to the database!");
        }catch(SQLException e){
            System.out.println(""+e);
        }
    }

    public void UpdateAccount(int studID) {
        boolean UpdateAccInterface = true;
        while(UpdateAccInterface){
            System.out.println("\n1 Update First Name");
            System.out.println("2 Update Middle Name");
            System.out.println("3 Update Last Name");
            System.out.println("0 Exit");
            System.out.print("Enter your choice:");
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter your new First Name: ");
                    String UFName = sc.next();
                    UpdateFName(studID,UFName);
                    break;

                case 2:
                    System.out.print("Enter your new Middle Name: ");
                    String UMName = sc.next();
                    UpdateMName(studID,UMName);
                    break;
                case 3:
                    System.out.println("Enter your new Last Name");
                    String ULName = sc.next();
                    UpdateLName(studID,ULName);
                    
                case 0:
                    UpdateAccInterface = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
    public void UpdateFName(int studID,String UFName){
        try{
        String query1 = "update attendance "
                + "set First_Name = '"+UFName+"'"
                + "WHERE studentID = '"+studID+"'";
        System.out.println("First name sucessfully upadted");
        Statement statement = conn.createStatement();
        statement.execute(query1);
        }catch(SQLException e){
            System.out.println(""+ e);
        }
    }
    public void UpdateMName(int studID,String UMName){
        try{
        String query1 = "update attendance "
                + "set Middle_Name = '"+UMName+"'"
                + "WHERE studentID = '"+studID+"'";
        System.out.println("First name sucessfully upadted");
        Statement statement = conn.createStatement();
        statement.execute(query1);
        }catch(SQLException e){
            System.out.println(""+ e);
        }
    }
    public void UpdateLName(int studID,String ULName){
        try{
        String query1 = "update attendance "
                + "set Last_Name = '"+ULName+"'"
                + "WHERE studentID = '"+studID+"'";
        System.out.println("First name sucessfully upadted");
        Statement statement = conn.createStatement();
        statement.execute(query1);
        }catch(SQLException e){
            System.out.println(""+ e);
        }
    }

    void ADTGraph(int numIDs) {
        boolean graph = true;
        for (int i = 1; i <= numIDs; i++) {
            addVertex(i);
        }
        
        while (graph) {
            System.out.println("Enter ID as the vertex (0 to exit): ");
            int source = sc.nextInt();
            if (source == 0) {
                graph = false;
                continue;
            }
            System.out.println("Enter the ID destination: ");
            int destination = sc.nextInt();

            addEdge(source,destination);
            
    }
      printGraph();  
}
    private void addVertex(int i) {
        adjacencyList.put(i, new LinkedList<>());
    }

    private void addEdge(int source, int destination) {
        if (!adjacencyList.containsKey(source)) {
            addVertex(source);
        }

        if (!adjacencyList.containsKey(destination)) {
            addVertex(destination);
        }

        // Check for repeated connection
        if (!adjacencyList.get(source).contains(destination)) {
            adjacencyList.get(source).add(destination);
            adjacencyList.get(destination).add(source);
        } else {
            System.out.println("Error: Repeated connection. Please choose a different destination.");
        }
    }

    private void printGraph() {
        try {
        String query = "SELECT * FROM attendance";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, String> idToNameMap = new HashMap<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("studentID");
                String firstName = resultSet.getString("First_Name");
                String middleName = resultSet.getString("Middle_Name");
                String lastName = resultSet.getString("Last_Name");

                idToNameMap.put(id, firstName + " " + middleName + " " + lastName);
            }

            for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
                int currentId = entry.getKey();
                String currentName = idToNameMap.get(currentId);

                System.out.print("ID: " + currentId + " (" + currentName + ") is connected to: ");
                
                for (Integer neighbor : entry.getValue()) {
                    String neighborName = idToNameMap.get(neighbor);
                    System.out.print(" ID: " + neighbor + " (" + neighborName + ")");
                }
                
                System.out.println();
            }
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving data from the database: " + e.getMessage());
    }
    }
}