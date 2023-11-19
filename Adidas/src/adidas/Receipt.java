/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;


/**
 *
 * @author Junwen
 */

import java.util.Scanner;
import java.util.*;

public class Receipt {
  
    public boolean receiptDecision(){
        Scanner sc = new Scanner(System.in);
        boolean Decision = true;
        
         
            Decision = getUserConfirm( sc,"\nDo you want to print receipt(Y/N)?:");
            if(Decision){
                return true;
            }
            
            else {
                return false;
            }
            
        
        
    }
    
     public static boolean getUserConfirm(Scanner sc,String msg){
         while(true){
             System.out.print(""+msg);
             String confirm = sc.next().toLowerCase();
             
             if(confirm.toLowerCase().equals("y")){
                 return true;
             }
             
             if(confirm.toLowerCase().equals("n")){
                 return false;
             }
             
             else{
                 System.out.println("Invalid input! Enter Y or N only");
             }
         }
    }
     
     
       public String printRHead(PurchaseOrder po){
         PromptUI u = new PromptUI();
         PurchaseOrder PO = new PurchaseOrder();
         
        return u.repeatChar('=', 71) + "\n" 
             + u.alignCenter("Receipt", 71)+ "\n"
             + u.repeatChar('=', 71) + "\n"
             + String.format("Date: %s\n",u.formatDate(PO.getOrderDate()))
             + u.repeatChar('-', 71) + "\n"
             + String.format("%-5s %-12s %-20s %-15s %-15s\n","No.","Product ID", "Product Name", "Price(RM)", "Order Quantity")
             + u.repeatChar('-', 71);
        
    }
   
     public void printCashReceipt(Product[] soldList,double total,ArrayList<Customer> c, int custIndex){
         int count=1;
         PromptUI u = new PromptUI();
         PurchaseOrder po = new PurchaseOrder();
         u.clearJavaConsoleScreen();
        System.out.println(printRHead(po));
        for(int j=0;j<soldList.length;j++){
            System.out.println(soldList[j].getOrderLine(count));
            count++;
        }
         System.out.print(u.repeatChar('=', 71) + "\n");
         System.out.printf("Total Price Paid:\t\t\t\t\t\tRM%.2f\n",total);
         System.out.printf("Balance Remaining:\t\t\t\t\t\tRM%.2f\n",c.get(custIndex).getBalance());
         System.out.print(u.repeatChar('=', 71) + "\n");
         System.out.println("                Thank You For Shopping At Adidas");
         System.out.println("                         www.adidas.com\t\t");
         System.out.println("              For more information, please call:1-500-22-3838");
         System.out.print(u.repeatChar('=', 71) + "\n");
         
         
         
     }
     
   
       
      
     public void printCreditCardReceipt(Product[] soldList,double Creditcardtotal,ArrayList<Customer>c){
         int count=1;
         int custIndex=0;
         PromptUI u = new PromptUI();
         PurchaseOrder po = new PurchaseOrder();
         u.clearJavaConsoleScreen();
        System.out.println(printRHead(po));
        for(int j=0;j<soldList.length;j++){
            System.out.println(soldList[j].getOrderLine(count));
            count++;
        }
         System.out.print(u.repeatChar('=', 71) + "\n");
         System.out.printf("Full Payment:\t\t\t\t\t\t\tRM%.2f\n",Creditcardtotal);
         System.out.print(u.repeatChar('=', 71) + "\n");
         System.out.println("                Thank You For Shopping At Adidas");
         System.out.println("                         www.adidas.com\t\t");
         System.out.println("              For more information, please call:1-500-22-3838");
         System.out.print(u.repeatChar('=', 71) + "\n");
         
         
         
     }
}
