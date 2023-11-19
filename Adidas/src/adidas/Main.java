/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import static adidas.PromptUI.UI;
import static adidas.PromptUI.inputValidation;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *
 * @author Efye
 */
public class Main {
    static Scanner sc = new Scanner(System.in);
    private static int CustIndex = 0;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    static LocalDateTime accessTime = LocalDateTime.now();
    
    public static void main(String[] args) throws ParseException, Exception{
        boolean error = false;
        
        while (true) {
            int choice = 0;
            boolean repeat;
            if (Authenticate.getCurrentWorker()== null) {
                Authenticate.auth();
            }

            do{
                repeat = false;

                UI.clearJavaConsoleScreen();
                showLogo();
                startSession(1);
                mainMenu();
                
                do {
                    error = false;
                    try {
                        System.out.print("Please enter your option(1 - 4): ");
                        choice = sc.nextInt();
                        error = inputValidation(choice, 1, 4);
                    } catch (Exception InputMismatchException) {
                        System.out.println("Invatid input type(make sure to type integer with positive value)");
                        error = true;
                        sc.nextLine();
                    }
                } while (error);

                switch (choice) {
                    case 1 -> {
                        order();
                        UI.pressEnterToContinue();
                        
                    }
                    case 2 -> {
                        Stock.stockMenu();
                        UI.pressEnterToContinue();
                    }
                    case 3 -> { // put staff classes under here
                        Authenticate.staff();
                        UI.pressEnterToContinue();
                    }
                        
                    case 4 -> {
                        Authenticate.clearCurrentWorker();
                        endSession();
                        showByeBye();
                        UI.pressEnterToContinue();
                        UI.clearJavaConsoleScreen();
                    }
                    default -> {
                        System.out.println("Invalid input!");
                        UI.pressEnterToContinue();
                        repeat = true;
                    }   
                }
            }while (repeat == true);
        }
    }
    
    private static void mainMenu() {
         System.out.print(
                        UI.repeatChar('=', 73)                                      + "\n"
                       + "||" + UI.alignCenter("M A I N   M E N U", 69)        + "||" + "\n"
                       + UI.repeatChar('=', 73)                                     + "\n"
                       + "|" + String.format("%-71s",  " (1) Order Product" ) + "|" + "\n"
                       + UI.repeatChar('-', 73)                                     + "\n"
                       + "|" + String.format("%-71s",  " (2) Stock Management"  )          + "|" + "\n"
                       + UI.repeatChar('-', 73)                                     + "\n"
                       + "|" + String.format("%-71s",  " (3) Staff Management"  )                 + "|" + "\n"
                       + UI.repeatChar('-', 73)                                     + "\n"
                       + "|" + String.format("%-71s",  " (4) Exit"  )                 + "|" + "\n"
                       + UI.repeatChar('=', 73)                                     + "\n");
    }
    
    public static void showLogo() {
        System.out.print("""
                           
                               :::      :::::::::   ::::::::::  :::::::::       :::        ::::::::
                             :+: :+:    :+:    :+:     :+:      :+:    :+:    :+: :+:     +:     +: 
                            +:+   +:+   +:+    +:+     +:+      +:+    +:+   +:+   +:+   +:+        
                           +#++:++#++:  +#+    +:+     +#+      +#+    +:+  +#++:++#++:  +#++:++#++ 
                           +#+     +#+  +#+    +#+     +#+      +#+    +#+  +#+     +#+         +#+ 
                           #+#     #+#  #+#    #+#     #+#      #+#    #+#  #+#     #+#  #+     #+# 
                           ###     ###  #########  ###########  #########   ###     ###   ########  
                           *************************************************************************
                           """);
        
    }
    
    private static void showByeBye(){
	//printf("Exiting Adidas Sales System - at %d-%d-%d %d:%2d\n", t.wDay, t.wMonth, t.wYear, t.wHour, t.wMinute);
        System.out.println("""
                           
                            +--------------------+
                            |  THANK YOU         |
                            |      BYE BYE       |
                            |        COME AGAIN  |
                            +--------------------+
                                  (\\_/) ||
                                  (^.^) ||
                                   />---||
                           """);
    }
    
    private static LocalDateTime startSession(int option) {
        LocalDateTime accessTime = LocalDateTime.now();
        
        if (option == 1) {
            System.out.println(
                    "\n" 
                    + UI.repeatChar('-', 25) 
                    + " Access Time : "
                    + accessTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS) 
                    + " " + UI.repeatChar('-', 24) + "\n");
            return accessTime;
        }
        else if (option == 2){
            return accessTime;
        }
        return null;
    }
    
    public static void endSession() {
        LocalDateTime exitTime = LocalDateTime.now();
        System.out.println(
                        "\n\n" 
                        + UI.repeatChar('-', 25) 
                        + " Exit Time : "
                        + exitTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS) 
                        + " " + UI.repeatChar('-', 24)
        );
    }
    
    public static int getCustIndex() {
        return CustIndex;
    }

    public static void setCustIndex(int CustIndex) {
        Main.CustIndex = CustIndex;
    }

    public static void order() throws ParseException, IOException {
        Product p = new Product();
        Customer c = new Customer();
        boolean receiptOpt;
        String memberOrNot;
        boolean found = false;
        
        ArrayList<Customer> Cust = FileManager.readCustomer();
        ArrayList<SalesRecord> allSales = FileManager.readSales();
        SalesRecord tempSales;
        SalesRecord newSales;
        Receipt receipt = new Receipt();
       

        ArrayList<Product> Prod = FileManager.readStock();
        p.storeProdData(Prod);
        ArrayList<Product> oriProd = p.readProdData();
        
        //--------------------------------------------------
        // To store all purchase orders
        ArrayList<PurchaseOrder> POarr = new ArrayList<PurchaseOrder>(); 

        memberOrNot = c.memberCheck(c, c.getCustIndex(), Cust);
        int act, act2, opt;
        do {

            act = UI.showMenu(); // Show Main Menu
            UI.clearJavaConsoleScreen();
            switch (act) {
                case 1: // Show Product Catalogue 
                    Cust = FileManager.readCustomer();
                    p.showProdCatalogue(oriProd);
                    act2 = p.showProdMenu();
                    
                    if (act2 == 1) { // Create new order
                        
                        Product[] tempProd = p.convertArrListtoArr(oriProd); // Make a copy of the original product to prevent unwanted changes.
                        PurchaseOrder tempPO = new PurchaseOrder(); // Create Temporary Order
                        tempPO = tempPO.orderConfirmation(tempPO.addItemsToCart(tempProd));
                        
                        if (tempPO != null) { // Confirm order
                            POarr.add(tempPO);

                            Product[] soldProd = Product.convertArrListtoArr(tempPO.getOrderLines());
                            oriProd = Product.convertArrToArrList(tempProd);

                            Payment tempPay = new Payment();
                            tempPay.setGrandTotal(tempPO.calTotal(soldProd)); //get totalcost from Purchase Order method and store into grandTotal in Payment

                            System.out.printf("Your Total Payment is: RM %.2f\n", tempPay.getGrandTotal());
                            if (!memberOrNot.equalsIgnoreCase("N")) {
                              
                            System.out.printf("Your current available balance is: RM %.2f\n", Cust.get(c.getCustIndex()).getBalance());
                          
                        }
                
                            opt = tempPay.PaymentMethod();

                            if (opt == 1) {

                                if (memberOrNot.equalsIgnoreCase("N")) {
                                    System.out.println("Not member are not allow to pay by Cash.");
                                    p.restoreQuantity(tempProd, tempPO.getOrderLines());
                                    tempProd = Product.updateRemainingStock(tempProd, tempPO.getOrderLines()); // Only update after order and payment confirmed and succeed
                                    UI.pressEnterToContinue();
                                    break;
                                }
                                
                                //to update the balance of customer after each payment
                                Cust.get(c.getCustIndex()).setBalance(tempPay.CashPayment(Cust.get(c.getCustIndex()).getBalance())); 

                                if (tempPay.insufficientBln() != true) {
                                    
                                    tempSales = new SalesRecord();
                                    for (Product orderedProduct : tempPO.getOrderLines()) {
                                        for (SalesRecord sr : allSales) {
                                            Product currentProduct = sr.getProduct();
                                            tempSales = new SalesRecord(currentProduct, currentProduct.getOrderQty());
                                            // Add to existing sales record
                                            if (currentProduct.getProdID().equals(orderedProduct.getProdID()))  {
                                                found = true;
                                                sr.updateSales(tempSales);
                                            }     
                                            
                                        }
                                    }
                                    
                                    FileManager.writeCustomer(Cust);
                                    tempProd = Product.updateRemainingStock(tempProd, tempPO.getOrderLines()); // Only update after order and payment confirmed and succeed
                                    FileManager.writeStock(p.convertArrToArrList(tempProd));
                                    receiptOpt = receipt.receiptDecision();
                                    if (receiptOpt == true) {
                                        receipt.printCashReceipt(soldProd, tempPay.getGrandTotal(), Cust, c.getCustIndex());

                                    } else {
                                        System.out.println("Thanks for purchasing at Adidas!");
                                       
                                    }

                                }

                                if (tempPay.insufficientBln() == true) {
                                    p.restoreQuantity(tempProd, tempPO.getOrderLines());
                                    tempProd = Product.updateRemainingStock(tempProd, tempPO.getOrderLines()); // Only update after order and payment confirmed and succeed
                                    System.out.println("Your Order details has been reset.");
                                    UI.pressEnterToContinue();
                                }

                            }

                            if (opt == 2) {
                                tempPay.CreditCardPayment();
                                
                                if(tempPay.isChkCredit() == true){
                                    // Only update stock after order and payment confirmed and succeed
                                    tempProd = Product.updateRemainingStock(tempProd, tempPO.getOrderLines()); 
                                    FileManager.writeStock(p.convertArrToArrList(tempProd));

                                    tempSales = new SalesRecord();
                                    for (Product orderedProduct : tempPO.getOrderLines()) {
                                        for (SalesRecord sr : allSales) {
                                            Product currentProduct = sr.getProduct();
                                            tempSales = new SalesRecord(currentProduct, currentProduct.getOrderQty());
                                            // Add to existing sales record
                                            if (currentProduct.getProdID().equals(orderedProduct.getProdID()))  {
                                                found = true;
                                                sr.updateSales(tempSales);
                                            }     
                                            
                                            }
                                        }
                                    
                                receiptOpt = receipt.receiptDecision();
                                if (receiptOpt == true) {
                                    receipt.printCreditCardReceipt(soldProd, tempPay.getGrandTotal(), Cust);

                                }
                                
                                else{
                                       System.out.println("Thanks for purchasing at Adidas!");
                                }
                                }
                                
                                if(tempPay.isChkCredit() != true){
                                    p.restoreQuantity(tempProd, tempPO.getOrderLines());
                                    tempProd = Product.updateRemainingStock(tempProd, tempPO.getOrderLines()); // Only update after order and payment confirmed and succeed
                                    System.out.println("Your Order details has been reset.");
                                }
                              

                            }
                            FileManager.writeSales(allSales);
                            UI.pressEnterToContinue();

                        } else { // Cancel order
                            System.out.println("Order cancelled. Returning to menu.");
                            UI.pressEnterToContinue();
                        }
                    }
                    break;
                case 2: //top up balance module
                    while (true) {
                        System.out.println(
                            "\n" + UI.repeatChar('=', 73) + "\n" +
                            "|" + UI.alignCenter("Top Up Balance", 71) + "|" + "\n" + 
                            UI.repeatChar('=', 73)
                        );
                        if (memberOrNot.equalsIgnoreCase("N")) {
                            System.out.println("Not member are not allow to top up");
                            UI.pressEnterToContinue();
                            break;
                        }

                        UI.clearJavaConsoleScreen();
                        Main.showLogo();
                        System.out.println(
                            "\n" + UI.repeatChar('=', 73) + "\n" +
                            "|" + UI.alignCenter("Top Up Balance", 71) + "|" + "\n" + 
                            UI.repeatChar('=', 73)
                        );
                        
                        System.out.print("Do you want to top-up (y/n): ");
                        String topUpDecision = sc.next();
                        if (topUpDecision.toLowerCase().equals("y")) {
                            System.out.printf("Your current available balance is: RM %.2f\n", Cust.get(c.getCustIndex()).getBalance());
                            Cust.get(CustIndex).topUp(Cust);
                            break;

                        } else if (topUpDecision.toLowerCase().equals("n")) {
                            UI.pressEnterToContinue();
                            break;
                        } else {
                            System.out.println("Invalid input!");
                            UI.pressEnterToContinue();
                            break;
                        }

                    }

                default: // EXIT
                    break;
            }
        } while (act != 3);
    }

}
