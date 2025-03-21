package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AscReport;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AccountStatementServiceWiseActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.ChannelReportActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FOSAccStmtAndCollActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosReportActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

public class FOSChannelReportAdapter extends RecyclerView.Adapter<FOSChannelReportAdapter.MyViewHolder> implements Filterable {


    private final boolean isFlatCommission;
    double calculatedTransferAmtVal = 0;
    LoginResponse mLoginDataResponse;
    CustomLoader loader;
    FundTransferCallBAck mFundTransferCallBAck;
    String deviceId;
    String deviceSerialNum;
    AppPreferences mAppPreferences;
    String filterFromDate;
    String filterToDate;
    private int walletType = 1;
    private ArrayList<AscReport> filterListItem = new ArrayList<>();
    private ArrayList<AscReport> listItem = new ArrayList<>();
    private Activity mContext;
    private boolean oType;
    private AlertDialog alertDialogFundTransfer;

    public FOSChannelReportAdapter(String filterFromDate, String filterToDate, ArrayList<AscReport> transactionsList, CustomLoader loader, LoginResponse mLoginDataResponse,
                                   Context mContext, String deviceId, String deviceSerialNum, AppPreferences mAppPreferences, FundTransferCallBAck mFundTransferCallBAck) {
        this.mContext = (Activity) mContext;
        this.filterListItem = transactionsList;
        this.listItem = transactionsList;
        this.mFundTransferCallBAck = mFundTransferCallBAck;
        this.loader = loader;
        this.mLoginDataResponse = mLoginDataResponse;
        this.deviceSerialNum = deviceSerialNum;
        this.deviceId = deviceId;
        this.mAppPreferences = mAppPreferences;
        this.filterFromDate = filterFromDate;
        this.filterToDate = filterToDate;

        isFlatCommission = ApiFintechUtilMethods.INSTANCE.isFlatCommission(mAppPreferences);
    }


    public void setDate(String filterFromDate, String filterToDate) {
        this.filterFromDate = filterFromDate;
        this.filterToDate = filterToDate;
    }

    @Override
    public FOSChannelReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(/*R.layout.adapter_fosaccledger_report,*/R.layout.adapter_fos_channel_report, parent, false);

        return new FOSChannelReportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FOSChannelReportAdapter.MyViewHolder holder, int position) {
        final AscReport operator = filterListItem.get(position);


        holder.openingValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getOpening() + ""));
        // holder.purchaseValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPurchase() + ""));
        holder.salesValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getSales() + ""));
        holder.cCollectionValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getcCollection() + ""));
        holder.closingValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getClosing() + ""));
        holder.outletNameValue.setText(operator.getOutletName() + "");
        holder.mobileValue.setText(operator.getMobile() + "");
        holder.lsaleValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getLsale() + ""));
        holder.lCollectionValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getlCollection() + ""));
        holder.lsDateValue.setText(operator.getLsDate() + "");
        holder.lcDateValue.setText(operator.getLcDate() + "");
        if (operator.isPrepaid()) {
            holder.balanceValue.setVisibility(View.VISIBLE);
        } else {
            holder.balanceValue.setVisibility(View.GONE);
        }
        if (operator.isUtility()) {
            holder.uBalanceValue.setVisibility(View.VISIBLE);
        } else {
            holder.uBalanceValue.setVisibility(View.GONE);
        }
        holder.balanceValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.uBalanceValue.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getuBalance() + ""));


        //   Log.d(getClass().getSimpleName(),"USER_ID : "+userList.get(position).getId());

        holder.fundTransferView.setOnClickListener(v -> {

            if (isFlatCommission) {
                ApiFintechUtilMethods.INSTANCE.GeUserCommissionRate(mContext, operator.getUserID(), loader, deviceId, deviceSerialNum,
                        mLoginDataResponse, object -> {
                            BasicResponse mBasicResponse = (BasicResponse) object;
                            showFundTransferDialog(operator.getRoleID(), operator.getUserID(), operator.getOutletName(), operator.getMobile(),
                                    mBasicResponse.getCommRate());
                        });
            } else {
                showFundTransferDialog(operator.getRoleID(), operator.getUserID(), operator.getOutletName(), operator.getMobile(),
                        operator.getCommission());
            }
        });

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 0);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
        holder.infoSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 4);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
        holder.infoCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AccountStatementServiceWiseActivity.class);
                intent.putExtra("Mobile", operator.getMobile() + "");
                intent.putExtra("ServiceId", 44);
                intent.putExtra("FilterFromDate", filterFromDate);
                intent.putExtra("FilterToDate", filterToDate);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });

        holder.collectionView.setOnClickListener(v -> {

            if (mContext instanceof FosReportActivity) {
                ((FosReportActivity) mContext).FosCollectionActivity(operator);
            }
            if (mContext instanceof ChannelReportActivity) {
                ((ChannelReportActivity) mContext).FosCollectionActivity(operator);
            }
            if (mContext instanceof FOSAccStmtAndCollActivity) {
                ((FOSAccStmtAndCollActivity) mContext).FosCollectionActivity(operator);
            }

           /* Intent i = new Intent(mContext, FosCollectionActivity.class);
            i.putExtra("userID", operator.getUserID() + "");
            i.putExtra("outletName", operator.getOutletName() + "");
            i.putExtra("mobile", operator.getMobile() + "");
            mContext.startActivity(i);*/

        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    ArrayList<AscReport> filteredList = new ArrayList<>();
                    for (AscReport row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getMobile() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getOutletName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getArea() + "").toLowerCase().contains(charString.toLowerCase())
                        ) {
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
                filterListItem = (ArrayList<AscReport>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void showFundTransferDialog(int roleId, final int uId, final String name, String mobile, final double commission) {
        try {
            if (alertDialogFundTransfer != null && alertDialogFundTransfer.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext, R.style.alert_dialog_light);
            alertDialogFundTransfer = dialogBuilder.create();
            alertDialogFundTransfer.setCancelable(false);
            //alertDialogFundTransfer.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fund_transfer, null);
            alertDialogFundTransfer.setView(dialogView);

            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            SwitchCompat actCreditSwitch = dialogView.findViewById(R.id.actCreditSwitch);
            if (mLoginDataResponse.isAccountStatement()) {
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            } else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            LinearLayout changetTypeView = dialogView.findViewById(R.id.changetTypeView);
            final AppCompatTextView prepaidTv = dialogView.findViewById(R.id.prepaidTv);
            final AppCompatTextView utilityTv = dialogView.findViewById(R.id.utilityTv);
            final AppCompatTextView creditTv = dialogView.findViewById(R.id.creditTv);
            final AppCompatTextView debitTv = dialogView.findViewById(R.id.debitTv);
            AppCompatTextView nameTv = dialogView.findViewById(R.id.nameTv);
            AppCompatTextView mobileTv = dialogView.findViewById(R.id.mobileTv);
            final AppCompatEditText amountEt = dialogView.findViewById(R.id.amountEt);
            final AppCompatTextView amountTv = dialogView.findViewById(R.id.amountTv);
            amountTv.setVisibility(View.GONE);
            amountEt.setVisibility(View.VISIBLE);
            AppCompatEditText commisionTv = dialogView.findViewById(R.id.commisionTv);
            if (isFlatCommission) {
                commisionTv.setFocusableInTouchMode(false);
                commisionTv.setFocusable(false);

            } else {
                commisionTv.setFocusableInTouchMode(true);
                commisionTv.setFocusable(true);
            }
            final AppCompatEditText remarksEt = dialogView.findViewById(R.id.remarksEt);
            final AppCompatTextView amountTxtTv = dialogView.findViewById(R.id.amountTxtTv);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView fundTransferBtn = dialogView.findViewById(R.id.fundTransferBtn);
            final AppCompatTextView pinPassTv = dialogView.findViewById(R.id.pinPassTv);
            final AppCompatEditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
            if (mLoginDataResponse.getData().getIsDoubleFactor()) {
                pinPassTv.setVisibility(View.VISIBLE);
                pinPassEt.setVisibility(View.VISIBLE);
            } else {
                pinPassTv.setVisibility(View.GONE);
                pinPassEt.setVisibility(View.GONE);
            }

            View creditDebitView = dialogView.findViewById(R.id.creditDebitView);

            if (mLoginDataResponse.getData().isCanDebit()) {
                creditDebitView.setVisibility(View.VISIBLE);
            } else {
                creditDebitView.setVisibility(View.GONE);
                oType = false;
            }


            View walletTypeView = dialogView.findViewById(R.id.walletTypeView);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            BalanceResponse mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);

            if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                    mBalanceResponse.getBalanceData().size() > 0) {
                ArrayList<BalanceData> mWalletTypesList = new ArrayList<>();
                for (BalanceData mWalletType : mBalanceResponse.getBalanceData()) {
                    if (mWalletType.isInFundProcess()) {
                        if (roleId == 3) {
                            if (mWalletType.getId() == 6) {
                                if (mWalletType.isPackageDedectionForRetailor()) {
                                    mWalletTypesList.add(mWalletType);
                                }
                            } else {
                                mWalletTypesList.add(mWalletType);
                            }
                        } else {
                            mWalletTypesList.add(mWalletType);
                        }


                    }
                }
                if (mWalletTypesList.size() <= 2 && creditDebitView.getVisibility() == View.VISIBLE) {
                    changetTypeView.setOrientation(LinearLayout.HORIZONTAL);
                } else {
                    changetTypeView.setOrientation(LinearLayout.VERTICAL);
                }

                walletTypeView.setVisibility(View.GONE);
                recyclerView.setAdapter(new WalletTypeAdapter(mContext, mWalletTypesList, id -> walletType = id));

                if (mWalletTypesList.size() == 1) {
                    walletType = mWalletTypesList.get(0).getId();
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                walletTypeView.setVisibility(View.VISIBLE);
            }


            nameTv.setText(name);
            mobileTv.setText(mobile);
            commisionTv.setText(Utility.INSTANCE.formatedAmountWithOutRupees(commission + "") + " %");
            oType = false;
            walletType = 1;

            prepaidTv.setOnClickListener(v -> {
                prepaidTv.setBackgroundResource(R.drawable.rounded_light_green);
                prepaidTv.setTextColor(Color.WHITE);
                utilityTv.setBackgroundResource(0);
                utilityTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                walletType = 1;
            });

            utilityTv.setOnClickListener(v -> {
                utilityTv.setBackgroundResource(R.drawable.rounded_light_green);
                utilityTv.setTextColor(Color.WHITE);
                prepaidTv.setBackgroundResource(0);
                prepaidTv.setTextColor(mContext.getResources().getColor(R.color.lightDarkGreen));
                walletType = 2;
            });

            creditTv.setOnClickListener(v -> {
                creditTv.setBackgroundResource(R.drawable.rounded_light_red);
                creditTv.setTextColor(Color.WHITE);
                debitTv.setBackgroundResource(0);
                debitTv.setTextColor(mContext.getResources().getColor(R.color.red));
                oType = false;
            });

            debitTv.setOnClickListener(v -> {
                debitTv.setBackgroundResource(R.drawable.rounded_light_red);
                debitTv.setTextColor(Color.WHITE);
                creditTv.setBackgroundResource(0);
                creditTv.setTextColor(mContext.getResources().getColor(R.color.red));
                oType = true;
            });


            closeIv.setOnClickListener(v -> alertDialogFundTransfer.dismiss());

            cancelBtn.setOnClickListener(v -> alertDialogFundTransfer.dismiss());

            fundTransferBtn.setOnClickListener(v -> {
                if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError(mContext.getResources().getString(R.string.err_empty_field));
                    amountEt.requestFocus();
                    return;
                } else if (pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
                    pinPassEt.setError("Please Enter Pin Password");
                    pinPassEt.requestFocus();
                    return;
                }
                ApiFintechUtilMethods.INSTANCE.fundTransferApi(mContext, actCreditSwitch.isChecked(), pinPassEt.getText().toString(), 0, oType, uId,
                        remarksEt.getText().toString(), walletType, /*amountEt.getText().toString()*/calculatedTransferAmtVal + "",
                        name, alertDialogFundTransfer, loader, mLoginDataResponse, deviceId, deviceSerialNum, object -> {
                            if (mFundTransferCallBAck != null) {
                                mFundTransferCallBAck.onSucessFund();
                            }
                        });
            });


            amountEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        try {
                            long amountVal = Long.parseLong(s.toString());

                            calculatedTransferAmtVal = amountVal + ((amountVal * commission) / 100);
                            amountTxtTv.setText(Utility.INSTANCE.formatedAmountWithRupees(calculatedTransferAmtVal + ""));
                        } catch (NumberFormatException nfe) {

                        }
                    } else {
                        calculatedTransferAmtVal = 0;
                        amountTxtTv.setText("\u20B9 " + 0);
                    }
                }
            });

            alertDialogFundTransfer.show();
            alertDialogFundTransfer.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        } catch (
                IllegalStateException ise) {

        } catch (
                IllegalArgumentException iae) {

        } catch (
                Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return filterListItem.size();
    }


    public interface FundTransferCallBAck {
        void onSucessFund();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView /*openingTv,*/ openingValue, /*purchaseTv,*/ /*purchaseValue,*/ /*salesTv, */
                salesValue, /*cCollectionTv,*/
                cCollectionValue,
        /*closingTv,*/ closingValue, /*outletNameTv,*/
                outletNameValue,/* mobileTv,*/
                mobileValue, /*lsaleTv, */
                lsaleValue,
        /*lCollectionTv,*/ lCollectionValue,/* lsDateTv,*/
                lsDateValue, /*lcDateTv,*/
                lcDateValue, /*balanceTv,*/
                balanceValue, /*uBalanceTv,*/
                uBalanceValue;
        View remarkView, info, infoCol, infoSale;
        private View fundTransferView, collectionView;


        public MyViewHolder(View view) {
            super(view);

            /*openingTv = view.findViewById(R.id.openingTv);*/
            openingValue = view.findViewById(R.id.openingValue);
            /*purchaseTv = view.findViewById(R.id.purchaseTv);*/
            /*purchaseValue = view.findViewById(R.id.purchaseValue);*/
            /*salesTv = view.findViewById(R.id.salesTv);*/
            salesValue = view.findViewById(R.id.salesValue);
            /*cCollectionTv = view.findViewById(R.id.cCollectionTv);*/
            cCollectionValue = view.findViewById(R.id.cCollectionValue);
            /*closingTv = view.findViewById(R.id.closingTv);*/
            closingValue = view.findViewById(R.id.closingValue);
            /*outletNameTv = view.findViewById(R.id.outletNameTv);*/
            outletNameValue = view.findViewById(R.id.outletNameValue);
            /*mobileTv = view.findViewById(R.id.mobileTv);*/
            mobileValue = view.findViewById(R.id.mobileValue);
            /*lsaleTv = view.findViewById(R.id.lsaleTv);*/
            lsaleValue = view.findViewById(R.id.lsaleValue);
            /*lCollectionTv = view.findViewById(R.id.lCollectionTv);*/
            lCollectionValue = view.findViewById(R.id.lCollectionValue);
            /*lsDateTv = view.findViewById(R.id.lsDateTv);*/
            lsDateValue = view.findViewById(R.id.lsDateValue);
            /*lcDateTv = view.findViewById(R.id.lcDateTv);*/
            lcDateValue = view.findViewById(R.id.lcDateValue);
            /*balanceTv = view.findViewById(R.id.balanceTv);*/
            balanceValue = view.findViewById(R.id.balanceValue);
            /*uBalanceTv = view.findViewById(R.id.uBalanceTv);*/
            uBalanceValue = view.findViewById(R.id.uBalanceValue);
            remarkView = view.findViewById(R.id.remarkView);
            fundTransferView = view.findViewById(R.id.fundTransferView);
            collectionView = view.findViewById(R.id.collectionView);
            info = view.findViewById(R.id.info);
            infoCol = view.findViewById(R.id.infoCol);
            infoSale = view.findViewById(R.id.infoSale);

        }
    }
}
