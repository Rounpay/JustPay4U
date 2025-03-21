package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmployeeUser;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmpUserAdapter extends RecyclerView.Adapter<EmpUserAdapter.MyViewHolder> {


    private List<GetEmployeeUser> transactionsList;
    private List<GetEmployeeUser> operatorList;
    private Activity mContext;

    public EmpUserAdapter(List<GetEmployeeUser> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;

        this.operatorList = new ArrayList<>();
        this.operatorList.addAll(transactionsList);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_emp_user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetEmployeeUser operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName() + " [" + operator.getPrefix() + operator.getIntroID() + "]");

        holder.roleTv.setText(operator.getRole() + "");
        holder.mobileTv.setText(operator.getMobileNo() + "");
        holder.ouyletNameTv.setText(operator.getOutletName() + "");
        holder.slabTv.setText(operator.getSlab() + "");
        holder.balanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.status.setImageResource(operator.isStatus() ? R.drawable.ic_check_mark : R.drawable.ic_cross_mark);
        ImageViewCompat.setImageTintList(holder.status, operator.isStatus() ?
                AppCompatResources.getColorStateList(mContext, R.color.green) : AppCompatResources.getColorStateList(mContext, R.color.color_red));


    }

    public void filter(String st) {
        String charText = st.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(operatorList);
        } else {
            for (GetEmployeeUser wp : operatorList) {
                if ((wp.getName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOutletName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobileNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getSlab() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getRole() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private LinearLayout roleView;
        private TextView roleTv;
        private LinearLayout outletNameView;
        private TextView ouyletNameTv;
        private LinearLayout mobileView;
        private TextView mobileTv;
        private LinearLayout slabView;
        private TextView slabTv;
        private LinearLayout rightView;
        private TextView balanceTv;
        private ImageView status;
        private ImageView calanderIcon;


        public MyViewHolder(View view) {
            super(view);

            nameTv = view.findViewById(R.id.nameTv);
            roleView = view.findViewById(R.id.roleView);
            roleTv = view.findViewById(R.id.roleTv);
            outletNameView = view.findViewById(R.id.outletNameView);
            ouyletNameTv = view.findViewById(R.id.ouyletNameTv);
            mobileView = view.findViewById(R.id.mobileView);
            mobileTv = view.findViewById(R.id.mobileTv);
            slabView = view.findViewById(R.id.slabView);
            slabTv = view.findViewById(R.id.slabTv);

            rightView = view.findViewById(R.id.rightView);
            balanceTv = view.findViewById(R.id.balanceTv);
            status = view.findViewById(R.id.status);
            calanderIcon = view.findViewById(R.id.calanderIcon);


        }
    }
}


