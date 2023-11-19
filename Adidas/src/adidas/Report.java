/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adidas;

import java.util.ArrayList;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import static adidas.PromptUI.UI;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author Efye
 */

public class Report extends JFrame {
    
    // Variable for statistical report
    ArrayList<SalesRecord> allSales;
    
    public Report(ArrayList<SalesRecord> salesData, String applicationTitle, String chartTitle, int option) {
        super( applicationTitle );      
        allSales = salesData;
        
        //create the dataset
        JFreeChart barChart = createChart(option, chartTitle, selection(option) );
      
        // put the chart into a panel
        ChartPanel chartPanel = new ChartPanel( barChart );     
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
        // add it to application
        setContentPane( chartPanel ); 
    }
    
    // Statistics report
    public void statistics(int option) {
        // Variables for statistical report
        SalesRecord[] bestQuantity = new SalesRecord[5];
        SalesRecord[] bestSales = new SalesRecord[5];
        int highestQuantity;
        double highestSales;
        int totalSalesQuantity = 0;
        double totalSalesAmount = 0.00;
        int[] highestQtyIndex = {0, 0, 0, 0, 0}; 
        int[] highestSalesIndex = {0, 0, 0, 0, 0};
        int quantityIndex;
        int salesIndex;
        boolean skip = false;
        
        // Calculate the total quantity sold for every item and find top 5 highest
        for (int i = 0; i < 5; ++i) {
            highestQuantity = -1;
            highestSales = -1;
            quantityIndex = 0;
            salesIndex = 0;
            
            for (SalesRecord sales : allSales) {
                
                if (i == 0) {
                    totalSalesQuantity += sales.getQuantitySold();
                    totalSalesAmount += sales.getSalesAmount();
                }
                else {
                    for (int j = 0; j < i; ++j) {
                        if (quantityIndex == highestQtyIndex[j]) {
                        skip = true;
                        }
                        if (salesIndex == highestSalesIndex[j]) {
                        skip = true;
                        }
                    }
                }
                
                if (skip) {
                    skip = false;
                    quantityIndex++;
                    salesIndex++;
                    continue;
                }
                if (sales.getQuantitySold() > highestQuantity) {
                    highestQuantity = sales.getQuantitySold();
                    highestQtyIndex[i] = quantityIndex;
                }
                if (sales.getSalesAmount() > highestSales) {
                    highestSales = sales.getSalesAmount();
                    highestSalesIndex[i] = salesIndex;
                }
                quantityIndex++;
                salesIndex++;
            }
            bestQuantity[i] = allSales.get(highestQtyIndex[i]);
            bestSales[i] = allSales.get(highestSalesIndex[i]);
        }
    
        
        //Printing the results
        System.out.println("\n\nStatistical Report on Best-Selling Products:");
        System.out.printf("Total Number of Products Sold: %d\n", totalSalesQuantity);
        System.out.printf("Total Sales Amount: RM%10.2f\n",totalSalesAmount);
        
        int index = 1;
        
        
        if (option == 1){
            System.out.printf("\n|-------------------|--------------------|-------------------|");
            System.out.print("\n|" + UI.alignCenter("Top 5 Best Selling Products", 60) + "|");
        
            for (SalesRecord sales : bestQuantity) {
                if (index > 15) 
                    break;
                Product product = sales.getProduct();
                System.out.printf("\n|-------------------|--------------------|-------------------|");
                System.out.printf("\n|(%d) %15s| Quantity sold: %4d| Percentage: %5.2f%c|",
                    index,product.getProdName(), sales.getQuantitySold(),
                    (100.0 * sales.getQuantitySold()/ totalSalesQuantity), '%');
                index++;
            }
            
            System.out.printf("\n|-------------------|--------------------|-------------------|\n\n");
        }
        
        else if (option == 2) {
            System.out.printf("\n|-------------------|---------------------------|-------------------|");
            System.out.print("\n|" + UI.alignCenter("Top 5 Best Selling Products", 67) + "|");
            
            for (SalesRecord sales : bestSales) {
                if (index > 15) 
                    break;
                Product product = sales.getProduct();
                System.out.printf("\n|-------------------|---------------------------|-------------------|");
                System.out.printf("\n|(%d) %15s| Sales Amount: RM%10.2f| Percentage: %5.2f%c|",
                    index,product.getProdName(), sales.getSalesAmount(),
                    (100.0 * sales.getSalesAmount() / totalSalesAmount), '%');
                index++;
            }
            
            System.out.printf("\n|-------------------|---------------------------|-------------------|\n\n");
        }
    }
    
    private String selection(int option){
        String label;
        if (option == 1) {
            label = "Quantity Sold";
        }
        else {
            label = "Sales Amount";
        }
        return label;
    }
    
    //Creates a sample data set
    private CategoryDataset createDataset(int option) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( ); 
        final String rowKey = selection(option);    
        
        int index = 1;
        if (option == 1) {
            for (SalesRecord sales : allSales){
                Product p = sales.getProduct();
                dataset.setValue(sales.getQuantitySold(), rowKey, p.getProdName());
            }
            
        } else if (option == 2) {
            for (SalesRecord sales : allSales){
                Product p = sales.getProduct();
                dataset.setValue(sales.getSalesAmount(), rowKey, p.getProdName());
            }
        }
        return dataset; 

    }
    
    //Creates a chart
    private JFreeChart createChart(int option, String chartTitle, String label) {
        
        JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Products",            
         label,            
         createDataset(option),          
         PlotOrientation.VERTICAL,           
         false, true, false
        );
        
        if (option == 1){
            CategoryPlot plot = barChart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            // set the color (r,g,b) or (r,g,b,a)
            Color color = new Color(79, 129, 189);
            renderer.setSeriesPaint(0, color);
        } else {
            CategoryPlot plot = barChart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();

            // set the color (r,g,b) or (r,g,b,a)
            Color color = new Color(0, 204, 102);
            renderer.setSeriesPaint(0, color);
        }

        return barChart;
    }
    
    
    
    
}
