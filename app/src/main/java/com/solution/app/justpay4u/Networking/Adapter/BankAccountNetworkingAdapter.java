package com.solution.app.justpay4u.Networking.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.SettlementAccountData;
import com.solution.app.justpay4u.Networking.Activity.BankTransferNetworkingActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class BankAccountNetworkingAdapter extends RecyclerView.Adapter<BankAccountNetworkingAdapter.MyViewHolder> implements Filterable {
    Dialog dialog = null;
    Activity activity;
    boolean isSattlemntAccountVerify;
    private ArrayList<SettlementAccountData> listItem, filterListItem;

    public BankAccountNetworkingAdapter(boolean isSattlemntAccountVerify, ArrayList<SettlementAccountData> listItem, Activity activity) {
        this.listItem = listItem;
        this.filterListItem = listItem;
        this.activity = activity;
        this.isSattlemntAccountVerify = isSattlemntAccountVerify;
    }

    public void setFlag(boolean isSattlemntAccountVerify) {
        this.isSattlemntAccountVerify = isSattlemntAccountVerify;
    }

    @Override
    public BankAccountNetworkingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_networking_bank_account, parent, false);


        return new BankAccountNetworkingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankAccountNetworkingAdapter.MyViewHolder holder, final int position) {
        final SettlementAccountData operator = filterListItem.get(position);
        holder.beneName.setText(operator.getAccountHolder());
        holder.beneAccountNumber.setText(operator.getAccountNumber());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());
        holder.verifyStatusTv.setText(operator.getVerificationText() + "");
        holder.approvalStatusTv.setText(operator.getApprovalText() + "");

        if (operator.getApprovalStatus() == 2) {
            holder.switchView.setVisibility(View.VISIBLE);
        } else {
            holder.switchView.setVisibility(View.GONE);
        }
        if (isSattlemntAccountVerify) {
            if (operator.getVerificationStatus() == 0) {
                holder.sendBtnTv.setText("Send");
                holder.sendView.setVisibility(View.VISIBLE);
            } else if (operator.getVerificationStatus() == 1) {
                holder.verifyBtnTv.setText("Update UTR");
                holder.verifyView.setVisibility(View.VISIBLE);
            } else {
                holder.sendView.setVisibility(View.GONE);
            }
        } else {
            holder.sendView.setVisibility(View.GONE);
        }
        if (operator.isDefault()) {
            holder.activeSwitch.setChecked(true);
        } else {
            holder.activeSwitch.setChecked(false);
        }
        /*holder.beneName.setText(operator.getNAME());
        holder.beneAccountNumber.setText(operator.getACCOUNT());
        holder.beneBank.setText(operator.getBANK());
        holder.beneIFSC.setText(operator.getIFSC());*/

        holder.verifyView.setOnClickListener(v -> {
            if (activity instanceof BankTransferNetworkingActivity) {
                ((BankTransferNetworkingActivity) activity).VerifyOrUtr(operator);
            }
        });
        holder.updateView.setOnClickListener(v -> {
            if (activity instanceof BankTransferNetworkingActivity) {
                ((BankTransferNetworkingActivity) activity).update(operator);
            }
        });
        holder.switchView.setOnClickListener(v -> {
            if (activity instanceof BankTransferNetworkingActivity) {
                ((BankTransferNetworkingActivity) activity).setDefault(operator);
            }
        });
        holder.deleteView.setOnClickListener(v -> {
            if (activity instanceof BankTransferNetworkingActivity) {
                ((BankTransferNetworkingActivity) activity).confirmationDialog(operator);
            }
        });holder.sendView.setOnClickListener(v -> {
            if (activity instanceof BankTransferNetworkingActivity) {
                ((BankTransferNetworkingActivity) activity).sendMoney(operator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }


    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
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
                    ArrayList<SettlementAccountData> filteredList = new ArrayList<>();
                    for (SettlementAccountData row : listItem) {
                        if ((row.getMobileNo() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAccountNumber() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getBankName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getIfsc() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAccountHolder() + "").toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<SettlementAccountData>) filterResults.values;
                notifyDataSetChanged();


                if (activity instanceof BankTransferNetworkingActivity) {
                    if (filterListItem.size() == 0) {
                        ((BankTransferNetworkingActivity) activity).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((BankTransferNetworkingActivity) activity).noDataView.setVisibility(View.GONE);
                    }

                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC, approvalStatusTv, verifyStatusTv, verifyBtnTv,sendBtnTv;
        public SwitchCompat activeSwitch;
        public View updateView;
        public View deleteView, verifyView, switchView, statusView,sendView;


        public MyViewHolder(View view) {
            super(view);
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneAccountNumber = (TextView) view.findViewById(R.id.beneAccountNumber);
            beneBank = (TextView) view.findViewById(R.id.beneBank);
            beneIFSC = (TextView) view.findViewById(R.id.beneIFSC);
            updateView = view.findViewById(R.id.updateView);
            deleteView = view.findViewById(R.id.deleteView);

            approvalStatusTv = view.findViewById(R.id.approvalStatusTv);
            verifyView = view.findViewById(R.id.verifyView);
            sendBtnTv = (TextView) view.findViewById(R.id.sendBtnTv);
            verifyBtnTv = view.findViewById(R.id.verifyBtnTv);
            verifyStatusTv = view.findViewById(R.id.verifyStatusTv);
            activeSwitch = view.findViewById(R.id.activeSwitch);
            switchView = view.findViewById(R.id.switchView);
            statusView = view.findViewById(R.id.statusView);sendView = view.findViewById(R.id.sendView);


            if (!isSattlemntAccountVerify) {
                statusView.setVisibility(View.GONE);
                verifyView.setVisibility(View.GONE);
            }
        }
    }


}
