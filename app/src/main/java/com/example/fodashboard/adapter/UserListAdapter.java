package com.example.fodashboard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fodashboard.R;
import com.example.fodashboard.model.TopPerformerItem;
import com.example.fodashboard.model.UserItem;
import com.example.fodashboard.pojo.VerificationAuditProspects;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder>{

    Activity context;
    ArrayList<VerificationAuditProspects> userData;
    String title;

    Dialog d;

    public UserListAdapter (Activity context,  ArrayList<VerificationAuditProspects> userData,String name) {
        this.context = context;
        this.userData = userData;
        this.title = title;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_audit, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView userProfileImg;
        public TextView txtUserName,txtMobileNumber,txtAmount,txtStatus;
        public LinearLayout statusLinear,amountLayout;

        public MyViewHolder(View view) {
            super(view);

            userProfileImg = view.findViewById(R.id.userProfileImg);
            txtUserName = view.findViewById(R.id.txtUserName);
            txtMobileNumber = view.findViewById(R.id.txtMobileNumber);
            txtAmount = view.findViewById(R.id.txtAmount);
            txtStatus = view.findViewById(R.id.txtStatus);
            statusLinear = view.findViewById(R.id.statusLinear);
            amountLayout = view.findViewById(R.id.amountLayout);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final VerificationAuditProspects userItem = userData.get(position);

       // holder.userProfileImg.setImageResource(userItem.getImage());

        holder.txtUserName.setText(userItem.getName());
        holder.txtMobileNumber.setText(userItem.getMobile());
       // holder.txtAmount.setText(userItem.getAmount());
       // holder.txtStatus.setText(userItem.getStatus());
        holder.statusLinear.setVisibility(View.GONE);
        holder.amountLayout.setVisibility(View.GONE);



       /* if (userItem.getStatus().equalsIgnoreCase("NEW")) {
            holder.statusLinear.setBackgroundColor(Color.parseColor("#fd4558"));
            holder.txtStatus.setTextColor(Color.parseColor("#f4f4f4"));
        } else if (userItem.getStatus().equalsIgnoreCase("SMA")) {
            holder.statusLinear.setBackgroundColor(Color.parseColor("#ff949f"));
            holder.txtStatus.setTextColor(Color.parseColor("#ffffff"));
        } else if (userItem.getStatus().equalsIgnoreCase("OTR")) {
            holder.statusLinear.setBackgroundColor(Color.parseColor("#ffe8ea"));
            holder.txtStatus.setTextColor(Color.parseColor("#8c0d19"));
        }*/
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }
}
