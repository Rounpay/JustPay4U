package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Shopping.Object.StatesCities;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Interfaces.ShoppingSelectStateCities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class StatesCitiesListShoppingAdapter extends RecyclerView.Adapter<StatesCitiesListShoppingAdapter.MyViewHolder> implements Filterable {

    private final List<StatesCities> menuList;
    ShoppingSelectStateCities mSelectStateCities;
    BottomSheetDialog mBottomSheetDialog;
    boolean isState;
    private List<StatesCities> filterListItem;

    public StatesCitiesListShoppingAdapter(List<StatesCities> menuList, Context mContext, ShoppingSelectStateCities mSelectStateCities, boolean isState, BottomSheetDialog mBottomSheetDialog) {
        this.menuList = menuList;
        this.filterListItem = menuList;
        this.mSelectStateCities = mSelectStateCities;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.isState = isState;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_circle_zone, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final StatesCities operator = filterListItem.get(position);

        if (isState) {
            holder.name.setText(operator.getStateName() + "");
        } else {
            holder.name.setText(operator.getCityName() + "");
        }

        holder.itemView.setOnClickListener(v -> {
            if (mSelectStateCities != null) {
                mSelectStateCities.onSelect(operator);
            }
            mBottomSheetDialog.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = menuList;
                } else {
                    ArrayList<StatesCities> filteredList = new ArrayList<>();
                    for (StatesCities row : menuList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getStateName() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getCityName() + "").toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<StatesCities>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        View itemView;


        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            ImageView opImage = view.findViewById(R.id.opImage);
            opImage.setVisibility(View.GONE);
            name = view.findViewById(R.id.title);

        }
    }


}