package com.solution.app.justpay4u.Fintech.CommissionSlab.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.Fintech.CommissionSlab.Activity.CommissionScreenActivity;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.RecyclerViewStickyHeader.HeaderStickyListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vishnu on 26-04-2017.
 */

public class CommissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HeaderStickyListener {
    public static final Integer ListData = 1;
    public static final Integer Header = 2;

    String searchText = "";
    RequestOptions requestOptions;
    int roleId;
    private ArrayList<SlabDetailDisplayLvl> itemList = new ArrayList<>();
    private ArrayList<SlabDetailDisplayLvl> itemFilterList = new ArrayList<>();
    private Context mContext;

    public CommissionAdapter(ArrayList<SlabDetailDisplayLvl> transactionsList, Context mContext, int roleId) {
        //super(ModelDiffUtilCallback);
        try {
            this.roleId = roleId;
        } catch (NumberFormatException nfe) {

        }
        this.itemList = transactionsList;
        this.itemFilterList.addAll(itemList);
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            if (roleId == 3 || roleId == 2) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_commision_retailer, parent, false);
                return new MyViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_commision, parent, false);
                return new MyViewHolder(view);
            }

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SlabDetailDisplayLvl operator = itemFilterList.get(position);
        if (getItemViewType(position) == ListData) {

            ((MyViewHolder) holder).operator.setText("" + operator.getOperator());
            if (operator.getOid() == 175 && operator.getOperator().contains("Digital") ||
                    operator.getOid() == 176 && operator.getOperator().contains("Physical")) {
                ((MyViewHolder) holder).maxMin.setText("Price : " + operator.getMin());
            } else {
                ((MyViewHolder) holder).maxMin.setText("Range : " + operator.getMin() + " - " + operator.getMax());
            }


            if (roleId == 3) {
                if (operator.getCommSettingType() == 2) {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).realView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.VISIBLE);
                } else {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).realView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.GONE);
                }
                ((MyViewHolder) holder).lapuTitle.setText("Margin");
                ((MyViewHolder) holder).lapuTitleView.setVisibility(View.GONE);
                ((MyViewHolder) holder).recycler_view.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 0));
                //((MyViewHolder) holder).recycler_view_real.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 1));

            } else if (roleId == 2) {
                if (operator.getCommSettingType() == 2) {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).realView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.VISIBLE);
                } else {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).realView.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.GONE);
                }
                ((MyViewHolder) holder).recycler_view.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 0));
                ((MyViewHolder) holder).recycler_view_real.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 1));
            } else {
                if (operator.getCommSettingType() == 2) {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).realView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.VISIBLE);
                } else {
                    ((MyViewHolder) holder).lapuView.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).realView.setVisibility(View.GONE);
                    ((MyViewHolder) holder).viewDetails.setVisibility(View.GONE);
                }
                ((MyViewHolder) holder).lapuTitle.setText("Margin");
                ((MyViewHolder) holder).lapuTitleView.setVisibility(View.GONE);
                ((MyViewHolder) holder).recycler_view.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 0));
                //((MyViewHolder) holder).recycler_view_real.setAdapter(new CommissionRoleAdapter(operator.getRoleCommission(), mContext, operator.getRoleCommission().size(), 1));
            }

            Glide.with(mContext)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + operator.getOid() + ".png")
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).icon);
            //holder.operator.setText(getname(operator.getOid()));
            String faqsearchDescription = operator.getOperator();
            if (faqsearchDescription.contains(searchText)) {
                int startPos = faqsearchDescription.indexOf(searchText);
                int endPos = startPos + searchText.length();
                Spannable spanText = Spannable.Factory.getInstance().newSpannable(operator.getOperator()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
                spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((MyViewHolder) holder).operator.setText(spanText, TextView.BufferType.SPANNABLE);

            } else {
                ((MyViewHolder) holder).operator.setText(operator.getOperator());
            }


            ((MyViewHolder) holder).viewDetails.setOnClickListener(v -> {
                if (mContext instanceof CommissionScreenActivity) {
                    ((CommissionScreenActivity) mContext).HitApiSlabDetail(operator.getOid(), operator.getOperator(),
                            operator.getMin() + " - " + operator.getMax());
                }
            });


        } else {
            ((HeaderViewHolder) holder).title.setText(operator.getHeader());
        }
    }
   /* @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_commision, parent, false);

        return new MyViewHolder(itemView);
    }*/

    @Override
    public int getItemViewType(int position) {
        if (itemFilterList.get(position).getHeader() != null && !itemFilterList.get(position).getHeader().isEmpty()) {
            return Header;
        } else {
            return ListData;
        }

    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        for (int i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public String getHeaderString(int headerPosition) {
        return itemFilterList.get(headerPosition).getHeader();
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = header.findViewById(R.id.opType);
        tv.setText(itemFilterList.get(headerPosition).getHeader());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (itemFilterList.get(itemPosition).getHeader() != null && !itemFilterList.get(itemPosition).getHeader().isEmpty()) {
            return true;
        }
        return false;
    }

    // Filter Class
    public void filter(String charText) {
        this.searchText = charText.toLowerCase(Locale.getDefault());
        itemFilterList.clear();
        if (charText.length() == 0) {
            itemFilterList.addAll(itemList);
        } else {
            /*for (SlabDetailDisplayLvl wp : rechargeStatus) {
                if (wp.getOperator().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOpType().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }*/

            String HeaderName = "";

            for (SlabDetailDisplayLvl wp : itemList) {
                if (wp.getOperator().toLowerCase(Locale.getDefault()).contains(searchText) || wp.getOpType().toLowerCase(Locale.getDefault()).contains(searchText)) {
                    if (!HeaderName.equalsIgnoreCase(wp.getOpType())) {
                        HeaderName = wp.getOpType();
                        itemFilterList.add(new SlabDetailDisplayLvl(wp.getOpType(), 0, "", 0, "", 0, 0, 0, null));
                    }
                    itemFilterList.add(new SlabDetailDisplayLvl(null, wp.getOid(), wp.getOperator(), wp.getOpTypeId(), wp.getOpType(), wp.getMin(), wp.getMax(), wp.getCommSettingType(), wp.getRoleCommission()));
                }
            }

        }

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return itemFilterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView operator;
        TextView maxMin, lapuTitle, realTitle;
        ImageView icon;
        View viewDetails, lapuTitleView, realTitleView, lapuView, realView;
        RecyclerView recycler_view, recycler_view_real;

        public MyViewHolder(View view) {
            super(view);
            viewDetails = view.findViewById(R.id.viewDetails);
            lapuTitle = view.findViewById(R.id.lapuTitle);
            realTitle = view.findViewById(R.id.realTitle);
            lapuTitleView = view.findViewById(R.id.lapuTitleView);
            realTitleView = view.findViewById(R.id.realTitleView);
            lapuView = view.findViewById(R.id.lapuView);
            realView = view.findViewById(R.id.realView);

            maxMin = view.findViewById(R.id.maxMin);
            operator = (AppCompatTextView) view.findViewById(R.id.operator);
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recycler_view_real = view.findViewById(R.id.recycler_view_real);
            recycler_view_real.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            if (itemList.size() > 1 && itemList.get(1).getRoleCommission() != null && itemList.get(1).getRoleCommission().size() > 0) {
                recycler_view.setLayoutManager(new GridLayoutManager(mContext, itemList.get(1).getRoleCommission().size()));
                recycler_view_real.setLayoutManager(new GridLayoutManager(mContext, itemList.get(1).getRoleCommission().size()));
            } else {
                recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
                recycler_view_real.setLayoutManager(new LinearLayoutManager(mContext));
            }
            recycler_view.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            recycler_view_real.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            icon =  view.findViewById(R.id.icon);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.opType);
        }

    }
}