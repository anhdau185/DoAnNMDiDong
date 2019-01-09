package com.example.loanmanagementapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loanmanagementapp.R;
import com.example.loanmanagementapp.model.Help;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {
    private Context context;
    private List<Help> helpList;

    private View.OnClickListener mOnClickListener;
    private View.OnLongClickListener mOnLongClickListener;

    public HelpAdapter(Context context, List<Help> objects) {
        this.context = context;
        this.helpList = objects;
    }

    @NonNull
    @Override
    public HelpAdapter.HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.help_item, parent, false);
        return new HelpViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.HelpViewHolder holder, int position) {
        Help currentHelp = helpList.get(position);
        holder.help = currentHelp;
        holder.tvHelpEntry.setText(currentHelp.getTitle());
    }

    @Override
    public int getItemCount() {
        if (helpList != null)
            return helpList.size();
        return 0;
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        this.mOnClickListener = clickListener;
    }

    public void setItemLongClickListener(View.OnLongClickListener longClickListener) {
        this.mOnLongClickListener = longClickListener;
    }

    public class HelpViewHolder extends RecyclerView.ViewHolder {
        Help help;
        TextView tvHelpEntry;

        HelpViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnClickListener);
            itemView.setOnLongClickListener(mOnLongClickListener);
            tvHelpEntry = itemView.findViewById(R.id.tv_help_entry);
        }

        public Help getHelpObject() {
            return help;
        }
    }
}
