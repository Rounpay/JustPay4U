package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.RoleCommission;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class AddMoneyTypeAdapter extends RecyclerView.Adapter<AddMoneyTypeAdapter.MyViewHolder> {

    int roleId = 0;
    RequestOptions requestOptions;
    private ArrayList<SlabDetailDisplayLvl> operatorList;
    private Context mContext;

    public AddMoneyTypeAdapter(ArrayList<SlabDetailDisplayLvl> operatorList, int roleId, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.roleId = roleId;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_add_money_type, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SlabDetailDisplayLvl operator = operatorList.get(position);
        holder.title.setText(operator.getOperator());

        if (operator.getRoleCommission() != null && operator.getRoleCommission().size() > 0) {
            for (RoleCommission item : operator.getRoleCommission()) {
                if (item.getRoleID() == roleId) {
                    if (item.getAmtType() == 1) {
                        holder.comm.setText("Charges : " + Utility.INSTANCE.formatedAmountWithRupees(item.getComm() + ""));

                    } else {
                        holder.comm.setText("Charges : " + Utility.INSTANCE.formatedAmountWithOutRupees(item.getComm() + "") + " %");

                    }
                }
            }
        }
        if (operator.getCommSettingType() == 2) {
            holder.comm.setVisibility(View.GONE);
            holder.viewCharge.setVisibility(View.VISIBLE);
        } else {
            holder.comm.setVisibility(View.VISIBLE);
            holder.viewCharge.setVisibility(View.GONE);
        }
        holder.minMax.setText("Min : \u20B9 " + operator.getMin() + " - " + "Max : \u20B9 " + operator.getMax());
        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof AddMoneyActivity) {
                ((AddMoneyActivity) mContext).paymentTypeClick(operator);
            }
        });

        holder.viewCharge.setOnClickListener(v -> {
            if (mContext instanceof AddMoneyActivity) {
                ((AddMoneyActivity) mContext).HitApiSlabDetail(operator.getOid(), operator.getOperator(),
                        operator.getMin() + " - " + operator.getMax());
            }
        });


        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                .apply(requestOptions)
                .into(holder.opImage);

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        // operatorList.clear();
        if (charText.length() == 0) {
            operatorList.addAll(operatorList);
        } else {
            for (SlabDetailDisplayLvl op : operatorList) {
                if (op.getOperator().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    operatorList.add(op);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter(ArrayList<SlabDetailDisplayLvl> newList) {
        operatorList = new ArrayList<>();
        operatorList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, comm, viewCharge, minMax;
        public ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            comm = view.findViewById(R.id.comm);
            viewCharge = view.findViewById(R.id.viewCharge);
            minMax = view.findViewById(R.id.minMax);
            opImage = view.findViewById(R.id.opImage);


        }
    }

}
