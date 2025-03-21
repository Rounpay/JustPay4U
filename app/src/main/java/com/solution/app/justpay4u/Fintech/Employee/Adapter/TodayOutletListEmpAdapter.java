package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTodayOutListForEmpData;
import com.solution.app.justpay4u.R;

import java.util.List;

public class TodayOutletListEmpAdapter extends RecyclerView.Adapter<TodayOutletListEmpAdapter.MyViewHolder> {


    private List<GetTodayOutListForEmpData> transactionsList;
    private Activity mContext;


    public TodayOutletListEmpAdapter(List<GetTodayOutListForEmpData> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_today_outlet_list_emp, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetTodayOutListForEmpData operator = transactionsList.get(position);
        holder.name.setText(operator.getName() + "");


        holder.count.setText(operator.getRole() + "");
        holder.amt.setText(operator.getOutletName() + "");
        holder.mobile.setText(operator.getMobileNo() + "");


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


