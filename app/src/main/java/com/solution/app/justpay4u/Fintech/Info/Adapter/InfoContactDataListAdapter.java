package com.solution.app.justpay4u.Fintech.Info.Adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Info.Model.InfoContactDataItem;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class InfoContactDataListAdapter extends RecyclerView.Adapter<InfoContactDataListAdapter.MyViewHolder> {


    private ArrayList<InfoContactDataItem> operatorList;
    private Context mContext;
    private String showHeaderName;

    public InfoContactDataListAdapter(Context mContext, ArrayList<InfoContactDataItem> operatorList) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_info_contact_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final InfoContactDataItem operator = operatorList.get(position);

        holder.value.setText(operator.getValue() + "");
        holder.icon.setImageResource(operator.getIcon());
        if (!operator.getHeaderName().equalsIgnoreCase(showHeaderName)) {
            showHeaderName = operator.getHeaderName();
            holder.headerView.setVisibility(View.VISIBLE);
        } else {
            holder.headerView.setVisibility(View.GONE);
        }
        holder.headerIcon.setImageResource(operator.getHeaderIcon());
        holder.headerTitle.setText(operator.getHeaderName() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (operator.getType() == 1 || operator.getType() == 2) {
                    try {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                        dialIntent.setData(Uri.parse("tel:" + operator.getValue() + ""));
                        mContext.startActivity(dialIntent);
                    } catch (Exception e) {
                        Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + operator.getValue() + ""));
                        mContext.startActivity(dialIntent);
                    }

                }
                if (operator.getType() == 3) {
                    openWhatsapp(operator.getValue() + "");
                }
                if (operator.getType() == 4) {
                    try {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + operator.getValue() + ""));
                        mContext.startActivity(emailIntent);
                    } catch (Exception e) {
                        Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + operator.getValue() + ""));
                        mContext.startActivity(emailIntent);
                    }
                }
               /* if (mClickView != null) {
                    mClickView.onClick(operator.getServiceID());
                }*/
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

    void openWhatsapp(String smsNumber) {

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView value, headerTitle;
        public ImageView icon, headerIcon;
        View itemView, headerView;

        public MyViewHolder(View view) {
            super(view);
            value = view.findViewById(R.id.value);
            icon = view.findViewById(R.id.icon);
            headerIcon = view.findViewById(R.id.headerIcon);
            headerTitle = view.findViewById(R.id.headerTitle);
            headerView = view.findViewById(R.id.headerView);
            itemView = view;

        }
    }
}
