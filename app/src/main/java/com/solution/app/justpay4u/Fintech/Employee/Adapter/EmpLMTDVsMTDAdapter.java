package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetLMTDVsMTD;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class EmpLMTDVsMTDAdapter extends RecyclerView.Adapter<EmpLMTDVsMTDAdapter.MyViewHolder> {


    CustomAlertDialog mCustomAlertDialog;
    private List<GetLMTDVsMTD> operatorList;
    private Activity mContext;

    public EmpLMTDVsMTDAdapter(List<GetLMTDVsMTD> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lmtd_mtd, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetLMTDVsMTD operator = operatorList.get(position);
        holder.name.setText(operator.getType() + "");


        holder.lm.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getLm() + ""));
        holder.lmtd.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getLmtd() + ""));
        holder.growth.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getGrowth() + ""));
        holder.mtd.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getMtd() + ""));
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, lm, lmtd, mtd, growth;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            lm = view.findViewById(R.id.lm);
            lmtd = view.findViewById(R.id.lmtd);
            mtd = view.findViewById(R.id.mtd);
            growth = view.findViewById(R.id.growth);

        }
    }
}


