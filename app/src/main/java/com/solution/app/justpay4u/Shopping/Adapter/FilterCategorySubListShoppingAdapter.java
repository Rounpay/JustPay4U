package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoFilterOptionList;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class FilterCategorySubListShoppingAdapter extends RecyclerView.Adapter<FilterCategorySubListShoppingAdapter.MyViewHolder> implements Filterable {

    private final List<ProductInfoFilterOptionList> listItem;
    private List<ProductInfoFilterOptionList> filterListItem;
    private ItemClickListener clickListener;

    public FilterCategorySubListShoppingAdapter(List<ProductInfoFilterOptionList> listItem, Context mContext) {
        this.listItem = listItem;
        this.filterListItem = listItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_filter_category_sub_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductInfoFilterOptionList item = filterListItem.get(position);

        holder.text.setText(item.getOptionName());
        holder.radioBtn.setChecked(item.getSelected());
        // setSubCategoryString(holder.sub_category,operator.getCategoryString());

        /*holder.viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainCategoryDetailActivity.class);
                intent.putExtra("Id",operator.getMID());
                intent.putExtra("CategoryId",0);
                intent.putExtra("isFromNavigationDrawer",false);
                intent.putExtra("CategoryName",operator.getName());
                intent.putExtra("CategoryList",operator.getCategories());
                mContext.startActivity(intent);
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filterdClickPosition = listItem.indexOf(filterListItem.get(position));
                if (clickListener != null) clickListener.viewClick(v, filterdClickPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterListItem = listItem;
                } else {
                    List<ProductInfoFilterOptionList> filteredList = new ArrayList<>();
                    for (ProductInfoFilterOptionList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOptionName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterListItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterListItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterListItem = (ArrayList<ProductInfoFilterOptionList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemClickListener {
        void viewClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public RadioButton radioBtn;
        View itemView;

        public MyViewHolder(View view) {
           super(view);
            itemView = view;
            text =  view.findViewById(R.id.text);
            radioBtn = (RadioButton) view.findViewById(R.id.radioBtn);

        }
    }

}
