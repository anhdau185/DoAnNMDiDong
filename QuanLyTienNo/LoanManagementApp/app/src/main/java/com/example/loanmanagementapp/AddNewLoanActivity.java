package com.example.loanmanagementapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.ActivityName;
import com.example.loanmanagementapp.model.Debtor;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewLoanActivity extends AppCompatActivity {
    private ActivityName sourceActivity;
    private int debtorId;
    private static final int PICK_CONTACT = 1;

    private EditText edtName;
    private EditText edtPhone;
    private Button btnPickContact;
    private EditText edtAddress;
    private EditText edtDebt;
    private EditText edtInterest_rate;
    private EditText edtDate;
    private Button btnSelectDate;
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

        Intent intent = getIntent();
        sourceActivity = (ActivityName) intent.getSerializableExtra("sourceActivity");
        debtorId = intent.getIntExtra("debtorId", -1);
        if (debtorId < 0) {
            mToolbar.setTitle("Thêm nợ mới");
            edtName.requestFocus();
        } else {
            mToolbar.setTitle("Chỉnh sửa nợ");
            initializeEditDebtor(debtorId);
            edtPhone.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent goToList, goToPersonalInfo;
        goToList = new Intent(AddNewLoanActivity.this, ListActivity.class);
        goToPersonalInfo = new Intent(AddNewLoanActivity.this, PersonalInfoActivity.class);

        switch (item.getItemId()) {
            case R.id.action_cancel:
                if (debtorId < 0) {
                    switch (sourceActivity) {
                        case MAIN:
                            finish();
                            break;
                        case LOAN_LIST:
                            startActivity(goToList);
                            finish();
                            break;
                    }
                } else {
                    switch (sourceActivity) {
                        case LOAN_LIST:
                            finish();
                            break;
                        case PERSONAL_INFO:
                            goToPersonalInfo.putExtra("debtorId", debtorId);
                            startActivity(goToPersonalInfo);
                            finish();
                            break;
                    }
                }
                return true;
            case R.id.action_save:
                Debtor debtor = createDebtor();
                if (debtor != null) {
                    if (debtorId < 0) {
                        dbManager.addDebtor(debtor);
                        Toast.makeText(AddNewLoanActivity.this, "Thêm thành công!", Toast.LENGTH_LONG).show();
                        startActivity(goToList);
                        finish();
                    } else {
                        if (dbManager.UpdateDebtor(debtor)) {
                            Toast.makeText(AddNewLoanActivity.this, "Sửa thành công!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddNewLoanActivity.this, "Có lỗi khi sửa nợ! Vui lòng thử lại", Toast.LENGTH_LONG).show();
                        }
                        goToPersonalInfo.putExtra("debtorId", debtorId);
                        startActivity(goToPersonalInfo);
                        finish();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Initialize() {
        dbManager = new DBManager(this);

        edtName = findViewById(R.id.add_loan_name);

        edtPhone = findViewById(R.id.add_loan_phone);
        btnPickContact = findViewById(R.id.add_pick_contact);
        btnPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickUpContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(pickUpContact, PICK_CONTACT);
            }
        });

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
                        String formatted = NumberFormat.getInstance().format((parsed));
                        current = formatted;
                        edtDebt.setText(formatted);
                        edtDebt.setSelection(formatted.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtInterest_rate = findViewById(R.id.add_loan_interest_rate);

        edtDate = findViewById(R.id.add_loan_date);
        btnSelectDate = findViewById(R.id.add_select_date);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        edtDescription = findViewById(R.id.add_loan_description);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == PICK_CONTACT) && (resultCode == RESULT_OK)) {
            Cursor cursor = null;
            try {
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    String phoneNumber = cursor.getString(0);
                    // do something with the phone number
                    edtPhone.setText(phoneNumber.replaceAll("-", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Debtor createDebtor() {
        if (checkValue()) {
            String name = edtName.getText().toString();
            String phone = edtPhone.getText().toString();
            String address = edtAddress.getText().toString();
            String strDebt = (edtDebt.getText().toString()).replaceAll("[,.]", "");
            Integer debt = Integer.valueOf(strDebt);
            Double interest_rate = Double.valueOf(edtInterest_rate.getText().toString());
            String date = edtDate.getText().toString();
            String description = edtDescription.getText().toString();
            Debtor debtor;
            if (debtorId < 0) {
                debtor = new Debtor(name, phone, address, debt, interest_rate, date, date, description);
            } else {
                debtor = new Debtor(debtorId, name, phone, address, debt, interest_rate, date, date, description);
            }
            return debtor;
        }
        return null;
    }

    private void initializeEditDebtor(int id) {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(id);
        Log.d("Edit: ", "id:" + id);

        edtName.setText(debtor.getmName());
        edtName.setEnabled(false);

        edtPhone.setText(debtor.getmPhone());

        edtAddress.setText(debtor.getmAddress());

        edtDebt.setText(String.valueOf(debtor.getmDebt()));
        edtDebt.setEnabled(false);

        edtInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
        edtInterest_rate.setEnabled(false);

        edtDate.setText(debtor.getmDate());
        edtDate.setEnabled(false);
        btnSelectDate.setEnabled(false);

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

    private boolean checkValue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi");
        if (edtName.getText().toString().isEmpty()) {
            builder.setMessage("Vui lòng nhập tên!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
        if (edtDebt.getText().toString().isEmpty()) {
            builder.setMessage("Vui lòng nhập số tiền nợ!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
        if (edtDate.getText().toString().isEmpty()) {
            builder.setMessage("Vui lòng chọn ngày vay!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
        if (edtInterest_rate.toString().isEmpty())
            edtInterest_rate.setText("0");
        return true;
    }
}
