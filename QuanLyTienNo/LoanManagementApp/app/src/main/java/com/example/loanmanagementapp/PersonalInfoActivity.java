package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

public class PersonalInfoActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvDebt;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvInterest_rate;
    private TextView tvDate;
    private TextView tvInterest;
    private TextView tvNote;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        return true;
    }

    private void Initialize() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getById(ListActivity.Id);
        tvName = (TextView) findViewById(R.id.persional_name);
        tvDebt = (TextView) findViewById(R.id.persional_debt);
        tvName.setText(debtor.getmName());
        tvDebt.setText(debtor.getmDebt());
        tvAddress.setText(debtor.getmAddress());
        tvPhone.setText(debtor.getmPhone());
        tvInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
        tvDate.setText(debtor.getmDate());
        tvInterest.setText(String.valueOf(debtor.getmInterest()));
        tvNote.setText(debtor.getmNote());

    }
}