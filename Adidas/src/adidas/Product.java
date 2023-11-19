/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import java.util.*;
import java.io.*;

//Serialization: allows us to convert an Object to stream that we can send over the network or save it as file or store in DB for later usage. 
public class Product implements Serializable {
    //properties
    private String prodID;
    private String prodName;
    private int stockQuantity;
    private double prodPrice;
    private int orderQty;
    private int select;
    private int numberOfProd;
    private static boolean error;
    
    private static int prodCount = 0;
    
    //for methods
    PromptUI u = new PromptUI();
    Scanner sc = new Scanner(System.in);
    String prodTxtFilePath = "Product.txt";
    
    //no arg constructor
    public Product(){
        this(null ,null, 0,0.0);
    }
    
    //to create new product
    public Product(String prodName, int stockQuantity, double price) {
        this.prodID = 'P' + String.format("%03d", prodCount + 1);
        this.prodName = prodName;
        this.stockQuantity = stockQuantity;
        this.prodPrice = price;
        this.orderQty = 0;
        prodCount++;
    }
    
    //to store read product
    public Product(String prodID, String prodName, int stockQuantity, double price){
        this.prodID = prodID;
        this.prodName = prodName;
        this.stockQuantity = stockQuantity;
        this.prodPrice = price;
        this.orderQty = 0;
    }
    
        public Product(int numberOfProd, String prodName, int stockQuantity, double price){ //number of product id increase
        this.prodID = 'P' + String.format("%03d", numberOfProd);        
        this.numberOfProd = numberOfProd;
        this.prodName = prodName;
        this.stockQuantity = stockQuantity;
        this.prodPrice = price;
        this.orderQty = 0;
    }
    
    //to create dupplicate product
    public Product(Product prod){
        this.prodID = prod.getProdID();
        this.prodName = prod.getProdName();
        this.stockQuantity = prod.getStockQuantity();
        this.prodPrice = prod.getProdPrice();
        this.orderQty = prod.getOrderQty();
    }

    public String getProdID() {
        return prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getOrderQty() {
        return orderQty;
    }
    
    //Setters
    public void setProdID(String prodID) {
        this.prodID = prodID;
    }
    
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
    
    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }
    
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

//I/O TEXT FILE------------------------------------------------------------------------
    //Write file
    public void storeProdData(List<Product> prodData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(prodTxtFilePath))) {
            for (Product p : prodData) {
                writer.println(p.prodID+ "," +
                               p.prodName + "," +
                               p.stockQuantity + "," +
                               p.prodPrice + "," );
        }
        } catch (IOException e) { e.printStackTrace(); }
    }
    //Read file
    public ArrayList<Product> readProdData(){
       ArrayList<Product> prodData = new ArrayList<Product>();

        try (BufferedReader reader = new BufferedReader(new FileReader(prodTxtFilePath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Assuming each cust info is separated by comma
                String[] parts = line.split(",");
                prodData.add(new Product(parts[0], parts[1],Integer.parseInt(parts[2]), Double.parseDouble(parts[3])));
            }
            
        } catch (IOException e) {  e.printStackTrace();  }
        return prodData;
    }
    
// Printing elements -------------------------------------------------------------------
    // Showing products
    public String getProdListHead(){
        return u.repeatChar('=', 67) + "\n" 
             + u.alignCenter("Product Catalogue", 67)+ "\n"
             + u.repeatChar('=', 67) + "\n"
             + String.format("%-5s %-12s %-20s %-15s %-15s\n","No.","Product ID", "Product Name", "Quantity", "Price(RM)")
             + u.repeatChar('-', 67);
    }
    
    public String getProdItems(int count){
        return String.format("%-5s %-12s %-20s %-15s %-15s",
                u.bracketNum(count),this.prodID, this.prodName, this.stockQuantity, prodPrice);
    }

    public void showProdCatalogue(List<Product> prodCat){
        int count = 1;
        System.out.println(getProdListHead());
        for(Product p: prodCat){
            System.out.println(p.getProdItems(count));
            count++;
        }
        System.out.print(u.repeatChar('=', 67) + "\n");
      
    }
    
    // Showing cart 
    public String getProdLineHead(){
        return u.repeatChar('=', 77) + "\n" 
             + u.alignCenter("Order Catalogue", 77)+ "\n"
             + u.repeatChar('=', 77) + "\n"
             + String.format("%-5s %-12s %-20s %-9s %-11s %-15s\n","No.","Product ID", "Product Name", "Quantity", "Price(RM)", "Order Quantity")
             + u.repeatChar('-', 77);
    }
    
    public String getProdLine(int count){
        return String.format("%-5s %-12s %-20s %-9d %-11s %15d",
                u.bracketNum(count),this.prodID, this.prodName, this.stockQuantity, prodPrice, orderQty);
    }
    
    public void showTempOrderItem(Product[] prodOrder){
        int count = 1;
        System.out.println(getProdLineHead());
        for(Product p: prodOrder){
            System.out.println(p.getProdLine(count));
            count++;
        }
        System.out.print(u.repeatChar('=', 77) + "\n");
    }
    
    //Show order products for final confirmation
    public String getOrderLine(int count){
        return String.format("%-5s %-12s %-20s %-15s %15d",
                u.bracketNum(count),this.prodID, this.prodName, prodPrice, orderQty);
    }
    
    public int showProdMenu(){

        System.out.print("""                        
                         
                         (1) Make order
                         (2) Return
                         
                          """);
        
        
        do {
                  
                try {
                    System.out.print("Enter selection: ");
                    select = sc.nextInt();
                    error = validationSelect(select);
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }
            } while (error);
        
        return select;
    }
    
//Purchase item verification------------------------------------------------------------------
    public boolean checkOutOfStock(int selection, Product[] prodList){
        return ( prodList[selection-1].getStockQuantity() == 0 ? true : false );
    }
    
    public boolean checkQtyAvailability(int selection, int orderQty, Product[] prodList){
        return ( orderQty <= prodList[selection - 1].getStockQuantity() ? true : false );
    }
   
    public void addOrderLine(int qty){
        this.orderQty += qty;
        this.stockQuantity -= qty;
    }
    
    public void restoreQuantity(Product[] prodList, ArrayList<Product> soldProd){
        for(Product sp : soldProd){
            for(Product pc : prodList){
                if(sp.getProdID().equals(pc.getProdID())){
                    pc.setStockQuantity(sp.getStockQuantity() + pc.getOrderQty());
                }
            }
        }
    }
    public static Product[] updateRemainingStock(Product[] prodList, ArrayList<Product> soldProd){  
        for(Product sp : soldProd){
            for(Product pc : prodList){
                if(sp.getProdID().equals(pc.getProdID())){
                    pc.setStockQuantity(sp.getStockQuantity());
                    pc.setOrderQty(0);
                }     
            }
        }
        return prodList;
    }
    
    /*ArrayList(Pass by reference):
        If you have a method that takes an ArrayList as a parameter and modifies it within the method, 
        those modifications will be reflected in the original ArrayList that was passed as an argument. 
        This behavior is because Java passes object references by value.
    */
    
    // Manage data stored
    public static Product[] convertArrListtoArr(ArrayList<Product> arrlist){
        Product[] arr = new Product[arrlist.size()];
        
        for (int i = 0; i < arrlist.size(); i++)
            arr[i] = new Product(arrlist.get(i));
        
        return arr;
    }
    
    public static ArrayList<Product> convertArrToArrList(Product[] arr){
        return new ArrayList<>(Arrays.asList(arr)); 
    }
    
    public Product[] removeUnorderedItemFromArr(Product[] arrP) {
        // Count the number of elements to keep
        int count = 0;
        for (Product p : arrP){
            if (p.orderQty != 0)
                count++;
        }
        
        // Create a new array with the elements to keep
        Product[] newArr = Arrays.copyOf(arrP, count);
        
        int newIndex = 0;
        for (Product p : arrP) {
            if (p.orderQty != 0)
                newArr[newIndex++] = p;
        }

        return newArr;
    }
    
    public static boolean validationSelect(int value){
        if (value < 1 || value > 2) {
            System.out.println("Selection should be (1 - 2)");
            return true;
        }
          else {
            return false;
        }
    
    }
    
    public void addProduct(){
        
        Scanner sc = new Scanner(System.in);
        String productName;
        int stock = 0;
        double price;
        Product p = new Product();
        ArrayList<Product> oriProd = p.readProdData();
        
        do{
        error = false;
        System.out.println("Enter product name(Press x to return):");
        productName = sc.nextLine();
        
        checkDuplicate(productName);
        if(error == true){
             System.out.println("Product name has duplicated !!");
         }
        else if(productName.isEmpty()){
            System.out.println("Product name should not be empty !!");
            error = true;
        }
        
        if(productName.toUpperCase().equals("X")){
            p.showProdCatalogue(oriProd);
            break;
        }
        
        }while(error);
        
        do {
                error = false;
                  
                try {
                    System.out.print("Enter stock quantity: ");
                    stock = sc.nextInt();
                    while(stock < 1){
                        System.out.println("it is invalid number");
                        System.out.print("Enter stock quantity: ");
                        stock = sc.nextInt(); //use this to avoid infinite loop of printing invalid meesage
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }
                
                 }while(error);
        do {
                error = false;
                  
                try {
                    System.out.print("Enter price: ");
                    price = sc.nextInt();
                    while(price <= 0){
                        System.out.println("it is invalid number");
                        System.out.print("Enter price: ");
                        price = sc.nextInt();
                        sc.nextLine();
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }
                
          }while(error);
        
       
           
    }
    
    
    public static boolean checkDuplicate(String prodName){
        Product p = new Product();
        ArrayList<Product> oriProd = p.readProdData();
        for (int i = 0 ; i < oriProd.size(); i++) {
            if(oriProd.get(i).getProdName().equals(prodName)){
               error = true;
               return error;
            }
           
        }
        return false;
    }
    
    

    
}