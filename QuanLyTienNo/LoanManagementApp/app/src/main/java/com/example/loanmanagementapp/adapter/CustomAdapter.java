package com.example.loanmanagementapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loanmanagementapp.R;
import com.example.loanmanagementapp.model.Debtor;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Debtor> {
    private Context context;
    private int resource;
    private List<Debtor> listDebtor;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Debtor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listDebtor =objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_debtor, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tv_Name);
            viewHolder.tvDebt = (TextView)convertView.findViewById(R.id.tv_Debt);
            convertView.setTag(viewHolder);
        }else{

        }
        return super.getView(position, convertView, parent);
    }

    public class ViewHolder{
        private TextView tvName;
        private TextView tvDebt;
    }
}
