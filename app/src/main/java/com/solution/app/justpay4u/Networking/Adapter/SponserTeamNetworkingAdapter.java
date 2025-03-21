package com.solution.app.justpay4u.Networking.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.MemberListData;
import com.solution.app.justpay4u.ApiHits.ApiNetworkingUtilMethods;
import com.solution.app.justpay4u.Networking.Activity.SponserTeamNetworkingActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class SponserTeamNetworkingAdapter extends RecyclerView.Adapter<SponserTeamNetworkingAdapter.MyViewHolder> implements Filterable {

    private ArrayList<MemberListData> mList = new ArrayList<>();
    private ArrayList<MemberListData> mFilterList = new ArrayList<>();
    int maxReportDisplayLevel, businessType;
    boolean isMultiLayerTeamDisplay;

    private Context mContext;


    public SponserTeamNetworkingAdapter(ArrayList<MemberListData> transactionsList, int maxReportDisplayLevel,
                                        boolean isMultiLayerTeamDisplay, int businessType, Activity mContext) {
        this.mContext = mContext;
        this.mFilterList = transactionsList;
        this.maxReportDisplayLevel = maxReportDisplayLevel;
        this.isMultiLayerTeamDisplay = isMultiLayerTeamDisplay;
        this.businessType = businessType;
        this.mList = transactionsList;
    }


    @Override
    public SponserTeamNetworkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_sponser_team_new, parent, false);
        return new SponserTeamNetworkingAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final SponserTeamNetworkingAdapter.MyViewHolder holder, int position) {
        final MemberListData operator = mFilterList.get(position);
        /*"UserId,Username,status,RegisterDate,ActivationDate,TotalDirectCount,ActiveDirect," +
                "DeActiveDirect,SelfBusiness,TeamBusiness,DirectBusiness,ParentId,ParentName"*/
        if (operator.getDisplayField().toLowerCase().contains("userid")) {
            holder.useridView.setVisibility(View.VISIBLE);
            holder.userId.setText(operator.getuPrefix() + operator.getUserID() + "");
            holder.userId.setText(/*operator.getuPrefix() +*/ operator.getUserID() + "");
        } else {
            holder.useridView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("username")) {
            holder.name.setText(operator.getName() + "");
            holder.name.setVisibility(View.VISIBLE);
        } else {
            holder.name.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("status")) {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText(operator.getStatus() + "");
            if (operator.getStatus().equalsIgnoreCase("Active")) {
                holder.status.setBackgroundResource(R.drawable.rounded_light_green);
            } else {
                holder.status.setBackgroundResource(R.drawable.rounded_light_red);
            }
        } else {
            holder.status.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("registerdate")) {
            holder.regDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getRegDate()));
            holder.regDateView.setVisibility(View.VISIBLE);
        } else {
            holder.regDateView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("activationdate")) {
            holder.activateDate.setText(Utility.INSTANCE.formatedDateWithT2(operator.getActivationDate()));
            holder.activedateView.setVisibility(View.VISIBLE);
        } else {
            holder.activedateView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("mobileno")) {
            holder.mobileNoView.setVisibility(View.VISIBLE);
            holder.mobileNo.setText(operator.getMobileNo() + "");
        } else {
            holder.mobileNoView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("rank")) {
            holder.rankNoView.setVisibility(View.VISIBLE);
            holder.rankNo.setText(operator.getRankNo() + "");
        } else {
            holder.rankNoView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("packageamount")) {
            holder.packageView.setVisibility(View.VISIBLE);
            holder.packageCost.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageCost() + ""));
        } else {
            holder.packageView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("levelno")) {
            holder.levelView.setVisibility(View.VISIBLE);
            holder.levelNoTitle.setText("Level No :");
            holder.levelNo.setText(operator.getLevelNo() + "");
        } else {
            holder.levelView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("targetstatus")) {
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

        if (operator.getDisplayField().toLowerCase().contains("parentuserid") && operator.getDisplayField().toLowerCase().contains("parentname")) {
            holder.parentView.setVisibility(View.VISIBLE);
            holder.parent.setText(operator.getParentName() + " (" + /*operator.getpPrefix() + */operator.getParentId() + ")");
        } else if (operator.getDisplayField().toLowerCase().contains("parentuserid") && !operator.getDisplayField().toLowerCase().contains("parentname")) {
            holder.parentView.setVisibility(View.VISIBLE);
            holder.parent.setText(/*operator.getpPrefix() + */operator.getParentId() + "");
        } else if (!operator.getDisplayField().toLowerCase().contains("parentuserid") && operator.getDisplayField().toLowerCase().contains("parentname")) {
            holder.parentView.setVisibility(View.VISIBLE);
            holder.parent.setText(operator.getParentName() + "");
        } else {
            holder.parentView.setVisibility(View.GONE);
        }

        if (operator.getDisplayField().toLowerCase().contains("sponserid") && operator.getDisplayField().toLowerCase().contains("sponsername")) {
            holder.sponserView.setVisibility(View.VISIBLE);
            holder.sponser.setText(operator.getSponserName() + " (" + operator.getsPrefix() + operator.getSponserId() + ")");
        } else if (operator.getDisplayField().toLowerCase().contains("sponserid") && !operator.getDisplayField().toLowerCase().contains("sponsername")) {
            holder.sponserView.setVisibility(View.VISIBLE);
            holder.sponser.setText(operator.getsPrefix() + operator.getSponserId() + "");
        } else if (!operator.getDisplayField().toLowerCase().contains("sponserid") && operator.getDisplayField().toLowerCase().contains("sponsername")) {
            holder.sponserView.setVisibility(View.VISIBLE);
            holder.sponser.setText(operator.getSponserName() + "");
        } else {
            holder.sponserView.setVisibility(View.GONE);
        }
        if (operator.getDisplayField().toLowerCase().contains("packagepv") && operator.getDisplayField().toLowerCase().contains("packagebv")) {
            holder.packagePVBVView.setVisibility(View.VISIBLE);
            holder.packagePVLabel.setVisibility(View.VISIBLE);
            holder.packagePV.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackagePV() + ""));
            holder.packageBVLabel.setVisibility(View.VISIBLE);
            holder.packageBV.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageBV() + ""));
        } else if (operator.getDisplayField().toLowerCase().contains("packagepv") && !operator.getDisplayField().toLowerCase().contains("packagebv")) {
            holder.packagePVBVView.setVisibility(View.VISIBLE);
            holder.packagePVLabel.setVisibility(View.VISIBLE);
            holder.packagePV.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackagePV() + ""));
            holder.packageBVLabel.setVisibility(View.GONE);
            holder.packageBV.setVisibility(View.GONE);
        } else if (!operator.getDisplayField().toLowerCase().contains("packagepv") && operator.getDisplayField().toLowerCase().contains("packagebv")) {
            holder.packagePVBVView.setVisibility(View.VISIBLE);
            holder.packageBVLabel.setVisibility(View.VISIBLE);
            holder.packageBV.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackagePV() + ""));
            holder.packagePVLabel.setVisibility(View.GONE);
            holder.packagePV.setVisibility(View.GONE);
        } else {
            holder.packagePVBVView.setVisibility(View.GONE);
            holder.packagePVLabel.setVisibility(View.GONE);
        }

        if (operator.getDisplayField().toLowerCase().contains("totaldirectcount") ||
                operator.getDisplayField().toLowerCase().contains("activedirect") ||
                operator.getDisplayField().toLowerCase().contains("deactivedirect")) {

            if (operator.getDisplayField().toLowerCase().contains("totaldirectcount")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.totalDirectCountLabel.setVisibility(View.VISIBLE);
                holder.totalDirectCount.setVisibility(View.VISIBLE);
                holder.totalDirectCount.setText(operator.getTotalDirectCount() + "");
            } else {
                holder.totalDirectCountLabel.setVisibility(View.GONE);
                holder.totalDirectCount.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().toLowerCase().contains("activedirect")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.activeDirectLabel.setVisibility(View.VISIBLE);
                holder.activeDirect.setVisibility(View.VISIBLE);
                holder.activeDirect.setText(operator.getActiveDirect() + "");
            } else {
                holder.activeDirectLabel.setVisibility(View.GONE);
                holder.activeDirect.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().toLowerCase().contains("deactivedirect")) {
                holder.teamView.setVisibility(View.VISIBLE);
                holder.deActiveDirectLabel.setVisibility(View.VISIBLE);
                holder.deActiveDirect.setVisibility(View.VISIBLE);
                holder.deActiveDirect.setText(operator.getDeActiveDirect() + "");
            } else {
                holder.deActiveDirectLabel.setVisibility(View.GONE);
                holder.deActiveDirect.setVisibility(View.GONE);
            }
        }else {
            holder.teamView.setVisibility(View.GONE);
        }



        if (operator.getDisplayField().toLowerCase().contains("selfbusiness") ||
                operator.getDisplayField().toLowerCase().contains("teambusiness") ||
                operator.getDisplayField().toLowerCase().contains("directbusiness")) {
            if (operator.getDisplayField().toLowerCase().contains("selfbusiness")) {
                holder.businessView.setVisibility(View.VISIBLE);
                holder.selfBusinessLabel.setVisibility(View.VISIBLE);
                holder.selfBusiness.setVisibility(View.VISIBLE);
                holder.selfBusiness.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSelfBusiness() + ""));
            } else {
                holder.selfBusinessLabel.setVisibility(View.GONE);
                holder.selfBusiness.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().toLowerCase().contains("teambusiness")) {
                holder.businessView.setVisibility(View.VISIBLE);
                holder.teamBusinessLabel.setVisibility(View.VISIBLE);
                holder.teamBusiness.setVisibility(View.VISIBLE);
                holder.teamBusiness.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTeamBusiness() + ""));
            } else {
                holder.teamBusinessLabel.setVisibility(View.GONE);
                holder.teamBusiness.setVisibility(View.GONE);
            }
            if (operator.getDisplayField().toLowerCase().contains("directbusiness")) {
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


        if (isMultiLayerTeamDisplay && operator.getTotalDirectCount() > 0 &&
                ApiNetworkingUtilMethods.INSTANCE.maxReportDisplay < maxReportDisplayLevel) {
            ApiNetworkingUtilMethods.INSTANCE.maxReportDisplay = ApiNetworkingUtilMethods.INSTANCE.maxReportDisplay + 1;
            holder.nextBtn.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(v -> {
                Intent i = new Intent(mContext, SponserTeamNetworkingActivity.class);
                i.putExtra("Level", 1);
                i.putExtra("DownlineID", operator.getUserID() + "");
//              i.putExtra("Deactive", filterStaus);
                i.putExtra("maxReportDisplayLevel", maxReportDisplayLevel);
                i.putExtra("isMultiLayerTeamDisplay", isMultiLayerTeamDisplay);
                i.putExtra("BusinessType", businessType);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(i);
            });
        } else {
            holder.nextBtn.setVisibility(View.GONE);
        }

  /*      BubbleDialog dialog = new BubbleDialog(mContext);
        dialog.addContentView(view);
        dialog.setClickedView(v);
        dialog.calBar(false);

        dialog.show();
        dialog.setBubbleLayoutBackgroundColor(Color.WHITE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
*/

        /*holder.targetStatus.setOnClickListener(v -> {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_targetstatus, null);

            BubbleDialog dialog = new BubbleDialog(mContext);
            dialog.addContentView(view);
            dialog.setClickedView(v);
            dialog.calBar(false);
            dialog.show();
            dialog.setBubbleLayoutBackgroundColor(Color.WHITE);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        });*/
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
                        if ((row.getName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getUserID() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getStatus() + "").toLowerCase().contains(charString.toLowerCase())) {
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


                if (mContext instanceof SponserTeamNetworkingActivity) {
                    if (mFilterList.size() == 0) {
                        ((SponserTeamNetworkingActivity) mContext).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((SponserTeamNetworkingActivity) mContext).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public final TextView status, packagePVLabel, packageBVLabel;
        public final ImageView targetStatus;
        public final AppCompatTextView name, packagePV, packageBV;
        public final LinearLayout useridView;
        public final AppCompatTextView userId;
        public final LinearLayout parentView, sponserView, regDateView, activedateView, packagePVBVView;
        public final AppCompatTextView parent, sponser;
        public final LinearLayout mobileNoView;
        public final AppCompatTextView mobileNo;
        public final LinearLayout rankNoView;
        public final AppCompatTextView rankNo;
        public final LinearLayout packageView;
        public final LinearLayout levelView;
        public final AppCompatTextView packageCost;
        public final AppCompatTextView levelNoTitle;
        public final AppCompatTextView levelNo;
        public final LinearLayout dateView;
        public final AppCompatTextView regDate;
        public final AppCompatTextView activateDate;
        public final ImageView line;
        public final ConstraintLayout businessView;
        public final AppCompatTextView teamBusinessLabel;
        public final AppCompatTextView teamBusiness;
        public final AppCompatTextView selfBusinessLabel;
        public final AppCompatTextView selfBusiness;
        public final AppCompatTextView directBusinessLabel;
        public final AppCompatTextView directBusiness;
        public final ConstraintLayout teamView;
        public final AppCompatTextView totalDirectCountLabel;
        public final AppCompatTextView totalDirectCount;
        public final AppCompatTextView activeDirectLabel;
        public final AppCompatTextView activeDirect;
        public final AppCompatTextView deActiveDirectLabel;
        public final AppCompatTextView deActiveDirect;
        public final AppCompatTextView nextBtn;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            status = view.findViewById(R.id.status);
            packagePVLabel = view.findViewById(R.id.packagePVLabel);
            packageBVLabel = view.findViewById(R.id.packageBVLabel);
            targetStatus = view.findViewById(R.id.targetStatus);
            name = view.findViewById(R.id.name);
            packagePV = view.findViewById(R.id.packagePV);
            packageBV = view.findViewById(R.id.packageBV);
            useridView = view.findViewById(R.id.useridView);
            userId = view.findViewById(R.id.userId);
            parentView = view.findViewById(R.id.parentView);
            sponserView = view.findViewById(R.id.sponserView);
            activedateView = view.findViewById(R.id.activedateView);
            packagePVBVView = view.findViewById(R.id.packagePVBVView);
            regDateView = view.findViewById(R.id.regDateView);
            parent = view.findViewById(R.id.parent);
            sponser = view.findViewById(R.id.sponser);
            mobileNoView = view.findViewById(R.id.mobileNoView);
            mobileNo = view.findViewById(R.id.mobileNo);
            rankNoView = view.findViewById(R.id.rankNoView);
            rankNo = view.findViewById(R.id.rankNo);
            packageView = view.findViewById(R.id.packageView);
            levelView = view.findViewById(R.id.levelView);
            packageCost = view.findViewById(R.id.packageCost);
            levelNoTitle = view.findViewById(R.id.levelNoTitle);
            levelNo = view.findViewById(R.id.levelNo);
            dateView = view.findViewById(R.id.dateView);
            regDate = view.findViewById(R.id.regDate);
            activateDate = view.findViewById(R.id.activateDate);
            line = view.findViewById(R.id.line);
            businessView = view.findViewById(R.id.businessView);
            teamBusinessLabel = view.findViewById(R.id.teamBusinessLabel);
            teamBusiness = view.findViewById(R.id.teamBusiness);
            selfBusinessLabel = view.findViewById(R.id.selfBusinessLabel);
            selfBusiness = view.findViewById(R.id.selfBusiness);
            directBusinessLabel = view.findViewById(R.id.directBusinessLabel);
            directBusiness = view.findViewById(R.id.directBusiness);
            teamView = view.findViewById(R.id.teamView);
            totalDirectCountLabel = view.findViewById(R.id.totalDirectCountLabel);
            totalDirectCount = view.findViewById(R.id.totalDirectCount);
            activeDirectLabel = view.findViewById(R.id.activeDirectLabel);
            activeDirect = view.findViewById(R.id.activeDirect);
            deActiveDirectLabel = view.findViewById(R.id.deActiveDirectLabel);
            deActiveDirect = view.findViewById(R.id.deActiveDirect);
            nextBtn = view.findViewById(R.id.nextBtn);


        }
    }
}
