package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingReport;
import com.solution.app.justpay4u.Fintech.Employee.Activity.MeetingtReport;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeetingReportAdapter extends RecyclerView.Adapter<MeetingReportAdapter.MyViewHolder> {


    private List<GetMeetingReport> operatorList, transactionsList;
    private Activity mContext;

    public MeetingReportAdapter(List<GetMeetingReport> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;

        this.operatorList = new ArrayList<>();
        this.operatorList.addAll(transactionsList);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_meeting_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetMeetingReport operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getUserName() + "");


        holder.expense.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getTotalExpense() + ""));
        holder.travel.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getTotalTravel() + ""));
        holder.count.setText(operator.getMeetingCount() + "");
        holder.date.setText(Utility.INSTANCE.formatedDateWithT(operator.getEntryDate() + ""));
        if (operator.isClosed()) {
            holder.close.setText("Close");
            holder.close.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.close.setBackgroundResource(R.drawable.rounded_light_red_border);
        } else {
            holder.close.setText("Open");
            holder.close.setTextColor(mContext.getResources().getColor(R.color.darkGreen));
            holder.close.setBackgroundResource(R.drawable.rounded_green_border);
        }

        holder.details.setOnClickListener(v -> {
            if (operator.getMeetingDetailList() != null && operator.getMeetingDetailList().size() > 0) {
                ((MeetingtReport) mContext).showSubListDialog(operator, operator.getMeetingDetailList());
            } else {
                ((MeetingtReport) mContext).HitSubReportApi(operator);
            }
        });
        holder.map.setOnClickListener(v -> {
            if (operator.getMapPointList() != null && operator.getMapPointList().size() > 0) {
                ((MeetingtReport) mContext).showMapDialog(operator, operator.getMapPointList());
            } else {
                ((MeetingtReport) mContext).HitMapPoinApi(operator);
            }
        });


    }

    public void filter(String st) {
        String charText = st.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(operatorList);
        } else {
            for (GetMeetingReport wp : operatorList) {
                if ((wp.getUserName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getEntryDate() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTotalExpense() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMeetingCount() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getTotalTravel() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getUserId() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
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
        private TextView travel;
        private LinearLayout mobileView;
        private TextView expense;
        private LinearLayout pincodeView;
        private TextView count;
        private TextView close;
        private TextView date;
        private View details, map;


        public MyViewHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            travel = (TextView) view.findViewById(R.id.travel);
            mobileView = (LinearLayout) view.findViewById(R.id.mobileView);
            expense = (TextView) view.findViewById(R.id.expense);
            pincodeView = (LinearLayout) view.findViewById(R.id.pincodeView);
            count = (TextView) view.findViewById(R.id.count);
            close = (TextView) view.findViewById(R.id.close);
            date = (TextView) view.findViewById(R.id.date);
            details =  view.findViewById(R.id.details);
            map =  view.findViewById(R.id.map);


        }
    }
}


