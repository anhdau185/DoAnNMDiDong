package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.util.List;

public class AddNewLoanActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtDebt;
    private EditText edtInterest_rate;
    private EditText edtDate;
    private EditText edtInterest;
    private EditText edtNote;
    private ListView lvDebtor;
    private DBManager dbManager;
    private CursorAdapter customAdapter;
    private List<Debtor> listDebtor;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_loan);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_addNewLoan);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        Initialize();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        return true;
    }

    private void Initialize() {
//        dbManager = new DBManager(this);
//        edtName = (EditText) findViewById(R.id.edt_Name);
//        edtPhone = (EditText) findViewById(R.id.edt_Phone);
//        edtAddress = (EditText) findViewById(R.id.edt_Address);
//        edtDebt = (EditText) findViewById(R.id.edt_Debt);
//        edtInterest_rate = (EditText) findViewById(R.id.edt_Interest_rate);
//        edtDate = (EditText) findViewById(R.id.edt_Date);
//        edtInterest = (EditText) findViewById(R.id.edt_Interest);
//        edtNote = (EditText) findViewById(R.id.edt_Note);
//        btnSave = (Button) findViewById(R.id.btn_Save);
//        btnCancel = (Button) findViewById(R.id.btn_Cancle);
    }

    private Debtor createDebtor() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        Integer debt = Integer.valueOf(edtDebt.getText().toString());
        Double interest_rate = Double.valueOf(edtInterest_rate.getText().toString());
        String date = edtDate.getText().toString();
        Double interest = Double.valueOf(edtInterest.getText().toString());
        String note = edtNote.getText().toString();

        Debtor debtor = new Debtor(name, phone, address, debt, interest_rate, date, interest, note);
        return debtor;
    }

    private void EditById(int id) {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getById(ListActivity.Id);
        edtName.setText(debtor.getmName());
        edtDebt.setText(debtor.getmDebt());
        edtAddress.setText(debtor.getmAddress());
        edtPhone.setText(debtor.getmPhone());
        edtInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
        edtDate.setText(debtor.getmDate());
        edtInterest.setText(String.valueOf(debtor.getmInterest()));
        edtNote.setText(debtor.getmNote());
    }
}
