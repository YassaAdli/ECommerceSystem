/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecommercesystem;

/**
 *
 * @author Yassa
 */
import java.time.LocalDate;
class ShippableProduct extends Product implements Shippable {
    private double weight;
    private int expirationDays; 
    
    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
        this.expirationDays = -1; 
    }
    
    public ShippableProduct(String name, double price, int quantity, double weight, int expirationDays) {
        super(name, price, quantity);
        this.weight = weight;
        this.expirationDays = expirationDays;
    }
    
    @Override
    public double getWeight() { return weight; }
    
    @Override
    public boolean isExpired() {
        return expirationDays != -1 && expirationDays < 0;
    }
    
    @Override
    public boolean needsShipping() { return true; }
    
    public void passDay() {
        if (expirationDays > 0) {
            expirationDays--;
        }
    }
}