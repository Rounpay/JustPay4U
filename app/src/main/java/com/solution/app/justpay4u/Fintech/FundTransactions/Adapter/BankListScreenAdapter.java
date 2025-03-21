package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSBankListActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosCollectionBankListActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.BankListScreenActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    int resourceId = 0;
    RequestOptions requestOptions;
    private ArrayList<BankListObject> operatorList;
    private Context mContext;

    public BankListScreenAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
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
        if (operator.getIfsc() != null) {
            holder.ifsc.setText(operator.getIfsc() + "");
            holder.ifsc.setVisibility(View.VISIBLE);
        } else {
            holder.ifsc.setVisibility(View.GONE);
        }

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + operator.getId() + ".png")
                .apply(requestOptions)
                .into(holder.banklogo);

        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof BankListScreenActivity) {
                ((BankListScreenActivity) mContext).ItemClick(operator);
            }
            if (mContext instanceof AEPSBankListActivity) {
                ((AEPSBankListActivity) mContext).ItemClick(operator);
            }
            if (mContext instanceof FosCollectionBankListActivity) {
                ((FosCollectionBankListActivity) mContext).ItemClick(operator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(ArrayList<BankListObject> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, ifsc;
        public ImageView opImage, banklogo;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            ifsc = view.findViewById(R.id.ifsc);
            title = view.findViewById(R.id.title);
            banklogo = view.findViewById(R.id.banklogo);

        }
    }

}



