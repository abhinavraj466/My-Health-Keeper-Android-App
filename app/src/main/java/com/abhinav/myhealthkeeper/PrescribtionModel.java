package com.abhinav.myhealthkeeper;

public class PrescribtionModel {
    String medicine;

    public PrescribtionModel() {
    }

    public PrescribtionModel(String medicine) {
        this.medicine = medicine;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
