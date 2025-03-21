package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.solution.app.justpay4u.Api.Networking.Response.ListUserPoolDetail;
import com.solution.app.justpay4u.Networking.Activity.GetPoolUpLineLevelDetails;
import com.solution.app.justpay4u.R;
import java.util.List;

public class ListUserPoolDetailAdapter extends RecyclerView.Adapter<ListUserPoolDetailAdapter.MyViewHolder> {
    private List<ListUserPoolDetail> transactionsList;
    private Activity mContext;

    public ListUserPoolDetailAdapter(List<ListUserPoolDetail> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upline_data, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ListUserPoolDetail operator = transactionsList.get(position);
        holder.userId.setText(operator.getUserID()+"");
        holder.levelNo.setText(operator.getLevelNo()+"");
        holder.totalCount.setText(operator.getTotalCount()+"");
        holder.activeCount.setText(operator.getActiveCount()+"");
        holder.deactivate.setText(operator.getDeactiveCount()+"");
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent=new Intent(mContext, GetPoolUpLineLevelDetails.class);
            intent.putExtra("LevelNo",operator.getLevelNo());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
       private AppCompatTextView userId,totalCount,levelNo,activeCount,deactivate;
       private  LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userId=itemView.findViewById(R.id.userId);
            totalCount=itemView.findViewById(R.id.totalCount);
            levelNo=itemView.findViewById(R.id.levelNo);
            activeCount=itemView.findViewById(R.id.activeCount);
            deactivate=itemView.findViewById(R.id.deactivate);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}