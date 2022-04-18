package com.example.fodashboard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fodashboard.R;
import com.example.fodashboard.model.TopPerformerItem;
import com.example.fodashboard.pojo.PerforamnceSummary;

import java.util.ArrayList;
import java.util.List;


public class TopPerformerListAdapter extends RecyclerView.Adapter<TopPerformerListAdapter.MyViewHolder>{

    Activity context;
    List<TopPerformerItem> topPerformerData;
    PerforamnceSummary summary;

    Dialog d;

    public TopPerformerListAdapter (Activity context, List<TopPerformerItem> topPerformerData) {
        this.context = context;
        this.topPerformerData = topPerformerData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_top_permormer, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView userProfileImg;
        public TextView txtName,txtType,txtCust,txtAmount;

        public MyViewHolder(View view) {
            super(view);

            userProfileImg = view.findViewById(R.id.userProfileImg);
            txtName = view.findViewById(R.id.txtName);
            txtType = view.findViewById(R.id.txtType);
            txtCust = view.findViewById(R.id.txtCust);
            txtAmount = view.findViewById(R.id.txtAmount);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {



       // holder.userProfileImg.setImageResource(topPerformerItem.getImage());
        ArrayList<PerforamnceSummary> summary = new ArrayList<PerforamnceSummary>();

        holder.txtName.setText(summary.get(position).getFieldOfficerName());
        holder.txtType.setText(summary.get(position).getLocaleName());
        holder.txtCust.setText(summary.get(position).getCount());
        holder.txtAmount.setText(summary.get(position).getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return topPerformerData.size();
    }
}
