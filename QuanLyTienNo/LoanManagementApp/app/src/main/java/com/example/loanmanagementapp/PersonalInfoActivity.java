package com.example.loanmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.ContactBottomSheetDialogFragment;
import com.example.loanmanagementapp.model.Debtor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private final int ID = ListActivity.Id;
    Button btnAddDebt;
    Button btnPayDebt;
    private Button btnContactDebtor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initialize();

        btnAddDebt = (Button) findViewById(R.id.debtor_add_debt_btn);
        btnAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDebt(ID, 100000);
                initialize();
            }
        });
        btnPayDebt= (Button) findViewById(R.id.debtor_pay_debt_btn);
        btnPayDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDebtAndInterest(ID);
                initialize();
            }
        });

        btnContactDebtor = (Button) findViewById(R.id.contact_debtor_btn);
        btnContactDebtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactBottomSheetDialogFragment sheet = new ContactBottomSheetDialogFragment();
                sheet.show(getSupportFragmentManager(), "contact_debtor");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.personal_info_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent goToAddNewLoan = new Intent(PersonalInfoActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
                break;
            case R.id.homeAsUp:
                Intent goToList = new Intent(PersonalInfoActivity.this, ListActivity.class);
                startActivity(goToList);
                ListActivity.Id = -1;
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void initialize() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);
        tvName = (TextView) findViewById(R.id.debtor_name);
        tvDebt = (TextView) findViewById(R.id.debtor_debt);
        tvName.setText(debtor.getmName());
        tvDebt.setText(String.valueOf(debtor.getmDebt()));

//        tvAddress.setText(debtor.getmAddress());
//        tvPhone.setText(debtor.getmPhone());
//        tvInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
//        tvDate.setText(debtor.getmDate());
//        tvInterest.setText(String.valueOf(debtor.getmInterest()));
//        tvNote.setText(debtor.getmNote());
    }
    private double calculateInterest(int id)
    {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(id);
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        String date = debtor.getmDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date interest = new Date();
        try {
            interest = simpleDateFormat.parse(date);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        long totalDay = (toDay.getTime() - interest.getTime())/(24*3600*1000);
        double interestRate = debtor.getmInterest_rate()/365 * debtor.getmDebt() * totalDay;
        dbManager.close();
        return Math.round(interestRate*10)/10;
    }
    private void payInterest(int id)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);
        debtor.setmInterest_date(date);
        if(dbManager.UpdateDebtor(debtor))
            Toast.makeText(PersonalInfoActivity.this, "Xóa lãi thành công!", Toast.LENGTH_LONG).show();
        dbManager.close();
    }
    private void payDebtAndInterest(int id)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);
        debtor.setmInterest_date("");
        debtor.setmDate("");
        debtor.setmInterest_rate(0);
        debtor.setmDebt(0);
        if(dbManager.UpdateDebtor(debtor))
            Toast.makeText(PersonalInfoActivity.this, "Trả nợ thành công!", Toast.LENGTH_LONG).show();
        dbManager.close();
    }
    private void addDebt(int id, int debt)
    {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        debtor.setmDebt(debtor.getmDebt() + debt);
        debtor.setmInterest_date(date);
        if(debtor.getmInterest_date() != date)
        {
//            Log.d("Today: ", debtor.getmInterest_date());
//            Log.d("Interest Date: ", date);
            Toast.makeText(PersonalInfoActivity.this, "Vui lòng thanh toán tiền lãi!", Toast.LENGTH_LONG).show();
        }
        else if(dbManager.UpdateDebtor(debtor))
            Toast.makeText(PersonalInfoActivity.this, "Thêm nợ thành công!", Toast.LENGTH_LONG).show();
        dbManager.close();
    }
}