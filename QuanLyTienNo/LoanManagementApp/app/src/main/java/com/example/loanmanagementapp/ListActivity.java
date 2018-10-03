package com.example.loanmanagementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
//        lvDebtor = (ListView) findViewById(R.id.lv_Debtor);
        listDebtor = dbManager.getAllDebtor();
        SetAdapter();

        lvDebtor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Debtor debtor = listDebtor.get(position);
                Id = debtor.getmID();
            }
        });
    }
    private void SetAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_debtor, listDebtor);
            lvDebtor.setAdapter(customAdapter);
        }
    }
}
