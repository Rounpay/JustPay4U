package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Interfaces.ShoppingSelectAddress;

import java.util.List;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class AddressBottomSheetShoppingAdapter extends RecyclerView.Adapter<AddressBottomSheetShoppingAdapter.MyViewHolder> {

    private final List<Address> menuList;
    ShoppingSelectAddress mSelectAddress;
    int addressId;
    BottomSheetDialog mBottomSheetDialog;

    public AddressBottomSheetShoppingAdapter(List<Address> menuList, Context mContext, ShoppingSelectAddress mSelectAddress, int addressId, BottomSheetDialog mBottomSheetDialog) {
        this.menuList = menuList;
        this.mSelectAddress = mSelectAddress;
        this.addressId = addressId;
        this.mBottomSheetDialog = mBottomSheetDialog;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_address_bottom_sheet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Address operator = menuList.get(position);

        if (addressId != 0 && addressId == operator.getId()) {
            holder.radioBtn.setChecked(true);
        } else holder.radioBtn.setChecked(operator.isDefault() && addressId == 0);


        holder.address.setText(operator.getAddressOnly() + "");

        holder.city.setText(operator.getCity() + " - " + operator.getPinCode());

        holder.mobile.setText(operator.getMobileNo() + "");
        holder.title.setText(operator.getTitle() + "");
        holder.deliverToName.setText(operator.getCustomerName() + "");

        holder.itemView.setOnClickListener(v -> {
            addressId = operator.getId();
            notifyDataSetChanged();
            if (mSelectAddress != null) {
                mSelectAddress.onSelect(operator);
            }
            mBottomSheetDialog.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final RadioButton radioBtn;
        private final TextView deliverToName;
        private final TextView title;
        private final TextView address;
        private final TextView city;
        private final TextView mobile;
        View itemView;


        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            radioBtn = (RadioButton) view.findViewById(R.id.radioBtn);
            RelativeLayout deliverToView = view.findViewById(R.id.deliverToView);
            deliverToName =  view.findViewById(R.id.deliverToName);
            title =  view.findViewById(R.id.title);
            address =  view.findViewById(R.id.address);
            city =  view.findViewById(R.id.city);
            mobile =  view.findViewById(R.id.mobile);

        }
    }


}