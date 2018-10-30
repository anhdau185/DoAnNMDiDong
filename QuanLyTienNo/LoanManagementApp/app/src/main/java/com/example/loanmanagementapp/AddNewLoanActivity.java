package com.example.loanmanagementapp;

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

        Intent intent = getIntent();
        sourceActivity = (ActivityName) intent.getSerializableExtra("sourceActivity");
        debtorId = intent.getIntExtra("debtorId", -1);

        Initialize();

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
        MenuItem item = menu.findItem(R.id.action_info);
        if (debtorId < 0)
            item.setVisible(true);
        else
            item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_save:
                Debtor debtor = createDebtor();
                if (debtor != null) {
                    if (debtorId < 0) {
                        dbManager.addDebtor(debtor);
                        Toast.makeText(AddNewLoanActivity.this, "Thêm thành công!", Toast.LENGTH_LONG).show();

                        switch (sourceActivity) {
                            case LOAN_LIST:
                                sendBroadcast(new Intent("recreate_loan_list_activity"));
                                break;
                            case MAIN:
                                startActivity(new Intent(AddNewLoanActivity.this, ListActivity.class));
                                break;
                        }
                        finish();
                    } else {
                        if (dbManager.UpdateDebtor(debtor)) {
                            Toast.makeText(AddNewLoanActivity.this, "Sửa thành công!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddNewLoanActivity.this, "Có lỗi khi sửa nợ! Vui lòng thử lại", Toast.LENGTH_LONG).show();
                        }

                        if (sourceActivity == ActivityName.PERSONAL_INFO) {
                            Intent finishOldPersonalInfo = new Intent("finish_personal_info_activity");
                            sendBroadcast(finishOldPersonalInfo);
                        }

                        Intent goToPersonalInfo = new Intent(AddNewLoanActivity.this, PersonalInfoActivity.class);
                        goToPersonalInfo.putExtra("debtorId", debtorId);
                        startActivity(goToPersonalInfo);

                        finish();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String textToDisplay =
                        "- Trường 'Tên' và 'Số tiền nợ' không được bỏ trống." +
                                "\n- Trường 'Lãi suất' bỏ trống sẽ tự hiểu là lãi suất 0%." +
                                "\n- Các trường 'Tên', 'Số tiền nợ', 'Lãi suất' và 'Ngày vay' không thể chỉnh sửa sau khi đã lưu.";
                builder.setTitle("Quy định");
                builder.setMessage(textToDisplay);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
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

        edtDescription = findViewById(R.id.add_loan_description);

        if (debtorId < 0) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            edtDate.setText(formatter.format(calendar.getTime()));

            btnSelectDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDate();
                }
            });
        }
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
        if (edtName.getText().toString().equals("")) {
            Toast.makeText(AddNewLoanActivity.this, "Vui lòng nhập tên", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtDebt.getText().toString().equals("")) {
            Toast.makeText(AddNewLoanActivity.this, "Vui lòng nhập số tiền nợ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtInterest_rate.getText().toString().equals("")) {
            edtInterest_rate.setText("0.0");
        }
        return true;
    }
}