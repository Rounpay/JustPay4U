package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.UserDaybookDMRReport;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;


public class UserDayBookDMTListAdapter extends RecyclerView.Adapter<UserDayBookDMTListAdapter.MyViewHolder> {

    int rollId;
    private List<UserDaybookDMRReport> listItem;
    private Activity mContext;

    public UserDayBookDMTListAdapter(Activity mContext, List<UserDaybookDMRReport> mUserLists, int rollId) {
        this.listItem = mUserLists;
        this.mContext = mContext;
        this.rollId = rollId;
    }

    @Override
    public UserDayBookDMTListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_day_book_dmt, parent, false);

        return new UserDayBookDMTListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final UserDayBookDMTListAdapter.MyViewHolder holder, final int position) {
        final UserDaybookDMRReport mItem = listItem.get(position);

        if (rollId == 3 || rollId == 2) {
            holder.commissionDetailView.setVisibility(View.GONE);

        } else {

            holder.commissionDetailView.setVisibility(View.VISIBLE);
            holder.selfCommision.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getSelfCommission()));
            holder.teamCommision.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getTeamCommission()));
            holder.commission.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getCommission()));
        }

        holder.commissionNormalLabel.setText("Credited Amount");
        holder.commissionNormal.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getCreditedAmount()));
        holder.sucessHitDmt.setText("" + mItem.getTotalHits());
        holder.sucessAmtDmt.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getTotalAmount()));
        holder.ccf.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getCcf()));
        holder.baseAmt.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getBaseAmount()));
        holder.surcharge.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getSurcharge()));
        holder.gstOnSurcharge.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getGstOnSurcharge()));
        holder.withTds.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getAmountWithTDS()));
        holder.tds.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getTds()));
        holder.afterSurcharge.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getAmountAfterSurcharge()));
        holder.refundGst.setText("\u20B9 " + Utility.INSTANCE.formatedAmountReplaceLastZero( mItem.getRefundGST()));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView operator;
        private LinearLayout commissionNormalView;
        private AppCompatTextView commissionNormalLabel;
        private AppCompatTextView commissionNormal;
        private LinearLayout commissionDetailView;
        private RelativeLayout selfCommisionView;
        private AppCompatTextView selfCommisionLabel;
        private AppCompatTextView selfCommision;
        private RelativeLayout teamCommisionView;
        private AppCompatTextView teamCommisionLabel;
        private AppCompatTextView teamCommision;
        private RelativeLayout commissionView;
        private AppCompatTextView commisiionLabel;
        private AppCompatTextView commission;
        private LinearLayout hitsAmountDetailView;
        private RelativeLayout sucessHitView, sucessHitDmtView;
        private AppCompatTextView sucessHitLabel, sucessHitDmtLabel;
        private AppCompatTextView sucessHit, sucessHitDmt;
        private RelativeLayout pendingHitView;
        private AppCompatTextView pendingHitLabel;
        private AppCompatTextView pendingHit;
        private RelativeLayout failedHitView;
        private AppCompatTextView failedHitLabel;
        private AppCompatTextView failedHit;
        private RelativeLayout totalHitView;
        private AppCompatTextView totalHitLabel;
        private AppCompatTextView totalHit;
        private RelativeLayout sucessAmtView;
        private AppCompatTextView sucessAmtLabel;
        private AppCompatTextView sucessAmt;
        private RelativeLayout sucessAmtDmtView;
        private AppCompatTextView sucessAmtDmtLabel;
        private AppCompatTextView sucessAmtDmt;
        private RelativeLayout pendingAmtView;
        private AppCompatTextView pendingAmtLabel;
        private AppCompatTextView pendingAmt;
        private RelativeLayout failedAmtView;
        private AppCompatTextView failedAmtLabel;
        private AppCompatTextView failedAmt;
        private RelativeLayout totalAmtView;
        private AppCompatTextView totalAmtLabel;
        private AppCompatTextView totalAmt;
        private LinearLayout surchargeDetailsView;
        private RelativeLayout ccfView;
        private AppCompatTextView ccfLabel;
        private AppCompatTextView ccf;
        private RelativeLayout surchargeView;
        private AppCompatTextView surchargeLabel;
        private AppCompatTextView surcharge;
        private RelativeLayout gstOnSurchargeView;
        private AppCompatTextView gstOnSurchargeLabel;
        private AppCompatTextView gstOnSurcharge;
        private RelativeLayout tdsView;
        private AppCompatTextView tdsLabel;
        private AppCompatTextView tds;
        private RelativeLayout baseAmtView;
        private AppCompatTextView baseAmtLabel;
        private AppCompatTextView baseAmt;
        private RelativeLayout afterSurchargeView;
        private AppCompatTextView afterSurchargeLabel;
        private AppCompatTextView afterSurcharge;
        private RelativeLayout refundGstView;
        private AppCompatTextView refundGstLabel;
        private AppCompatTextView refundGst;
        private RelativeLayout withTdsView;
        private AppCompatTextView withTdsLabel;
        private AppCompatTextView withTds;

        public MyViewHolder(View view) {
            super(view);
            operator = view.findViewById(R.id.operator);
            operator.setVisibility(View.GONE);
            commissionNormalView = view.findViewById(R.id.commissionNormalView);
            commissionNormalLabel = view.findViewById(R.id.commissionNormalLabel);
            commissionNormal = view.findViewById(R.id.commissionNormal);
            commissionDetailView = view.findViewById(R.id.commissionDetailView);
            selfCommisionView = view.findViewById(R.id.selfCommisionView);
            selfCommisionLabel = view.findViewById(R.id.selfCommisionLabel);
            selfCommision = view.findViewById(R.id.selfCommision);
            teamCommisionView = view.findViewById(R.id.teamCommisionView);
            teamCommisionLabel = view.findViewById(R.id.teamCommisionLabel);
            teamCommision = view.findViewById(R.id.teamCommision);
            commissionView = view.findViewById(R.id.commissionView);
            commisiionLabel = view.findViewById(R.id.commisiionLabel);
            commission = view.findViewById(R.id.commission);
            hitsAmountDetailView = view.findViewById(R.id.hitsAmountDetailView);
            hitsAmountDetailView.setVisibility(View.GONE);
            sucessHitView = view.findViewById(R.id.sucessHitView);
            sucessHitLabel = view.findViewById(R.id.sucessHitLabel);
            sucessHit = view.findViewById(R.id.sucessHit);
            sucessHitDmtView = view.findViewById(R.id.sucessHitDmtView);
            sucessHitDmtLabel = view.findViewById(R.id.sucessHitDmtLabel);
            sucessHitDmt = view.findViewById(R.id.sucessHitDmt);
            pendingHitView = view.findViewById(R.id.pendingHitView);
            pendingHitLabel = view.findViewById(R.id.pendingHitLabel);
            pendingHit = view.findViewById(R.id.pendingHit);
            failedHitView = view.findViewById(R.id.failedHitView);
            failedHitLabel = view.findViewById(R.id.failedHitLabel);
            failedHit = view.findViewById(R.id.failedHit);
            totalHitView = view.findViewById(R.id.totalHitView);
            totalHitLabel = view.findViewById(R.id.totalHitLabel);
            totalHit = view.findViewById(R.id.totalHit);
            sucessAmtView = view.findViewById(R.id.sucessAmtView);
            sucessAmtLabel = view.findViewById(R.id.sucessAmtLabel);
            sucessAmt = view.findViewById(R.id.sucessAmt);
            sucessAmtDmtView = view.findViewById(R.id.sucessAmtDmtView);
            sucessAmtDmtLabel = view.findViewById(R.id.sucessAmtDmtLabel);
            sucessAmtDmt = view.findViewById(R.id.sucessAmtDmt);
            pendingAmtView = view.findViewById(R.id.pendingAmtView);
            pendingAmtLabel = view.findViewById(R.id.pendingAmtLabel);
            pendingAmt = view.findViewById(R.id.pendingAmt);
            failedAmtView = view.findViewById(R.id.failedAmtView);
            failedAmtLabel = view.findViewById(R.id.failedAmtLabel);
            failedAmt = view.findViewById(R.id.failedAmt);
            totalAmtView = view.findViewById(R.id.totalAmtView);
            totalAmtLabel = view.findViewById(R.id.totalAmtLabel);
            totalAmt = view.findViewById(R.id.totalAmt);
            surchargeDetailsView = view.findViewById(R.id.surchargeDetailsView);
            ccfView = view.findViewById(R.id.ccfView);
            ccfLabel = view.findViewById(R.id.ccfLabel);
            ccf = view.findViewById(R.id.ccf);
            surchargeView = view.findViewById(R.id.surchargeView);
            surchargeLabel = view.findViewById(R.id.surchargeLabel);
            surcharge = view.findViewById(R.id.surcharge);
            gstOnSurchargeView = view.findViewById(R.id.gstOnSurchargeView);
            gstOnSurchargeLabel = view.findViewById(R.id.gstOnSurchargeLabel);
            gstOnSurcharge = view.findViewById(R.id.gstOnSurcharge);
            tdsView = view.findViewById(R.id.tdsView);
            tdsLabel = view.findViewById(R.id.tdsLabel);
            tds = view.findViewById(R.id.tds);
            baseAmtView = view.findViewById(R.id.baseAmtView);
            baseAmtLabel = view.findViewById(R.id.baseAmtLabel);
            baseAmt = view.findViewById(R.id.baseAmt);
            afterSurchargeView = view.findViewById(R.id.afterSurchargeView);
            afterSurchargeLabel = view.findViewById(R.id.afterSurchargeLabel);
            afterSurcharge = view.findViewById(R.id.afterSurcharge);
            refundGstView = view.findViewById(R.id.refundGstView);
            refundGstLabel = view.findViewById(R.id.refundGstLabel);
            refundGst = view.findViewById(R.id.refundGst);
            withTdsView = view.findViewById(R.id.withTdsView);
            withTdsLabel = view.findViewById(R.id.withTdsLabel);
            withTds = view.findViewById(R.id.withTds);


        }
    }


}
