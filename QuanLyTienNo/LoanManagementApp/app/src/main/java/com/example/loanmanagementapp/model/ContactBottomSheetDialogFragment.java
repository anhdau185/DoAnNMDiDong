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

public class ContactBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private TextView makeACall, sendSmsMessage;
    private PersonalInfoActivity debtor;

    public static ContactBottomSheetDialogFragment newInstance(PersonalInfoActivity debtor) {
        ContactBottomSheetDialogFragment contactBottomSheetDialogFragment = new ContactBottomSheetDialogFragment();
        contactBottomSheetDialogFragment.debtor = debtor;
        return contactBottomSheetDialogFragment;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.personal_info_contact_debtor, container, false);

        // attach the listener here
        makeACall = (TextView) v.findViewById(R.id.tv_make_a_call);
        makeACall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                debtor.callDebtor();
            }
        });

        sendSmsMessage = (TextView) v.findViewById(R.id.tv_send_sms_message);
        sendSmsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                debtor.sendSMS();
            }
        });
        return v;
    }
}