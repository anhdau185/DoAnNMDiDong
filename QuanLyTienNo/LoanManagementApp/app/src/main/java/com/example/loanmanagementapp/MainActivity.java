package com.example.loanmanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loanmanagementapp.database.DBManager;

public class MainActivity extends AppCompatActivity {
    private TextView tvSumDebt;
    private DBManager dbManager;
    Button btnAddLoan;
    Button btnListLoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(mToolbar);

        btnAddLoan = (Button) findViewById(R.id.add_loan_btn);
        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddLoan = new Intent(MainActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddLoan);
            }
        });

        btnListLoan = (Button) findViewById(R.id.list_loan_btn);
        btnListLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToList = new Intent(MainActivity.this, ListActivity.class);
                startActivity(goToList);
            }
        });

        Initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    private void Initialize(){
        dbManager = new DBManager(this);
        tvSumDebt = (TextView) findViewById(R.id.main_sum_debt);
        tvSumDebt.setText(String.valueOf(dbManager.sumOfDebt()));
    }
}