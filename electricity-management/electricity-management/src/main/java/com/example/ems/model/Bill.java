package com.example.ems.model;

public class Bill {
    private Integer billId;
    private Integer customerId;
    private double meterReading;
    private double billAmount;
    private String month;
    private int year;
    private String status;

    public Integer getBillId() { return billId; }
    public void setBillId(Integer billId) { this.billId = billId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public double getMeterReading() { return meterReading; }
    public void setMeterReading(double meterReading) { this.meterReading = meterReading; }
    public double getBillAmount() { return billAmount; }
    public void setBillAmount(double billAmount) { this.billAmount = billAmount; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

