package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTodayTransactorsData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class TodayTransactorAdapter extends RecyclerView.Adapter<TodayTransactorAdapter.MyViewHolder> {



    private List<GetTodayTransactorsData> transactionsList;
    private Activity mContext;


    public TodayTransactorAdapter(List<GetTodayTransactorsData> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_today_transactors, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetTodayTransactorsData operator = transactionsList.get(position);
        holder.name.setText(operator.getOutletName() + "");


        holder.count.setText(operator.getTransactionCount() + "");
        holder.amt.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.mobile.setText(operator.getMobilleNo() + "");


    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView amt;
        private TextView count;
        private TextView mobile;



        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amt = view.findViewById(R.id.amt);
            count = view.findViewById(R.id.count);
            mobile = view.findViewById(R.id.mobile);


        }
    }

    public interface onSucess {
        void onSucess();
    }
}


