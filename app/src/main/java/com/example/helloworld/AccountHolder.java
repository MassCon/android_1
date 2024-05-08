package com.example.helloworld;

import java.util.Date;

public class AccountHolder {
    private String Name;
    private String Surname;
    private Double creditRate;
    private Date registrationDT;

    // Accessors
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Double getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(Double creditRate) {
        this.creditRate = creditRate;
    }

    public Date getRegistrationDT() {
        return registrationDT;
    }

    public void setRegistrationDT(Date registrationDT) {
        this.registrationDT = registrationDT;
    }
}