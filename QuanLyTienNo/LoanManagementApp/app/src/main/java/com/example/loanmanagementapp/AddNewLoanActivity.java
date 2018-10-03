package com.example.loanmanagementapp;

import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.util.List;

public class AddNewLoanActivity extends AppCompatActivity{
    private TextInputEditText edtName;
    private TextInputEditText edtPhone;
    private TextInputEditText edtAddress;
    private TextInputEditText edtDebt;
    private TextInputEditText edtInterest_rate;
    private TextInputEditText edtDate;
    private TextInputEditText edtInterest;
    private TextInputEditText edtDescription;
    private ListView lvDebtor;
    private DBManager dbManager;
    private CursorAdapter customAdapter;
    private List<Debtor> listDebtor;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_loan);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_addNewLoan);
        setSupportActionBar(mToolbar);

        Initialize();


//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Debtor debtor = createDebtor();
//                if(debtor != null) {
//                    dbManager.addDebtor(debtor);
//                }
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_save:
                createDebtor();
                return true;
        default: return super.onOptionsItemSelected(item);
        }

    }
    private void Initialize()
    {
        dbManager = new DBManager(this);
        edtName = (TextInputEditText) findViewById(R.id.add_loan_name);
        edtPhone = (TextInputEditText) findViewById(R.id.add_loan_phone);
        edtAddress = (TextInputEditText) findViewById(R.id.add_loan_address);
        edtDebt = (TextInputEditText) findViewById(R.id.add_loan_loan_amount);
        edtInterest_rate = (TextInputEditText) findViewById(R.id.add_loan_interest_rate);
        edtDate = (TextInputEditText) findViewById(R.id.add_loan_date);
        edtDescription = (TextInputEditText) findViewById(R.id.add_loan_description);
    }
    private boolean createDebtor()
    {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        Integer debt = Integer.valueOf(edtDebt.getText().toString());
        Double interest_rate = Double.valueOf(edtInterest_rate.getText().toString());
        String date = edtDate.getText().toString();
        Double interest = Double.valueOf(edtInterest.getText().toString());
        String note = edtDescription.getText().toString();

        Debtor debtor= new Debtor(name, phone, address, debt, interest_rate, date, interest, note);
        return true;
    }
    private void EditById(int id)
    {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getById(ListActivity.Id);
        edtName.setText(debtor.getmName());
        edtDebt.setText(debtor.getmDebt());
        edtAddress.setText(debtor.getmAddress());
        edtPhone.setText(debtor.getmPhone());
        edtInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
        edtDate.setText(debtor.getmDate());
        edtInterest.setText(String.valueOf(debtor.getmInterest()));
        edtDescription.setText(debtor.getmNote());
    }
}
