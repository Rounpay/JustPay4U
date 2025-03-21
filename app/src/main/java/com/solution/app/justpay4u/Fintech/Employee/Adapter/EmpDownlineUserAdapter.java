package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmpDownlineUser;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.List;

public class EmpDownlineUserAdapter extends RecyclerView.Adapter<EmpDownlineUserAdapter.MyViewHolder> {


    CustomAlertDialog mCustomAlertDialog;
    private List<GetEmpDownlineUser> operatorList;
    private Activity mContext;

    public EmpDownlineUserAdapter(List<GetEmpDownlineUser> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_attandance, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetEmpDownlineUser operator = operatorList.get(position);
        holder.name.setText(operator.getUserName() + " [" + operator.getPrefix() + operator.getUserID() + "]");


        holder.role.setText(operator.getRole() + "");
        holder.city.setText(operator.getCity() + "");
        holder.state.setText(operator.getState() + "");
        holder.mobile.setText(operator.getUserMobile() + "");
        holder.status.setImageResource(operator.isAttandance() ? R.drawable.ic_success : R.drawable.ic_failed);
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView role, name, mobile, city, state;
        ImageView status;


        public MyViewHolder(View view) {
            super(view);
            role = view.findViewById(R.id.role);
            name = view.findViewById(R.id.name);
            mobile = view.findViewById(R.id.mobile);
            status = view.findViewById(R.id.status);
            city = view.findViewById(R.id.city);
            state = view.findViewById(R.id.state);


        }
    }
}


