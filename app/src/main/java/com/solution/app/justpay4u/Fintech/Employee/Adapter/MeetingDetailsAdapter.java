package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingDetails;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Employee.Activity.MeetingDetails;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeetingDetailsAdapter extends RecyclerView.Adapter<MeetingDetailsAdapter.MyViewHolder> {


    private List<GetMeetingDetails> transactionsList;
    private List<GetMeetingDetails> operatorList;
    private Activity mContext;
    boolean isFromDialog;

    public MeetingDetailsAdapter(List<GetMeetingDetails> transactionsList, Activity mContext, boolean isFromDialog) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.isFromDialog = isFromDialog;
        this.operatorList = new ArrayList<>();
        this.operatorList.addAll(transactionsList);


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_meeting_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetMeetingDetails operator = transactionsList.get(position);
        holder.nameTv.setText(operator.getName() + "");


        if (operator.getMobileNo() != null && !operator.getMobileNo().isEmpty()) {
            holder.mobileTv.setText(operator.getMobileNo() + "");
            holder.mobileView.setVisibility(View.VISIBLE);
        } else {
            holder.mobileView.setVisibility(View.GONE);
        }
        holder.outletNameTv.setText(operator.getOutletName() + "");
        holder.areaTv.setText(operator.getArea() + "");
        holder.pincodeTv.setText(operator.getPincode() + "");
        holder.purposeTv.setText(operator.getPurpose() + "");
        holder.consumptionTv.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getConsumption() + ""));


        if (operator.getReason() != null && !operator.getReason().isEmpty()) {
            holder.otherBrandReasonView.setVisibility(View.VISIBLE);
            holder.otherBrandReasonTv.setText(operator.getReason() + "");
        } else {
            holder.otherBrandReasonView.setVisibility(View.GONE);
        }

        if (operator.getRemark() != null && !operator.getRemark().isEmpty()) {
            holder.remarkView.setVisibility(View.VISIBLE);
            holder.remarkTv.setText(operator.getRemark() + "");
        } else {
            holder.remarkView.setVisibility(View.GONE);
        }
        holder.rechargeCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getRechargeConsumption() + ""));
        holder.billCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBillPaymentConsumption() + ""));
        holder.moneyTxnCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMoneyTransferConsumption() + ""));
        holder.aepsCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAepsConsumption() + ""));
        holder.panCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getPanConsumption() + ""));
        holder.miniAtmCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getMiniATMConsumption() + ""));
        holder.insuranceCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getInsuranceConsumption() + ""));
        holder.vehicleCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getVehicleConsumption() + ""));
        holder.hotelCons.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getHotelConsumption() + ""));

        if (operator.getLatitute() != null && !operator.getLatitute().isEmpty() && operator.getLongitute() != null && !operator.getLongitute().isEmpty()) {
            holder.map.setVisibility(View.VISIBLE);
            holder.map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MeetingDetails) mContext).showMapDialog(operator);
                }
            });
        } else {
            holder.map.setVisibility(View.GONE);
        }
        if (operator.getShopImagePath() != null && !operator.getShopImagePath().isEmpty()) {
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                        dialIntent.setData(Uri.parse(ApplicationConstant.INSTANCE.baseUrl + operator.getShopImagePath() + ""));
                        mContext.startActivity(dialIntent);
                    } catch (Exception e) {
                        Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + operator.getShopImagePath() + ""));
                        mContext.startActivity(dialIntent);
                    }
                }
            });
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    public void filter(String st) {
        String charText = st.toLowerCase(Locale.getDefault());

        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(operatorList);
        } else {
            for (GetMeetingDetails wp : operatorList) {
                if ((wp.getName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getReason() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getMobileNo() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getOutletName() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getPincode() + "").toLowerCase(Locale.getDefault()).contains(charText) ||
                        (wp.getPurpose() + "").toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private LinearLayout outletNameView;
        private TextView outletNameTv;
        private LinearLayout pincodeView;
        private TextView pincodeTv;
        private LinearLayout mobileView;
        private TextView mobileTv;
        private RelativeLayout consumptionView;
        private TextView consumptionTv;
        private LinearLayout purposeView;
        private TextView purposeTv;
        private LinearLayout remarkView;
        private TextView remarkTv;
        private LinearLayout areaView;
        private TextView areaTv;
        private LinearLayout otherBrandReasonView;
        private TextView otherBrandReasonTv;
        private LinearLayout rechargeConView;
        private TextView rechargeConLabel;
        private TextView rechargeCons;
        private LinearLayout billConView;
        private TextView billConLabel;
        private TextView billCons;
        private LinearLayout moneyTxnConView;
        private TextView moneyTxnConLabel;
        private TextView moneyTxnCons;
        private LinearLayout aepsConView;
        private TextView aepsConLabel;
        private TextView aepsCons;
        private LinearLayout panConView;
        private TextView panConLabel;
        private TextView panCons;
        private LinearLayout miniAtmConView;
        private TextView miniAtmConLabel;
        private TextView miniAtmCons;
        private LinearLayout hotelConView;
        private TextView hotelConLabel;
        private TextView hotelCons;
        private LinearLayout insuranceConView;
        private TextView insuranceConLabel;
        private TextView insuranceCons;
        private LinearLayout vehicleConView;
        private TextView vehicleConLabel;
        private TextView vehicleCons;
        View map, image, bottomLine;


        public MyViewHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.nameTv);
            outletNameView = (LinearLayout) view.findViewById(R.id.outletNameView);
            outletNameTv = (TextView) view.findViewById(R.id.outletNameTv);
            pincodeView = (LinearLayout) view.findViewById(R.id.pincodeView);
            pincodeTv = (TextView) view.findViewById(R.id.pincodeTv);
            mobileView = (LinearLayout) view.findViewById(R.id.mobileView);
            mobileTv = (TextView) view.findViewById(R.id.mobileTv);
            consumptionView = view.findViewById(R.id.consumptionView);
            consumptionTv = (TextView) view.findViewById(R.id.consumptionTv);
            purposeView = (LinearLayout) view.findViewById(R.id.purposeView);
            purposeTv = (TextView) view.findViewById(R.id.purposeTv);
            rechargeConView = (LinearLayout) view.findViewById(R.id.rechargeConView);
            rechargeConLabel = (TextView) view.findViewById(R.id.rechargeConLabel);
            rechargeCons = (TextView) view.findViewById(R.id.rechargeCons);
            billConView = (LinearLayout) view.findViewById(R.id.billConView);
            billConLabel = (TextView) view.findViewById(R.id.billConLabel);
            billCons = (TextView) view.findViewById(R.id.billCons);
            moneyTxnConView = (LinearLayout) view.findViewById(R.id.moneyTxnConView);
            moneyTxnConLabel = (TextView) view.findViewById(R.id.moneyTxnConLabel);
            moneyTxnCons = (TextView) view.findViewById(R.id.moneyTxnCons);
            aepsConView = (LinearLayout) view.findViewById(R.id.aepsConView);
            aepsConLabel = (TextView) view.findViewById(R.id.aepsConLabel);
            aepsCons = (TextView) view.findViewById(R.id.aepsCons);
            panConView = (LinearLayout) view.findViewById(R.id.panConView);
            panConLabel = (TextView) view.findViewById(R.id.panConLabel);
            panCons = (TextView) view.findViewById(R.id.panCons);
            miniAtmConView = (LinearLayout) view.findViewById(R.id.miniAtmConView);
            miniAtmConLabel = (TextView) view.findViewById(R.id.miniAtmConLabel);
            miniAtmCons = (TextView) view.findViewById(R.id.miniAtmCons);
            hotelConView = (LinearLayout) view.findViewById(R.id.hotelConView);
            hotelConLabel = (TextView) view.findViewById(R.id.hotelConLabel);
            hotelCons = (TextView) view.findViewById(R.id.hotelCons);
            insuranceConView = (LinearLayout) view.findViewById(R.id.insuranceConView);
            insuranceConLabel = (TextView) view.findViewById(R.id.insuranceConLabel);
            insuranceCons = (TextView) view.findViewById(R.id.insuranceCons);
            vehicleConView = (LinearLayout) view.findViewById(R.id.vehicleConView);
            vehicleConLabel = (TextView) view.findViewById(R.id.vehicleConLabel);
            vehicleCons = (TextView) view.findViewById(R.id.vehicleCons);
            remarkView = (LinearLayout) view.findViewById(R.id.remarkView);
            remarkTv = (TextView) view.findViewById(R.id.remarkTv);
            areaView = (LinearLayout) view.findViewById(R.id.areaView);
            areaTv = (TextView) view.findViewById(R.id.areaTv);
            otherBrandReasonView = (LinearLayout) view.findViewById(R.id.otherBrandReasonView);
            otherBrandReasonTv = (TextView) view.findViewById(R.id.otherBrandReasonTv);
            map = view.findViewById(R.id.map);
            image = view.findViewById(R.id.image);
            //bottomLine = view.findViewById(R.id.bottomLine);

            /*if (isFromDialog) {
                if (transactionsList.size() > 1) {
                    bottomLine.setVisibility(View.VISIBLE);
                } else {
                    bottomLine.setVisibility(View.GONE);
                }
            }*/
        }
    }
}


