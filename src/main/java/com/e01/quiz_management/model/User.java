package com.e01.quiz_management.model;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String username;
    private List<String> roles;
    private Token token;

    public User(Long id, String name, String username, List<String> roles, Token token) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
        this.token = token;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

//    public List<UserRole> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<UserRole> roles) {
//        this.roles = roles;
//    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
