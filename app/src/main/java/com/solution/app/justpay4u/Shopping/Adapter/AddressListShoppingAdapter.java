package com.solution.app.justpay4u.Shopping.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Activity.AddAddressShoppingActivity;
import com.solution.app.justpay4u.Shopping.Activity.AddressListShoppingActivity;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.List;

/**
 * Created by Vishnu on 24/02/2021.
 */

public class AddressListShoppingAdapter extends RecyclerView.Adapter<AddressListShoppingAdapter.MyViewHolder> {

    private final List<Address> menuList;
    private final Activity mContext;
    private int previousDefaultPos = -1;

    public AddressListShoppingAdapter(List<Address> menuList, Activity mContext) {
        this.menuList = menuList;
        this.mContext = mContext;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_address_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Address operator = menuList.get(position);

        holder.address.setText(operator.getAddressOnly() + "");

        holder.city.setText(operator.getCity() + ", " + operator.getState() + " - " + operator.getPinCode());

        holder.mobile.setText(operator.getMobileNo() + "");
        holder.title.setText(operator.getTitle() + "");
        holder.deliverToName.setText(operator.getCustomerName() + "");
        if (operator.isDefault()) {
            previousDefaultPos = position;
            holder.defaultAddress.setVisibility(View.VISIBLE);
            holder.primarybtn.setVisibility(View.GONE);
        } else {
            holder.defaultAddress.setVisibility(View.GONE);
            holder.primarybtn.setVisibility(View.VISIBLE);
        }
        holder.editbtn.setOnClickListener(v -> {
            mContext.startActivityForResult(new Intent(mContext, AddAddressShoppingActivity.class)
                    .putExtra("Address", operator), ((AddressListShoppingActivity) mContext).INTENT_ADD_ADDRESS);
        });

        holder.deletebtn.setOnClickListener(v -> {
            CustomAlertDialog mCustomAlertDialog = new CustomAlertDialog(mContext);
            mCustomAlertDialog.WarningWithCallBack("Are you sure, you want to delete this address?", "Delete Address", "Delete", true, new CustomAlertDialog.DialogCallBack() {
                @Override
                public void onPositiveClick() {
                    if (mContext instanceof AddressListShoppingActivity) {
                        ((AddressListShoppingActivity) mContext).ChangeAddress(1, position, previousDefaultPos, operator);
                    }
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        holder.primarybtn.setOnClickListener(v -> {
            if (mContext instanceof AddressListShoppingActivity) {
                ((AddressListShoppingActivity) mContext).ChangeAddress(2, position, previousDefaultPos, operator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView deliverToName;
        private final TextView title;
        private final TextView address;
        private final TextView city;
        private final TextView mobile;
        private final TextView defaultAddress;
        View itemView, editbtn, deletebtn, primarybtn;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            RelativeLayout deliverToView = view.findViewById(R.id.deliverToView);
            deliverToName = view.findViewById(R.id.deliverToName);
            title = view.findViewById(R.id.title);
            address = view.findViewById(R.id.address);
            city = view.findViewById(R.id.city);
            mobile = view.findViewById(R.id.mobile);
            defaultAddress = view.findViewById(R.id.defaultAddress);
            editbtn = view.findViewById(R.id.editbtn);
            deletebtn = view.findViewById(R.id.deletebtn);
            primarybtn = view.findViewById(R.id.primarybtn);
        }
    }


}