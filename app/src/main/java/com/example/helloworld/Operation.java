package com.example.helloworld;


import java.util.Date;

import javax.net.ssl.SSLEngineResult;

public class Operation {
    private Boolean isIncome;
    private Date operationDT;
    private Double amount;
    private Status status;
    private Account sender;
    private Account reciver;

    public Boolean getIncome() {
        return isIncome;
    }

    public void setIncome(Boolean income) {
        isIncome = income;
    }

    public Date getOperationDT() {
        return operationDT;
    }

    public void setOperationDT(Date operationDT) {
        this.operationDT = operationDT;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void Cancel() {
        status = Status.Canceled;
        sender.setMoney(sender.getMoney() + amount);
        reciver.setMoney(reciver.getMoney() - amount);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getReciver() {
        return reciver;
    }

    public void setReciver(Account reciver) {
        this.reciver = reciver;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public enum Status {
        Processing, Ready, Canceled
    }
}