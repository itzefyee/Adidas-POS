/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import static adidas.Main.sc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


/**
 *
 * @author Efye
 */

public class Customer extends User implements FileManager {
        static PromptUI UI = new PromptUI();
        private static int totalCustomer = 0;
        //private String password;
        private String customerID;
        private Date regDate;
        private double balance;
        private static int custIndex=0;
        private boolean error = false;
        static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
        
    //Constructors
    public Customer() {

    }
        
    public Customer(int customerID1, String name, String phoneNo,
            String gender, double balance) {
        super(name, phoneNo, gender);
        this.customerID = 'C' + String.format("%03d", customerID1);
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.balance = balance;
    }

    public Customer(String customerID2, String name, String phoneNo,
            String gender, double balance) {
        super(name, phoneNo, gender);
        this.customerID = customerID2;
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.balance= balance;
    }

    
    //Getters
    
    
    public int getCustIndex(){
        return custIndex;
    }
    
    public static int getTotalCustomer() {
        return totalCustomer; 
    }
    
    public String getCustomerID() { 
        return customerID; 
    }
    public Date getRegDate() {
        return regDate; 
    }
    public double getBalance() {
        return balance; 
    }
    
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

     @Override
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setBalance(double balance) {
        this.balance = balance; 
    }
    
   
    @Override
    public String toString(){
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
	    String dateRegistered= formatter.format(regDate);
	    
    	return String.format("\nCustomer ID: %s\nDate registered: %s\n",
    	customerID, dateRegistered) + super.toString();
    }

    // IDK ABOUT THIS
    @Override 
    public boolean equals(Object o) {
        if ( !(o instanceof Customer ) ) {
                return false;
        }
        Customer o7 = (Customer) o;
        return (this.customerID != null && o7.customerID != null) && this.customerID.equals(o7.customerID);
    }
    
    public void topUp(ArrayList<Customer> cust) throws ParseException{
        Scanner sc = new Scanner(System.in);
        PromptUI ui = new PromptUI();
        boolean validTopUpId= true;
        String id;
        double inputBln=0;
        List<String> topUp = new ArrayList<>();
        ArrayList<Customer> testWriteCustomer = cust;
        
        //read file 
       try{
           
       FileReader fileReader = new FileReader("TopUpID.txt");
       BufferedReader br = new BufferedReader(fileReader);
       
       String line;
       
        while((line = br.readLine())!=null){
            
            topUp.add( line);
        
       }
       }catch (IOException e) {
             System.err.println("Error reading from file: " + e.getMessage());
        }
  
        System.out.print("Enter the TOP-UP ID:");
        id=sc.next();
        
        if(topUp.contains(id)){
                 System.out.println("Your TOP-UP ID is valid.");
                 while(true){
                     System.out.print("How many you want to top up:");
                     if(sc.hasNextDouble()){
                          inputBln=sc.nextDouble();
                         cust.get(custIndex).setBalance(getBalance()+inputBln);
                        FileManager.writeCustomer(testWriteCustomer);
                        System.out.println("Balance updated successfuly!");
                        ui.pressEnterToContinue();
                        break;

                     }
                     else{
                         System.out.println("Invalid input amount");
                         sc.nextLine();
                         sc.nextLine();
                     }
                 }
        }
        
        else{
             System.out.println("Your TOP-UP ID is not found.");
             ui.pressEnterToContinue();
            
        }
            
        
    }
    
    public boolean customerSignUp(ArrayList<Customer> custOri) {
        UI.clearJavaConsoleScreen();
        Main.showLogo();
        System.out.println(
                "\n" + UI.repeatChar('=', 73) + "\n" +
                "|" + UI.alignCenter("Register as a member", 71) + "|" + "\n" + 
                UI.repeatChar('=', 73)
            );
        do {
            error = false;
            System.out.print("Enter Customer name(Press x to cancel):");
            name = sc.nextLine();

            if (name.isEmpty()) {
                System.out.println("Customer name should not be empty !!");
                error = true;
            }

            if (name.toUpperCase().equals("X")) {
                break;
            }

        } while (error);
        
        do {
            if (name.toUpperCase().equals("X")) {
                break;
            }
            System.out.print("Enter contact number (01X-XXXXXXX)(Press x to cancel): ");
            phoneNo = sc.nextLine();

            if (phoneNo.equalsIgnoreCase("X")) {
                break;
            }

            while (phoneNo.isEmpty()) {
                System.out.println("Please enter a valid contact number!");
                System.out.println("Enter the worker contact number (XXX-XXXXXXX)(Press x to cancel): ");
                phoneNo = sc.nextLine();

            }
        }while (!phoneNo.matches("\\d{3}-\\d{7}"));
            
            do {
                if (name.toUpperCase().equals("X")) {
                break;
            }
                
                error = false;
                System.out.print("Enter gender(Press x to cancel):");
                gender = sc.nextLine();

                if (gender.toUpperCase().equals("X")) {
                    break;
                }
                
                if (gender.isEmpty() || (!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F"))) {
                    System.out.println("Invalid gender !!");
                    error = true;
                }

            } while (error == true);
            if (!name.equalsIgnoreCase("x")) {
                System.out.println("Successfully added a new member into the programs...");
                ArrayList<Customer> tempArr = custOri;
                tempArr.add(new Customer(tempArr.size() + 1, name, phoneNo,gender.toUpperCase(), 500.0));
                System.out.println("Your Login ID is "+ tempArr.get(tempArr.size() - 1).getCustomerID() );
                FileManager.writeCustomer(tempArr);
                 error = false;
                return error;
            }
            error = true;
            return error;
        } 
    
    public int customerLogin() {

        ArrayList<Customer> custOri = FileManager.readCustomer();
        UI.clearJavaConsoleScreen();
        Main.showLogo();
        System.out.println(
                "\n" + UI.repeatChar('=', 73) + "\n" +
                "|" + UI.alignCenter("Membership Login", 71) + "|" + "\n" + 
                UI.repeatChar('=', 73)
            );
        int i = 0;
        do {
            if (custOri.size() == i) {
                    System.out.println("Invalid ID, should be (CXXX)");
                }
            
            i = 0;
            error = false;
            System.out.print("Enter Customer ID(Press x to return):");
            customerID = sc.nextLine();

            if (customerID.isEmpty()) {
                System.out.println("Customer ID should not be empty !!");
                error = true;
            }

            if (customerID.toUpperCase().equals("X")) {
                i = -1;
                break;
            }

            
            for (Customer c : custOri) {
                if (custOri.get(i).getCustomerID().equals(customerID)) {
                    break;
                } else {
                    i++;
                }
            }
        } while (custOri.size() == i || customerID.isEmpty());
       if(i != -1){ 
       System.out.println("Succesfully logined");
       return i;
       }
       return i;
    }
    
    public String memberCheck(Customer c, int CustIndex, ArrayList<Customer> Cust) {
        String memberOrNot;
        do {
            error = false;
            UI.clearJavaConsoleScreen();
            Main.showLogo();
            System.out.println(
                "\n" + UI.repeatChar('=', 73) + "\n" +
                "|" + UI.alignCenter("Membership Login", 71) + "|" + "\n" + 
                UI.repeatChar('=', 73)
            );
            System.out.printf("Are you a member(y/n)? : ");
            memberOrNot = sc.nextLine();
            while (memberOrNot.isEmpty()) {
                System.out.println("Do not enter empty!!");
                System.out.printf("Are you a member(y/n)? : ");
                memberOrNot = sc.nextLine();
            }
            
            if (memberOrNot.equalsIgnoreCase("N")) {
                System.out.printf("\nRegister as a member? (y/n): ");
                memberOrNot = sc.nextLine();
                while (memberOrNot.isEmpty()) {
                    System.out.println("Do not enter empty!!");
                    System.out.printf("\nRegister as a member? (y/n): ");
                    memberOrNot = sc.nextLine();
                }
                
                if(memberOrNot.equalsIgnoreCase("Y")){
                    System.out.println();
                    error = c.customerSignUp(Cust);
                    UI.pressEnterToContinue();
                }
            }
            
            
                UI.clearJavaConsoleScreen();
          if(error == false){
            if(memberOrNot.equalsIgnoreCase("Y")){
                    custIndex = c.customerLogin();
                    if(custIndex == -1){
                        System.out.println("Login failed going back to question...");
                        memberOrNot = "u";
                        UI.pressEnterToContinue();
                    }
                        
                }
          }
          else{
            memberOrNot = "u";
        }
            
        } while (!memberOrNot.equalsIgnoreCase("Y") && !memberOrNot.equalsIgnoreCase("N"));
        return memberOrNot;
    }
    
    public void customerDetail(ArrayList<Customer> c){
         PromptUI u = new PromptUI();

               int count = 0;
        System.out.println(u.repeatChar('=', 67) + "\n" 
             + u.alignCenter("Customer Details", 67)+ "\n"
             + u.repeatChar('=', 67) + "\n"
             + String.format("%-5s %-12s %-20s %-15s %-5s %-15s\n","No.","Customer ID", "Customer Name", "Phone No", "Gender", "Balance")
             + u.repeatChar('-', 67));
        for (int i = 0; i < c.size(); i++) {
            System.out.println(getCustDetail(count));
            count++;
        }
        System.out.print(u.repeatChar('=', 67) + "\n");

    }

    public String getCustDetail(int count){
        PromptUI u = new PromptUI();
        ArrayList<Customer> Cust = FileManager.readCustomer();
        setCustomerID(Cust.get(count).getCustomerID());
        setName(Cust.get(count).getName());
        setPhoneNo(Cust.get(count).getPhoneNo());
        setGender(Cust.get(count).getGender());
        setBalance(Cust.get(count).getBalance());


        return String.format("%-5s %-12s %-20s %-15s %-5s %-20s",
                u.bracketNum(count+1),this.customerID, this.name, this.phoneNo, gender, balance);
    }
    
    
}
