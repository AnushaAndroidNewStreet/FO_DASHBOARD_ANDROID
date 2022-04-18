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

public class CollectionTopPerformerListAdapter extends RecyclerView.Adapter<CollectionTopPerformerListAdapter.MyViewHolder>{

    Activity context;
    ArrayList<PerforamnceSummary> topPerformerData;

    Dialog d;

    public CollectionTopPerformerListAdapter (Activity context, ArrayList<PerforamnceSummary> topPerformerData) {
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

           // txtCust.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final PerforamnceSummary topPerformerItem = topPerformerData.get(position);

       // holder.userProfileImg.setImageResource(topPerformerItem.getImage());

        holder.txtName.setText(topPerformerItem.getFieldOfficerName());
        holder.txtType.setText(topPerformerItem.getLocaleName());

        holder.txtCust.setText(String.valueOf(topPerformerItem.getCount()));
        String amount = numDifferentiation(topPerformerItem.getAmount());
        holder.txtAmount.setText("$" +" "+amount);
    }

    @Override
    public int getItemCount() {
        return topPerformerData.size();
    }

   public String  numDifferentiation(Double amount) {
        String amount_in_words = "0";
        Double val = Math.abs(amount);
        if (val >= 10000000) {
            Double amt = val / 10000000;
            amount_in_words = amt + " "+"Cr";
            val = Double.valueOf((val / 10000000) + "Cr");
        } else if (val >= 100000) {
            Double amt = val/100000;
            amount_in_words = amt + " "+"Lac";

        }else{
            amount_in_words = String.valueOf(amount);

        }
        return amount_in_words;

    }
}
