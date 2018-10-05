package com.example.loanmanagementapp.model;

public class ListItem {
    private String name;
    private int loanAmount;

    public ListItem() {
        this.name = "";
        this.loanAmount = 0;
    }

    public ListItem(String name, int loanAmount) {
        this.name = name;
        this.loanAmount = loanAmount;
    }

    public String getName() {
        return name;
    }

    public int getLoanAmount() {
        return loanAmount;
    }
}