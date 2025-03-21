package com.solution.app.justpay4u.Fintech.Recharge.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.CircleList;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Activity.CircleZoneActivity;

import java.util.ArrayList;

public class CircleZoneAdapter extends RecyclerView.Adapter<CircleZoneAdapter.MyViewHolder> implements Filterable {

    int resourceId = 0;
    private ArrayList<CircleList> filterListItem, listItem;
    private Context mContext;

    public CircleZoneAdapter(ArrayList<CircleList> listItem, Context mContext) {
        this.filterListItem = listItem;
        this.listItem = listItem;

        this.mContext = mContext;

    }

    @Override
    public CircleZoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_circle_zone, parent, false);

        return new CircleZoneAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CircleZoneAdapter.MyViewHolder holder, int position) {
        final CircleList operator = filterListItem.get(position);
        holder.title.setPadding(20, 0, 0, 0);
        holder.title.setText(operator.getCircle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CircleZoneActivity) mContext).ItemClick(operator.getCircle(), operator.getId(), operator.getCircle());
            }
        });
        holder.opImage.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return filterListItem.size();
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
                    ArrayList<CircleList> filteredList = new ArrayList<>();
                    for (CircleList row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCircle().toLowerCase().contains(charString.toLowerCase())) {
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
                filterListItem = (ArrayList<CircleList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            opImage =  view.findViewById(R.id.opImage);
        }
    }

}
