package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTargetReport;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class TargetReportAdapter extends RecyclerView.Adapter<TargetReportAdapter.MyViewHolder> {


    private ArrayList<GetTargetReport> rechargeStatus;
    private ArrayList<GetTargetReport> transactionsList;
    private Activity mContext;
    private String charText;


    public TargetReportAdapter(ArrayList<GetTargetReport> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;

        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_target_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetTargetReport operator = transactionsList.get(position);

        holder.name.setText(operator.getUser() + " [" + operator.getuRole() + "]");
        holder.mobile.setText(operator.getUserMobile() + "");
        /* holder.outletName.setText(operator.getUser() + "");*/
        holder.saleHead.setText(operator.getShDetail() + "");
        holder.circleHead.setText(operator.getcDetail() + "");
        holder.zbm.setText(operator.getzDetail() + "");
        holder.asm.setText(operator.getaDetail() + "");
        holder.tsm.setText(operator.getAm() + " [" + operator.getAmRole() + "]");

        holder.preTarget.setText("Target : "+Utility.INSTANCE.formatedAmountWithRupees(operator.getTargetPrepaid() + ""));

        holder.prtlm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmPrepaid() + ""));
        holder.prtlmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmtdPrepaid() + ""));
        holder.prtmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMtdPrepaid() + ""));
        // holder.prtach.setText(Utility.INSTANCE.formatedAmount(operator.gettGrowth() + "") + " %");

        holder.prolm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmPrepaid() + ""));
        holder.prolmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmtdPrepaid() + ""));
        holder.promtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTomtdPrepaid() + ""));
        //holder.proach.setText(Utility.INSTANCE.formatedAmount(operator.getoGrowth() + "") + " %");

        holder.dmtTarget.setText("Target : "+Utility.INSTANCE.formatedAmountWithRupees(operator.getTargetDMT() + ""));

        holder.dtlm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmdmt() + ""));
        holder.dtlmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmtddmt() + ""));
        holder.dtmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMtddmt() + ""));
        // holder.dtach.setText(Utility.INSTANCE.formatedAmount(operator.gettGrowth() + "") + " %");

        holder.dolm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmdmt() + ""));
        holder.dolmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmtddmt() + ""));
        holder.domtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTomtddmt() + ""));
        // holder.doach.setText(Utility.INSTANCE.formatedAmount(operator.getoGrowth() + "") + " %");

        holder.bbpsTarget.setText("Target : "+Utility.INSTANCE.formatedAmountWithRupees(operator.getTargetBBPS() + ""));

        holder.btlm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmbbps() + ""));
        holder.btlmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmtdbbps() + ""));
        holder.btmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMtdbbps() + ""));
        // holder.btach.setText(Utility.INSTANCE.formatedAmount(operator.gettGrowth() + "") + " %");

        holder.bolm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmbbps() + ""));
        holder.bolmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmtdbbps() + ""));
        holder.bomtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTomtdbbps() + ""));
        // holder.boach.setText(Utility.INSTANCE.formatedAmount(operator.getoGrowth() + "") + " %");

        holder.aepsTarget.setText("Target : "+Utility.INSTANCE.formatedAmountWithRupees(operator.getTargetAEPS() + ""));

        holder.atlm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmaeps() + ""));
        holder.atlmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLmtdaeps() + ""));
        holder.atmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMtdaeps() + ""));
        //  holder.atach.setText(Utility.INSTANCE.formatedAmount(operator.gettGrowth() + "") + " %");

        holder.aolm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmaeps() + ""));
        holder.aolmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTolmtdaeps() + ""));
        holder.aomtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTomtdaeps() + ""));
        // holder.aoach.setText(Utility.INSTANCE.formatedAmount(operator.getoGrowth() + "") + " %");

        holder.etDate.setText(Utility.INSTANCE.formatedDate(operator.getEntryDate() + ""));
        holder.txnDate.setText(Utility.INSTANCE.formatedDate(operator.getTransactionDate() + ""));
    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (GetTargetReport wp : rechargeStatus) {
                if ((wp.getUserMobile() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getUser() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getEntryDate() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView outletName;
        private TextView mobile;
        private TextView saleHead;
        private TextView circleHead;
        private TextView zbm;
        private TextView asm;
        private TextView tsm;
        private TextView prtlm;
        private TextView prtlmtd;
        private TextView prtmtd;
        private TextView prtach;
        private TextView prolm;
        private TextView prolmtd;
        private TextView promtd;
        private TextView proach;
        private TextView dtlm;
        private TextView dtlmtd;
        private TextView dtmtd;
        private TextView dtach;
        private TextView dolm;
        private TextView dolmtd;
        private TextView domtd;
        private TextView doach;
        private TextView btlm;
        private TextView btlmtd;
        private TextView btmtd;
        private TextView btach;
        private TextView bolm;
        private TextView bolmtd;
        private TextView bomtd;
        private TextView boach;
        private TextView atlm;
        private TextView atlmtd;
        private TextView atmtd;
        private TextView atach;
        private TextView aolm;
        private TextView aolmtd;
        private TextView aomtd;
        private TextView aoach;
        private TextView etDate;
        private TextView txnDate;
        private TextView preTarget;
        private TextView bbpsTarget;
        private TextView dmtTarget;
        private TextView aepsTarget;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            outletName = (TextView) view.findViewById(R.id.outletName);
            mobile = (TextView) view.findViewById(R.id.mobile);
            saleHead = (TextView) view.findViewById(R.id.saleHead);
            circleHead = (TextView) view.findViewById(R.id.circleHead);
            zbm = (TextView) view.findViewById(R.id.zbm);
            asm = (TextView) view.findViewById(R.id.asm);
            tsm = (TextView) view.findViewById(R.id.tsm);
            prtlm = (TextView) view.findViewById(R.id.prtlm);
            prtlmtd = (TextView) view.findViewById(R.id.prtlmtd);
            prtmtd = (TextView) view.findViewById(R.id.prtmtd);
            prtach = (TextView) view.findViewById(R.id.prtach);
            prolm = (TextView) view.findViewById(R.id.prolm);
            prolmtd = (TextView) view.findViewById(R.id.prolmtd);
            promtd = (TextView) view.findViewById(R.id.promtd);
            proach = (TextView) view.findViewById(R.id.proach);
            dtlm = (TextView) view.findViewById(R.id.dtlm);
            dtlmtd = (TextView) view.findViewById(R.id.dtlmtd);
            dtmtd = (TextView) view.findViewById(R.id.dtmtd);
            dtach = (TextView) view.findViewById(R.id.dtach);
            dolm = (TextView) view.findViewById(R.id.dolm);
            dolmtd = (TextView) view.findViewById(R.id.dolmtd);
            domtd = (TextView) view.findViewById(R.id.domtd);
            doach = (TextView) view.findViewById(R.id.doach);
            btlm = (TextView) view.findViewById(R.id.btlm);
            btlmtd = (TextView) view.findViewById(R.id.btlmtd);
            btmtd = (TextView) view.findViewById(R.id.btmtd);
            btach = (TextView) view.findViewById(R.id.btach);
            bolm = (TextView) view.findViewById(R.id.bolm);
            bolmtd = (TextView) view.findViewById(R.id.bolmtd);
            bomtd = (TextView) view.findViewById(R.id.bomtd);
            boach = (TextView) view.findViewById(R.id.boach);
            atlm = (TextView) view.findViewById(R.id.atlm);
            atlmtd = (TextView) view.findViewById(R.id.atlmtd);
            atmtd = (TextView) view.findViewById(R.id.atmtd);
            atach = (TextView) view.findViewById(R.id.atach);
            aolm = (TextView) view.findViewById(R.id.aolm);
            aolmtd = (TextView) view.findViewById(R.id.aolmtd);
            aomtd = (TextView) view.findViewById(R.id.aomtd);
            aoach = (TextView) view.findViewById(R.id.aoach);
            etDate = (TextView) view.findViewById(R.id.etDate);
            txnDate = (TextView) view.findViewById(R.id.txnDate);
            preTarget = (TextView) view.findViewById(R.id.preTarget);
            dmtTarget = (TextView) view.findViewById(R.id.dmtTarget);
            bbpsTarget = (TextView) view.findViewById(R.id.bbpsTarget);
            aepsTarget = (TextView) view.findViewById(R.id.aepsTarget);
        }
    }
}