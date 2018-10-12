package com.example.loanmanagementapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.loanmanagementapp.adapter.CustomAdapter;
import com.example.loanmanagementapp.database.DBManager;
import com.example.loanmanagementapp.model.Debtor;
import com.example.loanmanagementapp.model.SortBottomSheetDialogFragment;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Debtor> listDebtor;
    private DBManager dbManager;
    private CustomAdapter customAdapter;
    private ListView lvDebtor;
    private SearchView searchView;
    public static int Id = -1;
    private FloatingActionButton mfbtn;
    private int[] listID;
    private boolean isSearching = false;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        android.support.v7.widget.Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Initialize();
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
                if(!isSearching) {
                    Debtor debtor = listDebtor.get(position);
                    Id = debtor.getmID();
//                    Log.d("Not searching ID:",String.valueOf(Id));
                }
                else{
                    Id = listID[position];
//                    Log.d("searching ID:",String.valueOf(Id));
                }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapter();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                lvDebtor.setAdapter(customAdapter);
                isSearching = false;
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter("");
                    lvDebtor.clearTextFilter();
                }
                else
                {
                    lvDebtor.setEnabled(true);
                    adapter.getFilter().filter(newText.toString());
                    int lengthID = dbManager.getIdByName(newText.toString()).length;
                    listID = new int [lengthID];
                    System.arraycopy(dbManager.getIdByName(newText.toString()), 0, listID, 0, lengthID);
                }
                return true;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                SortBottomSheetDialogFragment sheet = new SortBottomSheetDialogFragment();
                sheet.show(getSupportFragmentManager(), "list_sort_action");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_info:
                Intent goToAddNewLoan = new Intent(ListActivity.this, AddNewLoanActivity.class);
                startActivity(goToAddNewLoan);
//                Log.d("Update:", "position " + Id);
                break;
            case R.id.action_delete:
//                Log.d("Delete: ", Id+"");
                deleteDebtor(Id);
                    listDebtor.clear();
                    listDebtor.addAll(dbManager.getAllDebtorNameASC());
                    customAdapter.notifyDataSetChanged();

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
        switch (SortBottomSheetDialogFragment.sortStyle) {
            case -1:
                listDebtor = dbManager.getAllDebtorNameASC();
                break;
            case 1:
                listDebtor = dbManager.getAllDebtorNameASC();
                break;
            case 2:
                listDebtor = dbManager.getAllDebtorNameDESC();
                break;
            case 3:
                listDebtor = dbManager.getAllDebtorDebtASC();
                break;
            case 4:
                listDebtor = dbManager.getAllDebtorDebtDESC();
                break;
        }
        SortBottomSheetDialogFragment.sortStyle = -1;
    }
    private void searchAdapter()
    {
        List<Debtor> searchList = dbManager.getAllDebtorNameASC();
        String [] NAME  = new String[searchList.size()];
        for(int i=0; i<searchList.size();i++)
        {
            Debtor debtor = searchList.get(i);
            Log.d("Debtor: ", debtor.getmName());
            NAME[i] = debtor.getmName();
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, NAME);
        lvDebtor.setAdapter(adapter);
        isSearching = true;
    }
    private void deleteDebtor(final int ID)
    {
        dbManager = new DBManager(this);
        Debtor debtor = dbManager.getDebtorById(ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa thông tin");
        builder.setMessage("Bạn có chắc muốn xóa " +debtor.getmName() + " ra khỏi danh sách?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dbManager.deleteDebtor(ID))
                    Toast.makeText(ListActivity.this, "Xóa thành công!", Toast.LENGTH_LONG).show();
                dbManager.close();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}