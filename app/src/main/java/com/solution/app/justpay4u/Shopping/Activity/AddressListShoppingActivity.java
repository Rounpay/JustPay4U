package com.solution.app.justpay4u.Shopping.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Shopping.Object.Address;
import com.solution.app.justpay4u.Api.Shopping.Response.AddressListResponse;
import com.solution.app.justpay4u.ApiHits.ApiShoppingUtilMethods;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Shopping.Adapter.AddressListShoppingAdapter;
import com.solution.app.justpay4u.Util.CustomLoader;

import java.util.ArrayList;

public class AddressListShoppingActivity extends AppCompatActivity {

    public int INTENT_ADD_ADDRESS = 6456;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    ArrayList<Address> mAddressLists = new ArrayList<>();
    RecyclerView recyclerView;
    AddressListShoppingAdapter mAddressListAdapter;
    private CustomLoader loader;
    private AddressListResponse mAddressListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        new Handler(Looper.getMainLooper()).post(() -> {
            setContentView(R.layout.activity_shopping_address_list);
            findViews();
        });

    }


    void findViews() {

        noDataView = findViewById(R.id.noDataView);
        noNetworkView = findViewById(R.id.noNetworkView);
        retryBtn = findViewById(R.id.retryBtn);
        errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setText("Product is not available.");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddressListAdapter = new AddressListShoppingAdapter(mAddressLists, this);
        recyclerView.setAdapter(mAddressListAdapter);
        retryBtn.setOnClickListener(v ->
                getAddressList(this)
        );

        findViewById(R.id.btnAdd).setOnClickListener(view ->
                startActivityForResult(new Intent(this, AddAddressShoppingActivity.class), INTENT_ADD_ADDRESS));

        getAddressList(this);

    }


    void getAddressList(Activity mContext) {
        if (loader != null && !loader.isShowing()) {
            loader.show();
        }
        ApiShoppingUtilMethods.INSTANCE.getAddressList(mContext, loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
            @Override
            public void onSucess(Object object) {

                mAddressListResponse = (AddressListResponse) object;
                if (mAddressListResponse != null && mAddressListResponse.getAddresses() != null &&
                        mAddressListResponse.getAddresses().size() > 0) {
                    mAddressLists.clear();
                    mAddressLists.addAll(mAddressListResponse.getAddresses());
                    mAddressListAdapter.notifyDataSetChanged();
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.GONE);
                } else {
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int error) {
                if (error == 0) {
                    noDataView.setVisibility(View.VISIBLE);
                    noNetworkView.setVisibility(View.GONE);
                } else {
                    noDataView.setVisibility(View.GONE);
                    noNetworkView.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_ADD_ADDRESS && resultCode == RESULT_OK) {
            getAddressList(this);
        }
    }

    public void ChangeAddress(int type, int pos, int previousDefaultPos, Address operator) {
        if (loader != null) {
            loader.show();
        }

        //type =1 delete
        //type =2 default
        ApiShoppingUtilMethods.INSTANCE.AddAddress(this, type != 2, type == 2 || operator.isDefault(), operator.getId(), operator.getTitle(),
                operator.getCustomerName(), operator.getMobileNo(), operator.getAddressOnly(), operator.getCityID(), operator.getStateID(),
                operator.getArea(), operator.getPinCode(), operator.getLandmark(), loader, new ApiShoppingUtilMethods.ApiResponseCallBack() {
                    @Override
                    public void onSucess(Object object) {

                        if (type == 1) {
                            mAddressLists.remove(pos);
                            if (mAddressLists.size() == 0) {
                                noDataView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (previousDefaultPos != -1) {
                                mAddressLists.get(previousDefaultPos).setDefault(false);
                            }
                            mAddressLists.get(pos).setDefault(true);
                        }
                        mAddressListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int error) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
