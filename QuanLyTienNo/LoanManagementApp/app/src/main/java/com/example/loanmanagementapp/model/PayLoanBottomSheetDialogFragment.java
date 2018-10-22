package com.example.loanmanagementapp.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loanmanagementapp.PersonalInfoActivity;
import com.example.loanmanagementapp.R;

public class PayLoanBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private TextView payInterestOnly, payLoanAndInterest;

    public static PayLoanBottomSheetDialogFragment newInstance() {
        return new PayLoanBottomSheetDialogFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.personal_info_pay_loan, container, false);

        // attach the listener here
        payInterestOnly = (TextView) v.findViewById(R.id.tv_pay_interest_only);
        payInterestOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PersonalInfoActivity currentActivity = (PersonalInfoActivity) getActivity();
                if (currentActivity != null) {
                    currentActivity.payInterest();
                }
            }
        });

        payLoanAndInterest = (TextView) v.findViewById(R.id.tv_pay_loan_and_interest);
        payLoanAndInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PersonalInfoActivity currentActivity = (PersonalInfoActivity) getActivity();
                if (currentActivity != null) {
                    currentActivity.payDebtAndInterest();
                }
            }
        });
        return v;
    }
}