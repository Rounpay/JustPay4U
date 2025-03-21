package com.solution.app.justpay4u.Shopping.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.R;

import java.util.List;

public class ChooseQuantityListShoppingAdapter extends RecyclerView.Adapter<ChooseQuantityListShoppingAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    private final List<String> mList;
    Context context;
    int selectdPosition = -1;

    public ChooseQuantityListShoppingAdapter(Context context, List<String> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        ChooseQuantityListShoppingAdapter.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_choose_quantity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String item = mList.get(position);
        holder.text.setText(item);
        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();
                try {
                    mClickListner.onItemClick(position, Integer.parseInt(item), v);
                } catch (NumberFormatException nfe) {
                    mClickListner.onItemClick(position, 1, v);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int position, int value, View v);
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
