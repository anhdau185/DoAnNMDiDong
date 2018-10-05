package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.personal_info_menu, menu);
        return true;
    }

    private void Initialize() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getById(ListActivity.Id);
        tvName = (TextView) findViewById(R.id.debtor_name);
        tvDebt = (TextView) findViewById(R.id.loan_amount);
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