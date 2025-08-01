package com.ai.llm.generation.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "oauth_user")
public final class User extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String name;
    
    private String pictureUrl;
    
    public User() {
    
    }
    
    public User(String email, String name, String pictureUrl) {
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPictureUrl() {
        return pictureUrl;
    }
    
   
}