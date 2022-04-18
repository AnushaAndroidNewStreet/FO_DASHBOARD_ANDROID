package com.example.fodashboard.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fodashboard.R;
import com.example.fodashboard.fragment.DashboardFragment;
import com.example.fodashboard.model.BankItem;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SelectBankListAdapter extends RecyclerView.Adapter<SelectBankListAdapter.MyViewHolder>{

    Activity context;
    List<BankItem> bankData;

    public SelectBankListAdapter (Activity context, List<BankItem> bankData) {
        this.context = context;
        this.bankData = bankData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_item_select_bank, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView bankCardView;
        public ImageView tickImage,bankImage;
        public TextView txtBankName,txtProductName;

        public MyViewHolder(View view) {
            super(view);

            bankCardView = view.findViewById(R.id.bankCardView);
            tickImage = view.findViewById(R.id.tickImage);
            bankImage = view.findViewById(R.id.bankImage);
            txtBankName = view.findViewById(R.id.txtBankName);
            txtProductName = view.findViewById(R.id.txtProductName);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final BankItem bankItem = bankData.get(position);

        holder.bankImage.setImageResource(bankItem.getBankImage());
        holder.txtBankName.setText(bankItem.getBackName());
        holder.txtProductName.setText(bankItem.getProductName());

        if (DashboardFragment.selectedId.equalsIgnoreCase(bankItem.getId())) {
            holder.bankCardView.setStrokeWidth(2);
            holder.bankCardView.setStrokeColor(Color.parseColor("#C5171D"));
            holder.tickImage.setVisibility(View.VISIBLE);
        } else {
            holder.bankCardView.setStrokeWidth(0);
            holder.tickImage.setVisibility(View.INVISIBLE);
        }

//        holder.bankCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DashboardFragment.selectedId = bankItem.getId();
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return bankData.size();
    }
}
