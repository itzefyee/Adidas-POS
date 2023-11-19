/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import java.io.*;
import java.text.*;
import java.util.*;


/**
 *
 * @author Efye
 */
interface FileManager {
    
    final String CUST_FILE_PATH = "Customer.txt";
    final String STAFF_FILE_PATH = "Staff.txt";
    final String STOCK_FILE_PATH = "Stock.txt";
    final String SALES_FILE_PATH = "Sales.txt";
    
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    
    //--------CUSTOMER--------
    
    //Read file
    public static ArrayList<Customer> readCustomer(){
        
        String CustID = "";
        String name = "";
        String phoneNo = "";
        String gender = "";
        double balance = 0.0;
        
        ArrayList<Customer> allCustomer = new ArrayList<>();
        
        try {
            // open file
            FileReader fileReader = new FileReader(CUST_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
                    
            //read content of the file until EOF
            String line;
            
                while ( (line = bufferedReader.readLine()) != null) {
                    //split data by |
                    String[] data = line.split(",");
                    
                    if (data.length >= 5) {
                        CustID = data[0];
                        name = data[1];   
                        phoneNo = data[2];
                        gender = data[3];
                        balance = Double.parseDouble(data[4]);
                    }

                    Customer tempCustomer = new Customer(CustID,name, phoneNo, gender, balance);
                    allCustomer.add(tempCustomer);

                }
            
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        } catch(NullPointerException e){
            System.out.print("Caution. Null values are present!\n");
        }
        
        return allCustomer;
    }
    //Write file
    public static void writeCustomer(ArrayList<Customer> allCustomer){
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUST_FILE_PATH))) {
            for (Customer c : allCustomer) {
                writer.println(
                        c.getCustomerID() + "," +
                        c.getName() + "," +
                        c.getPhoneNo() + "," +
                        c.getGender() + "," +
                        c.getBalance()
                );
            }
        } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    }
    //Append file
    public static void appendCustomer(ArrayList<Customer> allCustomer){
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUST_FILE_PATH, true))) {
            for (Customer c : allCustomer) {
                writer.println(
                        c.getCustomerID() + "," +
                        c.getName() + "," +
                        c.getPhoneNo() + "," +
                        c.getGender() + "," +
                        c.getBalance()
                );
            }
        } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    }
    
    
    //--------STAFF--------
    
    public static ArrayList<Worker> readStaff(){
        String ID = "";
        String name = "";
        String password = "";
        String contact = "";
        String gender = "";
        String email = "";
        String position = "";

        ArrayList<Worker> staffList = new ArrayList<>();

        try {
            // open file
            FileReader fileReader = new FileReader(STAFF_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
                    
            //read content of the file until EOF
            String line;
                while ( (line = bufferedReader.readLine()) != null) {
                    
                    //split data by |
                    String[] data = line.split(",");
                    
                    if (data.length >= 7) {
                        ID = data[0];
                        name = data[1];
                        password = data[2];  
                        contact = data[3];
                        gender = data[4];
                        email = data[5];
                        position = data[6];
                    }

                    Worker tempStaff = new Worker(ID, name, password, contact, gender, email, position);
                    staffList.add(tempStaff);

                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        } catch(NullPointerException e){
            System.out.print("Caution. Null values are present!\n");
        }
    
        return staffList;
    }
    //Write file
    public static void writeStaff(ArrayList<Worker> staffList){
        try (PrintWriter writer = new PrintWriter(new FileWriter(STAFF_FILE_PATH))) {
            for (Worker w : staffList) {
                writer.println(
                        w.getStaffID() + "," +
                        w.getName() + "," +
                        w.getStaffPassword( )+ "," +
                        w.getPhoneNo() + "," +
                        w.getGender() + "," +
                        w.getStaffEmail() + "," +
                        w.getPosition()
                );
            }
        } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    }
    //Append file
    public static void appendStaff(ArrayList<Worker> staffList){
        try (PrintWriter writer = new PrintWriter(new FileWriter(STAFF_FILE_PATH, true))) {
            for (Worker w : staffList) {
                writer.println(
                        w.getStaffID() + "," +
                        w.getName() + "," +
                        w.getStaffPassword( )+ "," +
                        w.getPhoneNo() + "," +
                        w.getGender() + "," +
                        w.getStaffEmail() + "," +
                        w.getPosition()
                );
            }
        } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    }
    
    //--------STOCK--------
    
    public static ArrayList<Product> readStock(){
        String prodID = "";
        String name = "";
        double price = 0.0;
        int quantity = 0;

         ArrayList<Product> allStock = new ArrayList<>(); 
        try {
            // open file
            FileReader fileReader = new FileReader(STOCK_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
                    
            //read content of the file until EOF
            String line;
                while ( (line = bufferedReader.readLine()) != null) {
                    
                    //split data by |
                    String[] data = line.split(",");
                    
                    if (data.length >= 4) {
                        prodID = data[0];
                        name = data[1];
                        quantity = Integer.parseInt(data[2]);  
                        price = Double.parseDouble(data[3]);
                    }

                    
                    Product oneProduct = new Product(prodID, name, quantity, price);
                    allStock.add(oneProduct);
                }
            
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    
        return allStock;
    }
    
    //Write file
    public static void writeStock(ArrayList<Product> prodData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STOCK_FILE_PATH))) {
            for (Product p : prodData) {
                writer.println(
                        p.getProdID() + "," +
                        p.getProdName() + "," +
                        p.getStockQuantity() + "," +
                        p.getProdPrice()
                );
        }
        } catch (IOException e) { e.printStackTrace(); }
    }
   
    //--------SALES--------
    
    //Read file
     public static ArrayList<SalesRecord> readSales(){
        String productID = "";
        String productName = "";
        int salesQuantity = 0;
        double salesAmount = 0;

         ArrayList<SalesRecord >allSales = new ArrayList<>();
        try {
            // open file
            FileReader fileReader = new FileReader(SALES_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
                    
            //read content of the file until EOF
            String line;
                while ( (line = bufferedReader.readLine()) != null) {
                    
                    //split data by |
                    String[] data = line.split(",");
                    
                    if (data.length >= 4) {
                        productID = data[0];
                        productName = data[1];
                        salesQuantity = Integer.parseInt(data[2]);  
                        salesAmount = Double.parseDouble(data[3]);
                    }

                    Product product = new Product(productID, productName, 0, 0);
                    SalesRecord record = new SalesRecord(product, salesQuantity, salesAmount);
                    allSales.add(record);

                }
            
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
        }
    
        return allSales;
    }
    //Write file
    public static void writeSales(ArrayList<SalesRecord> allSales) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALES_FILE_PATH))) {
            for (SalesRecord s : allSales) {
                Product soldProduct = s.getProduct();
                writer.println(
                        soldProduct.getProdID() + "," +
                        soldProduct.getProdName() + "," +
                        s.getQuantitySold() + "," +
                        s.getSalesAmount()
                );
        }
        } catch (IOException e) { e.printStackTrace(); }
    }
}



