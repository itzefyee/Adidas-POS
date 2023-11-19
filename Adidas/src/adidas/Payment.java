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
import java.util.ArrayList;

public class Payment{
    private static double grandTotal=0;
    private static boolean insufficientBln = false;
    private static boolean chkCredit = true;
    private static double balance;
    
   
  
   public static boolean insufficientBln(){
    return insufficientBln;
}

    
    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public static void setInsufficientBln(boolean insufficientBln) {
        Payment.insufficientBln = insufficientBln;
    }

   
    public static double getGrandTotal() {
        return grandTotal;
    }

    public static boolean isInsufficientBln() {
        return insufficientBln;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static double getBalance() {
        return balance;
    }

    public static boolean isChkCredit() {
        return chkCredit;
    }

    public static void setChkCredit(boolean chkCredit) {
        Payment.chkCredit = chkCredit;
    }

   

    public static int PaymentMethod(){
    Scanner sc = new Scanner(System.in);
    System.out.println("Select your payment method: ");
    System.out.println("1. Account Balance"); 
    System.out.println("2. Credit card");
    int choice = getUseropt(sc, 1, 2);
    
    return choice;
        
      

    }
  
    public static int getUseropt(Scanner sc, int min, int max) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= min && choice <= max) {
                    System.out.print("\n");
                    validInput = true;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Consume the invalid input
            }
        }

        sc.nextLine(); // Consume the newline character
        return choice;
    }
    
  public double CashPayment(double balance) {
        
        insufficientBln = false;
        
        if (getGrandTotal() > balance) {
            System.out.printf("\nInsufficient balance! \nYou only have RM%.2f, you need RM%.2f more.Please top-up at the menu!\n", balance, getGrandTotal() - balance);
            insufficientBln = true;
            return balance;
        } else {
            System.out.printf("\nPayment successful! You now have RM%.2f\n", balance - getGrandTotal());
         
            return balance - getGrandTotal();
        }
    }
    
    
  
  
    
  public static void CreditCardPayment(){
       Scanner sc = new Scanner(System.in);
       PromptUI ui = new PromptUI();
      
       while(true){
            System.out.print("Enter credit card number: ");
            if(sc.hasNextInt()){
              
            int cardNo = sc.nextInt();
           if(isValidCreditLength(cardNo)){
           boolean chkCredit= checkCredit(cardNo);
       
           if(chkCredit==true){
           System.out.println("The number is valid");
           System.out.println("Payment is successful");
           setChkCredit(chkCredit);
           break;
          }
       
           if(chkCredit==false){
           System.out.println("The number is invalid");
           System.out.println("Payment is unsuccessful");
           setChkCredit(chkCredit);
           break;
           }
           }
           
           else{
               System.out.println("Please enter your credit card number with 8-digit of integer");
               ui.pressEnterToContinue();
           }
           
                
            }
            
            else{
                System.out.println("Please enter your credit card number without any special character.");
                ui.pressEnterToContinue();
                
            }
         
       }
      
        
   
       
    }
  
  public static boolean checkCredit(int cardNo){
      int sum1 = 0;
      int temp1 = cardNo;
        
        
        for(int i = 1; i < 8; i += 2){
            sum1 += temp1 % 10;
            temp1 /= 100;  
        }
        
        int sum2 = 0;
        int temp2 = cardNo/10;
       
        for(int i = 1; i < 8; i += 2){
            int digit  = temp2 % 10 * 2;
            sum2 += (digit / 10)+(digit % 10);
            temp2 /= 100; 
        }
        
        int finalSum = sum1 + sum2;
     
        if(finalSum % 10 == 0){
         return true;
        }
        else{
           return false;
        }
  
  }
  
    //to validate credit card length 
    public static boolean isValidCreditLength(int cardno){
        String creditCardStr = String.valueOf(cardno);
        return creditCardStr.length()==8;
    }

   }