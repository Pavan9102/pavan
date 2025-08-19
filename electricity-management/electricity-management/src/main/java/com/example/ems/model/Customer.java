package com.example.ems.model;

public class Customer {
    private Integer customerId;
    private String loginId;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String password;
    private String role;

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

