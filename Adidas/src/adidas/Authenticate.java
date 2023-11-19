/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

/**
 *
 * @author TAN JA MAN
 */
import static adidas.Main.showLogo;
import static adidas.PromptUI.UI;
import static adidas.PromptUI.inputValidation;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author TAN JA MAN
 */
public class Authenticate {
    private static Worker current;
    static ArrayList<Worker> staffList = FileManager.readStaff();
    static ArrayList<Customer> custList = FileManager.readCustomer();
    static Scanner sc = new Scanner(System.in);
    
    public static Worker getCurrentWorker() {
        return current;
    }
    
    public static void clearCurrentWorker() {
        current = null;
    }
    
    public static void auth() {
        String id, password;
        boolean error;
        Worker attempt;

        do {
            clearScreen();
            showLogo();
            PromptUI.printHeader("Authentication");
        
            error = false;
            System.out.print(UI.alignCenter("Press enter to start", 73));
            sc.nextLine();
            
            System.out.print("\nEnter your ID: ");
            id = sc.nextLine();
            System.out.print("Enter your PASSWORD: ");
            password = sc.nextLine();
            attempt = login(id, password);
            current = attempt;
            if (attempt != null) 
                getCurrentWorker();

            else 
                error = true;
        }while(error == true);
    }
    
    private static Worker login(String id, String password) {
        for (Worker matchAttempt : staffList) {
            if (matchAttempt.getStaffID().equals(id) && matchAttempt.getStaffPassword().equals(password)) {
                PromptUI.printHeader("Authentication Success!");
                return matchAttempt;
            }
        }
        PromptUI.printHeader("Authentication Failed. Please Try Again.");
        UI.pressEnterToContinue();
        return null;
    }
    
    public static boolean isManager() {
        return current.getPosition().equalsIgnoreCase("manager");
    }
    
    
    //perform task
     public static void clearScreen() {
        UI.clearJavaConsoleScreen();
    }
    
    public static int mainMenu() //To allow the user to perform any of the task
    {
        Main.showLogo();
        PromptUI.printHeader("MANAGER");

        System.out.printf("%s\n", "1. Add Staff Record");
        System.out.printf("%s\n", "2. Display Staff Record");
        System.out.printf("%s\n", "3. Search Staff Record");
        System.out.printf("%s\n", "4. Generate sales report");
        System.out.printf("%s\n", "5. Display Customer details");
        System.out.printf("%s\n", "6. Exit");

        //Input
        int choice = 0;
        boolean error = false;
        do {
            error = false;
            try {
                System.out.print("Please enter your option(1 - 6): ");
                choice = sc.nextInt();
                error = inputValidation(choice, 1, 6);
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);
        return choice;
    }
    
    public static void staff() throws Exception {
        //Variables declaration
        int choice;
        boolean repeat;
        String cont;

        //Find which worker or manager is performing action
        Worker s = getCurrentWorker();
        if (s == null) return;

        if(isManager()){
            do{
                do {
                    repeat = false;
                    clearScreen();
                    choice = mainMenu();
                
                    switch (choice) {
                        case 1 -> {
                            //System.out.println("Add Worker Record");
                            clearScreen();
                            PromptUI.printHeader("ADDING STAFF RECORD");
                            Manager.addStaff();
                        }

                        case 2 -> {
                            clearScreen();
                            PromptUI.printHeader("DISPLAYING STAFF RECORD");
                            Manager.displayStaff(staffList);
                        }

                        case 3 -> {
                            clearScreen();
                            PromptUI.printHeader("SEARCHING STAFF RECORD");
                            ArrayList<Worker> resultSearch = Manager.searchStaff();
                            Manager.displayStaff(resultSearch);
                        }

                        case 4 -> {
                            clearScreen();
                            Manager.generateReport();
                        }
                    
                        case 5 -> {
                            Customer c = new Customer();
                        
                            UI.clearJavaConsoleScreen();
                            c.customerDetail(custList);
                        }
                    
                        case 6 -> {
                            return;
                        }

                        default -> {
                            System.out.println("Please enter a valid number");
                            repeat = true;
                        }
                    }
                    System.out.println("\n");
                } while (repeat == true);
                
                sc.nextLine();
                do{
                    repeat = false;
                    System.out.print("Do you want to continue? (Y / N): ");
                    cont = sc.nextLine();
                
                    while(!cont.equalsIgnoreCase("Y") && !cont.equalsIgnoreCase("N")){
                        System.out.println("Invalid input! Please re-enter!");
                        System.out.print("Do you want to continue? (Y / N): ");
                        cont = sc.nextLine();
                        repeat = true;
                    }
                }while(repeat == true);
            }while (cont.equalsIgnoreCase("Y"));
        }
        
        else{
            System.out.println("\nYou have no permission to access!");
            System.out.println("MANAGER ONLY!!");
        }
    }
}
