package com.solution.app.justpay4u.Fintech.FundTransactions.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BenisObject;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRActivity;
import com.solution.app.justpay4u.Fintech.FundTransactions.Activity.DMRWithPipeActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.MyViewHolder> implements Filterable {

    int resourceId = 0;
    CustomLoader loader;
    Dialog dialog = null;
    Activity activity;
    private ArrayList<BenisObject> listItem, filterListItem;


    public BeneficiaryAdapter(ArrayList<BenisObject> listItem, Activity activity) {
        this.listItem = listItem;
        this.filterListItem = listItem;
        this.activity = activity;
    }

    @Override
    public BeneficiaryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_beneficiary, parent, false);


        return new BeneficiaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryAdapter.MyViewHolder holder, final int position) {
        final BenisObject operator = filterListItem.get(position);
        holder.beneName.setText(operator.getBeneName());
        holder.beneAccountNumber.setText(operator.getAccountNo());
        holder.beneBank.setText(operator.getBankName());
        holder.beneIFSC.setText(operator.getIfsc());

        /*holder.beneName.setText(operator.getNAME());
        holder.beneAccountNumber.setText(operator.getACCOUNT());
        holder.beneBank.setText(operator.getBANK());
        holder.beneIFSC.setText(operator.getIFSC());*/

        holder.sendView.setOnClickListener(v -> {
            if (activity instanceof DMRActivity) {
                ((DMRActivity) activity).sendMoneyClick(operator);
            } else if (activity instanceof DMRWithPipeActivity) {
                ((DMRWithPipeActivity) activity).sendMoneyClick(operator);
            }


        });

        holder.deleteView.setOnClickListener(v -> {
            if (activity instanceof DMRActivity) {
                ((DMRActivity) activity).confirmationDialog(operator);
            } else if (activity instanceof DMRWithPipeActivity) {
                ((DMRWithPipeActivity) activity).confirmationDialog(operator);
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
                    ArrayList<BenisObject> filteredList = new ArrayList<>();
                    for (BenisObject row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMobileNo().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getAccountNo().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBankName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getIfsc().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBeneName().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<BenisObject>) filterResults.values;
                notifyDataSetChanged();


                if (activity instanceof DMRActivity) {
                    if (filterListItem.size() == 0) {
                        ((DMRActivity) activity).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((DMRActivity) activity).noDataView.setVisibility(View.GONE);
                    }

                } else if (activity instanceof DMRWithPipeActivity) {
                    if (filterListItem.size() == 0) {
                        ((DMRWithPipeActivity) activity).noDataView.setVisibility(View.VISIBLE);
                    } else {
                        ((DMRWithPipeActivity) activity).noDataView.setVisibility(View.GONE);
                    }
                }

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView beneName;
        public TextView beneAccountNumber;
        public TextView beneBank;
        public TextView beneIFSC;

        public View sendView;
        public View deleteView;


        public MyViewHolder(View view) {
            super(view);
            beneName = (TextView) view.findViewById(R.id.beneName);
            beneAccountNumber = (TextView) view.findViewById(R.id.beneAccountNumber);
            beneBank = (TextView) view.findViewById(R.id.beneBank);
            beneIFSC = (TextView) view.findViewById(R.id.beneIFSC);
            sendView = view.findViewById(R.id.sendView);
            deleteView = view.findViewById(R.id.deleteView);

        }
    }


}
