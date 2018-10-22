package com.example.loanmanagementapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanmanagementapp.adapter.DebtorDetailAdapter;
import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.ActivityName;
import com.example.loanmanagementapp.model.ContactBottomSheetDialogFragment;
import com.example.loanmanagementapp.model.Debtor;
import com.example.loanmanagementapp.model.DebtorDetail;
import com.example.loanmanagementapp.model.PayLoanBottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonalInfoActivity extends AppCompatActivity {
    private ActivityName sourceActivity;
    private int debtorId;
    public int getDebtorId() {
        return debtorId;
    }
    private BroadcastReceiver receiver;

    private DBManager dbManager;
    private List<DebtorDetail> details;
    private DebtorDetailAdapter debtorDetailAdapter;
    private ListView lvDebtorDetails;
    private TextView tvName;
    private TextView tvDebt;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvInterest_rate;
    private TextView tvDate;
    private TextView tvInterest;
    private TextView tvNote;
    private Button btnAddDebt;
    private Button btnPayDebt;
    private Button btnContactDebtor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null && action.equals("finish_personal_info_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter("finish_personal_info_activity"));

        Intent intent = getIntent();
        sourceActivity = (ActivityName) intent.getSerializableExtra("sourceActivity");
        debtorId = intent.getIntExtra("debtorId", -1);

        Initialize();

        btnAddDebt = (Button) findViewById(R.id.debtor_add_debt_btn);
        // Su kien nut 'Them no'
        btnAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDebt();
            }
        });

        btnPayDebt = (Button) findViewById(R.id.debtor_pay_debt_btn);
        // Su kien nut 'Tru no'
        btnPayDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbManager.getDebtorById(debtorId).getmInterest_rate() == 0) {
                    payDebtAndInterest();
                } else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment prev = getSupportFragmentManager().findFragmentByTag("pay_loan_action");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    PayLoanBottomSheetDialogFragment payLoanBottomSheet = PayLoanBottomSheetDialogFragment.newInstance();
                    payLoanBottomSheet.show(getSupportFragmentManager(), "pay_loan_action");
                }
            }
        });

        btnContactDebtor = (Button) findViewById(R.id.contact_debtor_btn);
        // Su kien nut 'Lien lac'
        btnContactDebtor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("contact_debtor_action");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                ContactBottomSheetDialogFragment contactDebtorBottomSheet = ContactBottomSheetDialogFragment.newInstance();
                contactDebtorBottomSheet.show(getSupportFragmentManager(), "contact_debtor_action");
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
                Intent goToEditLoan = new Intent(PersonalInfoActivity.this, AddNewLoanActivity.class);
                goToEditLoan.putExtra("sourceActivity", ActivityName.PERSONAL_INFO);
                goToEditLoan.putExtra("debtorId", debtorId);
                startActivity(goToEditLoan);
                break;
            case R.id.homeAsUp:
//                Log.d("Reset ID:", ListActivity.Id + "");
                Intent goToList = new Intent(PersonalInfoActivity.this, ListActivity.class);
                startActivity(goToList);
                break;
            case R.id.action_delete:
                deleteDebtor();
        }
        return super.onContextItemSelected(item);
    }

    private void Initialize() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(debtorId);
        details = new ArrayList<>();
        lvDebtorDetails = (ListView) findViewById(R.id.lv_debtor_detail);
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        details.add(new DebtorDetail("Điện thoại", (debtor.getmPhone())));
        details.add(new DebtorDetail("Địa chỉ", debtor.getmAddress()));
        details.add(new DebtorDetail("Lãi suất (%/năm)", String.valueOf(debtor.getmInterest_rate())));
        details.add(new DebtorDetail("Tiền lãi hiện tại", String.valueOf(formatter.format((int) dbManager.calculateInterest(debtor)))));
        details.add(new DebtorDetail("Ngày vay", String.valueOf(debtor.getmInterest_date())));
        details.add(new DebtorDetail("Mô tả", debtor.getmDescription()));
        debtorDetailAdapter = new DebtorDetailAdapter(this, R.layout.personal_info_debtor_detail_item, details);

        tvName = (TextView) findViewById(R.id.debtor_name);
        tvDebt = (TextView) findViewById(R.id.debtor_debt);
        tvName.setText(debtor.getmName());
        tvDebt.setText(String.valueOf(formatter.format(debtor.getmDebt())));

        lvDebtorDetails.setAdapter(debtorDetailAdapter);

//        tvAddress.setText(debtor.getmAddress());
//        tvPhone.setText(debtor.getmPhone());
//        tvInterest_rate.setText(String.valueOf(debtor.getmInterest_rate()));
//        tvDate.setText(debtor.getmDate());
//        tvInterest.setText(String.valueOf(debtor.getmInterest()));
//        tvNote.setText(debtor.getmNote());
    }

    public void payInterest() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(debtorId);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String interest = String.valueOf(formatter.format((int) dbManager.calculateInterest(debtor)));

        if (dbManager.calculateInterest(debtor) == 0) {
            Toast.makeText(PersonalInfoActivity.this, "Chưa có tiền lãi để thanh toán", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Trừ tiền lãi");
            builder.setMessage("Tiền lãi hiện tại: " + interest +
                    "\nBạn có chắc chắn muốn xóa tiền lãi cho khoản nợ này?");

            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Debtor debtor = dbManager.getDebtorById(debtorId);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = simpleDateFormat.format(calendar.getTime());
                    debtor.setmInterest_date(date);
                    if (dbManager.UpdateDebtor(debtor))
                        Toast.makeText(PersonalInfoActivity.this, "Xóa lãi thành công!", Toast.LENGTH_LONG).show();
                    Initialize();
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
    }

    public void payDebtAndInterest() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(debtorId);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String loanAmount, interest;
        loanAmount = String.valueOf(formatter.format(debtor.getmDebt()));
        interest = String.valueOf(formatter.format((int) dbManager.calculateInterest(debtor)));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (dbManager.calculateInterest(debtor) == 0) {
            builder.setTitle("Hoàn tất khoản nợ");
            builder.setMessage("Tiền nợ gốc: " + loanAmount +
                    "\nBạn có chắc chắn muốn hoàn tất khoản nợ này?");
        } else {
            builder.setTitle("Hoàn tất khoản nợ");
            builder.setMessage("Tiền nợ gốc: " + loanAmount +
                    "\nTiền lãi hiện tại: " + interest +
                    "\nBạn có chắc chắn muốn hoàn tất khoản nợ này?");
        }
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Debtor debtor = dbManager.getDebtorById(debtorId);
                debtor.setmInterest_date("");
                debtor.setmDate("");
                debtor.setmInterest_rate(0);
                debtor.setmDebt(0);
                if (dbManager.UpdateDebtor(debtor))
                    Toast.makeText(PersonalInfoActivity.this, "Đã hoàn tất khoản nợ!", Toast.LENGTH_LONG).show();
                Initialize();
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

    private void addDebt() {
        dbManager = new DBManager(this);
        final Debtor debtor = dbManager.getDebtorById(debtorId);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final String date = simpleDateFormat.format(calendar.getTime());

        if (dbManager.calculateInterest(dbManager.getDebtorById(debtorId)) > 0) {
            Toast.makeText(PersonalInfoActivity.this, "Không thể thêm nợ khi tiền lãi chưa được thanh toán", Toast.LENGTH_LONG).show();
        } else {
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            View mView = layoutInflaterAndroid.inflate(R.layout.personal_info_input_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mView);

            final EditText edtAddDebt = (EditText) mView.findViewById(R.id.userInputDialog);
            edtAddDebt.requestFocus();

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
                            Initialize();
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

    private void deleteDebtor() {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(debtorId);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa thông tin");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + debtor.getmName() + " ra khỏi danh sách?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dbManager.deleteDebtor(debtorId))
                    Toast.makeText(PersonalInfoActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(PersonalInfoActivity.this, "Xảy ra lỗi khi xóa", Toast.LENGTH_LONG).show();
                }
                dbManager.close();

                switch (sourceActivity) {
                    case LOAN_LIST:
                        Intent finishOldLoanList, goToList;

                        finishOldLoanList = new Intent("finish_loan_list_activity");
                        sendBroadcast(finishOldLoanList);

                        goToList = new Intent(PersonalInfoActivity.this, ListActivity.class);
                        startActivity(goToList);
                        finish();
                        break;
                    case MAIN:
                        finish();
                        break;
                }
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

    public void callDebtor() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dbManager.getDebtorById(debtorId).getmPhone()));
        startActivity(callIntent);
    }

    public void sendSMS() {
        Intent sendSMS = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + dbManager.getDebtorById(debtorId).getmPhone()));
        startActivity(sendSMS);
    }

    @Override
    public void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }
}