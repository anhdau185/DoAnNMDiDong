package com.example.loanmanagementapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.ContactBottomSheetDialogFragment;
import com.example.loanmanagementapp.model.Debtor;
import com.example.loanmanagementapp.model.SortBottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
                addDebt(ID);
            }
        });
        btnPayDebt = (Button) findViewById(R.id.debtor_pay_debt_btn);

        //////Su kien nut Tra no
        //////
        //////
        //////
        btnPayDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbManager.getDebtorById(ID).getmInterest_rate() == 0)
                    payInterest();
                else {
//                    ContactBottomSheetDialogFragment sheet = new ContactBottomSheetDialogFragment();
//                    sheet.show(getSupportFragmentManager(), "list_sort_action");
//                    TextView tv1, tv2;
//                    tv1 = (TextView) findViewById(R.id.tv_pay_interest);
//                    tv1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            payInterest();
//                        }
//                    });
//                    tv2 = (TextView) findViewById(R.id.tv_pay_loan_interest);
//                    tv2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            payDebtAndInterest();
//                        }
//                    });
                initialize();
                }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.personal_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent goToAddNewLoan = new Intent(PersonalInfoActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
                break;
            case R.id.homeAsUp:
                Intent goToList = new Intent(PersonalInfoActivity.this, ListActivity.class);
                startActivity(goToList);
                ListActivity.Id = -1;
                break;
            case R.id.action_delete:
                deleteDebtor(ID);
        }
        return super.onContextItemSelected(item);
    }

    private void initialize() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);
        tvName = (TextView) findViewById(R.id.debtor_name);
        tvDebt = (TextView) findViewById(R.id.debtor_debt);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvName.setText(debtor.getmName());
        tvDebt.setText(String.valueOf(formatter.format(debtor.getmDebt())));

//        tvAddress.setText(debtor.getmAddress());
//        tvPhone.setText(debtor.getmPhone());
//        tvInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
//        tvDate.setText(debtor.getmDate());
//        tvInterest.setText(String.valueOf(debtor.getmInterest()));
//        tvNote.setText(debtor.getmNote());
    }

    private void payInterest() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final String date = simpleDateFormat.format(calendar.getTime());
        dbManager = new DBManager(this);
        final Debtor debtor = dbManager.getDebtorById(ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Trả nợ");
        builder.setMessage("Tiền lãi: " + dbManager.calculateInterest(dbManager.getDebtorById(ID))
                + "\nBạn có chắc muốn thanh toán chứ?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                debtor.setmInterest_date(date);
                if (dbManager.UpdateDebtor(debtor))
                    Toast.makeText(PersonalInfoActivity.this, "Xóa lãi thành công!", Toast.LENGTH_LONG).show();
                initialize();
                dbManager.close();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void payDebtAndInterest() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Trả nợ");
        builder.setMessage("Tiền nợ: " + debtor.getmDebt() + "\nTiền lãi: " +
                dbManager.calculateInterest(dbManager.getDebtorById(ID)) + "\nBạn có chắc muốn thanh toán chứ?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Debtor debtor = dbManager.getDebtorById(ID);
                debtor.setmInterest_date("");
                debtor.setmDate("");
                debtor.setmInterest_rate(0);
                debtor.setmDebt(0);
                if (dbManager.UpdateDebtor(debtor))
                    Toast.makeText(PersonalInfoActivity.this, "Trả nợ thành công!", Toast.LENGTH_LONG).show();
                initialize();
                dbManager.close();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addDebt(int id) {
        dbManager = new DBManager(this);
        final Debtor debtor = dbManager.getDebtorById(id);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final String date = simpleDateFormat.format(calendar.getTime());

        if (dbManager.calculateInterest(dbManager.getDebtorById(ID)) > 0) {
            Toast.makeText(PersonalInfoActivity.this, "Vui lòng thanh toán tiền lãi!", Toast.LENGTH_LONG).show();
        } else {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            View mView = layoutInflaterAndroid.inflate(R.layout.personal_input_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView);

            final EditText edtAddDebt = (EditText) mView.findViewById(R.id.userInputDialog);
            //Format dau phay don vi ngan
            edtAddDebt.addTextChangedListener(new TextWatcher() {
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
                            edtAddDebt.setText(formated);
                            edtAddDebt.setSelection(formated.length());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            String debt = (edtAddDebt.getText().toString()).replaceAll("[,.]", "");
                            debtor.setmDebt(debtor.getmDebt() + Integer.valueOf(debt));
                            debtor.setmInterest_date(date);
                            if (dbManager.UpdateDebtor(debtor))
                                Toast.makeText(PersonalInfoActivity.this, "Thêm nợ thành công!", Toast.LENGTH_LONG).show();
                            initialize();
                            dbManager.close();
                        }
                    })
                    .setNegativeButton("Hủy",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    dialogBox.cancel();
                                }
                            });
            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();
        }
    }

    private void deleteDebtor(final int ID) {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa thông tin");
        builder.setMessage("Bạn có chắc muốn xóa " + debtor.getmName() + " ra khỏi danh sách?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dbManager.deleteDebtor(ID))
                    Toast.makeText(PersonalInfoActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                dbManager.close();
                Intent goToList = new Intent(PersonalInfoActivity.this, ListActivity.class);
                startActivity(goToList);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}