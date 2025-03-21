package com.solution.app.justpay4u.Fintech.Recharge.Fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanLanguages;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanInfoPlan;
import com.solution.app.justpay4u.Api.Fintech.Response.PlanRPResponse;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthPlanListAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthPlanListLanguageAdapter;
import com.solution.app.justpay4u.Fintech.Recharge.Adapter.DthPlanListRPAdapter;

import java.util.ArrayList;


public class DthViewPlanFragment extends Fragment {

    RecyclerView recycler_view;
    ArrayList<PlanInfoPlan> operatorDetailshow = new ArrayList<PlanInfoPlan>();
    ArrayList<PlanInfoPlan> operatorDetailPAshow = new ArrayList<PlanInfoPlan>();
    ArrayList<PlanRPResponse> operatorDetailRPshow = new ArrayList<>();
    ArrayList<PlanRPResponse> operatorDetailRPNewshow = new ArrayList<>();
    ArrayList<DthPlanLanguages> operatorDetailRPNewLanguages = new ArrayList<>();
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    private View clearView;
    private DthPlanListAdapter mAdapter;
    private DthPlanListRPAdapter mRPAdapter;
    private DthPlanListLanguageAdapter mLanguageAdapter;
    private boolean isPlanServiceUpdated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dth_plan, container, false);

        try {
            String opId = getArguments().getString("OpID");
            isPlanServiceUpdated = getArguments().getBoolean("IsPlanServiceUpdated", false);
            operatorDetailshow =  getArguments().getParcelableArrayList("response");
            operatorDetailRPshow =  getArguments().getParcelableArrayList("responseRP");
            operatorDetailRPNewshow =  getArguments().getParcelableArrayList("responseRPNew");
            operatorDetailRPNewLanguages =  getArguments().getParcelableArrayList("responseRPNewLanguages");
            operatorDetailPAshow =  getArguments().getParcelableArrayList("responsePA");
            recycler_view = v.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                mAdapter = new DthPlanListAdapter(isPlanServiceUpdated, opId, operatorDetailshow, getActivity());
                recycler_view.setAdapter(mAdapter);
            } else if (operatorDetailPAshow != null && operatorDetailPAshow.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mAdapter = new DthPlanListAdapter(isPlanServiceUpdated, opId, operatorDetailPAshow, getActivity());
                recycler_view.setAdapter(mAdapter);
            } else if (operatorDetailRPshow != null && operatorDetailRPshow.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mRPAdapter = new DthPlanListRPAdapter(isPlanServiceUpdated, operatorDetailRPshow, getActivity(), opId);
                recycler_view.setAdapter(mRPAdapter);
            } else if (operatorDetailRPNewshow != null && operatorDetailRPNewshow.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mRPAdapter = new DthPlanListRPAdapter(isPlanServiceUpdated, operatorDetailRPNewshow, getActivity(), opId);
                recycler_view.setAdapter(mRPAdapter);
            } else if (operatorDetailRPNewLanguages != null && operatorDetailRPNewLanguages.size() > 0) {
                noDataView.setVisibility(View.GONE);
                mLanguageAdapter = new DthPlanListLanguageAdapter(operatorDetailRPNewLanguages, getActivity());
                recycler_view.setAdapter(mLanguageAdapter);
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
                    if (mRPAdapter != null) {
                        mRPAdapter.getFilter().filter(s);
                    }
                    if (mLanguageAdapter != null) {
                        mLanguageAdapter.getFilter().filter(s);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


}
