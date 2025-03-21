package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.Networking.Activity.DirectTeamNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class DirectTeamNetworkingAdapter extends RecyclerView.Adapter<DirectTeamNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<MemberListData> mList = new ArrayList<>();
    private ArrayList<MemberListData> mFilterList = new ArrayList<>();
    private Context mContext;
    private int balanceCheckResponse;


    public DirectTeamNetworkingAdapter(int balanceCheckResponse, ArrayList<MemberListData> transactionsList, Context mContext) {
        this.balanceCheckResponse = balanceCheckResponse;
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.mList = transactionsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_direct_team_new, parent, false);

        return new MyViewHolder(itemView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MemberListData operator = mFilterList.get(position);
        if (operator.getDisplayField().toLowerCase().contains("userid")) {
            holder.useridView.setVisibility(View.VISIBLE);
            holder.userId.setText(operator.getuPrefix() + operator.getUserID() + "");
        } else {
            holder.useridView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("UserName")) {
            holder.name.setText(operator.getName() + "");
            holder.name.setVisibility(View.VISIBLE);
        } else {
            holder.name.setVisibility(View.GONE);
        }  if (operator.getDisplayField().toLowerCase().contains("sponsoredid")) {
            holder.sponserId.setText(operator.getSponserId() + "");
            holder.sponserIdView.setVisibility(View.VISIBLE);
        } else {
            holder.sponserIdView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("SponserName")) {
            holder.sponserName.setText(operator.getSponserName() + "");
            holder.sponserNameView.setVisibility(View.VISIBLE);
        } else {
            holder.sponserNameView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("Status")) {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText(operator.getStatus()+" ");
            if (operator.getStatus().equalsIgnoreCase("Active")) {
                holder.status.setBackgroundResource(R.drawable.rounded_light_green);
            } else {
                holder.status.setBackgroundResource(R.drawable.rounded_light_red);
            }
        }
        else {
            holder.status.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("RegisterDate")) {
            holder.regDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getRegDate()));
            holder.regDateView.setVisibility(View.VISIBLE);
        } else {
            holder.regDateView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("ActivationDate")) {
            holder.activateDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getActivationDate()));
            holder.activedateView.setVisibility(View.VISIBLE);
        } else {
            holder.activedateView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("MobileNo")) {
            holder.mobileNoView.setVisibility(View.VISIBLE);
            holder.mobileNo.setText(operator.getMobileNo() + "");
        } else {
            holder.mobileNoView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("Rank")) {
            holder.rankNoView.setVisibility(View.VISIBLE);
            holder.rankNo.setText(operator.getRankNo() + "");
        } else {
            holder.rankNoView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("EmailID")) {
            holder.emailView.setVisibility(View.VISIBLE);
            holder.email.setText(operator.getEmailID() + "");
        } else {
            holder.emailView.setVisibility(View.GONE);
        }
        if (balanceCheckResponse == 2) {
            holder.levelView.setVisibility(View.VISIBLE);
            holder.levelNo.setText(operator.getPosition() + "");
        } else {
            holder.levelView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("TotalDirectCount") ||
                operator.getDisplayField().contains("ActiveDirect") ||
                operator.getDisplayField().contains("DeActiveDirect")) {

            if (operator.getDisplayField().contains("TotalDirectCount")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.totalDirectCountLabel.setVisibility(View.VISIBLE);
                holder.totalDirectCount.setVisibility(View.VISIBLE);
                holder.totalDirectCount.setText(operator.getTotalDirectCount() + "");
            } else {
                holder.totalDirectCountLabel.setVisibility(View.GONE);
                holder.totalDirectCount.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().contains("ActiveDirect")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.activeDirectLabel.setVisibility(View.VISIBLE);
                holder.activeDirect.setVisibility(View.VISIBLE);
                holder.activeDirect.setText(operator.getActiveDirect() + "");
            } else {
                holder.activeDirectLabel.setVisibility(View.GONE);
                holder.activeDirect.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().contains("DeActiveDirect")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.deActiveDirectLabel.setVisibility(View.VISIBLE);
                holder.deActiveDirect.setVisibility(View.VISIBLE);
                holder.deActiveDirect.setText(operator.getDeActiveDirect() + "");
            } else {
                holder.deActiveDirectLabel.setVisibility(View.GONE);
                holder.deActiveDirect.setVisibility(View.GONE);
            }
        } else {
            holder.teamView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("SelfBusiness") ||
                operator.getDisplayField().contains("TeamBusiness") ||
                operator.getDisplayField().contains("DirectBusiness")) {
            if (operator.getDisplayField().contains("SelfBusiness")) {
                holder.businessView.setVisibility(View.VISIBLE);
                holder.selfBusinessLabel.setVisibility(View.VISIBLE);
                holder.selfBusiness.setVisibility(View.VISIBLE);
                holder.selfBusiness.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSelfBusiness() + ""));
            } else {
                holder.selfBusinessLabel.setVisibility(View.GONE);
                holder.selfBusiness.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().contains("TeamBusiness")) {
                holder.businessView.setVisibility(View.VISIBLE);
                holder.teamBusinessLabel.setVisibility(View.VISIBLE);
                holder.teamBusiness.setVisibility(View.VISIBLE);
                holder.teamBusiness.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTeamBusiness() + ""));
            } else {
                holder.teamBusinessLabel.setVisibility(View.GONE);
                holder.teamBusiness.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().contains("DirectBusiness")) {
                holder.businessView.setVisibility(View.VISIBLE);
                holder.directBusinessLabel.setVisibility(View.VISIBLE);
                holder.directBusiness.setVisibility(View.VISIBLE);
                holder.directBusiness.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getDirectBusiness() + ""));
            } else {
                holder.directBusinessLabel.setVisibility(View.GONE);
                holder.directBusiness.setVisibility(View.GONE);
            }
        } else {
            holder.businessView.setVisibility(View.GONE);
        }

        if (operator.getDisplayField().contains("PackageAmount")) {
            holder.packageView.setVisibility(View.VISIBLE);
            holder.packageCost.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageCost() + ""));
        } else {
            holder.packageView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().contains("TargetStatus")) {
            holder.targetStatus.setVisibility(View.VISIBLE);
            if (operator.getTargetStatus() == 1) {
                holder.targetStatus.setImageResource(R.drawable.ic_failed);
            } else if (operator.getTargetStatus() == 2) {
                holder.targetStatus.setImageResource(R.drawable.ic_success);
            } else if (operator.getTargetStatus() == 3) {
                holder.targetStatus.setImageResource(R.drawable.ic_pending);
            }
        } else {
            holder.targetStatus.setVisibility(View.GONE);
        }
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
                    ArrayList<MemberListData> filteredList = new ArrayList<>();
                    for (MemberListData row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (((row.getUserID() + "").toLowerCase().contains(charString.toLowerCase())||
                                (row.getEmailID() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getPosition() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getStrUserId() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getMobileNo() + "").toLowerCase().contains(charString.toLowerCase())||
                                (row.getStatus() + "").toLowerCase().contains(charString.toLowerCase()))) {
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
                mFilterList = (ArrayList<MemberListData>) filterResults.values;
                notifyDataSetChanged();


                if (mContext instanceof DirectTeamNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((DirectTeamNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((DirectTeamNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final ConstraintLayout businessView, teamView;
        public final LinearLayout useridView, sponserIdView, sponserNameView, mobileNoView,rankNoView,emailView, levelView, regDateView, activedateView,

        packageView;
        public AppCompatTextView name, userId, sponserId, sponserName, mobileNo,rankNo, email, regDate, activateDate, status,
                teamBusinessLabel, teamBusiness, selfBusinessLabel, selfBusiness, directBusinessLabel, directBusiness,
                totalDirectCountLabel, totalDirectCount, activeDirectLabel, activeDirect, deActiveDirectLabel, deActiveDirect,
                packageCost, levelNo;
        public ImageView targetStatus, line;

        public MyViewHolder(View view) {
            super(view);
            useridView = view.findViewById(R.id.useridView);
            sponserIdView = view.findViewById(R.id.sponsoredIdView);
            sponserNameView = view.findViewById(R.id.sponserNameView);
            mobileNoView = view.findViewById(R.id.mobileNoView);
            rankNoView = view.findViewById(R.id.rankNoView);
            emailView = view.findViewById(R.id.emailView);
            levelView = view.findViewById(R.id.levelView);
            regDateView = view.findViewById(R.id.regDateView);
            activedateView = view.findViewById(R.id.activedateView);
            packageView = view.findViewById(R.id.packageView);
            businessView = view.findViewById(R.id.businessView);
            teamView = view.findViewById(R.id.teamView);
            name = view.findViewById(R.id.name);
            userId = view.findViewById(R.id.userId);
            sponserId = view.findViewById(R.id.sponsoredId);
            sponserName = view.findViewById(R.id.sponserName);
            mobileNo = view.findViewById(R.id.mobileNo);
            rankNo = view.findViewById(R.id.rankNo);
            regDate = view.findViewById(R.id.regDate);
            email = view.findViewById(R.id.email);
            activateDate = view.findViewById(R.id.activateDate);
            levelNo = view.findViewById(R.id.levelNo);
            status = view.findViewById(R.id.status);

            teamBusinessLabel = view.findViewById(R.id.teamBusinessLabel);
            teamBusiness = view.findViewById(R.id.teamBusiness);
            selfBusinessLabel = view.findViewById(R.id.selfBusinessLabel);
            selfBusiness = view.findViewById(R.id.selfBusiness);
            directBusinessLabel = view.findViewById(R.id.directBusinessLabel);
            directBusiness = view.findViewById(R.id.directBusiness);

            totalDirectCountLabel = view.findViewById(R.id.totalDirectCountLabel);
            totalDirectCount = view.findViewById(R.id.totalDirectCount);
            activeDirectLabel = view.findViewById(R.id.activeDirectLabel);
            activeDirect = view.findViewById(R.id.activeDirect);
            deActiveDirectLabel = view.findViewById(R.id.deActiveDirectLabel);
            deActiveDirect = view.findViewById(R.id.deActiveDirect);


            packageCost = view.findViewById(R.id.packageCost);
            targetStatus = view.findViewById(R.id.targetStatus);
            line = view.findViewById(R.id.line);

        }
    }
}
