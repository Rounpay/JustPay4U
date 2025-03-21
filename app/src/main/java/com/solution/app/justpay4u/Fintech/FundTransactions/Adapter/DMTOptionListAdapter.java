package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DataOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.ServiceIcon;

import java.util.List;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class DMTOptionListAdapter extends RecyclerView.Adapter<DMTOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    CustomAlertDialog mCustomAlertDialog;
    private List<OperatorList> operatorList;
    private Activity mContext;
    private DataOpType data;

    public DMTOptionListAdapter(List<OperatorList> operatorList, Activity mContext, ClickView mClickView, int layout) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OperatorList operator = operatorList.get(position);


        holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getName().replaceAll("\n", " ") : operator.getName());
        holder.icon.setImageResource(ServiceIcon.INSTANCE.serviceIcon(operator.getOpType(), false));


        holder.itemView.setOnClickListener(v -> {

            if (mClickView != null) {

                mClickView.onClick(operator);
            }


        });
       /* if (operator.getName() != null && operator.getName().toString().length() > 0) {
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl+operator.getImage()).
                    apply(requestOptions).into(holder.opImage);

        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }*/
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
//            name.setTextColor(Color.WHITE);
            comingsoonTv = view.findViewById(R.id.comingsoonTv);
            comingsoonTv.setVisibility(View.GONE);
            icon = view.findViewById(R.id.icon);
            imageContainer = view.findViewById(R.id.imageContainer);
            bgView = view.findViewById(R.id.bgView);
            itemView = view;

        }
    }
}
