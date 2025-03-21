package com.solution.app.justpay4u.Fintech.AEPS.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSDashboardActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.BankListObject;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class AEPSRecentBankAdapter extends RecyclerView.Adapter<AEPSRecentBankAdapter.MyViewHolder> {


    RequestOptions requestOptions;
    int selectedIndex = -1;
    private ArrayList<BankListObject> operatorList;
    private Context mContext;

    public AEPSRecentBankAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public AEPSRecentBankAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recent_aeps_bank, parent, false);

        return new AEPSRecentBankAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AEPSRecentBankAdapter.MyViewHolder holder, int position) {
        final BankListObject operator = operatorList.get(position);
        holder.title.setText(operator.getBankName());

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + operator.getId() + ".png")
                .apply(requestOptions)
                .into(holder.banklogo);

        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof AEPSDashboardActivity) {
                ((AEPSDashboardActivity) mContext).ItemClick(operator);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        if (selectedIndex != -1 && selectedIndex == position) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_primary_border);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.rounded_grey_border);
        }
    }

    public void setSelection(int index) {
        selectedIndex = index;
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
        public TextView title;
        public ImageView opImage, banklogo;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title = view.findViewById(R.id.title);
            banklogo = view.findViewById(R.id.banklogo);

        }
    }

}



