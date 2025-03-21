package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.DeliveryStatus;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.timelineview.TimelineView;

import java.util.List;

/**
 * Created by Asus on 20-12-2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class OrderTrackListShoppingAdapter extends RecyclerView.Adapter<OrderTrackListShoppingAdapter.MyViewHolder> {

    List<DeliveryStatus> listItem;
    Context mContext;


    public OrderTrackListShoppingAdapter(Context context, List<DeliveryStatus> listItem) {
        mContext = context;
        this.listItem = listItem;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, listItem.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_timeline, parent, false);
        return new MyViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DeliveryStatus item = listItem.get(position);
       /* if (item.getStatus().equalsIgnoreCase("In Transit")) {
            setMarker(holder, R.drawable.ic_marker_inactive, R.color.darkgrey);
        } else if (item.getStatus().equalsIgnoreCase("Not Picked")) {
            setMarker(holder, R.drawable.ic_marker_active, R.color.grey);
        } else if (item.getStatus().equalsIgnoreCase("Packaged")) {
            setMarker(holder, R.drawable.ic_marker_active, R.color.grey);
        } else if (item.getStatus().equalsIgnoreCase("Delivered")) {
            setMarker(holder, R.drawable.ic_marker_active, R.color.grey);
        } else if (item.getStatus().equalsIgnoreCase("Dispatched")) {
            setMarker(holder, R.drawable.ic_marker_active, R.color.grey);
        } else if (item.getStatus().equalsIgnoreCase("Pending")) {
            setMarker(holder, R.drawable.ic_marker_active, R.color.grey);
        }*/

        if (/*position == 0 && */(!item.getScan().equalsIgnoreCase("Delivered") || !item.getScanType().equalsIgnoreCase("DL"))) {
            holder.timeline.setMarker(mContext.getResources().getDrawable(R.drawable.ic_dot_and_circle));
        } else if (item.getScan().equalsIgnoreCase("Canceled") || item.getScanType().equalsIgnoreCase("CL")) {
            holder.timeline.setMarker(mContext.getResources().getDrawable(R.drawable.ic_circle_fill));
            holder.timeline.setMarkerColor(mContext.getResources().getColor(R.color.red));
        } else {
            holder.timeline.setMarker(mContext.getResources().getDrawable(R.drawable.ic_circle_fill));
        }


        holder.date.setText(ApiShoppingUtilMethods.INSTANCE.formatedDate(item.getStatusDateTime() + ""));
        holder.location.setText((item.getScannedLocation() + "").replaceAll("_", " "));
        holder.title.setText(item.getInstructions() + "");

        holder.status.setText(item.getScan() + "");


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, status, location;
        TimelineView timeline;
        View itemView;

        public MyViewHolder(View view, int viewType) {
           super(view);
            itemView = view;
            timeline = view.findViewById(R.id.timeline);
            status = view.findViewById(R.id.text_timeline_status);
            title = view.findViewById(R.id.text_timeline_title);
            date = view.findViewById(R.id.text_timeline_date);
            location = view.findViewById(R.id.text_timeline_location);
            timeline.initLine(viewType);
        }
    }

}
