package com.solution.app.justpay4u.Fintech.Notification.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.solution.app.justpay4u.Api.Fintech.Object.NotificationData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomAlertDialog;

import java.util.List;

public class FundRequestSliderAdapter extends PagerAdapter {


    Activity mContext;
    BtnClicks btnClicks;
    CustomAlertDialog mCustomAlertDialog;
    private List<NotificationData> itemList;

    public FundRequestSliderAdapter(Activity context, List<NotificationData> itemList, BtnClicks btnClicks) {

        this.mContext = context;
        this.itemList = itemList;
        this.btnClicks = btnClicks;
        mCustomAlertDialog = new CustomAlertDialog(mContext);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int listPosition) {
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.pager_item_notification_fund_request, container, false);

        NotificationData listObject = itemList.get(listPosition);
        ImageView closeIv = itemView.findViewById(R.id.closeIv);
        TextView name = itemView.findViewById(R.id.name);
        TextView mobileNo = itemView.findViewById(R.id.mobileNo);
        TextView userId = itemView.findViewById(R.id.userId);
        TextView amount = itemView.findViewById(R.id.amount);
        View fundTransferView = itemView.findViewById(R.id.fundTransferView);
        View rejectView = itemView.findViewById(R.id.rejectView);
        name.setText(listObject.getTitle());
        closeIv.setTag(listPosition);
        closeIv.setOnClickListener(v ->
                mCustomAlertDialog.WarningWithCallBack("Are you sure you want to transfer later?", "Transfer Later", "Later", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        itemView.animate()
                                .alpha(0f)
                                .scaleX(0f).scaleY(0f)
                                .setDuration(250)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        if (btnClicks != null) {
                                            btnClicks.onCloseClick((Integer) v.getTag());
                                        }
                                    }
                                })
                                .start();


                    }

                    @Override
                    public void onNegativeClick() {

                    }
                }));

        fundTransferView.setOnClickListener(v -> {
                    if (btnClicks != null) {
                        btnClicks.onAcceptClick(listPosition);
                    }
                }
        );
        rejectView.setOnClickListener(v -> {
            if (btnClicks != null) {
                btnClicks.onRejectClick(listPosition);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }

    public interface BtnClicks {
        void onCloseClick(int position);

        void onAcceptClick(int position);

        void onRejectClick(int position);
    }

}
