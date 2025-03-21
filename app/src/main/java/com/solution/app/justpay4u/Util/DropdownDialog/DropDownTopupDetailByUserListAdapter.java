package com.solution.app.justpay4u.Util.DropdownDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.GetTopupDetailsByUserIdData;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.Utility;

import java.util.List;

public class DropDownTopupDetailByUserListAdapter extends RecyclerView.Adapter<DropDownTopupDetailByUserListAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    int listType; /*1= BuisnessType, 2= PackageType, 3= WalletType*/
    private List<GetTopupDetailsByUserIdData> mList;

    public DropDownTopupDetailByUserListAdapter(Context context, int listType, List<GetTopupDetailsByUserIdData> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
        this.listType = listType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GetTopupDetailsByUserIdData item = mList.get(position);
        if (item.getName() != null && !item.getName().isEmpty()) {
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(item.getName() + "");
        } else if (item.getPackageName() != null && !item.getPackageName().isEmpty()) {
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(item.getPackageName() + "");
        } else {
            holder.text.setVisibility(View.GONE);
        }

        if (listType == 1) {
            holder.amount.setVisibility(View.GONE);
        } else if (listType == 2) {
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getPackageCost() + ""));

        } else if (listType == 3) {
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(Utility.INSTANCE.formatedAmountWithRupees(item.getUserBalance() + ""));
        }


        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();

                mClickListner.onItemClick(position, item);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, GetTopupDetailsByUserIdData object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text, amount;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            amount = view.findViewById(R.id.amount);
        }


    }
}
