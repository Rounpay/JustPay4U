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

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetPstReport;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class PstReportAdapter extends RecyclerView.Adapter<PstReportAdapter.MyViewHolder> {


    private ArrayList<GetPstReport> rechargeStatus;
    private ArrayList<GetPstReport> transactionsList;
    private Activity mContext;
    private String charText;


    public PstReportAdapter(ArrayList<GetPstReport> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;

        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pst_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetPstReport operator = transactionsList.get(position);

        holder.name.setText(operator.getUser() + "");
        holder.mobile.setText(operator.getUserMobile() + "");
        holder.saleHead.setText(operator.getShDetail() + "");
        holder.circleHead.setText(operator.getcDetail() + "");
        holder.zbm.setText(operator.getzDetail() + "");
        holder.asm.setText(operator.getaDetail() + "");
        holder.tsm.setText(operator.getAm() + "");
        holder.plm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPriLM() + ""));
        holder.plmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPriLMTD() + ""));
        holder.p.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPri() + ""));
        holder.pach.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getpGrowth() + "")+" %");

        holder.slm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSecLM() + ""));
        holder.slmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSecLMTD() + ""));
        holder.s.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSec() + ""));
        holder.sach.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getsGrowth() + "")+" %");

        holder.tlm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTerLM() + ""));
        holder.tlmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTerLMTD() + ""));
        holder.t.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getTer() + ""));
        holder.tach.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.gettGrowth() + "")+" %");

        holder.olm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.gettOutletLM() + ""));
        holder.olmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.gettOutletLMTD() + ""));
        holder.o.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.gettOutlet() + ""));
        holder.oach.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getoGrowth() + "")+" %");

        holder.pkglm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageSellLM() + ""));
        holder.pkglmtd.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageSellLMTD() + ""));
        holder.pkg.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageSell() + ""));
        holder.pkgach.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getPackageGrowth() + "")+" %");

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
            for (GetPstReport wp : rechargeStatus) {
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
        private TextView mobile;
        private TextView saleHead;
        private TextView circleHead;
        private TextView zbm;
        private TextView asm;
        private TextView tsm;
        private TextView plm;
        private TextView plmtd;
        private TextView p;
        private TextView pach;
        private TextView slm;
        private TextView slmtd;
        private TextView s;
        private TextView sach;
        private TextView tlm;
        private TextView tlmtd;
        private TextView t;
        private TextView tach;
        private TextView olm;
        private TextView olmtd;
        private TextView o;
        private TextView oach;
        private TextView pkglm;
        private TextView pkglmtd;
        private TextView pkg;
        private TextView pkgach;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            mobile = (TextView) view.findViewById(R.id.mobile);
            saleHead = (TextView) view.findViewById(R.id.saleHead);
            circleHead = (TextView) view.findViewById(R.id.circleHead);
            zbm = (TextView) view.findViewById(R.id.zbm);
            asm = (TextView) view.findViewById(R.id.asm);
            tsm = (TextView) view.findViewById(R.id.tsm);
            plm = (TextView) view.findViewById(R.id.plm);
            plmtd = (TextView) view.findViewById(R.id.plmtd);
            p = (TextView) view.findViewById(R.id.p);
            pach = (TextView) view.findViewById(R.id.pach);
            slm = (TextView) view.findViewById(R.id.slm);
            slmtd = (TextView) view.findViewById(R.id.slmtd);
            s = (TextView) view.findViewById(R.id.s);
            sach = (TextView) view.findViewById(R.id.sach);
            tlm = (TextView) view.findViewById(R.id.tlm);
            tlmtd = (TextView) view.findViewById(R.id.tlmtd);
            t = (TextView) view.findViewById(R.id.t);
            tach = (TextView) view.findViewById(R.id.tach);
            olm = (TextView) view.findViewById(R.id.olm);
            olmtd = (TextView) view.findViewById(R.id.olmtd);
            o = (TextView) view.findViewById(R.id.o);
            oach = (TextView) view.findViewById(R.id.oach);
            pkglm = (TextView) view.findViewById(R.id.pkglm);
            pkglmtd = (TextView) view.findViewById(R.id.pkglmtd);
            pkg = (TextView) view.findViewById(R.id.pkg);
            pkgach = (TextView) view.findViewById(R.id.pkgach);
        }
    }
}