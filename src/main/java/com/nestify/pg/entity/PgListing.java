package com.nestify.pg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pg_listings")
public class PgListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerUsername;
    private String pgName;
    private String address;
    private String city;
    private Double pricePerMonth;
    private String imageUrl;

    @Column(length = 2000)
    private String rules;

    @Column(length = 1000)
    private String description;

    private String contactNumber;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public String getPgName() { return pgName; }
    public void setPgName(String pgName) { this.pgName = pgName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getPricePerMonth() { return pricePerMonth; }
    public void setPricePerMonth(Double pricePerMonth) { this.pricePerMonth = pricePerMonth; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}