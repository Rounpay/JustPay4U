package com.solution.app.justpay4u.Util.DropdownDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.R;

import java.util.ArrayList;

public class DropDownStringListAdapter extends RecyclerView.Adapter<DropDownStringListAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    private ArrayList<String> mList;

    public DropDownStringListAdapter(Context context, ArrayList<String> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String item = mList.get(position);
        holder.text.setText(item + "");
        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();

                mClickListner.onItemClick(position, item + "", item + "");


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, String value, String object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
        }


    }
}
