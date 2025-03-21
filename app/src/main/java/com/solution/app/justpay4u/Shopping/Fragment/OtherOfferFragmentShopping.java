package com.solution.app.justpay4u.Shopping.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.OtherOffer;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.OtherOfferShoppingAdapter;

import java.util.ArrayList;

public class OtherOfferFragmentShopping extends Fragment {

    ArrayList<OtherOffer> mOtherOffers = new ArrayList<>();
    RecyclerView recyclerView;
    OtherOfferShoppingAdapter mOtherOfferAdapter;
    ProgressBar loader;


    View noDataView, noNetworkView, retryBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_main_category_detail, container, false);
        loader = view.findViewById(R.id.loader);
        noDataView = view.findViewById(R.id.noDataView);
        noNetworkView = view.findViewById(R.id.noNetworkView);
        retryBtn = view.findViewById(R.id.retryBtn);
        recyclerView =  view.findViewById(R.id.recyclerView);
        mOtherOffers.addAll((ArrayList<OtherOffer>) getArguments().getSerializable("OtherOfferList"));
        mOtherOfferAdapter = new OtherOfferShoppingAdapter(mOtherOffers, getActivity(), R.layout.adapter_shopping_other_offer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mOtherOfferAdapter);

        return view;
    }


}