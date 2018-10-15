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
    TextView makeACall, sendSmsMessage;
    PersonalInfoActivity person;

    public void setPerson (PersonalInfoActivity p){
        this.person = p;
    }

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
                person.callDebtor();
                // call the debtor here

            }
        });

        sendSmsMessage = (TextView) v.findViewById(R.id.tv_send_sms_message);
        sendSmsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                person.sendSMS();
                // send an SMS message to the debtor here

            }
        });

        return v;
    }
}