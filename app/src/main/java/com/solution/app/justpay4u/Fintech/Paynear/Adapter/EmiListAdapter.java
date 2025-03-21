package com.solution.app.justpay4u.Fintech.Paynear.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pnsol.sdk.vo.request.EMI;
import com.solution.app.justpay4u.R;

import java.util.List;

/**
 * @Author : Bhavani.A
 * @Version : V_13
 * @Date : Fed 20, 2017
 * @Copyright : (C) Paynear Solutions Pvt. Ltd.
 */
public class EmiListAdapter extends ArrayAdapter<EMI> {
    public RadioButton emitenure_c;
    private Activity activity;
    private int textViewResourceId;
    private List<EMI> list;

    public EmiListAdapter(Activity activity, int textViewResourceId,
                          List<EMI> list) {
        super(activity, textViewResourceId, list);
        this.activity = activity;
        this.textViewResourceId = textViewResourceId;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = null;
        try {
            vi = convertView;
            ViewHolder holder = null;
            if (vi == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                vi = inflater.inflate(textViewResourceId, null, true);
                holder = new ViewHolder();
                holder.emitenure_c = (TextView) vi
                        .findViewById(R.id.emi_tenure_s);
                holder.interest_rate = (TextView) vi
                        .findViewById(R.id.intrest_s);
                holder.monthly_instal = (TextView) vi
                        .findViewById(R.id.installments_s);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            EMI vo = list.get(position);
            holder.emitenure_c.setText("" + vo.getTenure() + " months");
            holder.interest_rate.setText("" + vo.getEmiPercentage() + "%");
            holder.monthly_instal.setText("" + vo.getEmiAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vi;

    }

    class ViewHolder {
        public TextView emitenure_c;
        public TextView interest_rate;
        public TextView monthly_instal;
    }
}
