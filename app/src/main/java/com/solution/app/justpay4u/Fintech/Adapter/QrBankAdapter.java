package com.solution.app.justpay4u.Fintech.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.Activities.QrCodeActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.Bank;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;

import java.util.List;

public class QrBankAdapter extends RecyclerView.Adapter<QrBankAdapter.MyViewHolder> {

    private final RequestOptions requestOptions;
    int parentId;
    private List<Bank> operatorList;
    private Context mContext;

    public QrBankAdapter(List<Bank> operatorList, Context mContext, int parentId, RequestOptions requestOptions) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.parentId = parentId;
        this.requestOptions = requestOptions;
    }

    @Override
    public QrBankAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_qr_bank_list, parent, false);

        return new QrBankAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QrBankAdapter.MyViewHolder holder, int position) {
        final Bank operator = operatorList.get(position);
        holder.bankName.setText(operator.getBankName());
        holder.accountHolder.setText(operator.getAccountHolder());
        holder.accountNumber.setText(operator.getAccountNo());
        holder.ifscCode.setText(operator.getIfscCode());
        holder.branchName.setText(operator.getBranchName());

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + operator.getLogo().replaceAll(" ", "%20"))
                .apply(requestOptions)
                .into(holder.bankLogo);


        holder.scanPay.setOnClickListener(v -> {
            if (operator != null && operator.getIsqrenable()) {
                mContext.startActivity(new Intent(mContext, QrCodeActivity.class)
                        .putExtra("SelectedBank", operator)
                        .putExtra("FROMINTENT", 0)
                        .putExtra("ParentId", parentId)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            } else {
                Toast.makeText(mContext, "Select Bank does't support QR Code.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        View scanPay;
        private AppCompatImageView bankLogo;
        private AppCompatTextView bankName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView accountHolder;
        private AppCompatTextView branchName;
        private AppCompatTextView ifscCode;

        public MyViewHolder(View view) {
            super(view);
            bankLogo = view.findViewById(R.id.bankLogo);
            bankName = view.findViewById(R.id.bankName);
            accountNumber = view.findViewById(R.id.accountNumber);
            accountHolder = view.findViewById(R.id.accountHolder);
            branchName = view.findViewById(R.id.branchName);
            ifscCode = view.findViewById(R.id.ifscCode);
            scanPay = view.findViewById(R.id.scanPayView);
        }
    }

}



/*
public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    private ArrayList<BankListObject> operatorList;
    private Context mContext;
    int resourceId = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            opImage =  view.findViewById(R.id.opImage);

        }
    }

    public BankListScreenAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public BankListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bank_list, parent, false);

        return new BankListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankListScreenAdapter.MyViewHolder holder, int position) {
        final BankListObject operator = operatorList.get(position);
        holder.title.setText(operator.getBankName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BankListScreen) mContext).ItemClick(operator.getBankId(), operator.getBankName(), operator.getAccVeri(), operator.getShortCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

}
*/
