package com.solution.app.justpay4u.Fintech.Notification.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.NotificationData;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.Notification.NotificationActivity;
import com.solution.app.justpay4u.Fintech.Notification.NotificationListActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu on 14-04-2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private final int width;
    int resourceId = 0;
    RequestOptions requestOptions;
    private List<NotificationData> mList;
    private Activity mContext;

    public NotificationListAdapter(Activity mContext, List<NotificationData> mList) {
        this.mList = mList;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.notification);
        requestOptions.error(R.drawable.notification);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //  int height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.4f;
        wm.updateViewLayout(container, p);
    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);

        return new NotificationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationListAdapter.MyViewHolder holder, final int position) {
        final NotificationData operator = mList.get(position);
        holder.title.setText(operator.getTitle());
        holder.msg.setText(operator.getMessage().replace("\n\n\n", "").replaceAll("\n\n", "\n").replaceAll("\r\n\r\n", "\n"));
        holder.date.setText(operator.getEntryDate());

        if (!operator.isSeen()) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_notifiaction_open_gradient_border);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.rounded_reports_gradient_border);
        }


        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl())
                .apply(requestOptions)
                .into(holder.logo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Title", operator.getTitle());
                intent.putExtra("Message", operator.getMessage());
                intent.putExtra("Image", (operator.getImageUrl() != null && !operator.getImageUrl().isEmpty()) ? ApplicationConstant.INSTANCE.baseUrl + "/Image/Notification/" + operator.getImageUrl() : "");
                intent.putExtra("Url", operator.getUrl());
                intent.putExtra("Time", operator.getEntryDate());
                mContext.startActivity(intent);
                if (!operator.isSeen()) {
                    ((NotificationListActivity) mContext).setReadNotification(position);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void filter(ArrayList<NotificationData> newList) {
        mList = new ArrayList<>();
        mList.addAll(newList);
        notifyDataSetChanged();
    }

    void showDropDown(View v) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_delete_popup, null);
        CardView mainView = (CardView) viewMyLayout.findViewById(R.id.mainView);

        PopupWindow mypopupWindow = new PopupWindow(viewMyLayout, width - 100, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        //  mypopupWindow.setElevation(3);
        mypopupWindow.setFocusable(true);
        mypopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mypopupWindow.showAsDropDown(v, 153, 0);
        dimBehind(mypopupWindow);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, msg, date;
        public ImageView logo, delete;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            title =  view.findViewById(R.id.title);
            msg =  view.findViewById(R.id.msg);
            date =  view.findViewById(R.id.date);
            logo =  view.findViewById(R.id.logo);
            delete =  view.findViewById(R.id.delete);

        }
    }
}
/*
public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    private ArrayList<BankListObject> operatorList;
    private Context mContext;
    int resourceId = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            opImage = view.findViewById(R.id.opImage);

        }
    }

    public BankListScreenAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public BankListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bank_list, parent, false);

        return new BankListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankListScreenAdapter.MyViewHolder holder, int position) {
        final BankListObject operator = operatorList.get(position);
        holder.title.setText(operator.getBankName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BankListScreen) mContext).ItemClick(operator.getBankId(), operator.getBankName(), operator.getAccVeri(), operator.getShortCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

}
*/
