package com.solution.app.justpay4u.Fintech.Dashboard.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AssignedOpType;
import com.solution.app.justpay4u.Api.Fintech.Object.DataOpType;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;
import com.solution.app.justpay4u.Util.ServiceIcon;

import java.util.List;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class HomeOptionListAdapter extends RecyclerView.Adapter<HomeOptionListAdapter.MyViewHolder> {

    ClickView mClickView;
    int layout;
    CustomAlertDialog mCustomAlertDialog;
    int type;
    private List<AssignedOpType> operatorList;
    private Activity mContext;
    private DataOpType data;

    public HomeOptionListAdapter(List<AssignedOpType> operatorList, Activity mContext, ClickView mClickView, int layout, int type) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.mClickView = mClickView;
        this.layout = layout;
        this.type = type;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AssignedOpType operator = operatorList.get(position);

        if (operator.getIsServiceActive()) {
            holder.comingsoonTv.setVisibility(View.GONE);
        } else {
            holder.comingsoonTv.setVisibility(View.VISIBLE);
        }
        if (operator.getSubOpTypeList() != null && operator.getSubOpTypeList().size() > 0) {
            holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getService().replaceAll("\n", " ") : operator.getService());
            holder.icon.setImageResource(ServiceIcon.INSTANCE.parentIcon(operator.getParentID()));

        } else {
            holder.name.setText(layout == R.layout.adapter_dashboard_option ? operator.getName().replaceAll("\n", " ") : operator.getName());
            holder.icon.setImageResource(ServiceIcon.INSTANCE.serviceIcon(operator.getServiceID(), false));
        }


        holder.itemView.setOnClickListener(v -> {
            if (operator.isAdditionalServiceType()) {
                if (operator.isPaidAdditional()) {
                    if (mClickView != null) {

                      //  mClickView.onClick(operator.getServiceID(), operator.getParentID(), operator.getSubOpTypeLis() != null && operator.getSubOpTypeLis().size() > 0 ? operator.getService() : operator.getName(), operator.getSubOpTypeLis());
                        mClickView.onClick(operator);
                    }
                } else {
                    String contactTxt = "";
                    if (operator.getUpline() != null && !operator.getUpline().isEmpty() && operator.getUplineMobile() != null && !operator.getUplineMobile().isEmpty()) {
                        contactTxt = operator.getUpline() + " : " + operator.getUplineMobile();
                    }
                    if (operator.getCcContact() != null && !operator.getCcContact().isEmpty()) {
                        contactTxt = contactTxt + "\nCustomer Care : " + operator.getCcContact();
                    }
                    mCustomAlertDialog.upgradePackageDialog(contactTxt, true, (isFromAdditionalService) -> {
                        if (mClickView != null) {
                            mClickView.onPackageUpgradeClick(isFromAdditionalService, operator.getServiceID());
                        }
                    });
                }
            } else {
                if (!operator.getIsServiceActive()) {
                    Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show();
                } else if (!operator.getIsActive()) {
                    String contactTxt = "";
                    if (operator.getUpline() != null && !operator.getUpline().isEmpty() && operator.getUplineMobile() != null && !operator.getUplineMobile().isEmpty()) {
                        contactTxt = operator.getUpline() + " : " + operator.getUplineMobile();
                    }
                    if (operator.getCcContact() != null && !operator.getCcContact().isEmpty()) {
                        contactTxt = contactTxt + "\nCustomer Care : " + operator.getCcContact();
                    }
                    mCustomAlertDialog.upgradePackageDialog(contactTxt, false, (isFromAdditionalService) -> {
                        if (mClickView != null) {
                            mClickView.onPackageUpgradeClick(isFromAdditionalService, operator.getServiceID());
                        }
                    });
                } else {
                    if (mClickView != null) {

                       // mClickView.onClick(operator.getServiceID(), operator.getParentID(), operator.getSubOpTypeLis() != null && operator.getSubOpTypeLis().size() > 0 ? operator.getService() : operator.getName(), operator.getSubOpTypeLis());
                        mClickView.onClick(operator);
                    }
                }
            }
        });
       /* if (operator.getName() != null && operator.getName().toString().length() > 0) {
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(ApplicationConstant.INSTANCE.baseIconUrl+operator.getImage()).
                    apply(requestOptions).into(holder.opImage);

        } else {
            holder.opImage.setImageResource(R.drawable.ic_operator_default_icon);
        }*/
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public interface ClickView {
        //  void onClick(int serviceId, int parentId, String ServiceParent, ArrayList<AssignedOpType> subOpTypeList);

        void onClick(AssignedOpType operator);

        void onPackageUpgradeClick(boolean isFromAdditionalService, int serviceId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, comingsoonTv;
        public ImageView icon;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);
            comingsoonTv = view.findViewById(R.id.comingsoonTv);
            icon = view.findViewById(R.id.icon);
            /*if (type == 1) {
                itemView.setBackgroundColor(Color.WHITE);
            }
            if (type == 2) {
                name.setLines(1);
                name.setTextColor(Color.WHITE);
            }*/


        }
    }
}
