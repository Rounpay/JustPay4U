package com.solution.app.justpay4u.Fintech.Reports.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.solution.app.justpay4u.Fintech.AEPS.Activity.AEPSSlipActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.RechargeStatus;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeBillPaymentActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.RechargeReportActivity;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.MyPrintDocumentAdapter;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class AepsReportAdapter extends RecyclerView.Adapter<AepsReportAdapter.MyViewHolder> {
    public TextInputLayout tilRemark;
    public EditText etRemark;
    public Button okButton;
    public Button cancelButton;
    String charText = "";
    int resourceId = 0;
    CustomAlertDialog mCustomAlertDialog;
    boolean isPsa;
    LoginResponse mLoginDataResponse;
    RequestOptions requestOptions;
    String deviceid, deviceSerialNum;
    private ArrayList<RechargeStatus> rechargeStatus;
    private ArrayList<RechargeStatus> transactionsList;
    private Activity mContext;
    private String status;
    private CustomLoader mCustomLoader;

    public AepsReportAdapter(ArrayList<RechargeStatus> transactionsList, Activity mContext, boolean isPsa,
                             LoginResponse mLoginDataResponse, String deviceid, String deviceSerialNum) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.isPsa = isPsa;
        this.mLoginDataResponse = mLoginDataResponse;
        this.rechargeStatus = transactionsList;
        this.deviceid = deviceid;
        this.deviceSerialNum = deviceSerialNum;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
        mCustomLoader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public AepsReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_aeps_report, parent, false);

        return new AepsReportAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final AepsReportAdapter.MyViewHolder holder, int position) {
        final RechargeStatus operator = transactionsList.get(position);
        holder.operatorName.setText("" + operator.getOperator());
        holder.txn.setText("" + operator.getTransactionID());
        holder.balance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.lastbalance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLastBalance() + ""));
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRequestedAmount() + ""));
        holder.debit.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount() + ""));
        holder.comm.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getCommission() + ""));
        holder.source.setText(operator.getRequestMode());
        if (mLoginDataResponse.getData().getRoleID() == 3 || mLoginDataResponse.getData().getRoleID() == 2) {
            holder.outletDetailView.setVisibility(View.GONE);
            holder.outletView.setVisibility(View.GONE);
        } else {
            holder.outletDetailView.setVisibility(View.VISIBLE);
            holder.outletView.setVisibility(View.VISIBLE);
            holder.outlet.setText(operator.getOutlet() + "");
            holder.outletNo.setText(operator.getOutletNo() + "");
        }

        if (operator.getCommType() == true) {
            holder.CommissionLabel.setText("Sur.");
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.green));
        } else {
            holder.comm.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.CommissionLabel.setText("Comm.");
        }

        //  holder.opid.setText(" "  + operator.getOpID());
        holder.date.setText(Utility.INSTANCE.formatedDate(operator.getEntryDate() + ""));

        if (operator.getAccount() != null && !operator.getAccount().isEmpty()) {
            holder.mobile.setText("" + operator.getAccount());
            holder.mobile.setVisibility(View.VISIBLE);
        } else {
            holder.mobile.setVisibility(View.GONE);
        }

        if (operator.getLiveID() != null && !operator.getLiveID().isEmpty()) {
            holder.liveID.setText("" + operator.getLiveID());
            holder.liveIDView.setVisibility(View.VISIBLE);
        } else {
            holder.liveIDView.setVisibility(View.GONE);
        }

        if (operator.getExtraParam() != null && !operator.getExtraParam().isEmpty()) {
            holder.bank.setText("" + operator.getExtraParam());
            holder.bankView.setVisibility(View.VISIBLE);
        } else {
            holder.bankView.setVisibility(View.GONE);
        }

        if (operator.getOptional2() != null && !operator.getOptional2().isEmpty()) {
            holder.bankBal.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getOptional2()));
            holder.bankBalView.setVisibility(View.VISIBLE);
        } else {
            holder.bankBalView.setVisibility(View.GONE);
        }
        String faqsearchDescription = operator.getType_().toLowerCase(Locale.getDefault());
        String faqsearchDescription2 = operator.getAccount().toLowerCase(Locale.getDefault());
        String faqsearchDescription3 = operator.getOperator().toLowerCase(Locale.getDefault());
        if (faqsearchDescription.contains(charText)) {
            int startPos = faqsearchDescription.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getType_()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.status.setText(operator.getType_());
        }
        if (faqsearchDescription3.contains(charText)) {
            int startPos = faqsearchDescription3.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getOperator()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.status.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.status.setText(operator.getType_());
        }
        if (faqsearchDescription2.contains(charText)) {
            int startPos = faqsearchDescription2.indexOf(charText);
            int endPos = startPos + charText.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getAccount()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mobile.setText(spanText, TextView.BufferType.SPANNABLE);

        } else {
            holder.mobile.setText(operator.getAccount());
        }

        if (operator.getType_().equalsIgnoreCase("SUCCESS")) {
            status = "Success";
            holder.liveIdLabel.setText("Bank RRN");
            holder.Share.setVisibility(View.VISIBLE);
            //holder.print.setVisibility(View.VISIBLE);
            holder.status.setText(status);
            holder.dispute.setVisibility(View.VISIBLE);
            holder.disputeStatusView.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_success);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.green));

        } else if (operator.getType_().equalsIgnoreCase("FAILED")) {
            status = "Failed";
            holder.liveIdLabel.setText("Reason");
            holder.Share.setVisibility(View.GONE);
            // holder.print.setVisibility(View.GONE);
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.disputeStatusView.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_failed);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_red));

        } else if (operator.getType_().equalsIgnoreCase("REFUNDED")) {
            status = "Refund";
            holder.liveIdLabel.setText("Bank RRN");
            holder.Share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.disputeStatusView.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_refund);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_cyan));

        } else if (operator.getType_().equalsIgnoreCase("PENDING") || operator.getType_().equalsIgnoreCase("REQUEST SEND")
                || operator.getType_().equalsIgnoreCase("REQUEST SENT")) {
            status = "Pending";
            holder.liveIdLabel.setText("Bank RRN");
            holder.Share.setVisibility(View.GONE);
            //  holder.print.setVisibility(View.GONE);
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.disputeStatusView.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_pending);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_amber));
        } else {
            status = "Other";
            holder.liveIdLabel.setText("Bank RRN");
            holder.Share.setVisibility(View.GONE);
            //holder.print.setVisibility(View.GONE);
            holder.status.setText(status);
            holder.dispute.setVisibility(View.GONE);
            holder.disputeStatusView.setVisibility(View.GONE);
            holder.statusIcon.setImageResource(R.drawable.ic_warning_other);
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_deep_orange));
        }


        if (!operator.getType_().equalsIgnoreCase("FAILED") && !operator.getType_().equalsIgnoreCase("PENDING")) {
            if (operator.getRefundStatus().equalsIgnoreCase("1")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("DISPUTE")) {
                    holder.dispute.setVisibility(View.VISIBLE);
                    holder.disputeStatusView.setVisibility(View.GONE);
                } else {
                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("3")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REFUNDED")) {

                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.VISIBLE);
                    holder.statusDispute.setText("Refunded");
                    holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.green));
                } else {
                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("4")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("REJECTED")) {

                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.VISIBLE);
                    holder.statusDispute.setText("Rejected");
                    holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.color_red));

                } else {
                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.GONE);
                }
            } else if (operator.getRefundStatus().equalsIgnoreCase("2")) {
                if (operator.getRefundStatus_().equalsIgnoreCase("UNDER REVIEW")) {

                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.VISIBLE);
                    holder.statusDispute.setText("Under Review");
                    holder.statusDispute.setTextColor(mContext.getResources().getColor(R.color.grey));
                } else {
                    holder.dispute.setVisibility(View.GONE);
                    holder.disputeStatusView.setVisibility(View.GONE);
                }
            }
        }


        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                .apply(requestOptions)
                .into(holder.operatorImage);


        holder.Share.setOnClickListener(v -> {
            Intent i = new Intent(mContext, AEPSSlipActivity.class);

            i.putExtra("data", operator);
           /* i.putExtra("RechargeMobileNo", "" + operator.getAccount());
            i.putExtra("liveId", "" + operator.getLiveID());

            i.putExtra("operatorname", "" + operator.getOperator());
            i.putExtra("pdate", "" + operator.getEntryDate());
            i.putExtra("ptime", "" + operator.getModifyDate());
            i.putExtra("txid", "" + operator.getTransactionID());
            i.putExtra("StatusCode", operator.get_Type());
            i.putExtra("txStatus", "" + operator.getType_());
            i.putExtra("typerecharge", "" + operator.getType_());
            i.putExtra("imageurl", "" + ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png");*/
            // i.putExtra("transaction_id",""+operator.getTransaction_ID());


            mContext.startActivity(i);
          /*  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Mobile Number : " + operator.getAccount() + "\n" +
                    "Operator Name : " + operator.getOperator() + "\n" +
                    "Amount : " + operator.getAmount() + "\n" +
                    "Date : " + operator.getEntryDate() + "\n" +
                    "Tx.ID : " + operator.getTransactionID() + "\n" +
                    "Live.ID : " + operator.getLiveID() + "\n" +
                    "Recharge Status :" + operator.getType_() + "\n" +
                    "Source : " + operator.getRequestMode();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));*/
        });
        holder.print.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                sendReport(operator.getTransactionID());
                /*String shareBody = "Mobile Number : " + operator.getAccount() + "\n" +
                        "Operator Name : " + operator.getOperator() + "\n" +
                        "Amount : " + operator.getAmount() + "\n" +
                        "Date : " + operator.getEntryDate() + "\n" +
                        "Txn. ID : " + operator.getTransactionID() + "\n" +
                        "Live. ID : " + operator.getLiveID() + "\n" +
                        "Txn. Status : " + operator.getType_()*//* + "\n" +
                        "Source : " + operator.getRequestMode()*//*;

                saveandprint(shareBody);*/
            }
        });
        holder.dispute.setOnClickListener(v -> {

            mCustomAlertDialog.WarningWithDoubleBtnCallBack(mContext.getResources().getString(R.string.dispute_msg), "Complaint", true, new CustomAlertDialog.DialogCallBack() {
                @Override
                public void onPositiveClick() {
                    if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        ApiFintechUtilMethods.INSTANCE.Dispute(mContext, operator.getTransactionID(), operator.getTid(),
                                mCustomLoader, mLoginDataResponse, deviceid, deviceSerialNum, object -> {
                                    v.setClickable(false);
                                    if (mContext instanceof RechargeReportActivity) {
                                        ((RechargeReportActivity) mContext).HitApi(false);
                                    } else if (mContext instanceof RechargeBillPaymentActivity) {
                                        ((RechargeBillPaymentActivity) mContext).HitApi();
                                    }
                                });

                    } else {
                        ApiFintechUtilMethods.INSTANCE.NetworkError(mContext);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });

        });

        if (operator.getIsWTR()) {
            if (operator.get_Type() == 1
                    || operator.get_Type() == 2
                    || operator.get_Type() == 5) {
                holder.w2r.setVisibility(View.VISIBLE);
            } else {
                holder.w2r.setVisibility(View.GONE);
            }
        } else {
            holder.w2r.setVisibility(View.GONE);
        }

        holder.w2r.setOnClickListener(v -> {
            w2rDialog(operator.getTid(), operator.getTransactionID(), holder.w2r, position);

        });
    }


    void w2rDialog(String tid, String transactionID, View w2r, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.dialog_w2r, null);

        final EditText RightAccountEt = vi.findViewById(R.id.RightAccountEt);
        final TextView submitButton = vi.findViewById(R.id.submitButton);
        final TextView cancelButton = vi.findViewById(R.id.cancelButton);
        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(vi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vi.findViewById(R.id.closeIv).setOnClickListener(v1 -> dialog.dismiss());
        cancelButton.setOnClickListener(v12 -> dialog.dismiss());

        submitButton.setOnClickListener(v13 -> {
            if (!RightAccountEt.getText().toString().isEmpty()) {
                mCustomLoader.show();
                ApiFintechUtilMethods.INSTANCE.MakeW2RRequest(mContext, tid, transactionID,
                        RightAccountEt.getText().toString(), mCustomLoader, mLoginDataResponse, deviceid, deviceSerialNum, object -> {
                            w2r.setVisibility(View.GONE);
                            transactionsList.get(position).setWTR(false);
                            notifyDataSetChanged();
                        });
                dialog.dismiss();
            } else {
                RightAccountEt.setError("Enter Password");
                RightAccountEt.requestFocus();
            }
        });
        dialog.show();
    }

    private boolean validateText() {
        if (etRemark.getText().toString().trim().isEmpty()) {
            tilRemark.setError(mContext.getString(R.string.err_msg_text));
            okButton.setEnabled(false);
            return false;
        } else {
            okButton.setEnabled(true);
        }
        return true;
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

        ArrayList<RechargeStatus> filterList = new ArrayList<>();
        if (charText.length() == 0) {
            filterList.addAll(rechargeStatus);
        } else {
            for (RechargeStatus wp : rechargeStatus) {
                if (wp.getType_().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getAccount().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filterList.add(wp);
                }
            }
        }

        transactionsList = filterList;
        notifyDataSetChanged();
    }

    void sendReport(String reportId) {
        if (mCustomAlertDialog != null) {
            mCustomAlertDialog.sendReportDialog(1, "", new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {

                }
            });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView balance, userName, operatorName, CommissionLabel;
        public AppCompatTextView lastbalance, bank, bankBal;
        public AppCompatTextView txn;
        public AppCompatTextView amount;
        public AppCompatTextView opid;
        public AppCompatTextView date, debit, comm;
        public AppCompatImageView operatorImage;
        public AppCompatTextView mobile;
        public AppCompatTextView status, statusDispute;
        public AppCompatTextView liveID, liveIdLabel;
        TextView source, outlet, outletNo;
        View dispute, w2r, bankView, bankBalView;
        View outletView, outletDetailView, liveIDView;
        ImageView statusIcon;
        View Share, print, disputeStatusView;

        public MyViewHolder(View view) {
            super(view);
            operatorName = view.findViewById(R.id.operatorName);
            userName = view.findViewById(R.id.userName);
            txn = view.findViewById(R.id.txn);
            balance = view.findViewById(R.id.balance);
            amount = view.findViewById(R.id.amount);
            source = view.findViewById(R.id.source);
            w2r = view.findViewById(R.id.w2r);
            outlet = view.findViewById(R.id.outlet);
            outletNo = view.findViewById(R.id.outletNo);
            outletView = view.findViewById(R.id.outletNameView);
            outletDetailView = view.findViewById(R.id.outletDetailView);
            date = view.findViewById(R.id.date);
            mobile = view.findViewById(R.id.mobile);
            liveID = view.findViewById(R.id.liveID);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveIDView = view.findViewById(R.id.liveIdView);
            status = view.findViewById(R.id.status);
            statusIcon = view.findViewById(R.id.statusIcon);
            statusDispute = view.findViewById(R.id.statusDispute);
            dispute = view.findViewById(R.id.dispute);
            Share = view.findViewById(R.id.share);
            print = view.findViewById(R.id.print);
            CommissionLabel = view.findViewById(R.id.CommissionLabel);
            operatorImage = view.findViewById(R.id.operatorImage);
            debit = view.findViewById(R.id.debit);
            comm = view.findViewById(R.id.comm);
            disputeStatusView = view.findViewById(R.id.disputeStatusView);
            lastbalance = view.findViewById(R.id.lastbalance);
            bank = view.findViewById(R.id.bank);
            bankView = view.findViewById(R.id.bankView);
            bankBal = view.findViewById(R.id.bankBal);
            bankBalView = view.findViewById(R.id.bankBalView);
        }
    }
}