package com.solution.app.justpay4u.Fintech.Dashboard.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.solution.app.justpay4u.Api.Fintech.Object.UserSmartDetail;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.Dashboard.Activity.VirtualAccountActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;


/**
 * it display the list of Images at start of app
 */


public class VirtualSmartAddressPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    RequestOptions requestOptions;
    UserSmartDetail response;
    private ArrayList<UserSmartDetail> ImageList;

    public VirtualSmartAddressPagerAdapter(ArrayList<UserSmartDetail> ImageList, Context context, RequestOptions requestOptions, UserSmartDetail response) {
        this.ImageList = ImageList;
        this.mContext = context;
        this.response = response;
        this.requestOptions = requestOptions;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.virtual_collection_pager_item, container, false);

        ImageView qrcode = itemView.findViewById(R.id.qrcode);
        TextView detailTv = itemView.findViewById(R.id.detail);
        View titleView = itemView.findViewById(R.id.titleView);
        UserSmartDetail item = ImageList.get(position);
        if (item.getModifystr() != null && !item.getModifystr().isEmpty()) {
            detailTv.setVisibility(View.VISIBLE);
            detailTv.setText(item.getModifystr());
        } else {
            String detailsStr = "";
            if (response != null && response.isVirtualShow()) {
                if (item.getBankName() != null && !item.getBankName().isEmpty()) {
                    detailsStr = "Bank Name : " + item.getBankName();
                }
                if (item.getBeneName() != null && !item.getBeneName().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Benificiary Name : " + item.getBeneName();
                }

                if (item.getAccountHolder() != null && !item.getAccountHolder().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Account Holder : " + item.getAccountHolder();
                }
                if (item.getSmartAccountNo() != null && !item.getSmartAccountNo().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Account Number : " + item.getSmartAccountNo();
                }
                if (item.getBranch() != null && !item.getBranch().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Branch : " + item.getBranch();
                }
                if (item.getIfsc() != null && !item.getIfsc().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "IFSC : " + item.getIfsc();
                }
                if (item.getCustomerID() != null && !item.getCustomerID().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Customer ID : " + item.getCustomerID();
                }
                if (item.getVirtualAccount() != null && !item.getVirtualAccount().isEmpty()) {
                    detailsStr = detailsStr + "\n" + "Virtual Account : " + item.getVirtualAccount();
                }
            }

            if (response != null && response.isVPAShow() && item.getSmartVPA() != null && !item.getSmartVPA().isEmpty()) {
                detailsStr = detailsStr + "\n" + "VPA : " + item.getSmartVPA();
            }


            if (!detailsStr.isEmpty()) {
                item.setModifystr(detailsStr);
                detailTv.setText(detailsStr);
                detailTv.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.GONE);

            } else {
                detailTv.setVisibility(View.GONE);
                titleView.setVisibility(View.VISIBLE);

            }

        }

        if (item.getQrImage() != null) {
            qrcode.setVisibility(View.VISIBLE);
            qrcode.setImageBitmap(item.getQrImage());
        } else {
            if (response != null && response.isQRShow() && item.getSmartQRShortURL() != null &&
                    !item.getSmartQRShortURL().isEmpty()) {
                qrcode.setVisibility(View.VISIBLE);
                String id = item.getSmartQRShortURL();
                if (id.contains("/")) {
                    id = id.substring(id.indexOf("/") + 1);
                }
                Glide.with(mContext)
                        .asBitmap()
                        .load(ApplicationConstant.INSTANCE.QRImageForCollectURL + ((VirtualAccountActivity) mContext).mLoginDataResponse.getData().getUserID() +
                                "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + ((VirtualAccountActivity) mContext).deviceId + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                                "&serialNo=" + ((VirtualAccountActivity) mContext).deviceSerialNum + "&sessionID=" + ((VirtualAccountActivity) mContext).mLoginDataResponse.getData().getSessionID() +
                                "&session=" + ((VirtualAccountActivity) mContext).mLoginDataResponse.getData().getSession() + "&loginTypeID=" + ((VirtualAccountActivity) mContext).mLoginDataResponse.getData().getLoginTypeID()
                                + "&id=" + id)
                        .apply(requestOptions)

                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                qrcode.setVisibility(View.VISIBLE);
                                qrcode.setImageResource(R.drawable.placeholder_square);
                                super.onLoadStarted(placeholder);
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                qrcode.setVisibility(View.GONE);
                                super.onLoadFailed(errorDrawable);
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                qrcode.setVisibility(View.VISIBLE);
                                item.setQrImage(resource);
                                qrcode.setImageBitmap(resource);
                                if (mContext instanceof VirtualAccountActivity) {
                                    ((VirtualAccountActivity) mContext).VisibleBtnView();
                                }
                            }
                        });
            } else {
                qrcode.setVisibility(View.GONE);
            }
        }
        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}