/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecommercesystem;

/**
 *
 * @author Yassa
 */
import java.util.*;
class ShippingService {
    private static final double BASE_SHIPPING_FEE = 30.0;
    
    public static double calculateShippingFee(Shippable[] shippableItems, int count) {
        if (count == 0) {
            return 0.0;
        }
        return BASE_SHIPPING_FEE;
    }
    
    public static void processShipment(Shippable[] shippableItems, int count) {
        if (count == 0) {
            return;
        }
        
        System.out.println("** Shipment notice **");
        
        // Count items and calculate weights
        String[] itemNames = new String[count];
        int[] itemCounts = new int[count];
        double[] itemWeights = new double[count];
        int uniqueItems = 0;
        
        for (int i = 0; i < count; i++) {
            String name = shippableItems[i].getName();
            double weight = shippableItems[i].getWeight();
            
            // Find if item already exists
            int existingIndex = -1;
            for (int j = 0; j < uniqueItems; j++) {
                if (itemNames[j].equals(name)) {
                    existingIndex = j;
                    break;
                }
            }
            
            if (existingIndex != -1) {
                itemCounts[existingIndex]++;
                itemWeights[existingIndex] += weight;
            } else {
                itemNames[uniqueItems] = name;
                itemCounts[uniqueItems] = 1;
                itemWeights[uniqueItems] = weight;
                uniqueItems++;
            }
        }
        
        double totalWeight = 0.0;
        for (int i = 0; i < uniqueItems; i++) {
            totalWeight += itemWeights[i];
            System.out.printf("%dx %s %.0fg%n", itemCounts[i], itemNames[i], itemWeights[i] * 1000);
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }
}