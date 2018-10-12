package com.example.loanmanagementapp.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.*;
import android.widget.TextView;

import com.example.loanmanagementapp.R;

public class SortBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private TextView ascAlphabeticalSort, descAlphabeticalSort, ascSortByLoanAmount, descSortByLoanAmount;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.list_sort_bottom_sheet, container, false);

        // attach the listener here
        ascAlphabeticalSort = (TextView) v.findViewById(R.id.asc_alphabetical_sort);
        ascAlphabeticalSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        descAlphabeticalSort = (TextView) v.findViewById(R.id.desc_alphabetical_sort);
        descAlphabeticalSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ascSortByLoanAmount = (TextView) v.findViewById(R.id.asc_sort_by_loan_amount);
        ascSortByLoanAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        descSortByLoanAmount = (TextView) v.findViewById(R.id.desc_sort_by_loan_amount);
        descSortByLoanAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
