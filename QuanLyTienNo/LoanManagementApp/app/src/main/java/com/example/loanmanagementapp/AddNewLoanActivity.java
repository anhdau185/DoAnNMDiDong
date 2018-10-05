package com.example.loanmanagementapp;

import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
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
import java.util.StringTokenizer;

public class AddNewLoanActivity extends AppCompatActivity {
    private TextInputEditText edtName;
    private TextInputEditText edtPhone;
    private TextInputEditText edtAddress;
    private TextInputEditText edtDebt;
    private TextInputEditText edtInterestRate;
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

        edtDebt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int cursorPosition = edtDebt.getSelectionEnd();
                String originalStr = edtDebt.getText().toString();

                //To restrict only two digits after decimal place
                edtDebt.setFilters(new InputFilter[]{new MoneyValueFilter(2)});

                try {
                    edtDebt.removeTextChangedListener(this);
                    String value = edtDebt.getText().toString();

                    if (value != null && !value.equals("")) {
                        if (value.startsWith(".")) {
                            edtDebt.setText("0.");
                        }
                        if (value.startsWith("0") && !value.startsWith("0.")) {
                            edtDebt.setText("");
                        }
                        String str = edtDebt.getText().toString().replaceAll(",", "");
                        if (!value.equals(""))
                            edtDebt.setText(getDecimalFormattedString(str));

                        int diff = edtDebt.getText().toString().length() - originalStr.length();
                        edtDebt.setSelection(cursorPosition + diff);
                    }
                    edtDebt.addTextChangedListener(this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtDebt.addTextChangedListener(this);
                }
            }
        });
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Debtor debtor = createDebtor();
//                if (debtor != null) {
//                    dbManager.addDebtor(debtor);
//                }
//            }
//        });
        // String s = "fffeejjjjj";
        // String s1 = s.replaceAll("f", "j"); // s1 should be: "jjjeejjjjj"
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.add_new_loan_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_save:
                createDebtor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Initialize() {
        dbManager = new DBManager(this);
        edtName = (TextInputEditText) findViewById(R.id.add_loan_name);
        edtPhone = (TextInputEditText) findViewById(R.id.add_loan_phone);
        edtAddress = (TextInputEditText) findViewById(R.id.add_loan_address);
        edtDebt = (TextInputEditText) findViewById(R.id.add_loan_loan_amount);
        edtInterestRate = (TextInputEditText) findViewById(R.id.add_loan_interest_rate);
        edtDate = (TextInputEditText) findViewById(R.id.add_loan_date);
        edtDescription = (TextInputEditText) findViewById(R.id.add_loan_description);
    }

    private boolean createDebtor() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        Integer debt = Integer.valueOf(edtDebt.getText().toString());
        Double interest_rate = Double.valueOf(edtInterestRate.getText().toString());
        String date = edtDate.getText().toString();
        Double interest = Double.valueOf(edtInterest.getText().toString());
        String note = edtDescription.getText().toString();

        Debtor debtor = new Debtor(name, phone, address, debt, interest_rate, date, interest, note);
        return true;
    }

    private void EditById(int id) {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getById(ListActivity.Id);
        edtName.setText(debtor.getmName());
        edtDebt.setText(debtor.getmDebt());
        edtAddress.setText(debtor.getmAddress());
        edtPhone.setText(debtor.getmPhone());
        edtInterestRate.setText(String.valueOf(debtor.getmInterest_rate()));
        edtDate.setText(debtor.getmDate());
        edtInterest.setText(String.valueOf(debtor.getmInterest()));
        edtDescription.setText(debtor.getmNote());
    }

    public static String getDecimalFormattedString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt(-1 + str1.length()) == '.') {
                j--;
                str3 = ".";
            }
            for (int k = j; ; k--) {
                if (k < 0) {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3) {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }
        }
        return "";
    }

    class MoneyValueFilter extends DigitsKeyListener {
        private int digits;

        public MoneyValueFilter(int i) {
            super(false, true);
            digits = i;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            CharSequence out = super.filter(source, start, end, dest, dstart, dend);

            // if changed, replace the source
            if (out != null) {
                source = out;
                start = 0;
                end = out.length();
            }

            int len = end - start;

            // if deleting, source is empty
            // and deleting can't break anything
            if (len == 0) {
                return source;
            }

            int dlen = dest.length();

            // Find the position of the decimal .
            for (int i = 0; i < dstart; i++) {
                if (dest.charAt(i) == '.') {
                    // being here means, that a number has
                    // been inserted after the dot
                    // check if the amount of digits is right
                    return getDecimalFormattedString((dlen - (i + 1) + len > digits) ? "" : String.valueOf(new SpannableStringBuilder(source, start, end)));
                }
            }

            for (int i = start; i < end; ++i) {
                if (source.charAt(i) == '.') {
                    // being here means, dot has been inserted
                    // check if the amount of digits is right
                    if ((dlen - dend) + (end - (i + 1)) > digits)
                        return "";
                    else
                        break; // return new SpannableStringBuilder(source,
                    // start, end);
                }
            }

            // if the dot is after the inserted part,
            // nothing can break
            return getDecimalFormattedString(String.valueOf(new SpannableStringBuilder(source, start, end)));
        }
    }
}