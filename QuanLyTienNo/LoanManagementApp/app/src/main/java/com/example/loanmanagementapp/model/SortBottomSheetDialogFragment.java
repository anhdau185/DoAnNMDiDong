package com.example.loanmanagementapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.*;
import android.widget.TextView;

import com.example.loanmanagementapp.ListActivity;
import com.example.loanmanagementapp.R;

import java.util.Objects;

public class SortBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private TextView ascAlphabeticalSort, descAlphabeticalSort, ascSortByLoanAmount, descSortByLoanAmount;

    public static int sortStyle = -1;

    public static SortBottomSheetDialogFragment newInstance() {
        return new SortBottomSheetDialogFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.list_sort_bottom_sheet, container, false);

        // attach the listener here
        ascAlphabeticalSort = (TextView) v.findViewById(R.id.asc_alphabetical_sort);
        ascAlphabeticalSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                sortStyle = 1;
                startActivity(new Intent(getActivity(), ListActivity.class));
                Objects.requireNonNull(getActivity()).finish(); // use this instead of getActivity().finish();
            }
        });

        descAlphabeticalSort = (TextView) v.findViewById(R.id.desc_alphabetical_sort);
        descAlphabeticalSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                sortStyle = 2;
                startActivity(new Intent(getActivity(), ListActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        ascSortByLoanAmount = (TextView) v.findViewById(R.id.asc_sort_by_loan_amount);
        ascSortByLoanAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                sortStyle = 3;
                startActivity(new Intent(getActivity(), ListActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        descSortByLoanAmount = (TextView) v.findViewById(R.id.desc_sort_by_loan_amount);
        descSortByLoanAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                sortStyle = 4;
                startActivity(new Intent(getActivity(), ListActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return v;
    }
}