package com.solution.app.justpay4u.Fintech.Info.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.SupportListItem;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.MyViewHolder> {
    String charText = "";
    RequestOptions requestOptions;
    private ArrayList<SupportListItem> mList;
    private Context mContext;

    public SupportAdapter(Context mContext, ArrayList<SupportListItem> mList) {
        this.mList = mList;
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public SupportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dth_support, parent, false);

        return new SupportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SupportAdapter.MyViewHolder holder, int position) {
        final SupportListItem operator = mList.get(position);
        holder.name.setText(operator.getName());

        holder.numbers.setText(operator.getNumber());
        if (operator.getMsg() != null && !operator.getMsg().isEmpty()) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(operator.getMsg());
        } else {
            holder.msg.setVisibility(View.GONE);
        }
        if (operator.getImage() != null && operator.getImage().length() > 0) {

            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                    .apply(requestOptions)
                    .into(holder.image);

        } else {
            holder.image.setImageResource(R.drawable.placeholder_square);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView numbers, msg, name;
        ImageView image;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            numbers = view.findViewById(R.id.numbers);
            msg = view.findViewById(R.id.msg);
            image = view.findViewById(R.id.image);
        }
    }


}
