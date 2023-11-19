/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import static adidas.Main.sc;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;
import static adidas.PromptUI.UI;
import static adidas.PromptUI.inputValidation;

/**
 *
 * @author TAN JA MAN
 */
public class Manager extends Worker{
    static ArrayList<Worker> staffList = Authenticate.staffList;
    
    public Manager() {
        super("", "", "", "","", "", "");
    }

    public Manager(String staffID, String staffName, String staffPassword, String staffContact, String gender, String staffEmail, String position){
        super(staffID, staffName, staffPassword, staffContact, gender, staffEmail, position);
    }
    
    public static void addStaff() {
        boolean repeat;
        String staffID, staffName, staffPassword, staffContact, gender, staffEmail, position;
        char confirm;
        
        do {
            repeat = false;
            System.out.print("Enter worker ID (X to stop): ");
            staffID = PromptUI.getString();
            for (Worker worker : staffList) {
                if (staffID.matches("\\d+")){
                    if(worker.getStaffID().equals(staffID)){
                        System.out.println("The Staff ID is already used. Please enter a new one.");
                        repeat = true;
                        break;
                    }
                
                    else if(staffID.isEmpty()){
                        System.out.println("Please enter a valid ID!");
                        repeat = true;
                        break;
                    }
                }
            }
        } while (repeat == true);

        while(!staffID.equalsIgnoreCase("X")){
            do {
                repeat = false;
                System.out.print("Enter worker name: ");
                staffName = PromptUI.getString();
                while(staffName.isEmpty()){
                    System.out.println("Please enter a valid name!");
                    repeat = true;
                    break;  
                }
            }while(repeat == true);
        
            do {
                repeat = false;
                System.out.print("Enter worker password: ");
                staffPassword = PromptUI.getString();
                for (Worker worker : staffList) {
                    if (worker.getStaffPassword().equals(staffPassword)) {
                        System.out.println("The Staff password is already used. Please enter a new one.");
                        repeat = true;
                        break;
                    }
                    
                    else if(staffPassword.isEmpty()){
                        System.out.println("Please enter a valid password!");
                        repeat = true;
                        break;
                    }
                }
            } while (repeat == true);
        
            do{
                repeat = false;
                System.out.print("Enter the worker contact number (01X-XXXXXXX): ");
                staffContact = PromptUI.getString();
                while(!staffContact.matches("\\d{3}-\\d{7}")){
                    if(staffContact.isEmpty()){
                        System.out.println("Please enter a valid contact number!");
                        repeat = true;
                        break;
                    }
                }
            }while(repeat == true); 
        
            do{
                repeat = false;
                System.out.print("Enter worker gender (M or F): ");
                gender = PromptUI.getString();
                while(!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")){
                    System.out.println("Please enter a valid gender!");
                    repeat = true;
                    break;
                }                                  
            }while(repeat == true);
        
        
            do {
                repeat = false;
                System.out.print("Enter worker email: ");
                staffEmail = PromptUI.getString();
                for (Worker worker : staffList) {
                    if(staffEmail.matches("[a-zA-Z0-9._%+-]+@gmail.com")){
                        if (worker.getStaffEmail().equals(staffEmail)) {
                            System.out.println("The Staff email is already used. Please enter a new one.");
                            repeat = true;
                            break;
                        }
                        
                        else if(staffEmail.isEmpty()){
                            System.out.println("Please enter a valid email!");
                            repeat = true;
                            break;
                        }
                    }
                    else{
                        System.out.println("Please enter a valid email!");
                            repeat = true;
                            break;
                    }
                }
            } while (repeat == true);
        
            do{
                repeat = false;
                System.out.print("Enter worker position (manager or clerk): ");
                position = PromptUI.getString();
                while(!"manager".equals(position.toLowerCase()) && !"clerk".equals(position.toLowerCase())){
                    if(position.isEmpty()){
                        System.out.println("Please enter a valid position!");
                        repeat = true;
                        break;
                    }
                }
            }while(repeat == true);
            
            do {
                System.out.print("Confirm add staff? (Y / N): ");
                confirm = PromptUI.getChar();
                switch (confirm) {
                    case 'Y', 'y' -> {
                        staffList.add(new Worker(staffID, staffName, staffPassword, staffContact, gender, staffEmail, position));
                        FileManager.writeStaff(staffList);
                        System.out.println("Staff Record Has Been Added Successfully!\n");
                    }
                    case 'N', 'n' -> {
                        System.out.print("Staff Record Has Cleared!\n");
                    }
                    default -> System.out.println("Invalid Input! Plase enter again\n");
                }
            } while (confirm != 'Y' && confirm != 'N' && confirm != 'y' && confirm != 'n');
            break;
        }
    }
    
    public static void displayStaff(ArrayList<Worker> data) {
        System.out.println(getStaffList());
        int i = 1;
        
        for(Worker temp : data){
            System.out.printf("%-3d %-10s %-20s %-20s %-10s %-20s %-20s\n", i, temp.getStaffID(), temp.getStaffName(), temp.getStaffContact(), temp.getGender(), temp.getStaffEmail(), temp.getPosition());
            i++;
            String stop = UI.repeatChar('=', 100) + "\n"; 
            System.out.print(stop);
        }
    }
    
    public static ArrayList<Worker> searchStaff() {
        String input;
        ArrayList<Worker> employeeList = Authenticate.staffList;
        ArrayList<Worker> result = new ArrayList<>();

        System.out.println("1. Staff ID");
        System.out.println("2. Staff Name");
        System.out.println("3. Staff Position");
        
        int choice = 0;
        boolean error = false;
        do {
            error = false;
            try {
                System.out.print("Select the search criteria : ");
                choice = sc.nextInt();
                error = inputValidation(choice, 1, 3);
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);

        switch (choice) {
            case 1 -> {
                System.out.print("Enter the worker Id (X to stop): ");
                input = PromptUI.getString();
                
                if (input.equalsIgnoreCase("X"))
                    break;

                for (Worker worker : employeeList) {
                    if (worker.getStaffID().contains(input))
                        result.add(worker);
                }
            }
            case 2 -> {
                System.out.print("Enter the name (X to stop): ");
                input = PromptUI.getString();
                
                if (input.equalsIgnoreCase("X"))
                    break;

                for (Worker worker : employeeList) {
                    if (worker.getStaffName().contains(input))
                        result.add(worker);
                }
            }
            case 3 -> {
                System.out.print("Enter the position (X to stop): ");
                input = PromptUI.getString();
                
                if (input.equalsIgnoreCase("X"))
                    break;

                for (Worker worker : employeeList) {
                    if (worker.getPosition().contains(input))
                        result.add(worker);
                }
            }
            default -> System.out.println("Invalid value!");
        }
        return result;
    }
    
    public static void generateReport() throws InterruptedException, Exception {
        ArrayList<SalesRecord> allSales = FileManager.readSales();
        //Sales Quantity Chart
       
        Report quantityChart = new Report(allSales, "Preview of Products Sales Quantity", 
         "Preview of Products Sales Quantity", 1);
        quantityChart.statistics(1);
        
        quantityChart.pack( );        
        RefineryUtilities.centerFrameOnScreen( quantityChart );        
        quantityChart.setVisible( true ); 
        
        UI.pressEnterToContinue();
        UI.clearJavaConsoleScreen();
        
        // Sales Amount Chart
        
        Report salesChart = new Report(allSales, "Preview of Products Sales Amount", 
         "Preview of Products Sales Amount", 2);
        salesChart.statistics(2);
               
        salesChart.pack( );        
        RefineryUtilities.centerFrameOnScreen( salesChart );        
        salesChart.setVisible( true ); 
        UI.pressEnterToContinue();
        UI.clearJavaConsoleScreen();
    }
    
    public static String getStaffList(){
        return UI.repeatChar('=', 100) + "\n" 
             + UI.alignCenter("Staff List", 100)+ "\n"
             + UI.repeatChar('=', 100) + "\n"
             + String.format("%-3s %-10s %-20s %-20s %-10s %-20s %-20s\n","No.","Staff ID", "Staff Name", "Staff Contact", "Gender", "Staff Email", "Position")
             + UI.repeatChar('-', 100);
    }
}
