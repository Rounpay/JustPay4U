package com.solution.app.justpay4u.Fintech.AppUser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.solution.app.justpay4u.Api.Fintech.Object.AreaMaster;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosAreaReportActivity;
import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class FosReportAreaListScreenAdapter extends RecyclerView.Adapter<FosReportAreaListScreenAdapter.MyViewHolder> implements Filterable {

    int resourceId = 0;
    RequestOptions requestOptions;
    String qwerty;
    private ArrayList<AreaMaster> filterListItem = new ArrayList<>();
    private ArrayList<AreaMaster> listItem = new ArrayList<>();
    private Context mContext;


    public FosReportAreaListScreenAdapter(ArrayList<AreaMaster> operatorList, Context mContext) {
        this.filterListItem = operatorList;
        this.listItem = operatorList;
        this.mContext = mContext;
        requestOptions = ApiFintechUtilMethods.INSTANCE.getRequestOption_With_Placeholder();
    }


    @Override
    public FosReportAreaListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_area_list, parent, false);

        return new FosReportAreaListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FosReportAreaListScreenAdapter.MyViewHolder holder, int position) {
        final AreaMaster operator = filterListItem.get(position);

        holder.title.setText(operator.getArea());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof FosAreaReportActivity) {
                    ((FosAreaReportActivity) mContext).setArea(operator);
                }

            }
        });


//            holder.title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    Toast.makeText(mContext, ""+operator.getAreaID(), Toast.LENGTH_SHORT).show();
//                }
//            });

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
                    ArrayList<AreaMaster> filteredList = new ArrayList<>();
                    for (AreaMaster row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ((row.getArea() + "").toLowerCase().contains(charString.toLowerCase()) ||
                                (row.getAreaID() + "").toLowerCase().contains(charString.toLowerCase())
                        ) {
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
                filterListItem = (ArrayList<AreaMaster>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView areakarrow;


        public MyViewHolder(View view) {
            super(view);


            title = view.findViewById(R.id.title);
            areakarrow = view.findViewById(R.id.areakarrow);

        }
    }

}
