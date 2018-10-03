package com.example.loanmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.loanmanagementapp.adapter.CustomAdapter;
import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.util.List;

public class ListActivity extends AppCompatActivity{
    private List<Debtor> listDebtor;
    private DBManager dbManager;
    private CustomAdapter customAdapter;
    private ListView lvDebtor;
    public static int Id;
    private FloatingActionButton mfbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mfbtn = (FloatingActionButton) findViewById(R.id.list_add_btn);
        mfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddNewLoan = new Intent(ListActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
            }
        });
//        lvDebtor = (ListView) findViewById(R.id.lv_Debtor);
//        listDebtor = dbManager.getAllDebtor();
//        SetAdapter();
//
//        lvDebtor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Debtor debtor = listDebtor.get(position);
//                Id = debtor.getmID();
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }
    private void SetAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_debtor, listDebtor);
            lvDebtor.setAdapter(customAdapter);
        }
    }
}
