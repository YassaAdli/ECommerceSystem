/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecommercesystem;

/**
 *
 * @author Yassa
 */
class Cart {
    private CartItem[] items;
    private int size;
    private static final int MAX_ITEMS = 100;
    
    public Cart() {
        this.items = new CartItem[MAX_ITEMS];
        this.size = 0;
    }
    
    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available for " + product.getName());
        }
        
        for (int i = 0; i < size; i++) {
            if (items[i].getProduct().getName().equals(product.getName())) {
                int totalQuantity = items[i].getQuantity() + quantity;
                if (totalQuantity > product.getQuantity()) {
                    throw new IllegalArgumentException("Not enough stock available for " + product.getName());
                }
                items[i].addQuantity(quantity);
                return;
            }
        }
        
        // Add new item
        if (size < MAX_ITEMS) {
            items[size] = new CartItem(product, quantity);
            size++;
        } else {
            throw new IllegalStateException("Cart is full");
        }
    }
    
    public CartItem[] getItems() { 
        CartItem[] result = new CartItem[size];
        for (int i = 0; i < size; i++) {
            result[i] = items[i];
        }
        return result;
    }
    
    public boolean isEmpty() { return size == 0; }
    
    public double getSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < size; i++) {
            subtotal += items[i].getTotalPrice();
        }
        return subtotal;
    }
    
    public int getSize() { return size; }
}