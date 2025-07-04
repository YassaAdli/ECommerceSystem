/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ecommercesystem;

/**
 *
 * @author Yassa
 */
public class ECommerceSystem {    
    public static void checkout(Customer customer, Cart cart) {
        try {
            if (cart.isEmpty()) {
                throw new IllegalStateException("Cart is empty");
            }
            
            Shippable[] shippableItems = new Shippable[1000]; // Max capacity
            int shippableCount = 0;
            
            CartItem[] cartItems = cart.getItems();
            
            // Validate all items before processing
            for (int i = 0; i < cart.getSize(); i++) {
                Product product = cartItems[i].getProduct();
                int requestedQuantity = cartItems[i].getQuantity();
                
                if (product.isExpired()) {
                    throw new IllegalStateException("Product " + product.getName() + " is expired");
                }
                
                if (requestedQuantity > product.getQuantity()) {
                    throw new IllegalStateException("Product " + product.getName() + " is out of stock");
                }
                
                // Collect shippable items
                if (product.needsShipping() && product instanceof Shippable) {
                    for (int j = 0; j < requestedQuantity; j++) {
                        shippableItems[shippableCount] = (Shippable) product;
                        shippableCount++;
                    }
                }
            }
            
            double subtotal = cart.getSubtotal();
            double shippingFee = ShippingService.calculateShippingFee(shippableItems, shippableCount);
            double totalAmount = subtotal + shippingFee;
            
            if (customer.getBalance() < totalAmount) {
                throw new IllegalStateException("Customer's balance is insufficient");
            }
            
            // Process shipment if needed
            ShippingService.processShipment(shippableItems, shippableCount);
            
            // Update product quantities
            for (int i = 0; i < cart.getSize(); i++) {
                cartItems[i].getProduct().reduceQuantity(cartItems[i].getQuantity());
            }
            
            customer.deductBalance(totalAmount);
            
            // Print receipt
            System.out.println("** Checkout receipt **");
            for (int i = 0; i < cart.getSize(); i++) {
                System.out.printf("%dx %s %.0f%n", 
                    cartItems[i].getQuantity(), 
                    cartItems[i].getProduct().getName(), 
                    cartItems[i].getTotalPrice());
            }
            System.out.println("----------------------");
            System.out.printf("Subtotal %.0f%n", subtotal);
            System.out.printf("Shipping %.0f%n", shippingFee);
            System.out.printf("Amount %.0f%n", totalAmount);
            System.out.printf("Customer balance after payment: %.0f%n", customer.getBalance());
            System.out.println("END.");
            
        } catch (Exception e) {
            System.err.println("Checkout failed: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== E-Commerce System Demo ===\n");
        
        // Create products
        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 10, 0.2, 7);
        ShippableProduct biscuits = new ShippableProduct("Biscuits", 150, 5, 0.7, 30);
        ShippableProduct tv = new ShippableProduct("TV", 500, 3, 15.0);
        NonShippableProduct scratchCard = new NonShippableProduct("Mobile Scratch Card", 25, 50);
        
        // Create customer with sufficient balance
        Customer customer = new Customer("John Doe", 2000);
        
        // Test Case 1: Successful checkout (matching expected output)
        System.out.println("=== Test Case 1: Successful Checkout ===");
        Cart cart1 = new Cart();
        cart1.add(cheese, 2);
        cart1.add(biscuits, 1);
        cart1.add(scratchCard, 1);
        
        checkout(customer, cart1);
        
        // Test Case 2: Empty cart
        System.out.println("\n=== Test Case 2: Empty Cart ===");
        Cart cart2 = new Cart();
        checkout(customer, cart2);
        
        // Test Case 3: Insufficient balance
        System.out.println("\n=== Test Case 3: Insufficient Balance ===");
        Customer poorCustomer = new Customer("Poor Customer", 50);
        Cart cart3 = new Cart();
        cart3.add(tv, 1);
        checkout(poorCustomer, cart3);
        
        // Test Case 4: Out of stock
        System.out.println("\n=== Test Case 4: Out of Stock ===");
        Cart cart4 = new Cart();
        try {
            cart4.add(cheese, 15); // Only have 8 left after previous purchase
        } catch (Exception e) {
            System.err.println("Add to cart failed: " + e.getMessage());
        }
        
        // Test Case 5: Expired product
        System.out.println("\n=== Test Case 5: Expired Product ===");
        ShippableProduct expiredMilk = new ShippableProduct("Expired Milk", 50, 5, 1.0, -1); // Already expired
        Cart cart5 = new Cart();
        cart5.add(expiredMilk, 1);
        checkout(customer, cart5);
        
        // Test Case 6: Only non-shippable items
        System.out.println("\n=== Test Case 6: Only Non-Shippable Items ===");
        Cart cart6 = new Cart();
        cart6.add(scratchCard, 3);
        checkout(customer, cart6);
        
        System.out.println("\n=== Demo Complete ===");
    }
}