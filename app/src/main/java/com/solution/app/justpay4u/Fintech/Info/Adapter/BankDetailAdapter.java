package com.solution.app.justpay4u.Fintech.Info.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.Bank;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BankDetailAdapter extends RecyclerView.Adapter<BankDetailAdapter.MyViewHolder> {

    CustomAlertDialog mCustomAlertDialog;
    RequestOptions requestOptions;
    private List<Bank> list, filterList;
    private Activity mContext;

    public BankDetailAdapter(List<Bank> operatorList, Activity mContext) {
        this.list = operatorList;
        this.filterList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public BankDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_qr_bank_list, parent, false);

        return new BankDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankDetailAdapter.MyViewHolder holder, int position) {
        final Bank operator = filterList.get(position);
        holder.bankName.setText(operator.getBankName());
        holder.accountHolder.setText(operator.getAccountHolder());
        holder.accountNumber.setText(operator.getAccountNo());
        holder.ifscCode.setText(operator.getIfscCode());
        holder.branchName.setText(operator.getBranchName());

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + operator.getLogo().replaceAll(" ", "%20"))
                .apply(requestOptions)
                .into(holder.bankLogo);


        holder.shareView.setOnClickListener(v -> share(operator));

        holder.msgView.setOnClickListener(v -> sendReport(operator.getId() + ""));

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    void sendReport(String reportId) {
        if (mCustomAlertDialog != null) {
            mCustomAlertDialog.sendReportDialog(2, "", new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {

                }
            });
        }
    }

    public void filter(String charText) {

        ArrayList<Bank> mLocfilterList = new ArrayList<>();
        if (charText.length() == 0) {
            mLocfilterList.addAll(list);
        } else {
            for (Bank wp : list) {
                if (wp.getBankName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getAccountNo().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getIfscCode().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getBranchName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getAccountHolder().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mLocfilterList.add(wp);
                }
            }
        }

        filterList = mLocfilterList;
        notifyDataSetChanged();
    }

    void share(Bank operator) {
        String shareContent = "Bank Name : " + operator.getBankName() + "\n" +
                "A/C No : " + operator.getAccountNo() + "\n" +
                "Account Holder : " + operator.getAccountHolder() + "\n" +
                "Branch Name : " + operator.getBranchName() + "\n" +
                "IFSC Code : " + operator.getIfscCode() + "\n";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        mContext.startActivity(shareIntent);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView bankLogo;
        private AppCompatTextView bankName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView accountHolder;
        private AppCompatTextView branchName;
        private AppCompatTextView ifscCode;
        private View shareView, msgView;


        public MyViewHolder(View view) {
            super(view);
            bankLogo = view.findViewById(R.id.bankLogo);
            bankName = view.findViewById(R.id.bankName);
            accountNumber = view.findViewById(R.id.accountNumber);
            accountHolder = view.findViewById(R.id.accountHolder);
            branchName = view.findViewById(R.id.branchName);
            ifscCode = view.findViewById(R.id.ifscCode);
            shareView = view.findViewById(R.id.shareView);
            msgView = view.findViewById(R.id.msgView);
            view.findViewById(R.id.scanPayView).setVisibility(View.GONE);
            view.findViewById(R.id.btnView).setVisibility(View.VISIBLE);
        }
    }
}
