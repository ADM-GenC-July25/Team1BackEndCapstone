package com.example.bytebazaar.service;

import com.example.bytebazaar.model.Product;
import com.example.bytebazaar.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Update all product images with Unsplash URLs
        productRepository.findAll().forEach(product -> {
            switch (product.getProductName()) {
                case "Wireless Headphones":
                    product.setImageLink("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop");
                    break;
                case "Smart Watch":
                    product.setImageLink("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=300&h=300&fit=crop");
                    break;
                case "Laptop Stand":
                    product.setImageLink("https://images.unsplash.com/flagged/photo-1576697010739-6373b63f3204?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8bGFwdG9wJTIwc3RhbmR8ZW58MHx8MHx8fDA%3D");
                    break;
                case "Coffee Maker":
                    product.setImageLink("https://images.unsplash.com/photo-1637029436347-e33bf98a5412?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y29mZmVlJTIwbWFjaGluZXxlbnwwfHwwfHx8MA%3D%3D");
                    break;
                case "Toaster":
                    product.setImageLink("https://plus.unsplash.com/premium_photo-1718559007272-26a72b83fdcc?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8dG9hc3RlcnxlbnwwfHwwfHx8MA%3D%3D");
                    break;
                case "Wireless Charger":
                    product.setImageLink("https://images.unsplash.com/photo-1633381638729-27f730955c23?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fHdpcmVsZXNzJTIwY2hhcmdlcnxlbnwwfHwwfHx8MA%3D%3D");
                    break;
                case "Laptop":
                    product.setImageLink("https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mzh8fGxhcHRvcHxlbnwwfHwwfHx8MA%3D%3D");
                    break;
                case "Smart Thermostat":
                    product.setImageLink("https://images.unsplash.com/photo-1636569608385-58efc32690ea?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8c21hcnQlMjB0aGVybW9zdGF0fGVufDB8fDB8fHww");
                    break;
            }
            productRepository.save(product);
        });
            
        if (productRepository.count() == 0) {
            productRepository.save(new Product("Wireless Headphones", 50, 99.99, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop", "High-quality wireless headphones with noise cancellation", 3));
            productRepository.save(new Product("Smart Watch", 30, 199.99, "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=300&h=300&fit=crop", "Advanced smartwatch with fitness tracking", 2));
            productRepository.save(new Product("Laptop Stand", 100, 39.99, "https://images.unsplash.com/flagged/photo-1576697010739-6373b63f3204?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8bGFwdG9wJTIwc3RhbmR8ZW58MHx8MHx8fDA%3D", "Adjustable laptop stand for better ergonomics", 1));
            productRepository.save(new Product("Coffee Maker", 25, 129.99, "https://images.unsplash.com/photo-1637029436347-e33bf98a5412?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y29mZmVlJTIwbWFjaGluZXxlbnwwfHwwfHx8MA%3D%3D", "Programmable coffee maker with timer", 5));
            productRepository.save(new Product("Toaster", 40, 99.99, "https://plus.unsplash.com/premium_photo-1718559007272-26a72b83fdcc?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8dG9hc3RlcnxlbnwwfHwwfHx8MA%3D%3D", "4-slice toaster with multiple settings", 2));
            productRepository.save(new Product("Wireless Charger", 60, 29.99, "https://images.unsplash.com/photo-1633381638729-27f730955c23?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fHdpcmVsZXNzJTIwY2hhcmdlcnxlbnwwfHwwfHx8MA%3D%3D", "Fast wireless charging pad", 1));
            productRepository.save(new Product("Laptop", 15, 600.00, "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mzh8fGxhcHRvcHxlbnwwfHwwfHx8MA%3D%3D", "High-performance laptop for work and gaming", 7));
            productRepository.save(new Product("Smart Thermostat", 20, 129.99, "https://images.unsplash.com/photo-1636569608385-58efc32690ea?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8c21hcnQlMjB0aGVybW9zdGF0fGVufDB8fDB8fHww", "WiFi-enabled smart thermostat", 4));
        }
    }
}