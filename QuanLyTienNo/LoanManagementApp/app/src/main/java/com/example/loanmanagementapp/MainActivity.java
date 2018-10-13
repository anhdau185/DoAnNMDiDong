package com.example.loanmanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.loanmanagementapp.adapter.CustomAdapter;
import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvSumDebt;
    private DBManager dbManager;
    ListView lvNotify;
    LinearLayout btnAddLoan;
    LinearLayout btnListLoan;
    List<Debtor> listDebtor;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(mToolbar);

        btnAddLoan = (LinearLayout) findViewById(R.id.add_loan_button);
        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddLoan = new Intent(MainActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddLoan);
            }
        });

        btnListLoan = (LinearLayout) findViewById(R.id.loan_list_button);
        btnListLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToList = new Intent(MainActivity.this, ListActivity.class);
                startActivity(goToList);
            }
        });

        Initialize();
        SetAdapter();

        lvNotify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListActivity.Id = listDebtor.get(position).getmID();
//                    Log.d("searching ID:",String.valueOf(Id));
                Intent goToPersonalInfo = new Intent(MainActivity.this, PersonalInfoActivity.class);
                startActivity(goToPersonalInfo);
            }
        });
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
        lvNotify = (ListView) findViewById(R.id.main_notify_lv);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvSumDebt.setText(String.valueOf(formatter.format(dbManager.sumOfDebt())));
    }
    public void SetAdapter(){
        listDebtor = dbManager.notifyDebtor();
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_debtor, listDebtor);
            lvNotify.setAdapter(customAdapter);
        }
        else{
            lvNotify.setSelection(customAdapter.getCount()-1);
        }
    }
}