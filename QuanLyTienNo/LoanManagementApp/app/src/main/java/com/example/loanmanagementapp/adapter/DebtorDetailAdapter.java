package com.example.loanmanagementapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loanmanagementapp.R;
import com.example.loanmanagementapp.model.Debtor;
import com.example.loanmanagementapp.model.DebtorDetail;

import java.text.DecimalFormat;
import java.util.List;

public class DebtorDetailAdapter extends ArrayAdapter<DebtorDetail> {
    private Context context;
    private int resource;
    private List<DebtorDetail> details;

    public DebtorDetailAdapter(@NonNull Context context, int resource, @NonNull List<DebtorDetail> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.details = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.personal_info_debtor_detail_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.heading = (TextView) convertView.findViewById(R.id.tv_detail_heading);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_detail_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DebtorDetail detail = details.get(position);
        if (detail.getHeading().equals("Tiền lãi hiện tại") || detail.getHeading().equals("Lãi suất (%/năm)")) {
            viewHolder.content.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            viewHolder.content.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        viewHolder.heading.setText(detail.getHeading());
        viewHolder.content.setText(detail.getContent());
        return convertView;
    }

    public class ViewHolder {
        private TextView heading;
        private TextView content;
    }
}