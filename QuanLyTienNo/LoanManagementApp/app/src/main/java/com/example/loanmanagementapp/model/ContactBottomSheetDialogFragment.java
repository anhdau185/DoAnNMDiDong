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
import com.example.loanmanagementapp.database.DBManager;

import java.util.Objects;

public class ContactBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private TextView makeACall, sendSmsMessage;

    public static ContactBottomSheetDialogFragment newInstance() {
        return new ContactBottomSheetDialogFragment();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.personal_info_contact_debtor, container, false);
        makeACall = (TextView) v.findViewById(R.id.tv_make_a_call);
        sendSmsMessage = (TextView) v.findViewById(R.id.tv_send_sms_message);
        final PersonalInfoActivity currentActivity = (PersonalInfoActivity) getActivity();
        assert currentActivity != null;

        DBManager dbManager = new DBManager(currentActivity);
        String phoneNumber = (dbManager.getDebtorById(currentActivity.getDebtorId())).getmPhone();
        if (!phoneNumber.equals("")) {
            if (phoneNumber.length() == 10 && String.valueOf(phoneNumber.charAt(0)).equals("0")) {
                phoneNumber = "+84" + phoneNumber.substring(1);
            }
            makeACall.setText(String.valueOf("Gọi điện thoại (" + phoneNumber + ")"));
            sendSmsMessage.setText(String.valueOf("Nhắn tin SMS (" + phoneNumber + ")"));
        }

        // attach the listener here
        makeACall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                currentActivity.callDebtor();
            }
        });

        sendSmsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                currentActivity.sendSMS();
            }
        });
        return v;
    }
}