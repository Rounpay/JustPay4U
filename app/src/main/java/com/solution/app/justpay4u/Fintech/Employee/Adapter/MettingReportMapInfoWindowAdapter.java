package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.solution.app.justpay4u.R;

/**
 * Created by Vishnu Agarwal on 06/03/2021.
 */
public class MettingReportMapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;

    public MettingReportMapInfoWindowAdapter(Activity mActivity) {
        myContentsView = mActivity.getLayoutInflater().inflate(R.layout.adapter_map_marker_meeting_report, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView descTv = myContentsView.findViewById(R.id.desc);
        descTv.setText(marker.getTitle() + "");
        return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
