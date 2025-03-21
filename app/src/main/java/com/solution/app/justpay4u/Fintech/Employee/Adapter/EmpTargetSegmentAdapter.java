package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTargetSegment;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class EmpTargetSegmentAdapter extends RecyclerView.Adapter<EmpTargetSegmentAdapter.MyViewHolder> {


    CustomAlertDialog mCustomAlertDialog;
    private List<GetTargetSegment> operatorList;
    private Activity mContext;

    public EmpTargetSegmentAdapter(List<GetTargetSegment> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_target_segment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetTargetSegment operator = operatorList.get(position);
        holder.name.setText(operator.getType() + "");


        holder.target.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getTarget() + ""));
        holder.achieve.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getAchieve() + ""));
        holder.achievePer.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getAchievePercent() + ""));
        holder.incentive.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getIncentive() + ""));
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, target, achieve, achievePer, incentive;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            target = view.findViewById(R.id.target);
            achieve = view.findViewById(R.id.achieve);
            achievePer = view.findViewById(R.id.achievePer);
            incentive = view.findViewById(R.id.incentive);

        }
    }
}


