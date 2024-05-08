package com.example.helloworld;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private AccountHolder holder;
    private Date openDT;
    private Date closeDT;
    private Double money;
    private ArrayList<Operation> operations = new ArrayList<Operation>();

    public AccountHolder getHolder() {
        return holder;
    }

    public void setHolder(AccountHolder holder) {
        this.holder = holder;
    }

    public Date getOpenDT() {
        return openDT;
    }

    public void setOpenDT(Date openDT) {
        this.openDT = openDT;
    }

    public Date getCloseDT() {
        return closeDT;
    }

    public void setCloseDT(Date closeDT) {
        this.closeDT = closeDT;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public  void addOperation(Operation operation){
        operations.add(operation);
    }

    @NonNull
    @Override
    public String toString() {
        return holder.getName() + " " + holder.getSurname() + " " + money;
    }
}