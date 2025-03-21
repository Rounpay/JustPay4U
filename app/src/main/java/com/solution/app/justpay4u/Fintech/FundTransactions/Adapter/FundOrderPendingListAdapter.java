package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.FundRequestForApproval;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;


public class FundOrderPendingListAdapter extends RecyclerView.Adapter<FundOrderPendingListAdapter.MyViewHolder> implements Filterable {

    private final AppPreferences mAppPreferences;
    private final LoginResponse mLoginDataResponse;
    CustomAlertDialog mCustomAlertDialog;
    private List<FundRequestForApproval> listItem;
    private List<FundRequestForApproval> filterListItem;
    private Activity mContext;
    private AlertDialog alertDialogFundTransfer;


    public FundOrderPendingListAdapter(Activity mContext, List<FundRequestForApproval> mFundRequestForApprovalLists, AppPreferences mAppPreferences, LoginResponse mLoginDataResponse) {
        this.listItem = mFundRequestForApprovalLists;
        filterListItem = mFundRequestForApprovalLists;
        this.mContext = mContext;
        this.mAppPreferences = mAppPreferences;
        this.mLoginDataResponse = mLoginDataResponse;
        mCustomAlertDialog = new CustomAlertDialog();
    }

    @Override
    public FundOrderPendingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_fund_order_pending_report, parent, false);

        return new FundOrderPendingListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundOrderPendingListAdapter.MyViewHolder holder, final int position) {
        final FundRequestForApproval operator = filterListItem.get(position);

        holder.accountNumber.setText(operator.getAccountNo() + "");
        if (operator.getAccountNo() != null && !operator.getAccountNo().isEmpty()) {
            holder.accountNumber.setText(operator.getAccountNo() + "");
            holder.accountNumber.setVisibility(View.VISIBLE);
        } else {
            holder.accountNumber.setVisibility(View.GONE);
        }

        if (operator.getAccountHolder() != null && !operator.getAccountHolder().isEmpty()) {
            holder.acHolderName.setText(operator.getAccountHolder() + "");
            holder.acHolderName.setVisibility(View.VISIBLE);
        } else {
            holder.acHolderName.setVisibility(View.GONE);
        }

        if (operator.getBank() != null && !operator.getBank().isEmpty()) {
            holder.bankName.setText(operator.getBank() + "");
            holder.bankName.setVisibility(View.VISIBLE);
        } else {
            holder.bankName.setVisibility(View.GONE);
        }

        if (operator.getBranch() != null && !operator.getBranch().isEmpty()) {
            holder.branchName.setText(operator.getBranch() + "");
            holder.branchName.setVisibility(View.VISIBLE);
        } else {
            holder.branchName.setVisibility(View.GONE);
        }
        holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAmount()));
        holder.txnId.setText(operator.getTransactionId() + "");

        if (operator.getMode() != null && !operator.getMode().isEmpty()) {
            holder.transactionMode.setText(operator.getMode() + "");
            holder.transactionModeView.setVisibility(View.VISIBLE);
        } else {
            holder.transactionModeView.setVisibility(View.GONE);
        }

        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remark.setText(operator.getRemark() + "");
            holder.remarkView.setVisibility(View.VISIBLE);
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }


        if (operator.getMobileNo() != null && !operator.getMobileNo().isEmpty()) {
            holder.mobileTv.setText(operator.getMobileNo() + "");
            holder.mobileView.setVisibility(View.VISIBLE);
        } else {
            holder.mobileView.setVisibility(View.GONE);
        }
        holder.outletNameTv.setText(operator.getUserName() + "");
        holder.outletNoTv.setText(operator.getUserMobile() + "");

        if (operator.getChequeNo() != null && !operator.getChequeNo().isEmpty()) {
            holder.chequeNo.setText(operator.getChequeNo() + "");
            holder.chequeNoView.setVisibility(View.VISIBLE);
        } else {
            holder.chequeNoView.setVisibility(View.GONE);
        }

        if (operator.getCardNumber() != null && !operator.getCardNumber().isEmpty()) {
            holder.cardNo.setText(operator.getCardNumber() + "");
            holder.cardNoView.setVisibility(View.VISIBLE);
        } else {
            holder.cardNoView.setVisibility(View.GONE);
        }

        if (operator.getUpiid() != null && !operator.getUpiid().isEmpty()) {
            holder.upiId.setText(operator.getUpiid() + "");
            holder.upiIdView.setVisibility(View.VISIBLE);
        } else {
            holder.upiIdView.setVisibility(View.GONE);
        }


        if (operator.getDescription() != null && !operator.getDescription().isEmpty()) {
            holder.description.setText(operator.getDescription() + "");
            holder.descripionView.setVisibility(View.VISIBLE);
        } else {
            holder.descripionView.setVisibility(View.GONE);
        }
        if (operator.getDescription() != null && !operator.getDescription().isEmpty()) {
            holder.approveRejectDateTv.setText(operator.getApproveDate() + "");
            holder.aproveDateView.setVisibility(View.VISIBLE);
        } else {
            holder.aproveDateView.setVisibility(View.GONE);
        }

        holder.requestDateTv.setText(operator.getEntryDate());

        if (operator.getStatus() != null && !operator.getStatus().isEmpty()) {
            holder.statusView.setVisibility(View.VISIBLE);
            holder.statusTv.setText(operator.getStatus() + "");
            if (operator.getStatus().equalsIgnoreCase("Accepted")) {
                holder.statusIcon.setImageResource(R.drawable.ic_success);
                holder.approveRejectDateLabel.setText("Accepted Date");
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.green));
            } else if (operator.getStatus().equalsIgnoreCase("Rejected")) {
                holder.statusIcon.setImageResource(R.drawable.ic_failed);
                holder.approveRejectDateLabel.setText("Rejected Date");
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
            } else {
                holder.approveRejectDateLabel.setText("Pending Date");
                holder.statusIcon.setImageResource(R.drawable.ic_pending);
                holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_amber));
            }
        } else {
            holder.statusView.setVisibility(View.GONE);
        }


        holder.rejectView.setOnClickListener(v ->
                mCustomAlertDialog.showRejectDialog(mContext, operator.getUserId(), operator.getUserName(), operator.getMobileNo(),
                        operator.getAmount(), operator.getPaymentId(), mLoginDataResponse, mAppPreferences));

        holder.fundTransferView.setOnClickListener(v ->
                mCustomAlertDialog.showFundTransferDialog(mContext, operator.getUserId(), operator.getUserName(),
                        operator.getMobileNo(), operator.getCommRate(), operator.getAmount(), operator.getPaymentId(), mLoginDataResponse, mAppPreferences));


    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    /* private void showFundTransferDialog(final int uId, final String name, String mobile, final double commission, String amount, final int paymentId) {
         try {
             if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                 return;
             }


             AlertDialog.Builder dialogBuilder;
             dialogBuilder = new AlertDialog.Builder(mContext, R.style.alert_dialog_light);
             alertDialogFundTransfer = dialogBuilder.create();
             alertDialogFundTransfer.setCancelable(false);
             alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
             LayoutInflater inflater = mContext.getLayoutInflater();
             View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
             alertDialogFundTransfer.setView(dialogView);


             AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
             LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
             changeTypeView.setVisibility(View.GONE);
             final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
             final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
             final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
             final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
             AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
             AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
             final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
             final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
             amountTv.setVisibility(View.VISIBLE);
             amountEt.setVisibility(View.GONE);

             AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
             final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
             final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
             final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
             final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
             if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.DoubleFactorPref)) {
                 pinPassTv.setVisibility(View.VISIBLE);
                 pinPassEt.setVisibility(View.VISIBLE);
             } else {
                 pinPassTv.setVisibility(View.GONE);
                 pinPassEt.setVisibility(View.GONE);
             }
             AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
             AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
             nameTv.setText(name);
             mobileTv.setText(mobile);
             commisionTv.setText(commission + "");


             View creditDebitView = dialogView.findViewById(R.id.creditDebitView);

             if (mLoginDataResponse.getData().isCanDebit()) {
                 creditDebitView.setVisibility(View.VISIBLE);
             } else {
                 creditDebitView.setVisibility(View.GONE);
             }

             try {
                 double amountVal = Double.parseDouble(amount);

                 double calculatedVal = amountVal + ((amountVal * commission) / 100);
                 amountTxtTv.setText("\u20B9 " + calculatedVal);
             } catch (NumberFormatException nfe) {

             }
             amountTv.setText(amount);


             closeIv.setOnClickListener(v -> alertDialogFundTransfer.dismiss());

             cancelBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     alertDialogFundTransfer.dismiss();
                 }
             });

             fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                         pinPassEt.setError("Please Enter Pin Password");
                         pinPassEt.requestFocus();
                         return;
                     }
                     ((FundOrderPendingActivity) mContext).fundTransferApi(pinPassEt.getText().toString(), paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                 }
             });


             alertDialogFundTransfer.show();
             alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

         } catch (IllegalStateException ise) {

         } catch (IllegalArgumentException iae) {

         } catch (Exception e) {

         }
     }

     private void showRejectDialog(final int uId, final String name, String mobile, String amount, final int paymentId) {
         try {
             if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                 return;
             }

             AlertDialog.Builder dialogBuilder;
             dialogBuilder = new AlertDialog.Builder(mContext, R.style.alert_dialog_light);
             alertDialogFundTransfer = dialogBuilder.create();
             alertDialogFundTransfer.setCancelable(false);
             alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
             LayoutInflater inflater = mContext.getLayoutInflater();
             View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
             alertDialogFundTransfer.setView(dialogView);


             View creditDebitView = dialogView.findViewById(R.id.creditDebitView);


             if (mLoginDataResponse.getData().isCanDebit()) {
                 creditDebitView.setVisibility(View.VISIBLE);
             } else {
                 creditDebitView.setVisibility(View.GONE);
             }
             AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
             LinearLayout changeTypeView = dialogView.findViewById(R.id.changetTypeView);
             changeTypeView.setVisibility(View.GONE);

             final AppCompatTextView titleTv = dialogView.findViewById(R.id.titleTv);
             titleTv.setText("Fund Reject Form");
             final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
             final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
             final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
             final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
             AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
             AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
             final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
             final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
             final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
             final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
             if (mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.DoubleFactorPref)) {
                 pinPassTv.setVisibility(View.VISIBLE);
                 pinPassEt.setVisibility(View.VISIBLE);
             } else {
                 pinPassTv.setVisibility(View.GONE);
                 pinPassEt.setVisibility(View.GONE);
             }
             amountTv.setVisibility(View.VISIBLE);
             amountEt.setVisibility(View.GONE);

             AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
             commisionTv.setVisibility(View.GONE);
             AppCompatTextView commisionTitleTv = dialogView.findViewById(R.id.commisionTitleTv);
             commisionTitleTv.setVisibility(View.GONE);
             final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
             final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
             View totalAmtView = dialogView.findViewById(R.id.totalAmtView);
             totalAmtView.setVisibility(View.GONE);
             AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
             AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
             nameTv.setText(name);
             mobileTv.setText(mobile);

             amountTv.setText(amount);


             closeIv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     alertDialogFundTransfer.dismiss();
                 }
             });

             cancelBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     alertDialogFundTransfer.dismiss();
                 }
             });
             fundTransferBtn.setText("Reject");
             fundTransferBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                         pinPassEt.setError("Please Enter Pin Password");
                         pinPassEt.requestFocus();
                         return;
                     }
                     ((FundOrderPendingActivity) mContext).rejectApi(pinPassEt.getText().toString(), paymentId, uId, remarksEt.getText().toString(), amountTv.getText().toString(), name, alertDialogFundTransfer);
                 }
             });


             alertDialogFundTransfer.show();
             alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

         } catch (IllegalStateException ise) {
             ise.printStackTrace();
         } catch (IllegalArgumentException iae) {
             iae.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<FundRequestForApproval> filteredList = new ArrayList<>();
                    for (FundRequestForApproval row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) || row.getUserMobile().toLowerCase().contains(charString.toLowerCase()) || row.getUserName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getAmount().toLowerCase().contains(charString.toLowerCase()) || row.getBank().toLowerCase().contains(charString.toLowerCase()) || row.getAccountHolder().toLowerCase().contains(charString.toLowerCase())
                                || row.getMode().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<FundRequestForApproval>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView operatorImage;
        private AppCompatTextView bankName;
        private AppCompatTextView branchName;
        private AppCompatTextView acHolderName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView amount;
        private LinearLayout transactionDetailView;
        private LinearLayout transactionIdView;
        private AppCompatTextView transactionIdLabel;
        private AppCompatTextView txnId;
        private LinearLayout transactionModeView;
        private AppCompatTextView transactionModeLabel;
        private AppCompatTextView transactionMode;
        private LinearLayout liveIdView;
        private AppCompatTextView liveIdLabel;
        private AppCompatTextView liveID;
        private LinearLayout mobileView;
        private AppCompatTextView mobileNoLabel;
        private AppCompatTextView mobileTv;
        private LinearLayout chequeNoView;
        private AppCompatTextView chequeNoLabel;
        private AppCompatTextView chequeNo;
        private LinearLayout cardNoView;
        private AppCompatTextView cardNoLabel;
        private AppCompatTextView cardNo;
        private LinearLayout upiIdView;
        private AppCompatTextView upiIdLabel;
        private AppCompatTextView upiId;
        private LinearLayout remarkView;
        private AppCompatTextView remarkLabel;
        private AppCompatTextView remark;
        private LinearLayout descripionView;
        private AppCompatTextView descriptionLabel;
        private AppCompatTextView description;
        private LinearLayout joinByView;
        private AppCompatImageView joinByIcon;
        private AppCompatTextView requestDateLabel;
        private AppCompatTextView requestDateTv;
        private LinearLayout joinDateView;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView approveRejectDateLabel;
        private AppCompatTextView approveRejectDateTv;
        private LinearLayout statusView;
        private AppCompatImageView statusIcon;
        private AppCompatTextView statusLabel;
        private AppCompatTextView statusTv;
        private LinearLayout rejectView;
        private LinearLayout fundTransferView;
        private AppCompatTextView fundTransferTv, outletNameTv, outletNoTv, senderNoTv;
        private View aproveDateView, outletNameView, outletNoView, senderNoView;

        public MyViewHolder(View view) {
            super(view);
            operatorImage = view.findViewById(R.id.operatorImage);
            bankName = view.findViewById(R.id.bankName);
            branchName = view.findViewById(R.id.branchName);
            acHolderName = view.findViewById(R.id.acHolderName);
            accountNumber = view.findViewById(R.id.accountNumber);
            amount = view.findViewById(R.id.amount);
            transactionDetailView = view.findViewById(R.id.transactionDetailView);
            transactionIdView = view.findViewById(R.id.transactionIdView);
            transactionIdLabel = view.findViewById(R.id.transactionIdLabel);
            txnId = view.findViewById(R.id.txnId);
            transactionModeView = view.findViewById(R.id.transactionModeView);
            transactionModeLabel = view.findViewById(R.id.transactionModeLabel);
            transactionMode = view.findViewById(R.id.transactionMode);
            liveIdView = view.findViewById(R.id.liveIdView);
            liveIdLabel = view.findViewById(R.id.liveIdLabel);
            liveID = view.findViewById(R.id.liveID);
            mobileView = view.findViewById(R.id.mobileView);
            mobileNoLabel = view.findViewById(R.id.mobileNoLabel);
            mobileTv = view.findViewById(R.id.mobileTv);
            chequeNoView = view.findViewById(R.id.chequeNoView);
            chequeNoLabel = view.findViewById(R.id.chequeNoLabel);
            chequeNo = view.findViewById(R.id.chequeNo);
            cardNoView = view.findViewById(R.id.cardNoView);
            cardNoLabel = view.findViewById(R.id.cardNoLabel);
            cardNo = view.findViewById(R.id.cardNo);
            upiIdView = view.findViewById(R.id.upiIdView);
            upiIdLabel = view.findViewById(R.id.upiIdLabel);
            upiId = view.findViewById(R.id.upiId);
            remarkView = view.findViewById(R.id.remarkView);
            remarkLabel = view.findViewById(R.id.remarkLabel);
            remark = view.findViewById(R.id.remark);
            descripionView = view.findViewById(R.id.descripionView);
            descriptionLabel = view.findViewById(R.id.descriptionLabel);
            description = view.findViewById(R.id.description);
            joinByView = view.findViewById(R.id.joinByView);
            joinByIcon = view.findViewById(R.id.joinByIcon);
            requestDateLabel = view.findViewById(R.id.requestDateLabel);
            requestDateTv = view.findViewById(R.id.requestDateTv);
            joinDateView = view.findViewById(R.id.joinDateView);
            calanderIcon = view.findViewById(R.id.calanderIcon);
            approveRejectDateLabel = view.findViewById(R.id.approveRejectDateLabel);
            approveRejectDateTv = view.findViewById(R.id.approveRejectDateTv);
            statusView = view.findViewById(R.id.statusView);
            statusIcon = view.findViewById(R.id.statusIcon);
            statusLabel = view.findViewById(R.id.statusLabel);
            statusTv = view.findViewById(R.id.statusTv);
            rejectView = view.findViewById(R.id.rejectView);
            fundTransferView = view.findViewById(R.id.fundTransferView);
            fundTransferTv = view.findViewById(R.id.fundTransferTv);
            aproveDateView = view.findViewById(R.id.aproveDateView);
            outletNameView = view.findViewById(R.id.outletNameView);
            outletNoView = view.findViewById(R.id.outletNoView);
            senderNoView = view.findViewById(R.id.senderNoView);
            senderNoView.setVisibility(View.GONE);
            outletNameTv = view.findViewById(R.id.outletName);
            outletNoTv = view.findViewById(R.id.outletNo);
            senderNoTv = view.findViewById(R.id.senderNo);
        }


    }

}
