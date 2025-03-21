package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.PaymentGatewayType;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.AddMoneyActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class GatewayTypeAdapter extends RecyclerView.Adapter<GatewayTypeAdapter.MyViewHolder> {

    int resourceId = 0;
    RequestOptions requestOptions;
    private ArrayList<PaymentGatewayType> operatorList;
    private Context mContext;

    public GatewayTypeAdapter(ArrayList<PaymentGatewayType> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_provider_operator, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PaymentGatewayType operator = operatorList.get(position);
        holder.title.setText(operator.getPg());
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddMoneyActivity) mContext).startGateway(operator);
            }
        });

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.pgIconUrl + operator.getPgType() + ".png")
                .apply(requestOptions)
                .into(holder.opImage);

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;
        LinearLayout ll_top;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            opImage =  view.findViewById(R.id.opImage);
            ll_top = view.findViewById(R.id.ll_top);

        }
    }

}
