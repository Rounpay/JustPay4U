package com.solution.app.justpay4u.Fintech.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class AccountOpenOPListAdapter extends RecyclerView.Adapter<AccountOpenOPListAdapter.MyViewHolder> {

    int resourceId = 0;
    RequestOptions requestOptions;
    ClickView mClickView;
    private ArrayList<OperatorList> operatorList;
    private Context mContext;

    public AccountOpenOPListAdapter(ArrayList<OperatorList> operatorList, Context mContext, ClickView mClickView) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_AppLogo();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dashboard_option_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);
        holder.name.setText(operator.getName());
        holder.itemView.setOnClickListener(v -> {
            if (mClickView != null) {
                mClickView.onClick(operator);
            }
        });


        if (operator.getImage() != null && operator.getImage().length() > 0) {
            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                    .apply(requestOptions)
                    .into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.ic_tower);
        }
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public interface ClickView {
        void onClick(OperatorList mOperatorList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, comingsoonTv;
        public ImageView icon, bgView;
        RelativeLayout imageContainer;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            // name.setTextColor(Color.WHITE);
            comingsoonTv = view.findViewById(R.id.comingsoonTv);
            comingsoonTv.setVisibility(View.GONE);
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}




