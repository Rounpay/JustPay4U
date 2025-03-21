package com.solution.app.justpay4u.Fintech.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.solution.app.justpay4u.Api.Fintech.Object.MNPClaimsList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.MyPrintDocumentAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class MNPClaimReportAdapter extends RecyclerView.Adapter<MNPClaimReportAdapter.MyViewHolder> {
    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    String charText = "";
    int resourceId = 0;

    private ArrayList<MNPClaimsList> rechargeStatus;
    private ArrayList<MNPClaimsList> transactionsList;
    private Activity mContext;

    public MNPClaimReportAdapter(ArrayList<MNPClaimsList> transactionsList,
                                 Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mnp_claim_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MNPClaimsList operator = transactionsList.get(position);
        holder.operatorName.setText("" + operator.getOpName());
        /*holder.txn.setText("" + operator.getTransactionID());*/
        /*holder.balance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getBalance());
        holder.lastbalance.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getLastBalance());*/
        holder.amount.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount());
      /*  holder.debit.setText("" + mContext.getResources().getString(R.string.rupiya) + " -" + operator.getAmount());
        holder.comm.setText("" + mContext.getResources().getString(R.string.rupiya) + " " + operator.getCommission());
        holder.source.setText(operator.getRequestMode());*/


        //  holder.opid.setText(" "  + operator.getOpID());
        holder.date.setText(operator.getApprovedDate() + "");
        holder.mobile.setText("" + operator.getMnpMobile());
        holder.liveID.setText("" + operator.getReferenceID());


        holder.status.setText(operator.getStatus() + "");
        if (operator.getStatus().toLowerCase().equalsIgnoreCase("approve") ||
                operator.getStatus().toLowerCase().equalsIgnoreCase("Success")) {
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.green));
        } else if (operator.getStatus().toLowerCase().equalsIgnoreCase("failed") ||
                operator.getStatus().toLowerCase().equalsIgnoreCase("Reject")) {
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.red));


        } else {
            ViewCompat.setBackgroundTintList(holder.status, ContextCompat.getColorStateList(mContext, R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveandprint(String string) {
        MyPrintDocumentAdapter pd = new MyPrintDocumentAdapter(mContext, string);
        pd.printDocument(string);
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            for (MNPClaimsList wp : rechargeStatus) {
                if (wp.getStatus().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getMnpMobile().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getOpName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

   /* void sendReport(String reportId) {
        if (mCustomAlertDialog != null) {
            mCustomAlertDialog.sendReportDialog(1, "", new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {

                }
            });
        }
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView /*balance, userName,*/ operatorName/*, CommissionLabel*/;
        /*public AppCompatTextView lastbalance;*/
        /*public AppCompatTextView txn;*/
        public AppCompatTextView amount;
        public AppCompatTextView opid;
        public AppCompatTextView date/*, debit, comm*/;
        public AppCompatImageView operatorImage;
        public AppCompatTextView mobile;
        public AppCompatTextView status;
        public AppCompatTextView liveID;
        /*TextView source, outlet, outletNo;*/
        /*AppCompatButton dispute, w2r;*/
        /*View outletView, outletNoView;*/

        /*  ImageView Share, print;*/

        public MyViewHolder(View view) {
            super(view);
            operatorName = view.findViewById(R.id.operatorName);
            /*userName =  view.findViewById(R.id.userName);*/
            /*txn =  view.findViewById(R.id.txn);*/
            /* balance =  view.findViewById(R.id.balance);*/
            amount = view.findViewById(R.id.amount);
            /*source = view.findViewById(R.id.source);
            w2r = view.findViewById(R.id.w2r);
            outlet = view.findViewById(R.id.outlet);
            outletNo = view.findViewById(R.id.outletNo);
            outletView = view.findViewById(R.id.outletNameView);
            outletNoView = view.findViewById(R.id.outletNoView);*/
            date = view.findViewById(R.id.date);
            mobile = view.findViewById(R.id.mobile);
            liveID = view.findViewById(R.id.liveID);
            status = view.findViewById(R.id.status);
            /*dispute = view.findViewById(R.id.dispute);
            Share =  view.findViewById(R.id.Share);
            print =  view.findViewById(R.id.print);
            CommissionLabel = view.findViewById(R.id.CommissionLabel);*/
            operatorImage = view.findViewById(R.id.operatorImage);
            /*debit =  view.findViewById(R.id.debit);
            comm =  view.findViewById(R.id.comm);
            lastbalance =  view.findViewById(R.id.lastbalance);*/
        }
    }
}