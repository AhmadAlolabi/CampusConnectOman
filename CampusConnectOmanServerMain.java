package com.mycompany.campusconnectomanservermain;
import java.io.*;
import java.net.*;
import java.sql.*;

class CampusConnectOmanServer implements Runnable{
public void run(){
try{
  // create server socket and wait for client
ServerSocket SS = new ServerSocket(4000);
Socket S = SS.accept();

  // create streams to send and receive data from client
DataInputStream DIS = new DataInputStream(S.getInputStream());
DataOutputStream DOS = new DataOutputStream(S.getOutputStream());

  // receive data from client
int ID = DIS.readInt();
String serviceCode = DIS.readUTF();
String priority = DIS.readUTF();
System.out.println("Student ID: " + ID);
System.out.println("Service Code: " + serviceCode);
System.out.println("Priority: " + priority);
 
  // validate priority type
if (priority.equalsIgnoreCase("Normal")|| priority.equalsIgnoreCase("Express"))
{ 
System.out.println("Valid Priority");
}

else
{
System.out.println("Invalid Priority Type");
}
            
  // database connection
String host = "jdbc:derby://localhost:1527/StudentInformationDatabasesAhmad";
String UN = "Ahmad";
String Pwd= "1234";       
Connection con = DriverManager.getConnection(host , UN , Pwd);
Statement stmt = con.createStatement();

  // student query table 1
String query1 = "SELECT * FROM AHMAD.STUDENT_INFORMATION WHERE ID=" + ID;
stmt.execute(query1);
ResultSet RS1 = stmt.getResultSet();

String name = "";
int age = 0;
String scholarship = "";

  // get student query data from table 1
if (RS1.next()) 
{
name = RS1.getString("NAME");
age = RS1.getInt("AGE");
scholarship = RS1.getString("SCHOLARSHIP_TYPE");
}
else
{
    System.out.println("Student ID Not Found");
}

  // get service query data from table 2
String query2 =
"SELECT * FROM AHMAD.STUDENT_SERVICE_DETAILS WHERE SERVICE_CODE='"+ serviceCode + "'";
Statement stmt2 = con.createStatement();
stmt2.execute(query2);
ResultSet RS2 = stmt2.getResultSet();

double baseFee = 0;

// get service fee
if (RS2.next()) 
{
baseFee = RS2.getDouble("BASE_FEE");
}
else
{
    System.out.println("Service Code Not Found");
}

//calculation
double finalBill = baseFee;
double scholarshipDiscount = 0;

  // scholarship discount
if(scholarship != null && scholarship.equalsIgnoreCase("Deans List"))
{
    scholarshipDiscount = baseFee * 0.15;
}

else if(scholarship != null && scholarship.equalsIgnoreCase("Economically Weak"))
{
    scholarshipDiscount = baseFee * 0.20;
}

else if(scholarship != null && scholarship.equalsIgnoreCase("Employee Special Support"))
{
    scholarshipDiscount = baseFee * 0.10;
}
  //age adjustment
finalBill = finalBill - scholarshipDiscount;
double ageAdjustment = 0;

if(age <= 18)
{
    ageAdjustment = finalBill * 0.05;
    finalBill = finalBill - ageAdjustment;
}

else if(age > 23)
{
    ageAdjustment = finalBill * 0.05;
    finalBill = finalBill + ageAdjustment;
}
  //Priority charge
double priorityCharge = 0;
String deliveryTime = "";

if(priority.equalsIgnoreCase("Express"))
{
    priorityCharge = finalBill * 0.10;
    finalBill = finalBill + priorityCharge;
    deliveryTime = "15-20 minutes";
}

else
{
    deliveryTime = "30-45 minutes";
}

  // send result to client
DOS.writeUTF(name);
DOS.writeInt(age);
DOS.writeUTF(scholarship);
DOS.writeDouble(baseFee);
DOS.writeDouble(scholarshipDiscount);
DOS.writeDouble(ageAdjustment);
DOS.writeDouble(priorityCharge);
DOS.writeDouble(finalBill);
DOS.writeUTF(deliveryTime);
DOS.flush();

  // close resources
RS1.close();
RS2.close();
stmt.close();
stmt2.close();
con.close();
DIS.close();
DOS.close();
S.close();
SS.close();
 }
 catch(Exception e){
 e.printStackTrace();
 }
}
}

public class CampusConnectOmanServerMain {

    public static void main(String[] args) {
    CampusConnectOmanServer ccos = new CampusConnectOmanServer();
    Thread T = new Thread(ccos);
      T.start();    
    }
}
