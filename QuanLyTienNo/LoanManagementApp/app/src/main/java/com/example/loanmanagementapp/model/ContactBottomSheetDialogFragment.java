package com.example.loanmanagementapp.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loanmanagementapp.R;

public class ContactBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.personal_info_contact_debtor, container, false);

        // attach the listener here
        return v;
    }
}