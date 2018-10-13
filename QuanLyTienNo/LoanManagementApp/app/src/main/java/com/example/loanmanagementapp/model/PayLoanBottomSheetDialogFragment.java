package com.example.loanmanagementapp.model;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.*;
import android.widget.TextView;

import com.example.loanmanagementapp.PersonalInfoActivity;
import com.example.loanmanagementapp.R;

public class PayLoanBottomSheetDialogFragment extends BottomSheetDialogFragment {
    TextView payInterestOnly, payLoanAndInterest;
    PersonalInfoActivity person;

    public void setPerson(PersonalInfoActivity p) {
        this.person = p;
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.personal_info_pay_loan, container, false);

        // attach the listener here
        payInterestOnly = (TextView) v.findViewById(R.id.tv_pay_interest_only);
        payInterestOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                person.payInterest();
            }
        });

        payLoanAndInterest = (TextView) v.findViewById(R.id.tv_pay_loan_and_interest);
        payLoanAndInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                person.payDebtAndInterest();
            }
        });
        return v;
    }

    public void onDismiss(DialogInterface dialog) {

    }
}
