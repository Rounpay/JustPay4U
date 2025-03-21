package com.solution.app.justpay4u.Fintech.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Activities.UpgradePackageActivity;
import com.solution.app.justpay4u.Api.Fintech.Object.PackageDetails;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class UpgradePackageAdapter extends RecyclerView.Adapter<UpgradePackageAdapter.MyViewHolder> {


    CustomAlertDialog customAlertDialog;
    boolean isFromAdditionalService;
    private List<PackageDetails> transactionsList;
    private Context mContext;

    public UpgradePackageAdapter(Context mContext, boolean isFromAdditionalService, List<PackageDetails> transactionsList, CustomAlertDialog customAlertDialog) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.isFromAdditionalService = isFromAdditionalService;
        this.customAlertDialog = customAlertDialog;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upgrade_package, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PackageDetails operator = transactionsList.get(position);
        holder.packageNameTv.setText(operator.getPackageName() + "");
        holder.amountTv.setText("You have to pay " + Utility.INSTANCE.formatedAmountWithRupees(operator.getPackageCost() + ""));
        if (operator.getServices() != null && operator.getServices().size() > 0) {
            holder.recyclerView.setAdapter(new UpgradePackageServiceAdapter(mContext, operator.getServices()));
        }
        holder.switchView.setOnClickListener(view -> {
            if (isFromAdditionalService) {
                customAlertDialog.WarningWithCallBack("Are you sure you want to upgrade additional service?", "Upgrade Additional Service",
                        "Upgrade", true, new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                if (mContext instanceof UpgradePackageActivity) {
                                    ((UpgradePackageActivity) mContext).upgradeService(operator, position);
                                }
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });

            } else {
                customAlertDialog.WarningWithCallBack("Are you sure you want to upgrade package?", "Upgrade Package",
                        "Upgrade", true, new CustomAlertDialog.DialogCallBack() {
                            @Override
                            public void onPositiveClick() {
                                if (mContext instanceof UpgradePackageActivity) {
                                    ((UpgradePackageActivity) mContext).upgradePackage(operator, position);
                                }
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
            }
        });
        if (isFromAdditionalService) {
            holder.upgradeSwitch.setChecked(operator.isActive());
            holder.switchView.setClickable(!operator.isActive());
        } else {
            holder.upgradeSwitch.setChecked(operator.isDefault());
            holder.switchView.setClickable(!operator.isDefault());
        }


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView packageNameTv, amountTv;
        SwitchCompat upgradeSwitch;
        RecyclerView recyclerView;
        View switchView;

        public MyViewHolder(View view) {
            super(view);
            amountTv = view.findViewById(R.id.amountTv);
            packageNameTv = view.findViewById(R.id.packageNameTv);
            upgradeSwitch = view.findViewById(R.id.upgradeSwitch);
            switchView = view.findViewById(R.id.switchView);
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        }
    }
}
