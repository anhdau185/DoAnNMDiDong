package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.example.loanmanagementapp.adapter.RecyclerViewAdapter;
import com.example.loanmanagementapp.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class IncomingPayersActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;
    List<ListItem> items;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_payers);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_incoming_payers);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.incomingPayers_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        // Add items to RecyclerView here
        items.add(new ListItem("Debtor 1", "10,000"));
        items.add(new ListItem("Debtor 2", "250,000"));
        items.add(new ListItem("Debtor 3", "50,000"));
        items.add(new ListItem("Debtor 4", "180,000"));
        items.add(new ListItem("Debtor 5", "8,000"));
        items.add(new ListItem("Debtor 6", "70,000"));
        items.add(new ListItem("Debtor 7", "500,000"));
        items.add(new ListItem("Debtor 8", "700,000"));
        items.add(new ListItem("Debtor 9", "1,500,000"));

        adapter = new RecyclerViewAdapter(items, this);
        mRecyclerView.setAdapter(adapter);
    }
}