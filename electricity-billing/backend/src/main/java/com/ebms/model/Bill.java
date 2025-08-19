package com.ebms.model;

public class Bill {
    private int billId;
    private double meterReading;
    private double billAmount;
    private int customerId;
    private String monthStr;
    private int yearInt;
    private String status; // PENDING or PAID or PARTIAL

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public double getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(double meterReading) {
        this.meterReading = meterReading;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public int getYearInt() {
        return yearInt;
    }

    public void setYearInt(int yearInt) {
        this.yearInt = yearInt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

