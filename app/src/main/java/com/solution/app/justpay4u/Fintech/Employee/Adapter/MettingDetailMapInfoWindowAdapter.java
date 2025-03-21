package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetMeetingDetails;
import com.solution.app.justpay4u.R;

/**
 * Created by Vishnu Agarwal on 06/03/2021.
 */
public class MettingDetailMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    GetMeetingDetails item;

    public MettingDetailMapInfoWindowAdapter(Activity mActivity, GetMeetingDetails item) {
        myContentsView = mActivity.getLayoutInflater().inflate(R.layout.adapter_map_marker_meeting_details, null);
        this.item = item;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView nameTv = myContentsView.findViewById(R.id.nameTv);
        TextView outletNameTv = myContentsView.findViewById(R.id.outletNameTv);
        TextView mobileTv = myContentsView.findViewById(R.id.mobileTv);
        TextView pincodeTv = myContentsView.findViewById(R.id.pincodeTv);
        TextView areaTv = myContentsView.findViewById(R.id.areaTv);

        nameTv.setText(item.getName() + "");
        mobileTv.setText(item.getMobileNo() + "");
        outletNameTv.setText(item.getOutletName() + "");
        areaTv.setText(item.getArea() + "");
        pincodeTv.setText(item.getPincode() + "");
        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
