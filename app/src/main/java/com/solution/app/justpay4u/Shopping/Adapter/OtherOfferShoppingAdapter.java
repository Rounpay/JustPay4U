package com.solution.app.justpay4u.Shopping.Adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Shopping.Object.OtherOffer;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;

import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class OtherOfferShoppingAdapter extends RecyclerView.Adapter<OtherOfferShoppingAdapter.MyViewHolder> {

    private final RequestOptions requestOptions;
    private final List<OtherOffer> transactionsList;
    private final Activity mContext;
    int layout;


    public OtherOfferShoppingAdapter(List<OtherOffer> transactionsList, Activity mContext, int layout) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.layout = layout;

        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder_square);
        requestOptions.error(R.drawable.placeholder_square);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        //requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.skipMemoryCache(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OtherOffer operator = transactionsList.get(position);
        holder.tv_type.setText(operator.getAffiliateName());
        holder.tvOtherOfferDescription.setText(operator.getDescription());
        holder.tvOtherOfferTitle.setText(operator.getName());
        Glide.with(mContext)
                .load(operator.getImage())
                .apply(requestOptions)
                .into(holder.otherOfferImage);
        if (operator.getCashback() != null && !operator.getCashback().isEmpty() && operator.getCashbackType() != null && !operator.getCashbackType().isEmpty()) {
            holder.tvOtherOfferCashBack.setVisibility(View.VISIBLE);
            if (operator.getCashbackType().equalsIgnoreCase("2")) {
                holder.tvOtherOfferCashBack.setText("Get " + operator.getCashback().replace(".00", "") + "% Cashback in Moneyfy wallet");
            } else {
                holder.tvOtherOfferCashBack.setText("Get \u20B9 " + operator.getCashback().replace(".00", "") + " Cashback in Moneyfy wallet");
            }

        } else {
            holder.tvOtherOfferCashBack.setVisibility(View.GONE);
        }
        holder.otherOfferImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.getOpenType().equalsIgnoreCase("0")) {
                    String appRedirectUrl = operator.getURL();

                    if (appRedirectUrl.contains("{USERID}")) {
                        appRedirectUrl = appRedirectUrl.replace("{USERID}", ApiShoppingUtilMethods.INSTANCE.mUserID + "");
                    }
                    if (appRedirectUrl.contains("{CLICKID}")) {
                        appRedirectUrl = appRedirectUrl.replace("{CLICKID}", ApiShoppingUtilMethods.INSTANCE.mMobileNumber + "");
                    }


                    if (appRedirectUrl.contains("{DEVICEID}")) {
                        appRedirectUrl = appRedirectUrl.replace("{DEVICEID}", ApiShoppingUtilMethods.INSTANCE.mDeviceId);
                    }
                    if (appRedirectUrl.contains("{ANDROIDID}")) {
                        appRedirectUrl = appRedirectUrl.replace("{ANDROIDID}", ApiShoppingUtilMethods.INSTANCE.mDeviceId);
                    }

                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(appRedirectUrl)));
                    } catch (ActivityNotFoundException anfe) {
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW)
                                    .setData(Uri.parse(appRedirectUrl)));
                        } catch (ActivityNotFoundException anf) {

                        }
                    } catch (Exception e) {

                    }
                } else {

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    private void setSubCategoryString(TextView textView, String subCategoryString) {


        if (subCategoryString != null && !subCategoryString.isEmpty() && subCategoryString.length() > 0) {
            if (subCategoryString.length() > 45) {
                String str = subCategoryString.substring(0, 46);
                str = str.substring(0, str.lastIndexOf(","));
                textView.setText(str + " & More");
            } else {
                textView.setText(subCategoryString);
            }

        } else {
            textView.setVisibility(View.GONE);
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOtherOfferTitle, tvOtherOfferDescription, tvOtherOfferCashBack, tv_type;

        View otherOfferView;
        ImageView otherOfferImage;

        public MyViewHolder(View view) {
           super(view);

            otherOfferView = view.findViewById(R.id.otherOfferView);
            tvOtherOfferDescription = view.findViewById(R.id.tv_other_offer_description);
            tvOtherOfferCashBack = view.findViewById(R.id.tv_other_offer_cashback);
            tvOtherOfferTitle = view.findViewById(R.id.tv_other_offer_title);
            otherOfferImage = view.findViewById(R.id.offerImage);
            tv_type = view.findViewById(R.id.tv_type);

        }
    }

}
