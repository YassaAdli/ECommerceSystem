/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecommercesystem;

/**
 *
 * @author Yassa
 */
abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    public void reduceQuantity(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot reduce quantity by more than available stock");
        }
        this.quantity -= amount;
    }
    
    public abstract boolean isExpired();
    public abstract boolean needsShipping();
}