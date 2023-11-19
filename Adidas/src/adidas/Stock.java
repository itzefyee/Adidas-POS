/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import java.util.*;
import static adidas.PromptUI.UI;

public class Stock {

    Scanner sc = new Scanner(System.in);
    private int select;
    private String productName;
    private int stock = 0;
    private double price;
    private boolean error = false;
    
    static Product p = new Product();
    static PurchaseOrder o = new PurchaseOrder();

    public static void stockMenu() {
        Product p = new Product();
        Stock s = new Stock();
        PurchaseOrder o = new PurchaseOrder();
        boolean error = false;
        int choice = 0;

        //Testing read & write data ------------------------
        ArrayList<Product> oriProd;
        //--------------------------------------------------

        int act, act2;
        do {

            UI.clearJavaConsoleScreen();
            act = s.printStockSelection();
            switch (act) {
                case 1: // Show Product Catalogue 
                    oriProd = FileManager.readStock();
                    UI.clearJavaConsoleScreen();
                    s.printStockMenu1(oriProd);
                    s.addProduct(oriProd);
                    act = 1;

                    break;
                case 2:
                    oriProd = FileManager.readStock();
                    UI.clearJavaConsoleScreen();
                    s.printStockMenu(oriProd);
                    s.removeProduct(oriProd);
                    UI.pressEnterToContinue();
                    act = 1;
                    break;
                case 3:
                    oriProd = FileManager.readStock();
                    UI.clearJavaConsoleScreen();
                    s.printStockMenu(oriProd);
                    UI.pressEnterToContinue();
                    act = 1;
                    break;
                case 4:
                    oriProd = FileManager.readStock();
                    UI.clearJavaConsoleScreen();
                    s.printStockMenu(oriProd);
                    s.reStock(oriProd);
                    UI.pressEnterToContinue();
                    break;
                case 5:
                    oriProd = FileManager.readStock();
                    UI.clearJavaConsoleScreen();
                    act2 = s.printStockSelection1();
                    if (act2 != 4) {
                        UI.clearJavaConsoleScreen();
                        s.printStockMenu(oriProd);
                        s.editStock(oriProd, act2);
                        UI.pressEnterToContinue();
                    }
                    break;
                default: // EXIT
                    break;
            }
        } while (act != 6);
    }
    
    public int printStockSelection() {

        System.out.print("*" + UI.repeatChar('=', 65) + "*" + "\n"
                + "|" + UI.alignCenter("Stock Menu", 65) + "|" + "\n"
                + "*" + UI.repeatChar('=', 65) + "*" + "\n"
                + "|" + String.format("%-65s", " (1) Add product stock") + "|" + "\n"
                + "|" + String.format("%-65s", " (2) Delete product stock") + "|" + "\n"
                + "|" + String.format("%-65s", " (3) Display product stock") + "|" + "\n"
                + "|" + String.format("%-65s", " (4) Restock product") + "|" + "\n"
                + "|" + String.format("%-65s", " (5) Edit product details") + "|" + "\n"
                + "|" + String.format("%-65s", " (6) Back") + "|" + "\n"
                + "*" + UI.repeatChar('=', 65) + "*\n\n"
        );

        do {

            try {
                System.out.print("Enter selection: ");
                select = sc.nextInt();
                error = validationSelect2(select);
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);

        return select;
    }

    public int printStockSelection1() {

        System.out.print("*" + UI.repeatChar('=', 65) + "*" + "\n"
                + "|" + UI.alignCenter("Stock Editing", 65) + "|" + "\n"
                + "*" + UI.repeatChar('=', 65) + "*" + "\n"
                + "|" + String.format("%-65s", " (1) Change product name") + "|" + "\n"
                + "|" + String.format("%-65s", " (2) Change stock quantity") + "|" + "\n"
                + "|" + String.format("%-65s", " (3) Change stock price") + "|" + "\n"
                + "|" + String.format("%-65s", " (4) Back") + "|" + "\n"
                + "*" + UI.repeatChar('=', 65) + "*\n\n"
        );

        do {

            try {
                System.out.print("Enter selection: ");
                select = sc.nextInt();
                error = validationSelect3(select);
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);

        return select;
    }

    public void printStockMenu(List<Product> stockCat) {
        int count = 1;
        System.out.println(p.getProdListHead());
        for (Product p : stockCat) {
            System.out.println(p.getProdItems(count));
            count++;
        }
        System.out.print(UI.repeatChar('=', 67) + "\n");
    }

    public void printStockMenu1(List<Product> stockCat) {
        int count = 1;
        System.out.println(p.getProdListHead());
        for (Product p : stockCat) {
            System.out.println(p.getProdItems(count));

            if (count >= 5) {
                break;
            }
            count++;
        }
        System.out.print(UI.repeatChar('=', 67) + "\n");
    }

    public void addProduct(ArrayList<Product> oriStock) {

        sc.nextLine();

        do {
            error = false;
            System.out.print("Enter new product name(Press x to return):");
            productName = sc.nextLine();

            checkDuplicateName(productName);
            if (error == true) {
                System.out.print("""
                              Product name has duplicated !!
                              """);
            } else if (productName.isEmpty()) {
                System.out.print("Product name should not be empty !!");
                error = true;
            }

            if (productName.toUpperCase().equals("X")) {
                break;
            }

        } while (error);

        do {
            error = false;
            if (productName.toUpperCase().equals("X")) {
                break;
            }
            try {
                System.out.print("Enter stock quantity(press 0 to exit): ");
                stock = sc.nextInt();
                if (stock == 0) {
                        break;
                    }
                while (stock < 0 || stock >= 1000) {
                    System.out.println("it is invalid number (0 - 999)");
                    System.out.print("Enter stock quantity(press 0 to exit): ");
                    stock = sc.nextInt();
                    if (stock == 0) {
                        break;
                    }
                }
                if (stock == 0) {
                        break;
                    }
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }

        } while (error);
        do {
            error = false;

            if (productName.toUpperCase().equals("X") || stock ==0) {
                break;
            }

            try {
                System.out.print("Enter price(press of 0 to exit): ");
                price = sc.nextDouble();
                if (price == 0) {
                        break;
                    }
                while (price < 0) {
                    System.out.println("it is invalid number");
                    System.out.print("Enter price(press of 0 to exit): ");
                   price = sc.nextDouble();
                    if (price == 0) {
                        break;
                    }
                }
                if (price == 0) {
                        break;
                    }
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }

        } while (error);

        if (!productName.equalsIgnoreCase("x") && stock != 0 && price != 0) {
            System.out.println("Successfully added a new product into the stock...");
            ArrayList<Product> tempArr = oriStock;
            tempArr.add(new Product(tempArr.size() + 1, productName, stock, price));
            FileManager.writeStock(tempArr);
            UI.pressEnterToContinue();
        }
    }

    public void removeProduct(ArrayList<Product> oriStock) {

        error = false;

        do {
            error = false;

            try {
                System.out.print("Enter number of selection to remove(Press 0 to return):");
                select = sc.nextInt();
                if (select == 0) {
                    break;
                }
                error = o.validationNumberOfProduct(select, oriStock);
                while (error == true) {
                    System.out.println("it is invalid number");
                    System.out.print("Enter number of selection to remove(Press 0 to return): ");
                    select = sc.nextInt();
                    if (select == 0) {
                        break;
                    }
                    error = o.validationNumberOfProduct(select, oriStock);
                    sc.nextLine();
                    
                }
                if (select == 0) {
                        break;
                    }
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);

        if (select != 0) {
            select--;
            System.out.println("Product succesfully removed...");
            oriStock.remove(select);
            ArrayList<Product> tempArr = new ArrayList<Product>();
            int i = 1;
            for (Product p : oriStock) {
                tempArr.add(new Product(i, p.getProdName(), p.getStockQuantity(), p.getProdPrice()));
                i++;
            }
            FileManager.writeStock(tempArr);
        }
    }

    public void reStock(ArrayList<Product> oriStock) {

        int totalStock = 0;

        do {
            error = false;

            try {
                System.out.print("Choose product to restock(Press 0 to return): ");
                select = sc.nextInt();
                if (select == 0) {
                    break;
                }
                error = o.validationNumberOfProduct(select, oriStock);
                while (error == true) {
                    System.out.println("it is invalid number");
                    System.out.print("Enter number of product to restock(Press 0 to return): ");
                    select = sc.nextInt();
                    error = o.validationNumberOfProduct(select, oriStock);
                    sc.nextLine();
                    if (select == 0) {
                        break;
                    }
                    if (select == 0) {
                        break;
                    }
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }

        } while (error);

        do {
            error = false;

            if (select == 0) {
                break;
            }

            try {
                System.out.print("Enter re-stock quantity(total of quantity stock < 1000,Press 0 to return): ");
                stock = sc.nextInt();
                
                if(stock == 0){
                    break;
                }
                totalStock = oriStock.get(select - 1).getStockQuantity() + stock;
                while (stock < 1 || totalStock >= 1000) {
                    totalStock = 0;
                    System.out.println("it is invalid number (1 - 1000)");
                    System.out.print("Enter stock quantity: ");
                    stock = sc.nextInt();
                    if(stock == 0){
                    break;
                }
                    totalStock = oriStock.get(select - 1).getStockQuantity() + stock;
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }

        } while (error);

        if (select != 0 && stock != 0) {
            System.out.println("Product succesfully re-stock...");
            ArrayList<Product> tempArr = new ArrayList<Product>();
            int i = 0;
            for (Product p : oriStock) {
                if (i != select - 1) {
                    tempArr.add(new Product(i + 1, p.getProdName(), p.getStockQuantity(), p.getProdPrice()));
                } else {
                    tempArr.add(new Product(i + 1, p.getProdName(), totalStock, p.getProdPrice()));
                }
                i++;
            }
            FileManager.writeStock(tempArr);
        }
    }

    public void editStock(ArrayList<Product> oriStock, int act2) {

        do {
            error = false;

            try {
                System.out.print("Enter number of product to edit(Press 0 to return):");
                select = sc.nextInt();
                if (select == 0) {
                    break;
                }
                error = o.validationNumberOfProduct(select, oriStock);
                while (error == true) {
                    System.out.println("it is invalid number");
                    System.out.print("Enter number of product to edit(Press 0 to return): ");
                    select = sc.nextInt();
                    error = o.validationNumberOfProduct(select, oriStock);
                    sc.nextLine();
                    if (select == 0) {
                        break;
                    }
                }
                if (select == 0) {
                        break;
                    }
                
            } catch (Exception InputMismatchException) {
                System.out.println("Invatid input type(make sure to type integer with positive value)");
                error = true;
                sc.nextLine();
            }
        } while (error);
        if (select != 0) {
                p.setProdID(oriStock.get(select - 1).getProdID());
                p.setProdName(oriStock.get(select - 1).getProdName());
                p.setProdPrice(oriStock.get(select - 1).getProdPrice());
                p.setStockQuantity(oriStock.get(select - 1).getStockQuantity());
        }
        UI.clearJavaConsoleScreen();
        if (select != 0) {
            System.out.println(p.getProdListHead());
            System.out.println(p.getProdItems(select));
            System.out.print(UI.repeatChar('=', 67) + "\n");
        }
        if (act2 == 1) {

            if (select != 0) {
                System.out.print("|" + UI.alignCenter("Editing Name", 65) + "|" + "\n"
                        + "*" + UI.repeatChar('=', 65) + "*" + "\n\n");
            }
            
            sc.nextLine();
            
            do {

                if (select == 0) {
                    break;
                }

                
                
                error = false;
                System.out.print("Enter to name change(Press x to return):");
                productName = sc.nextLine();

                checkDuplicateName(productName);
                if (error == true) {
                    System.out.print("""
                              Product name has duplicated !!
                              """);
                } else if (productName.isEmpty()) {
                    System.out.println("Product name should not be empty !!");
                    error = true;
                }

                if (productName.toUpperCase().equals("X")) {
                    break;
                }

                productName = productName.toUpperCase();
                
            } while (error);

            if (select != 0 && !productName.equalsIgnoreCase("X")) {
                System.out.println("Product name succesfully modified...");
                ArrayList<Product> tempArr = new ArrayList<Product>();
                int i = 0;
                for (Product p : oriStock) {
                    if (i != select - 1) {
                        tempArr.add(new Product(i + 1, p.getProdName(), p.getStockQuantity(), p.getProdPrice()));
                    } else {
                        tempArr.add(new Product(i + 1, productName, p.getStockQuantity(), p.getProdPrice()));
                    }
                    i++;
                }
                FileManager.writeStock(tempArr);
            }
        } else if (act2 == 2) {
            if (select != 0) {
                System.out.print("|" + UI.alignCenter("Editing quantity", 65) + "|" + "\n"
                        + "*" + UI.repeatChar('=', 65) + "*" + "\n\n");
            }
            do {

                if (select == 0) {
                    break;
                }

                error = false;

                try {
                    System.out.print("Enter edit stock quantity(press 0 to exit): ");
                    stock = sc.nextInt();
                     if (stock == 0) {
                    break;
                }
                    while (stock < 0 || stock >= 1000) {
                        System.out.println("it is invalid number (1 - 999)");
                        System.out.print("Enter edit stock quantity(press 0 to exit): ");
                    if (stock == 0) {
                        break;
                    }
                        stock = sc.nextInt();
                    }
                    if (stock == 0) {
                        break;
                    }
                    
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }

               

            } while (error);

            if (stock != 0) {
                System.out.println("Product stock succesfully modified...");
                ArrayList<Product> tempArr = new ArrayList<Product>();
                int i = 0;
                for (Product p : oriStock) {
                    if (i != select - 1) {
                        tempArr.add(new Product(i + 1, p.getProdName(), p.getStockQuantity(), p.getProdPrice()));
                    } else {
                        tempArr.add(new Product(i + 1, p.getProdName(), stock, p.getProdPrice()));
                    }
                    i++;
                }
                FileManager.writeStock(tempArr);
            }

        } else {
            if (select != 0) {
                System.out.print("|" + UI.alignCenter("Editing price", 65) + "|" + "\n"
                        + "*" + UI.repeatChar('=', 65) + "*" + "\n\n");
            }
            do {

                if (select == 0) {
                    break;
                }

                error = false;

                try {
                    System.out.print("Enter edit price(press 0 to cancel editing): ");
                    price = sc.nextInt();
                    
                    if (price == 0) {
                    break;
                }
                    while (price < 0) {
                        System.out.println("it is invalid number");
                        System.out.print("Enter edit price(press 0 to cancel editing): ");       
                        price = sc.nextInt();
                        if (price == 0) {
                    break;
                }
                        
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("Invatid input type(make sure to type integer with positive value)");
                    error = true;
                    sc.nextLine();
                }

                

            } while (error);

            if (price != 0) {
                System.out.println("Product price succesfully modified...");
                ArrayList<Product> tempArr = new ArrayList<Product>();
                int i = 0;
                for (Product p : oriStock) {
                    if (i != select - 1) {
                        tempArr.add(new Product(i + 1, p.getProdName(), p.getStockQuantity(), p.getProdPrice()));
                    } else {
                        tempArr.add(new Product(i + 1, p.getProdName(), p.getStockQuantity(), price));
                    }
                    i++;
                }
                FileManager.writeStock(tempArr);
            }
        }

    }

    public boolean checkDuplicateName(String prodName) {
        Product p = new Product();
        ArrayList<Product> oriProd = FileManager.readStock();
        for (int i = 0; i < oriProd.size(); i++) {
            if (oriProd.get(i).getProdName().equals(prodName)) {
                error = true;
                return error;
            }

        }
        return false;

    }

    public static boolean validationSelect2(int value) {
        if (value < 1 || value > 6) {
            System.out.println("Selection should be (1 - 6)");
            return true;
        } else {
            return false;
        }
    }

    public static boolean validationSelect3(int value) {
        if (value < 1 || value > 4) {
            System.out.println("Selection should be (1 - 4)");
            return true;
        } else {
            return false;
        }
    }
}