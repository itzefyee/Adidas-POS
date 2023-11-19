/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

/**
 *
 * @author limsh
 */
import java.io.*;
import java.util.*;
import static adidas.PromptUI.UI;

public class PurchaseOrder{
    //properties
    private String poID;
    private Date orderDate;
    private ArrayList<Product> orderLines;
    private static int orderCount = 0;
    private boolean error;
    private static double total=0.0;
    private static int numberOfProd;


    //for methods
    Scanner sc = new Scanner(System.in);
    Product p = new Product();
    private String poFilePath = "PO.txt";
    
    
    public PurchaseOrder() {
        this.poID = 'O' + String.format("%03d", orderCount + 1 );
        this.orderDate = new Date();
        this.orderLines = new ArrayList<Product>();
        
    }

    public PurchaseOrder(int newOrder){
        this();
        orderCount++;
        //create new order
    }
    
    //to create dupplicate product
    public PurchaseOrder(PurchaseOrder po){
        this.poID = po.getPoID();
        this.orderDate = po.getOrderDate();
        this.orderLines = po.getOrderLines();
    }

    //read data
    public PurchaseOrder(String poID, Date orderDate, ArrayList<Product> orderLines) {
        this.poID = poID;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
    }
    
    public String getPoID() {
        return poID;
    }

    public void setPoID(String poID) {
        this.poID = poID;
    }
    
    

    public Date getOrderDate() {
        return orderDate;
    }


    public double calculateTotalCost() {
        double totalCost = 0;
        for (Product p : orderLines) {
            totalCost += p.getProdPrice();
        }
        return totalCost;
    }

    public ArrayList<Product> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<Product> orderLines) {
        this.orderLines = orderLines;
    }

    public Product[] addItemsToCart(Product[] prodList){
        int action = 1, selection = 0, qty = 0;
        ArrayList<Product> prodSelection = FileManager.readStock();
        do{
            switch(action){
                case 1:
                    UI.clearJavaConsoleScreen();
                    p.showTempOrderItem(prodList); // Show existing ordered items.
                    
            do {
            error = false;

            try {
                System.out.print("Enter number of selection:");
                selection = sc.nextInt();
               
                error = validationNumberOfProduct(selection, prodSelection);
                while (error == true) {
                    System.out.println("it is invalid number");
                    System.out.print("Enter number of selection: ");
                    selection = sc.nextInt();
                  
                    error = validationNumberOfProduct(selection, prodSelection);
                    sc.nextLine();
                    
                }
                
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);
            
                    if(p.checkOutOfStock(selection, prodList)){ // Check whether item is out of stock.
                        System.out.println("\nOut of stock. Please choose another product.");
                        UI.pressEnterToContinue();
                        action = 1;
                        break;
                    }
                case 2: 
                    
           do {
                error = false;
                  
                try {
                    System.out.print("Enter quantity: ");
                    qty = sc.nextInt();
                    error = validationOrderQuantity(qty);
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }
            } while (error);
           
                    if(! p.checkQtyAvailability(selection, qty, prodList)){ // Check whether the order quantity exceed the stock quantity.
                        System.out.println("\nNot enough stock. Please re-enter quantity.");
                        UI.pressEnterToContinue();
                        action = 2;
                        break;
                    } else{
                        prodList[selection -1].addOrderLine(qty);
                        UI.clearJavaConsoleScreen();
                        p.showTempOrderItem(prodList);
                    }
                case 3:
                    System.out.print("Add item(Y/N)?: ");
                    String add = sc.next();
                    
      while(!add.equals("Y") && !add.equals("N") && !add.equals("y") && !add.equals("n")){
            System.out.println("Invalid input!! (only Y and N)");
            System.out.print("Add item(Y/N)?:");
            add = sc.next();
        }                
                    
                    if(add.equals("N") || add.equals("n"))
                        action = 3;
                    else
                        action = 1;
                default:
                    break;
            }
        } while(action != 3);

        return p.removeUnorderedItemFromArr(prodList); // Remove not ordered items
    } 
    
    public String printPOHead(PurchaseOrder po){
        return UI.repeatChar('=', 71) + "\n" 
             + UI.alignCenter("Product Order", 71)+ "\n"
             + UI.repeatChar('=', 71) + "\n"
             + String.format("Date: %s\n", UI.formatDate(orderDate))
             + UI.repeatChar('-', 71) + "\n"
             + String.format("%-5s %-12s %-20s %-15s %-15s\n","No.","Product ID", "Product Name", "Price(RM)", "Order Quantity")
             + UI.repeatChar('-', 71);
        
    }
    
    //calculate total price
    public double calTotal(Product[] prodList){
        total=0.0;
        for(int i=0;i<prodList.length;i++){
            double subtotal =prodList[i].getProdPrice()*prodList[i].getOrderQty();
            total+=subtotal;
        }
        
      
      
        return total;
        
    }
    
    public PurchaseOrder orderConfirmation(Product[] orderProd){
        PurchaseOrder po = new PurchaseOrder();
        po.setOrderLines(p.convertArrToArrList(orderProd));

        int count = 1;
        //Print order
        UI.clearJavaConsoleScreen();
        System.out.println(printPOHead(po));
        for(Product prod: po.orderLines){
            System.out.println(prod.getOrderLine(count));
            count++;
        }
        System.out.print(UI.repeatChar('=', 71) + "\n");
        System.out.print("Proceed to Payment(Y/N) ? ");
        String confirm = sc.next();

        while(!confirm.equals("Y") && !confirm.equals("N") && !confirm.equals("y") && !confirm.equals("n")){
            System.out.println("Invalid input!! (only Y and N)");
            System.out.print("Proceed to Payment(Y/N) ?  ");
            confirm = sc.next();
        }
        
        confirm = confirm.toUpperCase();
        
        return (confirm.equals("Y")? po: null);
    }
    
    
    //I/O TEXT FILE------------------------------------------------------------------------
    //Write file
    public void storeProdData(List<PurchaseOrder> poData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(poFilePath))) {
            for (PurchaseOrder po : poData) {
                writer.println("Purchase Order ID: " + po.poID + "\n" +
                               "Order Date: " + po.orderDate);
                writer.format("%s\n ",UI.repeatChar('-', 64));
                writer.format("%-5s %-12s %-20s %-15s %-15s %-15s\n","No.","Product ID", "Product Name", "Price(RM)", "Order Quantity");
                int prodCount = 1;
                for (Product ol : po.getOrderLines()) {
                    writer.format("(%-5d) %-12s %-20s %-15lf %-15d\n", 
                            prodCount,ol.getProdID(),ol.getProdName(),
                            ol.getProdPrice(), ol.getOrderQty());
                }
        }
            System.out.println("Data written successfully.");
        } catch (IOException e) { e.printStackTrace(); }
    }
   
    //Manage data stored
    public static PurchaseOrder[] convertArrListtoArr(ArrayList<PurchaseOrder> arrlist){
        PurchaseOrder[] arr = new PurchaseOrder[arrlist.size()];
        
        for (int i = 0; i < arrlist.size(); i++)
            arr[i] = new PurchaseOrder(arrlist.get(i));
        
        return arr;
    }

    public ArrayList<PurchaseOrder> convertArrToArrList(PurchaseOrder[] arr){
        return new ArrayList<>(Arrays.asList(arr)); 
    }
 
    @Override
    public String toString() {
        return "Purchase Order Number: " + poID +
             "\nOrder Date           : " + orderDate;
    }  

        //validation for selection and quantity------------------------------------------
        public static boolean validationOrderQuantity(int testValue) {
        if (testValue <= 0 || testValue > 99) {
            System.out.println("Quantity should be only(1 - 99)");
            return true;
        }
          else {
            return false;
        }
    }
        
    public static boolean validationProductID(int testValue) {
        if (testValue < 1 || testValue >= 5) {
            System.out.println("productID should be (1 - 5)");
            return true;
        } 
        else {
            return false;
        }
    }
        
    public static boolean validationNumberOfProduct(int testValue,ArrayList<Product> prodArrList) {
            
            numberOfProd = prodArrList.size() + 1;
            int tempNumberOfProd = numberOfProd - 1;
        if (testValue < 1 || testValue >= numberOfProd) {            
            System.out.println("productID should be (1 - " + tempNumberOfProd + ")");
            return true;
        } 
        else {
            return false;
        }
    }
}
