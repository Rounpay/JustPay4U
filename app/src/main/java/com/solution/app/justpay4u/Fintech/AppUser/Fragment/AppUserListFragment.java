package com.solution.app.justpay4u.Fintech.AppUser.Fragment;

import static android.app.Activity.RESULT_OK;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.app.justpay4u.Api.Fintech.Object.BalanceData;
import com.solution.app.justpay4u.Api.Fintech.Object.BalanceType;
import com.solution.app.justpay4u.Api.Fintech.Object.UserList;
import com.solution.app.justpay4u.Api.Fintech.Request.AppUserListRequest;
import com.solution.app.justpay4u.Api.Fintech.Response.AppUserListResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.BalanceResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.GetEKYCDetailResponse;
import com.solution.app.justpay4u.Api.Fintech.Response.LoginResponse;
import com.solution.app.justpay4u.ApiHits.ApiClient;
import com.solution.app.justpay4u.ApiHits.ApiFintechUtilMethods;
import com.solution.app.justpay4u.ApiHits.ApplicationConstant;
import com.solution.app.justpay4u.ApiHits.FintechEndPointInterface;
import com.solution.app.justpay4u.BuildConfig;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.AppUserListActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Activity.FosCollectionActivity;
import com.solution.app.justpay4u.Fintech.AppUser.Adapter.AppUserListAdapter;
import com.solution.app.justpay4u.R;
import com.solution.app.justpay4u.Util.AppPreferences;
import com.solution.app.justpay4u.Util.CustomLoader;
import com.solution.app.justpay4u.Util.EKYCStepsDialog;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class AppUserListFragment extends Fragment {

    AppUserListAdapter mAppUserListAdapter;
    View loader, clearView;
    RecyclerView mRecyclerView;
    String rollId;
    View noDataView, noNetworkView, retryBtn;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    LoginResponse mLoginDataResponse;
    AppPreferences mAppPreferences;
    private ArrayList<UserList> mUserLists = new ArrayList<>();
    private String deviceId, deviceSerialNum;
    private BalanceResponse balanceCheckResponse;
    private int INTENT_COLLECTION = 6161;
    private boolean isEKYCCompleted;
    private EKYCStepsDialog mEKYCStepsDialog;

    public AppUserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_user_list, container, false);

        try {
            if (((AppUserListActivity) getActivity()).mAppPreferences != null) {
                mAppPreferences = ((AppUserListActivity) getActivity()).mAppPreferences;
            } else {
                mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(getActivity());
            }
        } catch (NullPointerException npe) {
            mAppPreferences = ApiFintechUtilMethods.INSTANCE.getAppPreferences(getActivity());
        }
        try {
            if (((AppUserListActivity) getActivity()).mLoginDataResponse != null) {
                mLoginDataResponse = ((AppUserListActivity) getActivity()).mLoginDataResponse;
            } else {
                mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
            }
        } catch (NullPointerException npe) {
            mLoginDataResponse = ApiFintechUtilMethods.INSTANCE.getLoginResponse(mAppPreferences);
        }

        try {
            if (((AppUserListActivity) getActivity()).deviceId != null) {
                deviceId = ((AppUserListActivity) getActivity()).deviceId;
            } else {
                deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
            }
        } catch (NullPointerException npe) {
            deviceId = ApiFintechUtilMethods.INSTANCE.getDeviceId(mAppPreferences);
        }

        try {
            if (((AppUserListActivity) getActivity()).deviceSerialNum != null) {
                deviceSerialNum = ((AppUserListActivity) getActivity()).deviceSerialNum;
            } else {
                deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
            }
        } catch (NullPointerException npe) {
            deviceSerialNum = ApiFintechUtilMethods.INSTANCE.getSerialNumber(mAppPreferences);
        }
        try {
            if (((AppUserListActivity) getActivity()).deviceSerialNum != null) {
                isEKYCCompleted = ((AppUserListActivity) getActivity()).isEKYCCompleted;
            } else {
                isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
            }
        } catch (NullPointerException npe) {
            isEKYCCompleted = mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.isEKYCComplete);
        }

        try {
            if (((AppUserListActivity) getActivity()).deviceSerialNum != null) {
                mEKYCStepsDialog = ((AppUserListActivity) getActivity()).mEKYCStepsDialog;
            } else {
                mEKYCStepsDialog = new EKYCStepsDialog(getActivity(), mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
            }
        } catch (NullPointerException npe) {
            mEKYCStepsDialog = new EKYCStepsDialog(getActivity(), mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences);
        }
        try {
            if (((AppUserListActivity) getActivity()).balanceCheckResponse != null) {
                balanceCheckResponse = ((AppUserListActivity) getActivity()).balanceCheckResponse;
            } else {
                balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
            }
        } catch (NullPointerException npe) {
            balanceCheckResponse = ApiFintechUtilMethods.INSTANCE.getBalanceResponse(mAppPreferences);
        }

        rollId = getArguments().getString("Id");

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        loader = rootView.findViewById(R.id.loader);
        searchEt = rootView.findViewById(R.id.search_all);
        clearIcon = rootView.findViewById(R.id.clearIcon);
        clearView = rootView.findViewById(R.id.clearView);
        noDataView = rootView.findViewById(R.id.noDataView);
        noNetworkView = rootView.findViewById(R.id.noNetworkView);
        retryBtn = rootView.findViewById(R.id.retryBtn);
        errorMsg = rootView.findViewById(R.id.errorMsg);
        errorMsg.setText("You don't have any user.");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAppUserListAdapter = new AppUserListAdapter(getActivity(), mUserLists, 0, mLoginDataResponse, mAppPreferences, this,
                new AppUserListAdapter.FundTransferCallBAck() {
                    @Override
                    public void onSucessFund() {
                        appUserListApi();
                    }

                    @Override
                    public void onSucessEdit() {
                        appUserListApi();
                    }
                });
        mRecyclerView.setAdapter(mAppUserListAdapter);


        clearIcon.setOnClickListener(v -> searchEt.setText(""));

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

                mAppUserListAdapter.getFilter().filter(s);
            }
        });
        retryBtn.setOnClickListener(v -> appUserListApi());
        if (balanceCheckResponse != null && balanceCheckResponse.isEKYCForced() && !isEKYCCompleted) {
            mEKYCStepsDialog.GetKycDetails(new EKYCStepsDialog.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(GetEKYCDetailResponse object) {

                    isEKYCCompleted = object.getData().isIsEKYCDone();
                }

                @Override
                public void onError(Object object) {

                }
            });

        }
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                && balanceCheckResponse.getBalanceData().size() > 0) {
            mAppUserListAdapter.setBalanceData(balanceCheckResponse.getBalanceData());
            appUserListApi();
        } else {
            ApiFintechUtilMethods.INSTANCE.Balancecheck(getActivity(), new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar),
                    mLoginDataResponse, deviceId, deviceSerialNum, mAppPreferences, mEKYCStepsDialog, object -> {
                        balanceCheckResponse = (BalanceResponse) object;
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null
                                && balanceCheckResponse.getBalanceData().size() > 0) {
                            mAppUserListAdapter.setBalanceData(balanceCheckResponse.getBalanceData());
                            appUserListApi();
                        }
                    });
        }

        return rootView;
    }



    public void appUserListApi() {
        try {
            loader.setVisibility(View.VISIBLE);
            FintechEndPointInterface git = ApiClient.getClient().create(FintechEndPointInterface.class);
            Call<AppUserListResponse> call = git.AppUserList(new AppUserListRequest(rollId, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    deviceId, "", BuildConfig.VERSION_NAME,
                    deviceSerialNum, mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();

                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    ArrayList<UserList> mResponseUserLists = data.getUserList();
                                    if (mResponseUserLists != null && mResponseUserLists.size() > 0) {
                                        noDataView.setVisibility(View.GONE);
                                        noNetworkView.setVisibility(View.GONE);
                                        int gridSize = 3;
                                        for (int i = 0; i < mResponseUserLists.size(); i++) {

                                            ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
                                            for (BalanceData item : balanceCheckResponse.getBalanceData()) {
                                                if (item.getId() == 1) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getBalance()));
                                                }
                                                if (item.getId() == 2) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getuBalance()));
                                                }
                                                if (item.getId() == 3) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getbBalance()));
                                                }
                                                if (item.getId() == 4) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getcBalance()));
                                                }
                                                if (item.getId() == 5) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getIdBalnace()));
                                                }
                                                if (item.getId() == 6&&(mResponseUserLists.get(i).getRoleID() != 3 ||
                                                        !balanceCheckResponse.isPackageDeducionForRetailor() &&
                                                                mResponseUserLists.get(i).getRoleID() == 3)) {
                                                    mBalanceTypes.add(new BalanceType(item.getWalletType() + " Balance", mResponseUserLists.get(i).getPacakgeBalance()));
                                                }
                                            }

                                            if (mLoginDataResponse.isAccountStatement()) {
                                                mBalanceTypes.add(new BalanceType("Outstanding Balance", mResponseUserLists.get(i).getOsBalance()));
                                            }

                                            if (i == 0) {
                                                gridSize = mBalanceTypes.size();
                                            }
                                            mResponseUserLists.get(i).setBalanceTypes(mBalanceTypes);
                                        }
                                        mUserLists.clear();
                                        mUserLists.addAll(mResponseUserLists);
                                        mAppUserListAdapter.setGridSize(gridSize);
                                        mAppUserListAdapter.notifyDataSetChanged();

                                    } else {
                                        noDataView.setVisibility(View.VISIBLE);
                                        noNetworkView.setVisibility(View.GONE);
                                    }


                                } else {
                                    noDataView.setVisibility(View.VISIBLE);
                                    noNetworkView.setVisibility(View.GONE);
                                    ApiFintechUtilMethods.INSTANCE.Error(getActivity(), data.getMsg() + "");
                                }

                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                            }
                        } else {
                            noDataView.setVisibility(View.VISIBLE);
                            noNetworkView.setVisibility(View.GONE);
                            ApiFintechUtilMethods.INSTANCE.apiErrorHandle(getActivity(), response.code(), response.message());
                        }
                        loader.setVisibility(View.GONE);
                    } catch (Exception e) {
                        loader.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    loader.setVisibility(View.GONE);
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                noDataView.setVisibility(View.GONE);
                                noNetworkView.setVisibility(View.VISIBLE);
                                ApiFintechUtilMethods.INSTANCE.NetworkError(getActivity());
                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(getActivity(), "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                noNetworkView.setVisibility(View.GONE);
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    ApiFintechUtilMethods.INSTANCE.ErrorWithTitle(getActivity(), "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    ApiFintechUtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                                }
                            }
                        }
                    } catch (IllegalStateException ise) {
                        loader.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                        noNetworkView.setVisibility(View.GONE);
                        ApiFintechUtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
            noNetworkView.setVisibility(View.GONE);
            ApiFintechUtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
        }

    }


    public void CollectionFromFosActivity(UserList mItem) {
        Intent intent = new Intent(getActivity(), FosCollectionActivity.class);
        intent.putExtra("id", mItem.getId());
        intent.putExtra("mobile", mItem.getMobileNo());
        intent.putExtra("outletName", mItem.getOutletName());
        startActivityForResult(intent, INTENT_COLLECTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_COLLECTION && resultCode == RESULT_OK) {
            appUserListApi();
        }
    }
}
