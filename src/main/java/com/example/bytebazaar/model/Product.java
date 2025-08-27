package com.example.bytebazaar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(nullable = false)
    private Integer inventory;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "image_link")
    private String imageLink;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "days_to_deliver")
    private Integer daysToDeliver;
    
    public Product() {}
    
    public Product(String productName, Integer inventory, Double price, String imageLink, String description, Integer daysToDeliver) {
        this.productName = productName;
        this.inventory = inventory;
        this.price = price;
        this.imageLink = imageLink;
        this.description = description;
        this.daysToDeliver = daysToDeliver;
    }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getDaysToDeliver() { return daysToDeliver; }
    public void setDaysToDeliver(Integer daysToDeliver) { this.daysToDeliver = daysToDeliver; }
}