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
class NonShippableProduct extends Product {
    private int expirationDays; 
    
    public NonShippableProduct(String name, double price, int quantity) {
        super(name, price, quantity);
        this.expirationDays = -1; 
    }
    
    public NonShippableProduct(String name, double price, int quantity, int expirationDays) {
        super(name, price, quantity);
        this.expirationDays = expirationDays;
    }
    
    @Override
    public boolean isExpired() {
        return expirationDays != -1 && expirationDays < 0;
    }
    
    @Override
    public boolean needsShipping() { return false; }
    
    public void passDay() {
        if (expirationDays > 0) {
            expirationDays--;
        }
    }
}