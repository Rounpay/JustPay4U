package com.solution.app.justpay4u.Networking.Adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.solution.app.justpay4u.Api.Networking.Response.ListUserPoolDetail;
import com.solution.app.justpay4u.R;
import java.util.List;

public class ListUserPoolDetailItemAdapter extends RecyclerView.Adapter<ListUserPoolDetailItemAdapter.MyViewHolder> {
    private List<ListUserPoolDetail> transactionsList;
    private Activity mContext;

    public ListUserPoolDetailItemAdapter(List<ListUserPoolDetail> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upline_data_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ListUserPoolDetail operator = transactionsList.get(position);
        holder.status.setText(operator.getStatus()+"");
        holder.userID.setText("User Id :"+operator.getUserID()+"");
        holder.userName.setText(operator.getUserName()+"");
        holder.mobileNo.setText(operator.getMobileNo()+"");
        holder.emailID.setText(operator.getEmailID()+"");
        holder.signupDate.setText(operator.getSignupDate()+"");
        holder.lastRechargeDate.setText(operator.getLastRechargeDate()+"");
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView status, userName, mobileNo, emailID, signupDate, lastRechargeDate,userID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            userName = itemView.findViewById(R.id.userName);
            mobileNo = itemView.findViewById(R.id.mobileNo);
            emailID = itemView.findViewById(R.id.emailID);
            signupDate = itemView.findViewById(R.id.signupDate);
            lastRechargeDate = itemView.findViewById(R.id.lastRechargeDate);
            userID = itemView.findViewById(R.id.userID);
        }
    }
}
