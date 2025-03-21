package com.solution.app.justpay4u.Util.DropdownDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.MoveToWalletMappings;
import com.solution.app.justpay4u.R;

import java.util.List;



public class DropDownListAdapterNeww extends RecyclerView.Adapter<DropDownListAdapterNeww.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    String isTwo;
    int selectdPosition = -1;
    boolean isShopping;
    private List<MoveToWalletMappings> mList;

    public DropDownListAdapterNeww(Context context,String isTwo, boolean isShopping, List<MoveToWalletMappings> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.isTwo = isTwo;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
        this.isShopping = isShopping;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final MoveToWalletMappings item = mList.get(position);


          if (isTwo.equalsIgnoreCase("to")){
            holder.text.setText(item.getToWalletType() + "");
          }
          else {
              holder.text.setText(item.getFromWalletType() + "");
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
           if (isTwo.equalsIgnoreCase("to")){
             mClickListner.onItemClick(position, item.getToWalletType(), item);

           }else {
               mClickListner.onItemClick(position, item.getFromWalletType(), item);
           }

                }
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, String value, Object object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text, amount;
        View itemView;
        View manView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            amount = view.findViewById(R.id.amount);
            manView = view.findViewById(R.id.manView);
        }


    }
}
