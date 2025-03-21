package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.OperatorList;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.RechargeProviderActivity;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderStickyListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class RechargeProviderHeaderAdapter extends ListAdapter<OperatorList, RecyclerView.ViewHolder> implements HeaderStickyListener {
    public static final DiffUtil.ItemCallback<OperatorList> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<OperatorList>() {
                @Override
                public boolean areItemsTheSame(@NonNull OperatorList model, @NonNull OperatorList t1) {
                    return model.getHeader().equals(t1.getHeader());
                }

                @Override
                public boolean areContentsTheSame(@NonNull OperatorList model, @NonNull OperatorList t1) {
                    return model.equals(t1);
                }
            };
    public static int ListData = 1;
    public static int Header = 2;
    String charText = "";
    int resourceId = 0;
    String operatorType = "";
    RequestOptions requestOptions;
    int stateId;
    String from;
    boolean isLocalAvailable;
    private ArrayList<OperatorList> rechargeStatus;
    private ArrayList<OperatorList> transactionsList;
    private Context mContext;

    public RechargeProviderHeaderAdapter(ArrayList<OperatorList> transactionsList, Context mContext, RequestOptions requestOptions,
                                         int stateId, String from, boolean isLocalAvailable) {
        super(ModelDiffUtilCallback);
        this.transactionsList = transactionsList;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.mContext = mContext;
        this.requestOptions = requestOptions;
        this.stateId = stateId;
        this.from = from;
        this.isLocalAvailable = isLocalAvailable;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_provider_operator, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final OperatorList operator = transactionsList.get(position);
        if (getItemViewType(position) == ListData) {
            ((MyViewHolder) holder).title.setText(operator.getName());
            ((MyViewHolder) holder).ll_top.setOnClickListener(v -> ((RechargeProviderActivity) mContext).ItemClick(operator));


            if (operator.getImage() != null && operator.getImage().length() > 0) {
                Glide.with(mContext)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getImage())
                        .apply(requestOptions)
                        .into(((MyViewHolder) holder).opImage);
            } else {
                ((MyViewHolder) holder).opImage.setImageResource(R.drawable.ic_tower);
            }


        } else {
            ((HeaderViewHolder) holder).title.setText(operator.getHeader());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (transactionsList.get(position).getHeader() != null && !transactionsList.get(position).getHeader().isEmpty()) {
            return Header;
        } else {
            return ListData;
        }

    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = header.findViewById(R.id.opType);
        tv.setText(transactionsList.get(headerPosition).getHeader());
    }

    @Override
    public String getHeaderString(int headerPosition) {
        return transactionsList.get(headerPosition).getHeader();
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (transactionsList.get(itemPosition).getHeader() != null && !transactionsList.get(itemPosition).getHeader().isEmpty()) {
            return true;
        }
        return false;
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {

            ArrayList<OperatorList> localProviderArray = new ArrayList<>();
            ArrayList<OperatorList> otherProviderArray = new ArrayList<>();

            for (OperatorList wp : rechargeStatus) {
                if (wp.getName() != null && wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {

                    if (isLocalAvailable && wp.getStateID() == stateId) {
                        localProviderArray.add(wp);
                    } else {
                        otherProviderArray.add(wp);
                    }

                }
            }
            if (isLocalAvailable) {
                if (localProviderArray.size() > 0) {
                    OperatorList localProviderHeader = new OperatorList();
                    localProviderHeader.setHeader("Local " + from + " Provider");
                    localProviderArray.add(0, localProviderHeader);
                    transactionsList.addAll(localProviderArray);
                }
                if (otherProviderArray.size() > 0) {
                    OperatorList otherProviderHeader = new OperatorList();
                    otherProviderHeader.setHeader("Other " + from + " Provider");
                    otherProviderArray.add(0, otherProviderHeader);
                    transactionsList.addAll(otherProviderArray);
                }
            } else {
                OperatorList otherProviderHeader = new OperatorList();
                otherProviderHeader.setHeader(from + " Provider");
                otherProviderArray.add(0, otherProviderHeader);
                transactionsList.addAll(otherProviderArray);
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;
        LinearLayout ll_top;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            opImage = view.findViewById(R.id.opImage);
            ll_top = view.findViewById(R.id.ll_top);

        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.opType);
           /* ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
            params.bottomMargin = 12;*/
        }

    }
}