package com.mycompany.campusconnectomanclientmain;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class CampusConnectOmanClient implements Runnable{
 public void run(){
 try{
   // Connect to server
Socket S = new Socket("localhost", 4000);
  // Send data to server
DataInputStream DIS = new DataInputStream(S.getInputStream());
  // Receive data from server
DataOutputStream DOS = new DataOutputStream(S.getOutputStream());
  // Get user input    
Scanner input = new Scanner(System.in);

System.out.println("Enter Student ID");
int ID = input.nextInt();
input.nextLine();

System.out.println("Enter Service Code");
String serviceCode = input.nextLine().toUpperCase();

System.out.println("Enter Priority Type");
String priority = input.nextLine();

  // send data to server
DOS.writeInt(ID);
DOS.writeUTF(serviceCode);
DOS.writeUTF(priority);
DOS.flush();

  // receive results from server
String name = DIS.readUTF();
int age = DIS.readInt();
String scholarship = DIS.readUTF();

double baseFee = DIS.readDouble();
double scholarshipDiscount = DIS.readDouble();
double ageAdjustment = DIS.readDouble();
double priorityCharge = DIS.readDouble();
double finalBill = DIS.readDouble();

String deliveryTime = DIS.readUTF();
// Print result
System.out.println("\n===== BILL DETAILS =====");

System.out.println("Student Name: " + name);
System.out.println("Age: " + age);
System.out.println("Scholarship Type: " + scholarship);
System.out.println("Base Fee: " + baseFee);
System.out.println("Scholarship Discount: " + scholarshipDiscount);
System.out.println("Age Adjustment: " + ageAdjustment);
System.out.println("Priority Charge: " + priorityCharge);
System.out.println("Final Bill: " + finalBill);
System.out.println("Expected Delivery Time: " + deliveryTime);
  // Close connection
DIS.close();
DOS.close();
S.close();
 }
 catch(Exception e){
 e.printStackTrace();
 }
}
}

public class CampusConnectOmanClientMain {

    public static void main(String[] args) {
    CampusConnectOmanClient CCOC = new CampusConnectOmanClient();
    Thread T = new Thread(CCOC);
      T.start();    
    }
}
