package com.solution.app.justpay4u.Networking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Networking.Object.GlobalPoolTargetData;
import com.solution.app.justpay4u.R;

import java.util.List;

public class GlobalDataAdapter extends RecyclerView.Adapter<GlobalDataAdapter.ViewHolder> {
    private List<GlobalPoolTargetData> datumList;
    private Context context;

    public GlobalDataAdapter(Context context, List<GlobalPoolTargetData> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_datum_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlobalPoolTargetData datum = datumList.get(position);
        holder.levelIDTextView.setText(String.valueOf(datum.getLevelID()));
        holder.targetTextView.setText(String.valueOf(datum.getTarget()));
        holder.totalTeamTextView.setText(String.valueOf(datum.getTotalTeam()));
        holder.remainingTeamTextView.setText(String.valueOf(datum.getRemainingTeam()));
        if (datum.getRemainingTeam() == 0){
            holder.targetStatus.setImageResource(R.drawable.right_icon);
        } else {
            holder.targetStatus.setImageResource(R.drawable.ic_entry_status_cross);
        }
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView levelIDTextView, targetTextView, totalTeamTextView, remainingTeamTextView;
        ImageView targetStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            levelIDTextView = itemView.findViewById(R.id.levelIDTextView);
            targetTextView = itemView.findViewById(R.id.targetTextView);
            totalTeamTextView = itemView.findViewById(R.id.totalTeamTextView);
            remainingTeamTextView = itemView.findViewById(R.id.remainingTeamTextView);
            targetStatus = itemView.findViewById(R.id.targetStatus);
        }
    }
}
