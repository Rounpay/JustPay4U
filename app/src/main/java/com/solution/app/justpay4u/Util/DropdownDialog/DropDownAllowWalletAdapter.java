package com.solution.app.justpay4u.Util.DropdownDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.AllowedWallet;
import com.solution.app.justpay4u.R;

import java.util.List;

public class DropDownAllowWalletAdapter extends RecyclerView.Adapter<DropDownAllowWalletAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    int listType; /*1= BuisnessType, 2= PackageType, 3= WalletType*/
    private List<AllowedWallet> mList;

    public DropDownAllowWalletAdapter(Context context, int listType, List<AllowedWallet> mList, int selectdPosition, ClickListner mClickListner) {
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
        final AllowedWallet item = mList.get(position);

        holder.text.setVisibility(View.VISIBLE);
        holder.text.setText(item.getToWalletName() + "");






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
        void onItemClick(int clickPosition, AllowedWallet object);
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
