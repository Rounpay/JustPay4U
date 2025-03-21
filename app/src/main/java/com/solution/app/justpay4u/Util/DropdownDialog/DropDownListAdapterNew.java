package com.solution.app.justpay4u.Util.DropdownDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class DropDownListAdapterNew extends RecyclerView.Adapter<DropDownListAdapterNew.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    boolean isShopping;

    private ArrayList<BalanceData> mList;

    public DropDownListAdapterNew(Context context,  boolean isShopping, ArrayList<BalanceData> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
        this.isShopping = isShopping;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BalanceData item = mList.get(position);

if ( item.isWithdrawal()){
holder.manView.setVisibility(View.GONE);
}else {
    holder.text.setText(item.getWalletType()+ "");
    holder.manView.setVisibility(View.VISIBLE);
}


        if (item.getWalletType() != null) {
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getBalance()+""));
        }

            holder.amount.setVisibility(View.VISIBLE);

            //holder.amount.setText("Min " + Utility.INSTANCE.formatedAmountWithRupees(((EMI) item.getDataObject()).getMinBankAmount() + ""));




        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();

                mClickListner.onItemClick(position, item.getWalletType(), item);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, String value, Object object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, amount;
        View itemView;
        RelativeLayout manView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            manView = view.findViewById(R.id.manView);
            amount = view.findViewById(R.id.amount);
        }


    }
}
