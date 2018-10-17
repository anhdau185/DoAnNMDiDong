package com.example.loanmanagementapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debtor {
    private int mID;
    private String mName;
    private String mPhone;
    private String mAddress;
    private int mDebt;
    private double mInterest_rate;
    private String mDate;
    private String mInterest_date;
    private String mDescription;

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public int getmDebt() {
        return mDebt;
    }

    public void setmDebt(int mDebt) {
        this.mDebt = mDebt;
    }

    public double getmInterest_rate() {
        return mInterest_rate;
    }

    public void setmInterest_rate(double mInterest_rate) {
        this.mInterest_rate = mInterest_rate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmInterest_date() {
        return mInterest_date;
    }

    public void setmInterest_date(String mInterest_date) {
        this.mInterest_date = mInterest_date;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Debtor() {
    }

    public Debtor(String mName, String mPhone, String mAddress, int mDebt, double mInterest_rate, String mDate, String mInterest_date, String mDescription) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mDebt = mDebt;
        this.mInterest_rate = mInterest_rate;
        this.mDate = mDate;
        this.mInterest_date = mInterest_date;
        this.mDescription = mDescription;
    }

    public Debtor(int mID, String mName, String mPhone, String mAddress, int mDebt, double mInterest_rate, String mDate, String mInterest_date, String mDescription) {
        this.mID = mID;
        this.mName = mName;
        this.mPhone = mPhone;
        this.mAddress = mAddress;
        this.mDebt = mDebt;
        this.mInterest_rate = mInterest_rate;
        this.mDate = mDate;
        this.mInterest_date = mInterest_date;
        this.mDescription = mDescription;
    }
}
