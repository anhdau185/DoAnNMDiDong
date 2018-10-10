package com.example.loanmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loanmanagementapp.adapter.CustomAdapter;
import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;

import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class ListActivity extends AppCompatActivity{
    private List<Debtor> listDebtor;
    private DBManager dbManager;
    private CustomAdapter customAdapter;
    private ListView lvDebtor;
    public static int Id = -1;
    private FloatingActionButton mfbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Initialize();
        listDebtor = dbManager.getAllDebtorDebtASC();
        SetAdapter();

        mfbtn = findViewById(R.id.list_add_btn);
        mfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddNewLoan = new Intent(ListActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
            }
        });

        lvDebtor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Debtor debtor = listDebtor.get(position);
                Id = debtor.getmID();
                Intent goToPersonalInfo = new Intent(ListActivity.this, PersonalInfoActivity.class);
                startActivity(goToPersonalInfo);
            }
        });
        lvDebtor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Debtor debtor = listDebtor.get(position);
                Id = debtor.getmID();
                return false;
            }
        });
        registerForContextMenu(lvDebtor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit_info:
                Intent goToAddNewLoan = new Intent(ListActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
//                Log.d("Update:", "position " + Id);
                break;
            case R.id.action_delete:
//                Log.d("Delete: ", Id+"");
                if(dbManager.deleteDebtor(Id)) {
                    Toast.makeText(ListActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                    listDebtor.clear();
                    listDebtor.addAll(dbManager.getAllDebtorNameASC());
                    customAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.action_call:
                break;
            case R.id.action_sms:
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void SetAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_debtor, listDebtor);
            lvDebtor.setAdapter(customAdapter);
        }
        else{
            lvDebtor.setSelection(customAdapter.getCount()-1);
        }
    }

    private void Initialize(){
        dbManager = new DBManager(this);
        lvDebtor = findViewById(R.id.list_lv);
    }
}
