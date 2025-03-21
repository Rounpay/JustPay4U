package com.solution.app.justpay4u.Fintech.Employee.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetUserCommitment;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SetUserCommitmentAdapter extends RecyclerView.Adapter<SetUserCommitmentAdapter.MyViewHolder> {


    private final String todatay;
    private CustomLoader loader;
    LoginResponse loginPrefResponse;
    private List<GetUserCommitment> transactionsList;
    private Activity mContext;
    onSucess mOnSucess;
    String deviceId;
    String deviceSerialNum;

    public SetUserCommitmentAdapter(List<GetUserCommitment> transactionsList, Activity mContext,
                                    LoginResponse loginPrefResponse, String deviceId, String deviceSerialNum, CustomLoader loader, onSucess mOnSucess) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.loader = loader;
        this.mOnSucess = mOnSucess;
        this.deviceId = deviceId;
        this.deviceSerialNum = deviceSerialNum;
        this.loginPrefResponse = loginPrefResponse;
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        todatay = sdf.format(myCalendar.getTime());

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user_commitment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GetUserCommitment operator = transactionsList.get(position);
        holder.name.setText(operator.getUserName() + " [" + operator.getPrefix() + operator.getUserID() + "]");


        //  holder.id.setText(operator.getUserID() + "");
        holder.role.setText(operator.getRole() + "");
        holder.mobile.setText(operator.getUserMobile() + "");
       /* if (operator.getUserMobile() != null && !operator.getUserMobile().isEmpty()) {
            String htmlNum = "<a href=tel:" + operator.getUserMobile() + ">" + operator.getUserMobile() + "</a>";
            holder.mobile.setText(Html.fromHtml(htmlNum));
        } else {
            holder.mobile.setText(operator.getUserMobile() + "");
        }*/
        holder.city.setText(operator.getCity() + "");
        holder.state.setText(operator.getState() + "");
        if (operator.getCommitment() != 0) {
            holder.applyBtn.setVisibility(View.GONE);
            holder.commit.setFocusable(false);
        } else {
            holder.applyBtn.setVisibility(View.VISIBLE);
            holder.commit.setFocusableInTouchMode(true);
            holder.commit.setFocusable(true);
        }
        holder.commit.setText(operator.getCommitment() + "");
        holder.commit.setSelection(holder.commit.getText().length());
        if (position == 0) {
            holder.commit.requestFocus();
        }
        holder.achieved.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getAchieved() + ""));
        holder.balance.setText(Utility.INSTANCE.formatedAmountWithRupees(operator.getBalance() + ""));
        holder.applyBtn.setOnClickListener(v -> {
            if (holder.commit.getText().toString().isEmpty()) {
                holder.commit.setError("Please Enter Amount");
                holder.commit.requestFocus();
                return;
            }

            if (ApiFintechUtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                try {

                    ApiFintechUtilMethods.INSTANCE.SetUserCommitment(mContext, todatay, operator.getUserID(), holder.commit.getText().toString(),
                            0, 0, operator.getEmpID(), operator.getRole(), loginPrefResponse, deviceId, deviceSerialNum, loader, object -> {
                                // holder.commit.setText("");
                                if (mOnSucess != null) {
                                    mOnSucess.onSucess();
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                ApiFintechUtilMethods.INSTANCE.NetworkError(mContext);

            }
        });

        holder.callView.setOnClickListener(v -> {
            try {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + operator.getUserMobile() + ""));
                mContext.startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + operator.getUserMobile() + ""));
                mContext.startActivity(dialIntent);
            }
        });

        holder.whatsappView.setOnClickListener(v -> Utility.INSTANCE.openWhatsapp(mContext,operator.getUserMobile() + ""));
    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        View callView, whatsappView;
        // private TextView id;
        private TextView role;
        private TextView mobile;
        private TextView city;
        private TextView state;
        private TextView achieved, balance;
        private EditText commit;
        private View applyBtn;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            //  id = view.findViewById(R.id.id);
            role = view.findViewById(R.id.role);
            callView = view.findViewById(R.id.callView);
            whatsappView = view.findViewById(R.id.whatsapView);
            mobile = view.findViewById(R.id.mobile);
            mobile.setMovementMethod(LinkMovementMethod.getInstance());
            city = view.findViewById(R.id.city);
            state = view.findViewById(R.id.state);
            achieved = view.findViewById(R.id.achieved);
            balance = view.findViewById(R.id.balance);
            commit = view.findViewById(R.id.commit);
            applyBtn = view.findViewById(R.id.applyBtn);


        }
    }

    public interface onSucess {
        void onSucess();
    }


}


