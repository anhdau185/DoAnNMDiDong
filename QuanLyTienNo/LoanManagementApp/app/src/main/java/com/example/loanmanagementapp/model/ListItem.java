package com.example.loanmanagementapp.model;

public class ListItem {
    private String name;
    private String loanAmount;

    public ListItem() {
        this.name = "";
        this.loanAmount = "";
    }

    public ListItem(String name, String loanAmount) {
        this.name = name;
        this.loanAmount = loanAmount;
    }

    public String getName() {
        return name;
    }

    public String getLoanAmount() {
        return loanAmount;
    }
}