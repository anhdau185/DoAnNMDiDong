package com.example.loanmanagementapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewLoanActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtDebt;
    private EditText edtInterest_rate;
    private EditText edtDate;
    private EditText edtInterest_date;
    private EditText edtDescription;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_loan);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_addNewLoan);
        setSupportActionBar(mToolbar);

        Initialize();
        if (ListActivity.Id >= 0) {
            initializeEditDebtor(ListActivity.Id);
        }
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                ListActivity.Id = -1;
                finish();
                return true;
            case R.id.action_save:
                Debtor debtor = createDebtor();
                if (ListActivity.Id == -1) {
                    if (debtor != null) {
                        dbManager.addDebtor(debtor);
                        Toast.makeText(AddNewLoanActivity.this, "Thêm thành công!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (debtor != null) {
//                        Log.d("update:", "ready");
                        if (dbManager.UpdateDebtor(debtor))
                            Toast.makeText(AddNewLoanActivity.this, "Chỉnh sửa thành công!", Toast.LENGTH_LONG).show();
                    }
                }
                Intent goToList = new Intent(AddNewLoanActivity.this, ListActivity.class);
                startActivity(goToList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Initialize() {
        dbManager = new DBManager(this);
        edtName = findViewById(R.id.add_loan_name);
        edtPhone = findViewById(R.id.add_loan_phone);
        edtAddress = findViewById(R.id.add_loan_address);
        edtDebt = findViewById(R.id.add_loan_loan_amount);
        edtDebt.addTextChangedListener(new TextWatcher() {
            private String current = " ";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    if (!s.toString().equals(current)) {
                        String cleanString = s.toString().replaceAll("[,.]", "");
                        double parsed = Double.parseDouble(cleanString);
                        String formated = NumberFormat.getInstance().format((parsed));
                        current = formated;
                        edtDebt.setText(formated);
                        edtDebt.setSelection(formated.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtInterest_rate = findViewById(R.id.add_loan_interest_rate);
        edtDate = findViewById(R.id.add_loan_date);
        edtDescription = findViewById(R.id.add_loan_description);
    }

    private Debtor createDebtor() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String strDebt = (edtDebt.getText().toString()).replaceAll("[,.]", "");
        Integer debt = Integer.valueOf(strDebt);
        Double interest_rate = Double.valueOf(edtInterest_rate.getText().toString());
        String date = edtDate.getText().toString();
        String description = edtDescription.getText().toString();
        Debtor debtor = new Debtor(name, phone, address, debt, interest_rate, date, "", description);
        return debtor;
    }

    private void initializeEditDebtor(int id) {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(id);
        Log.d("Edit: ", "id:" + id);
        edtName.setText(debtor.getmName());
        edtName.setEnabled(false);
        edtDebt.setText(String.valueOf(debtor.getmDebt()));
        edtDebt.setEnabled(false);
        edtAddress.setText(debtor.getmAddress());
        edtPhone.setText(debtor.getmPhone());
        edtInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
        edtInterest_rate.setEnabled(false);
        edtDate.setText(debtor.getmDate());
        edtDescription.setText(debtor.getmDescription());
        dbManager.close();
    }

    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int mDay = calendar.get(Calendar.DATE);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
