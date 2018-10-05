package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.loanmanagementapp.model.ListItem;

import java.util.List;

public class IncomingPayersActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    Toolbar mToolbar;
    List<ListItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_payers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_incoming_payers);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.incomingPayers_recyclerView);
    }
}