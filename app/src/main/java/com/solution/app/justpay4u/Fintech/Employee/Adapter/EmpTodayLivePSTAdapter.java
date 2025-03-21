package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmpTodayLivePST;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTodayOutListForEmpData;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetTodayTransactorsData;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayOutletListForEmpResponse;
import com.solution.app.justpay4u.Fintech.Employee.Api.Response.GetTodayTransactorsResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Reports.Activity.FundDcReportActivity;
import com.solution.app.justpay4u.Fintech.Reports.Activity.RechargeReportActivity;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class EmpTodayLivePSTAdapter extends RecyclerView.Adapter<EmpTodayLivePSTAdapter.MyViewHolder> {


    private final CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    private List<GetEmpTodayLivePST> operatorList;
    private Activity mContext;
    LoginResponse loginPrefResponse;
    private AlertDialog alertDialogTodayTransactor;
    private String deviceId;
    private String deviceSerialNum;

    public EmpTodayLivePSTAdapter(List<GetEmpTodayLivePST> operatorList, LoginResponse loginPrefResponse, CustomLoader loader,
                                  String deviceId, String deviceSerialNum, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.loginPrefResponse = loginPrefResponse;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
        this.loader = loader;
        this.deviceId = deviceId;
        this.deviceSerialNum = deviceSerialNum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_today_records, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetEmpTodayLivePST operator = operatorList.get(position);
        holder.name.setText(operator.getType() + "");


        holder.amt.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getTotalAmount() + ""));
        holder.txn.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getTotalUser() + ""));
        holder.uniq.setText(Utility.INSTANCE.formatedAmountWithOutRupees(operator.getUniqueUser() + ""));

        if (operator.getType().equalsIgnoreCase("Outlet")) {
            holder.user.setVisibility(View.GONE);
        } else {
            holder.user.setVisibility(View.VISIBLE);
        }

        holder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.getType().equalsIgnoreCase("Primary")) {
                    showTodayTransactors(1);
                } else if (operator.getType().equalsIgnoreCase("Secoundary")) {
                    showTodayTransactors(2);
                } else if (operator.getType().equalsIgnoreCase("Tertiary")) {
                    showTodayTransactors(3);
                }
            }
        });

        if (operator.getType().equalsIgnoreCase("Outlet")) {
            holder.user.setVisibility(View.GONE);
        } else {
            holder.user.setVisibility(View.VISIBLE);
        }
        holder.viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.getType().equalsIgnoreCase("Primary") || operator.getType().equalsIgnoreCase("Secoundary")) {
                    Intent i = new Intent(mContext, FundDcReportActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(i);
                } else if (operator.getType().equalsIgnoreCase("Tertiary")) {
                    Intent intent = new Intent(mContext, RechargeReportActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } else {
                    showOutletJoiners();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, amt, txn, uniq;
        ImageView viewReport, user;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            amt = view.findViewById(R.id.amt);
            txn = view.findViewById(R.id.txn);
            uniq = view.findViewById(R.id.uniq);
            viewReport = view.findViewById(R.id.viewReport);
            user = view.findViewById(R.id.user);


        }
    }


    void showOutletJoiners() {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
            try {
                ApiFintechUtilMethods.INSTANCE.GetTodayOutletsListForEmp(mContext, loginPrefResponse, deviceId, deviceSerialNum, loader, object -> {
                    GetTodayOutletListForEmpResponse mResponse = (GetTodayOutletListForEmpResponse) object;
                    if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
                        showTodayOutletListFirEmpDialog(mResponse.getData());
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(mContext, "Data not available");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            ApiFintechUtilMethods.INSTANCE.NetworkError(mContext);
        }
    }

    void showTodayTransactors(int criteria) {
        if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
            try {
                ApiFintechUtilMethods.INSTANCE.GetTodayTransactors(mContext, criteria, loginPrefResponse, deviceId, deviceSerialNum, loader, object -> {
                    GetTodayTransactorsResponse mResponse = (GetTodayTransactorsResponse) object;
                    if (mResponse != null && mResponse.getData() != null && mResponse.getData().size() > 0) {
                        showTodayTransactorDialog(mResponse.getData());
                    } else {
                        ApiFintechUtilMethods.INSTANCE.Error(mContext, "Data not available");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            ApiFintechUtilMethods.INSTANCE.NetworkError(mContext);
        }
    }

    void showTodayOutletListFirEmpDialog(List<GetTodayOutListForEmpData> data) {
        try {
            if (alertDialogTodayTransactor != null && alertDialogTodayTransactor.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext,R.style.full_screen_dialog);
            alertDialogTodayTransactor = dialogBuilder.create();
            //alertDialogTodayTransactor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_today_outlet_list_emp, null);
            alertDialogTodayTransactor.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);

            titleTv.setText("Active Users (Today)");

            recyclerView.setAdapter(new TodayOutletListEmpAdapter(data, mContext));

            closeIv.setOnClickListener(v -> alertDialogTodayTransactor.dismiss());
            alertDialogTodayTransactor.show();
            alertDialogTodayTransactor.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialogTodayTransactor.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    void showTodayTransactorDialog(List<GetTodayTransactorsData> data) {
        try {
            if (alertDialogTodayTransactor != null && alertDialogTodayTransactor.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(mContext,R.style.full_screen_dialog);
            alertDialogTodayTransactor = dialogBuilder.create();
        //    alertDialogTodayTransactor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            LayoutInflater inflater = mContext.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_today_transactor_list, null);
            alertDialogTodayTransactor.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);

            titleTv.setText("Active Users(Today)");

            recyclerView.setAdapter(new TodayTransactorAdapter(data, mContext));

            closeIv.setOnClickListener(v -> alertDialogTodayTransactor.dismiss());
            alertDialogTodayTransactor.show();
            alertDialogTodayTransactor.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            alertDialogTodayTransactor.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }
}