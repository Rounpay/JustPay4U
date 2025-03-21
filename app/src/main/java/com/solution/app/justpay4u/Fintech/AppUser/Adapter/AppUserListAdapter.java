package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.UserList;
import com.solution.app.justpay4u.Api.Fintech.Request.FundTransferRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BasicResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Activities.UpgradePackageActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosUserListActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Fragment.AppUserListFragment;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class AppUserListAdapter extends RecyclerView.Adapter<AppUserListAdapter.MyViewHolder> implements Filterable {

    private final String deviceId, deviceSerialNum;
    FundTransferCallBAck mFundTransferCallBAck;
    CustomLoader loader;
    double calculatedTransferAmtVal = 0;
    LoginResponse mLoginDataResponse;
    AppPreferences mAppPreferences;
    int gridSize;
    AppUserListFragment appUserListFragment;
    boolean isFlatCommision;
    private ArrayList<UserList> listItem;
    private ArrayList<UserList> filterListItem;
    private Activity mContext;
    private AlertDialog alertDialogFundTransfer;
    private boolean oType;
    private int walletType = 1;
    private ArrayList<BalanceData> mBalanceDataList = new ArrayList<>();

    public AppUserListAdapter(Activity mContext, ArrayList<UserList> mUserLists, int gridSize, LoginResponse mLoginDataResponse,
                              AppPreferences mAppPreferences, AppUserListFragment appUserListFragment,
                              FundTransferCallBAck mFundTransferCallBAck) {
        this.listItem = mUserLists;
        this.filterListItem = mUserLists;
        this.mLoginDataResponse = mLoginDataResponse;
        this.mFundTransferCallBAck = mFundTransferCallBAck;
        this.mAppPreferences = mAppPreferences;
        this.mContext = mContext;
        this.gridSize = gridSize;
        this.appUserListFragment = appUserListFragment;
        isFlatCommision = ApiFintechUtilMethods.INSTANCE.isFlatCommission(mAppPreferences);
        deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
        deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public void setBalanceData(ArrayList<BalanceData> mBalanceDataList) {
        this.mBalanceDataList = mBalanceDataList;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    @Override
    public AppUserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_app_user, parent, false);

        return new AppUserListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppUserListAdapter.MyViewHolder holder, final int position) {
        final UserList mItem = filterListItem.get(position);
        holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mItem.getBalanceTypes()));
        /*if (mItem.getBalanceTypes() != null && mItem.getBalanceTypes().size() > 0) {
            if(holder.balRecycerView.getLayoutManager()!=null &&holder.balRecycerView.getLayoutManager()  instanceof GridLayoutManager) {
                holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mItem.getBalanceTypes()));
            }else {
            if(mItem.getBalanceTypes().size()>0 && mItem.getBalanceTypes().size()<=3 ){
                holder. balRecycerView.setLayoutManager(new GridLayoutManager(mContext, mItem.getBalanceTypes().size()));
            }else {
                holder. balRecycerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            }
                holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mItem.getBalanceTypes()));
            }
        } else {
            ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
            if (mBalanceData.isBalance()) {
                mBalanceTypes.add(new BalanceType("Prepaid Balance", mItem.getBalance()));
            }
            if (mBalanceData.getIsUBalance()) {
                mBalanceTypes.add(new BalanceType("Utiity Balance", mItem.getuBalance()));
            }
            if (mBalanceData.getIsBBalance()) {
                mBalanceTypes.add(new BalanceType("Bank Balance", mItem.getbBalance()));
            }
            if (mBalanceData.getIsCBalance()) {
                mBalanceTypes.add(new BalanceType("Card Balance", mItem.getcBalance()));
            }
            if (mBalanceData.getIsIDBalance()) {
                mBalanceTypes.add(new BalanceType("ID Balance", mItem.getIdBalnace()));
            }
            if (mBalanceData.getIsPacakgeBalance() && mItem.getRoleID()!=3) {
                mBalanceTypes.add(new BalanceType("Package Balance", mItem.getPacakgeBalance()));
            }
            if (mLoginDataResponse.isAccountStatement()) {
                mBalanceTypes.add(new BalanceType("Outstanding Balance", mItem.getOsBalance()));
            }
            mItem.setBalanceTypes(mBalanceTypes);
            if(mBalanceTypes.size()>0 && mBalanceTypes.size()<=3 ){
                holder. balRecycerView.setLayoutManager(new GridLayoutManager(mContext, mBalanceTypes.size()));
            }else {
                holder. balRecycerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            }
            holder.balRecycerView.setAdapter(new AppUserBalanceAdapter(mContext, mBalanceTypes));
        }*/
        // holder.balanceTv.setText(Utility.INSTANCE.formatedAmountWithRupees(mItem.getBalance()+""));

        if (mItem.getRoleID() == 3) {
            holder.upgradePackageView.setVisibility(View.VISIBLE);
        } else {
            holder.upgradePackageView.setVisibility(View.GONE);
        }

        if (mItem.getRoleID() == 8 && mLoginDataResponse.isAccountStatement()) {
            holder.collectionView.setVisibility(View.VISIBLE);
        } else {
            holder.collectionView.setVisibility(View.GONE);
        }
        holder.dateTv.setText(mItem.getJoinDate());
        holder.joinByTv.setText(mItem.getJoinBy());
        if (mItem.getbCapping() != 0) {
            holder.cappingTv.setText(Utility.INSTANCE.formatedAmountWithOutRupees(mItem.getbCapping() + ""));
        } else {
            holder.cappingView.setVisibility(View.GONE);
        }
        if (mItem.getSlab() != null) {
            if (mItem.getSlab().contains("Level")) {
                holder.slabView.setVisibility(View.GONE);
            } else {
                holder.slabView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.slabView.setVisibility(View.GONE);
        }

        holder.slabTv.setText(mItem.getSlab() + "");
        holder.mobileTv.setText(mItem.getMobileNo());
        holder.ouyletNameTv.setText(mItem.getOutletName() + "[ " + mItem.getPrefix() + mItem.getId() + " ]");

        holder.kycStatusTv.setText(mItem.getKycStatusStr());
        //GradientDrawable bgShape = (GradientDrawable) holder.kycStatusTv.getBackground();

        if (mItem.getKycStatus() == 1 || mItem.getKycStatusStr().equalsIgnoreCase("NOT APPLIED")) {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.grey));
        } else if (mItem.getKycStatus() == 2 || mItem.getKycStatusStr().equalsIgnoreCase("NOT VERIFIED")) {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else if (mItem.getKycStatus() == 3 || mItem.getKycStatusStr().equalsIgnoreCase("VERIFIED")) {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else if (mItem.getKycStatus() == 3 || mItem.getKycStatusStr().equalsIgnoreCase("COMPLETED")) {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (mItem.getKycStatus() == 4 || mItem.getKycStatusStr().equalsIgnoreCase("APPLIED")) {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.color_orange));
        } else {
            holder.kycStatusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
        if (mItem.isOTP()) {
            holder.otpStatusTv.setText("Enable");
            holder.otpStatusTv.setTextColor(mContext.getResources().getColor(R.color.darkGreen));
        } else {
            holder.otpStatusTv.setText("Disable");
            holder.otpStatusTv.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }
        if (mItem.isStatus()) {
            holder.activeSwitch.setChecked(true);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.activeTv.setText("Active");
        } else {
            holder.activeSwitch.setChecked(false);
            holder.activeTv.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.activeTv.setText("Inactive");
        }

        holder.fundTransferView.setOnClickListener(v -> {
            if (isFlatCommision) {
                ApiFintechUtilMethods.INSTANCE.GeUserCommissionRate(mContext, mItem.getId(), loader, deviceId, deviceSerialNum, mLoginDataResponse, object -> {
                    BasicResponse mBasicResponse = (BasicResponse) object;
                    showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(),
                            mBasicResponse.getCommRate());
                });
            } else {
                showFundTransferDialog(mItem.getRoleID(), mItem.getId(), holder.ouyletNameTv.getText().toString(), mItem.getMobileNo(), mItem.getCommRate());
            }


        });
        holder.upgradePackageView.setOnClickListener(view ->
                mContext.startActivity(new Intent(mContext, UpgradePackageActivity.class)
                        .putExtra("UID", mItem.getId() + "")
                        .putExtra("BENE_NAME", mItem.getOutletName() + " (" + mItem.getRole() + ")")
                        .putExtra("BENE_MOBILE", mItem.getMobileNo())
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
        holder.collectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appUserListFragment != null) {
                    appUserListFragment.CollectionFromFosActivity(mItem);
                }
                if (mContext instanceof FosUserListActivity) {
                    ((FosUserListActivity) mContext).CollectionFromFosActivity(mItem);
                }
            }
        });
        holder.switchView.setOnClickListener(v -> changeStatusApi(false, "", mItem.getId(), position, holder.activeSwitch, holder.activeTv, holder.ouyletNameTv.getText().toString()));

        holder.callView.setOnClickListener(v -> {
            try {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + mItem.getMobileNo() + ""));
                mContext.startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + mItem.getMobileNo() + ""));
                mContext.startActivity(dialIntent);
            }
        });

        holder.whatsappView.setOnClickListener(v -> openWhatsapp(mContext, mItem.getMobileNo() + ""));
    }


    @Override
    public int getItemCount() {
        return filterListItem.size();
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

            SwitchCompat actCreditSwitch = dialogView.findViewById(R.id.actCreditSwitch);
            if (mLoginDataResponse.isAccountStatement()) {
                actCreditSwitch.setVisibility(View.VISIBLE);
                actCreditSwitch.setChecked(true);
            } else {
                actCreditSwitch.setVisibility(View.GONE);
            }
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
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
            if (isFlatCommision) {
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

            if (mBalanceDataList == null || mBalanceDataList.size() == 0) {
                BalanceResponse mBalanceResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
                if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null &&
                        mBalanceResponse.getBalanceData().size() > 0) {
                    mBalanceDataList = mBalanceResponse.getBalanceData();
                }
            }
            if (mBalanceDataList != null && mBalanceDataList.size() > 0) {
                ArrayList<BalanceData> mWalletTypesList = new ArrayList<>();
                for (BalanceData mWalletType : mBalanceDataList) {
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

    public void changeStatusApi(boolean isMarkCredit, String securityKey, int uid, final int position, final SwitchCompat switchView, final TextView activeText, final String name) {
        try {

            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.ChangeUserStatus(new FundTransferRequest(isMarkCredit, securityKey, false, uid,
                    "", 0, 0, "", mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ApiFintechUtilMethods.INSTANCE.Successful(mContext, data.getMsg().replace("{User}", name));
                                    if (switchView.isChecked()) {
                                        switchView.setChecked(false);
                                        activeText.setText("Inactive");
                                        activeText.setTextColor(mContext.getResources().getColor(R.color.red));
                                        filterListItem.get(position).setStatus(false);
                                        for (int i = 0; i < listItem.size(); i++) {
                                            if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                                listItem.get(i).setStatus(false);
                                                break;
                                            }
                                        }
                                    } else {
                                        switchView.setChecked(true);
                                        activeText.setTextColor(mContext.getResources().getColor(R.color.green));
                                        activeText.setText("Active");
                                        filterListItem.get(position).setStatus(true);
                                        for (int i = 0; i < listItem.size(); i++) {
                                            if (listItem.get(i).getId() == filterListItem.get(position).getId()) {
                                                listItem.get(i).setStatus(true);
                                                break;
                                            }
                                        }
                                    }
                                    notifyDataSetChanged();
                                } else {

                                    ApiFintechUtilMethods.INSTANCE.Error(mContext, data.getMsg().replace("{User}", name));
                                }

                            } else {

                                ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        ApiFintechUtilMethods.INSTANCE.apiFailureError(mContext, t);

                    } catch (IllegalStateException ise) {

                        ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            ApiFintechUtilMethods.INSTANCE.Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
        }

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
                    ArrayList<UserList> filteredList = new ArrayList<>();
                    for (UserList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) || row.getOutletName().toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBalance() + "").toLowerCase().contains(charString.toLowerCase()) || row.getSlab().toLowerCase().contains(charString.toLowerCase()) || row.getJoinBy().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<UserList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void openWhatsapp(Activity mContext, String smsNumber) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_MAIN);
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            // sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
            sendIntent.putExtra("jid", "91" + smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            mContext.startActivity(sendIntent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                if (mContext.getPackageManager().resolveActivity(intent, 0) != null) {
                    mContext.startActivity(intent);
                } else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                    mContext.startActivity(intent);
                }
            } catch (Exception ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "91" + smsNumber)));
                mContext.startActivity(intent);

            }
        }

    }

    public interface FundTransferCallBAck {
        void onSucessFund();

        void onSucessEdit();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView balRecycerView;
        private RelativeLayout mainView;
        private LinearLayout outletNameView;
        private AppCompatTextView ouyletNameTv;
        private LinearLayout mobileView;
        private AppCompatTextView mobileTv;
        private LinearLayout slabView;
        private AppCompatTextView slabTv, cappingTv;
        private LinearLayout joinByView, switchView;
        private AppCompatTextView joinByTv;
        private AppCompatImageView calanderIcon;
        private AppCompatTextView dateTv;
        private LinearLayout rightView;
        private SwitchCompat activeSwitch;
        private AppCompatTextView activeTv;
        private AppCompatTextView otpStatusTv;
        private AppCompatTextView kycStatusTv;
        /*private AppCompatTextView balanceTv;*/
        private View editView, upgradePackageView, fundTransferView, collectionView, cappingView, callView, whatsappView;

        public MyViewHolder(View view) {
            super(view);
            mainView = view.findViewById(R.id.mainView);
            outletNameView = view.findViewById(R.id.outletNameView);
            ouyletNameTv = view.findViewById(R.id.outletNameTv);
            mobileView = view.findViewById(R.id.mobileView);
            mobileTv = view.findViewById(R.id.mobileTv);
            slabView = view.findViewById(R.id.slabView);
            slabTv = view.findViewById(R.id.slabTv);
            joinByView = view.findViewById(R.id.joinByView);
            joinByTv = view.findViewById(R.id.joinByTv);
            calanderIcon = view.findViewById(R.id.calanderIcon);
            dateTv = view.findViewById(R.id.dateTv);
            rightView = view.findViewById(R.id.rightView);
            activeSwitch = view.findViewById(R.id.activeSwitch);
            activeTv = view.findViewById(R.id.activeTv);
            otpStatusTv = view.findViewById(R.id.otpStatusTv);
            kycStatusTv = view.findViewById(R.id.kycStatusTv);
            //  balanceTv = view.findViewById(R.id.balanceTv);
            editView = view.findViewById(R.id.editView);
            upgradePackageView = view.findViewById(R.id.upgradePackageView);
            switchView = view.findViewById(R.id.switchView);
            fundTransferView = view.findViewById(R.id.fundTransferView);
            collectionView = view.findViewById(R.id.collectionView);
            cappingView = view.findViewById(R.id.cappingView);
            cappingTv = view.findViewById(R.id.cappingTv);
            callView = view.findViewById(R.id.callView);
            whatsappView = view.findViewById(R.id.whatsapView);
            balRecycerView = view.findViewById(R.id.balRecycerView);
            if (gridSize > 0 && gridSize <= 3) {
                balRecycerView.setLayoutManager(new GridLayoutManager(mContext, gridSize));
            } else {
                balRecycerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            }

            if (mContext instanceof FosUserListActivity) {
                cappingView.setVisibility(View.VISIBLE);
            }
        }
    }
}
