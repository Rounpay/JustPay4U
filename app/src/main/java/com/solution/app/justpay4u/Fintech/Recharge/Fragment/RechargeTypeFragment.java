package com.solution.app.justpay4u.Fintech.Recharge.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.PlanDataDetails;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.RechargeTypeAdapter;

import java.util.ArrayList;

public class RechargeTypeFragment extends Fragment {

    RecyclerView recycler_view;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    ArrayList<PlanDataDetails> operatorDetailshow = new ArrayList<>();
    RechargeTypeAdapter mAdapter;
    private View clearView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recharge_plan, container, false);

        try {
            operatorDetailshow = getArguments().getParcelableArrayList("response");

            recycler_view = v.findViewById(R.id.recycler_view);
            searchEt = v.findViewById(R.id.search_all);
            clearIcon = v.findViewById(R.id.clearIcon);
            clearView = v.findViewById(R.id.clearView);
            noDataView = v.findViewById(R.id.noDataView);
            noNetworkView = v.findViewById(R.id.noNetworkView);
            retryBtn = v.findViewById(R.id.retryBtn);
            errorMsg = v.findViewById(R.id.errorMsg);
            errorMsg.setText("Plan not available.");


            if (operatorDetailshow != null && operatorDetailshow.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mAdapter = new RechargeTypeAdapter(operatorDetailshow, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);

            } else {
                noDataView.setVisibility(View.VISIBLE);
            }

            clearIcon.setOnClickListener(v1 -> searchEt.setText(""));

            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        clearView.setVisibility(View.VISIBLE);
                    } else {
                        clearView.setVisibility(View.GONE);
                    }

                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(s);
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}
