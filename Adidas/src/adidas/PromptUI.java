package adidas;



import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.*;

public class PromptUI{
    static PromptUI UI = new PromptUI();
    Scanner sc = new Scanner(System.in);
    private int select; 
    private boolean error;
    private static final Scanner scan = new Scanner(System.in);
    private final static int LINE_LENGTH = 72;
    
    public String repeatChar(char c, int count){
        return Character.toString(c).repeat(count);
    }
    
    public String alignCenter(String text, int totalWidth){
        // Calculate the number of spaces needed on each side to center-align the text
        int padding = (totalWidth - text.length()) / 2; 
        int odd = (totalWidth - text.length()) % 2; // Check odd
        
        // Create a format string with padding on both sides
        String format = "%" + padding + "s%s%" + padding + "s"; 
        if(odd != 0)
            format +=  " ";
            
        // Use String.format to center-align the text
        return String.format(format, "", text, "");
    }
    
    public String formatDate(Date date){
        // Define a date format pattern
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Format the date as a string
        return sdf.format(date);
    }
    
    public String bracketNum(int number){
        return "(" + number + ")";
    }
    
    public int showMenu(){
        System.out.print("\n\n*" + repeatChar('=', 65)                                     + "*" + "\n"
                       + "|" + alignCenter("Order Menu", 65)                            + "|" + "\n"
                       + "*" + repeatChar('=', 65)                                     + "*" + "\n"
                       + "|" + String.format("%-65s",  " (1) Purchase Product" ) + "|" + "\n"
                       + "|" + String.format("%-65s",  " (2) Top up Balance"  )          + "|" + "\n"
                       + "|" + String.format("%-65s",  " (3) Back To Main Menu"  )                 + "|" + "\n"
                       + "*" + repeatChar('=', 65)                                     + "*\n\n"
                       );
                do {
                  
                try {
                    System.out.print("Enter selection: ");
                    select = sc.nextInt();
                    error = validationSelect1(select);
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }
            } while (error);
                
                return select;
    }
    //console screen controls
    public void clearJavaConsoleScreen() {
        try{
            Robot rob = new Robot();
            try {
            rob.keyPress(KeyEvent.VK_CONTROL); // press "CTRL"
            rob.keyPress(KeyEvent.VK_L); // press "L"
            rob.keyRelease(KeyEvent.VK_L); // unpress "L"
            rob.keyRelease(KeyEvent.VK_CONTROL); // unpress "CTRL"
            Thread.sleep(1000); // add delay in milisecond, if not there will automatically stop after clear
            } catch (InterruptedException e) { e.printStackTrace(); }
        } catch(AWTException e) { e.printStackTrace(); }  
    }
    
    public void pauseScreen(int s){
        int ms = s * 1000;//1 second = 1000 milliseconds 
        try { 
            Thread.sleep(ms);
        } catch (InterruptedException e) {  e.printStackTrace(); }
    }
    
    public void pressEnterToContinue(){
        System.out.println("Press Enter to continue...");
        sc.nextLine(); 
    }
    
    public static boolean validationSelect1(int value){
        if (value < 1 || value > 3) {
            System.out.println("Selection should be (1 - 2)");
            return true;
        }
          else {
            return false;
        }
    }
    
    //staff use
    public static int getInt() {
        int i;

        while (!scan.hasNextInt()) {
            System.out.println("Invalid input!");
            System.out.print("Enter a number: ");
            scan.next();
        }
        i = scan.nextInt();
        scan.nextLine();
        return i;
    }

    public static String getString() {
        String s;

        s = scan.nextLine();

        while (s.length() <= 0) {
            System.out.println("Invalid input!");
            System.out.print("Enter a string: ");
            s = scan.nextLine();
        }
        return s;
    }

    public static char getChar() {

        char c = scan.nextLine().charAt(0);

        while (c == ' ' || c == '\n') {
            System.out.println("Invalid input!");
            System.out.print("Enter a character: ");
            c = scan.nextLine().charAt(0);
        }
        return c;
    }
    
    public static void printHeader(String s) {
        int counts = (LINE_LENGTH - s.length()) / 2;
        StringBuilder head = new StringBuilder();

        for (int i = 0; i < counts - 1; i++) {
            head.append("=");
        }

        System.out.println("\n" + head + " " + s.toUpperCase() + " " + head + "\n");
    }
    
    public static boolean inputValidation(int value, int start, int end){
        if (value < start || value > end + 1) {
            System.out.printf("Selection should be (%d - %d)\n", start, end);
            return true;
        }
          else {
            return false;
        }
    }
    
}