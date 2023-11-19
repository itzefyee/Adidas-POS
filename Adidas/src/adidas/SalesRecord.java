/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;


/**
 *
 * @author Efye
 */
public class SalesRecord {
    
    private Product product;
    private int quantitySold;
    private double SalesAmount;

    public SalesRecord(){
    }
    
    //Initialize
    public SalesRecord(Product product, int quantitySold) {
        this.product = product;
        this.quantitySold = quantitySold;
        this.SalesAmount = quantitySold * product.getProdPrice();
    }
    
    //Read file
    public SalesRecord(Product product, int quantitySold, double salesAmount) {
        this.product = product;
        this.quantitySold = quantitySold;
        this.SalesAmount = salesAmount;
    }
    
    public Product getProduct() {
        return product;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getSalesAmount() {
        return SalesAmount;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public void setSalesAmount(double SalesAmount) {
        this.SalesAmount = SalesAmount;
    }
    
    public void updateSales(SalesRecord tempSales) {
        this.quantitySold += tempSales.getQuantitySold();
        this.SalesAmount += tempSales.getSalesAmount();
    }
    
    
}
