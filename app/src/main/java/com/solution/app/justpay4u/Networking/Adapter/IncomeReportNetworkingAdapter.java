package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.IncomeWiseReport;
import com.solution.app.justpay4u.Networking.Activity.IncomeReportNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class IncomeReportNetworkingAdapter extends RecyclerView.Adapter<IncomeReportNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<IncomeWiseReport> mList = new ArrayList<>();
    private ArrayList<IncomeWiseReport> mFilterList = new ArrayList<>();
    private Context mContext;
    /*private String incomeSymbol;*/

    public IncomeReportNetworkingAdapter(ArrayList<IncomeWiseReport> transactionsList, Context mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;
       /* this.incomeSymbol = incomeSymbol;*/

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_income_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final IncomeWiseReport operator = mFilterList.get(position);
        holder.name.setText(operator.getName() + "");

        if (operator.getAdminCharge() > 0) {
            holder.adminChargeView.setVisibility(View.VISIBLE);
            holder.adminCharge.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getAdminCharge()));
        } else {
            holder.adminChargeView.setVisibility(View.GONE);
        }
        if (operator.getLapsIncome() > 0) {
            holder.lapsIncomeView.setVisibility(View.VISIBLE);
            holder.lapsIncome.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getLapsIncome()));
        } else {
            holder.lapsIncomeView.setVisibility(View.GONE);
        }
        if (operator.getFromName() != null && !operator.getFromName().isEmpty()) {
            holder.fromUserView.setVisibility(View.VISIBLE);
            if (operator.getFromUserId() != null && !operator.getFromUserId().isEmpty()) {
                holder.fromUser.setText(operator.getFromName() + " (" + operator.getFromUserId() + ")");
            } else {
                holder.fromUser.setText(operator.getFromName() + "");
            }

        } else {
            holder.fromUserView.setVisibility(View.GONE);
        }
        if (operator.getIncomeName() != null && !operator.getIncomeName().isEmpty()) {
            holder.incomeNameView.setVisibility(View.VISIBLE);
            holder.incomeName.setText(operator.getIncomeName() + "");

        } else {
            holder.incomeNameView.setVisibility(View.GONE);
        }
        if (operator.getFromTeamOf() != null && !operator.getFromTeamOf().isEmpty()) {
            holder.fromTeamOfView.setVisibility(View.VISIBLE);
            holder.fromTeamOf.setText(operator.getFromTeamOf() + "");

        } else {
            holder.fromTeamOfView.setVisibility(View.GONE);
        }
        if (operator.getLevelNo() > 0) {
            holder.levelView.setVisibility(View.VISIBLE);
            holder.levelNo.setText(operator.getLevelNo() + "");

        } else {
            holder.levelView.setVisibility(View.GONE);
        }

        /*if (operator.getToName() !=null && !operator.getToName().isEmpty()) {
            holder.toUserView.setVisibility(View.VISIBLE);
            if (operator.getToUserId() !=null && !operator.getToUserId().isEmpty()) {
                holder.toUser.setText(operator.getToName() + " ("+operator.getToUserId()+")");
            }else {
                holder.toUser.setText(operator.getToName() + "");
            }

        } else {
            holder.toUserView.setVisibility(View.GONE);
        }*/
        /*if (operator.getCreditedAmount() == operator.getBinaryIncome()) {
            holder.binaryIncome.setText(incomeSymbol+" "+Utility.INSTANCE.formatedAmountWithOutRupees(operator.getBinaryIncome() + ""));
            holder.creditedAmount.setVisibility(View.GONE);
        } else {
            holder.creditedAmount.setText("Credit\n" + incomeSymbol+" "+Utility.INSTANCE.formatedAmountWithOutRupees(operator.getCreditedAmount() + ""));
            holder.binaryIncome.setText(incomeSymbol+" "+Utility.INSTANCE.formatedAmountWithOutRupees(operator.getBinaryIncome() + ""));
            holder.creditedAmount.setVisibility(View.VISIBLE);
        }*/
        holder.creditedAmount.setText("Credit\n" +"\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getCreditedAmount()));
        String holdAmount = Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getholdCommAmt());
        if (operator.getholdCommAmt() == 0) {
            holder.HoldAmount.setVisibility(View.GONE);
        } else {
            holder.HoldAmount.setVisibility(View.VISIBLE);
            holder.HoldAmount.setText("HoldAmt\n\u20B9 " + holdAmount);
        }
        holder.binaryIncome.setText(/*incomeSymbol+" "+*/"\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getBinaryIncome()));

        holder.tid.setText(operator.getTid() + "");

        if (operator.getEntryDate().equalsIgnoreCase(operator.getPayoutDate())) {
            holder.dateMultiView.setVisibility(View.GONE);
            holder.dateView.setVisibility(View.VISIBLE);
            holder.date.setText(Utility.INSTANCE.formatedDateTimeOfSlash2(operator.getEntryDate()));
        } else {
            holder.dateMultiView.setVisibility(View.VISIBLE);
            holder.dateView.setVisibility(View.GONE);

            holder.regDate.setText(Utility.INSTANCE.formatedDateTimeOfSlash2(operator.getEntryDate()));
            holder.activateDate.setText(Utility.INSTANCE.formatedDateTimeOfSlash2(operator.getPayoutDate()));
        }

        if (operator.getTotalRightBusiness() > 0 || operator.getTotalLeftBusiness() > 0 || operator.getTodayMatchBusiness() > 0) {
            holder.businessView.setVisibility(View.VISIBLE);
            holder.leftBv.setText(/*incomeSymbol+" "+*/"\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getTotalLeftBusiness()));
            holder.rightBv.setText(/*incomeSymbol+" "+*/"\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getTotalRightBusiness()));
            holder.matchingBv.setText(/*incomeSymbol+" "+*/"\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero(operator.getTodayMatchBusiness()));
        } else {
            holder.businessView.setVisibility(View.GONE);
        }
        /*if (operator.getStatus().equalsIgnoreCase("Active")) {
            holder.status.setBackgroundResource(R.drawable.rounded_light_green);
        } else {
            holder.status.setBackgroundResource(R.drawable.rounded_light_red);
        }*/
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterList = mList;
                } else {
                    ArrayList<IncomeWiseReport> filteredList = new ArrayList<>();
                    for (IncomeWiseReport row : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBinaryIncome() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAdminCharge() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getLapsIncome() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getCreditedAmount() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getTid() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getFromTeamOf() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getIncomeName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getTotalLeftBusiness() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getTodayMatchBusiness() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getTotalRightBusiness() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getLevelNo() + "").toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<IncomeWiseReport>) filterResults.values;
                notifyDataSetChanged();


                if (mContext instanceof IncomeReportNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((IncomeReportNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((IncomeReportNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView name;
        private LinearLayout tidView;
        private AppCompatTextView tid;
        private LinearLayout binaryIncomeView, businessView, fromUserView, toUserView, incomeNameView, levelView, fromTeamOfView, dateMultiView, dateView;
        private AppCompatTextView binaryIncome, leftBv, rightBv, matchingBv, fromUser, toUser, incomeName, levelNo, fromTeamOf, date;
        private LinearLayout lapsIncomeView;
        private AppCompatTextView lapsIncome;
        private LinearLayout adminChargeView;
        private AppCompatTextView adminCharge;
        private TextView creditedAmount,HoldAmount;
        private AppCompatTextView regDate;
        private AppCompatTextView activateDate;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            tidView = view.findViewById(R.id.tidView);
            tid = view.findViewById(R.id.tid);
            fromUserView = view.findViewById(R.id.fromUserView);
            toUserView = view.findViewById(R.id.toUserView);
            fromUser = view.findViewById(R.id.fromUser);
            toUser = view.findViewById(R.id.toUser);
            incomeNameView = view.findViewById(R.id.incomeNameView);
            incomeName = view.findViewById(R.id.incomeName);
            levelView = view.findViewById(R.id.levelView);
            levelNo = view.findViewById(R.id.levelNo);
            fromTeamOfView = view.findViewById(R.id.fromTeamOfView);
            fromTeamOf = view.findViewById(R.id.fromTeamOf);
            binaryIncomeView = view.findViewById(R.id.binaryIncomeView);
            binaryIncome = view.findViewById(R.id.binaryIncome);
            lapsIncomeView = view.findViewById(R.id.lapsIncomeView);
            lapsIncome = view.findViewById(R.id.lapsIncome);
            adminChargeView = view.findViewById(R.id.adminChargeView);
            adminCharge = view.findViewById(R.id.adminCharge);
            creditedAmount = view.findViewById(R.id.creditedAmount);
            HoldAmount = view.findViewById(R.id.HoldAmount);
            dateView = view.findViewById(R.id.dateView);
            date = view.findViewById(R.id.date);
            dateMultiView = view.findViewById(R.id.dateMultiView);
            regDate = view.findViewById(R.id.regDate);
            activateDate = view.findViewById(R.id.activateDate);
            businessView = view.findViewById(R.id.businessView);
            leftBv = view.findViewById(R.id.leftBv);
            rightBv = view.findViewById(R.id.rightBv);
            matchingBv = view.findViewById(R.id.matchingBv);

        }
    }
}
