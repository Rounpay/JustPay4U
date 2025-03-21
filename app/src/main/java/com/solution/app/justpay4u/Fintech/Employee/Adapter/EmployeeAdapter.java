package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmployees;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {


    private List<GetEmployees> operatorList, transactionsList;
    private Activity mContext;

    public EmployeeAdapter(List<GetEmployees> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;

        this.operatorList = new ArrayList<>();
        this.operatorList.addAll(transactionsList);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_employee, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetEmployees operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName() + " [" + operator.getEmpCode() + "]");


        holder.roleTv.setText(operator.getEmpRole() + "");
        holder.mobileTv.setText(operator.getMobileNo() + "");
        holder.emailIdTv.setText(operator.getEmailID() + "");
        holder.aadharTv.setText(operator.getAadhar() + "");
        holder.panTv.setText(operator.getPan() + "");
        holder.referralByTv.setText(operator.getReferralBy() + "");
        holder.addressTv.setText(operator.getAddress() + "");

        if (operator.getPan().isEmpty()) {
            holder.panView.setVisibility(View.GONE);
        } else {
            holder.panView.setVisibility(View.VISIBLE);
        }

        if (operator.getAadhar().isEmpty()) {
            holder.aadharView.setVisibility(View.GONE);
        } else {
            holder.aadharView.setVisibility(View.VISIBLE);
        }

        if (operator.getAddress().isEmpty()) {
            holder.addressView.setVisibility(View.GONE);
        } else {
            holder.addressView.setVisibility(View.VISIBLE);
        }

        holder.callView.setOnClickListener(v -> {
            try {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + operator.getMobileNo() + ""));
                mContext.startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + operator.getMobileNo() + ""));
                mContext.startActivity(dialIntent);
            }
        });
        holder.emailIconView.setOnClickListener(v -> {
            try {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("mailto:" + operator.getEmailID() + ""));
                mContext.startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + operator.getEmailID() + ""));
                mContext.startActivity(dialIntent);
            }
        });

        holder.whatsapView.setOnClickListener(v -> Utility.INSTANCE.openWhatsapp(mContext, operator.getMobileNo() + ""));
    }

    public void filter(String st) {
        String charText = st.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(operatorList);
        } else {
            for (GetEmployees wp : operatorList) {
                if ((wp.getName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getReferralBy() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobileNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getEmailID() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getEmpRole() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView nameTv;
        private LinearLayout roleView;
        private TextView roleTv;
        private LinearLayout emailIdView;
        private TextView emailIdTv;
        private LinearLayout mobileView;
        private TextView mobileTv;
        private LinearLayout aadharView;
        private TextView aadharTv;
        private LinearLayout panView;
        private TextView panTv;
        private LinearLayout addressView;
        private TextView addressTv;
        private LinearLayout referralByView;
        private TextView referralByTv;
        private LinearLayout joinByView;
        private TextView joinByTv;
        private View callView, whatsapView, emailIconView;

        public MyViewHolder(View view) {
            super(view);

            nameTv = (TextView) view.findViewById(R.id.nameTv);
            roleView = (LinearLayout) view.findViewById(R.id.roleView);
            roleTv = (TextView) view.findViewById(R.id.roleTv);
            emailIdView = (LinearLayout) view.findViewById(R.id.emailIdView);
            emailIdTv = (TextView) view.findViewById(R.id.emailIdTv);
            mobileView = (LinearLayout) view.findViewById(R.id.mobileView);
            mobileTv = (TextView) view.findViewById(R.id.mobileTv);
            aadharView = (LinearLayout) view.findViewById(R.id.aadharView);
            aadharTv = (TextView) view.findViewById(R.id.aadharTv);
            panView = (LinearLayout) view.findViewById(R.id.panView);
            panTv = (TextView) view.findViewById(R.id.panTv);
            addressView = (LinearLayout) view.findViewById(R.id.addressView);
            addressTv = (TextView) view.findViewById(R.id.addressTv);
            referralByView = (LinearLayout) view.findViewById(R.id.referralByView);
            referralByTv = (TextView) view.findViewById(R.id.referralByTv);
            callView = view.findViewById(R.id.callView);
            whatsapView = view.findViewById(R.id.whatsapView);
            emailIconView = view.findViewById(R.id.emailIconView);
        }
    }
}


